package dk.dtu.philipsclockradio;

import android.os.Handler;
import java.util.Date;

import dk.dtu.philipsclockradio.Alarm.StateAlarmAL1;
import dk.dtu.philipsclockradio.Alarm.StateAlarmAL2;
import dk.dtu.philipsclockradio.Radio.StateRadio;
import dk.dtu.philipsclockradio.Sleep.StateSleepOn;
import dk.dtu.philipsclockradio.Unmuted.StateAlarmPlaying;

public class StateStandby extends StateAdapter {

    private Date mTime, alarmTime1, alarmTime2, snoozeTime1, snoozeTime2;
    private static Handler mHandler = new Handler();
    private static Handler aHandler = new Handler();
    private ContextClockradio mContext;
    private long delay = 5000;
    private boolean stateRadio = false, alarmRunning =  false, alarm1Running = false,alarm2Running = false;

    public StateStandby(Date time){
        mTime = time;
    }

    //Opdaterer hvert 60. sekund med + 1 min til tiden
    Runnable mSetTime = new Runnable() {

        @Override
        public void run() {
            try {
                long currentTime = mTime.getTime();
                mTime.setTime(currentTime + 60000);
                mContext.setTime(mTime);
            } finally {
                mHandler.postDelayed(mSetTime,20000); //60000);
            }
        }
    };

    void startClock() {
        mSetTime.run();
        mContext.isClockRunning = true;
    }

    void stopClock() {
        mHandler.removeCallbacks(mSetTime);
        mContext.isClockRunning = false;
    }

    @Override
    public void onEnterState(ContextClockradio context) {
        //Lokal context oprettet for at Runnable kan få adgang
        mContext = context;
        context.updateDisplayTime();
        if(!context.isClockRunning){
            startClock();
        }
            alarmTime1 = mContext.getAlarm1();

            alarmTime2 = mContext.getAlarm2();

        //Sikre mig den ikke pointer til null. -> nullpointer exceptions
        snoozeTime1 = alarmTime1;
        snoozeTime2 = alarmTime2;
        context.setSnoozeTime1(snoozeTime1);
        context.setSnoozeTime2(snoozeTime2);

        //Stopper alarmen
        if (context.alarmPlaying){
            stopAlarm();
            context.ui.turnOffTextBlink();
            context.alarmPlaying = false;
        }
        if (!alarm1Running)
            context.ui.turnOffLED(2);
        if (!alarm2Running)
            context.ui.turnOffLED(5);

    }

    @Override
    public void onLongClick_Preset(ContextClockradio context) {
        stopClock();
        context.setState(new StateSetTime());
    }

    @Override
    public void onClick_Sleep(ContextClockradio context) {
        context.setState(new StateSleepOn());
    }

    @Override
    public void onLongClick_AL1(ContextClockradio context) {
        context.setState(new StateAlarmAL1());
    }

    @Override
    public void onLongClick_AL2(ContextClockradio context) {
        context.setState(new StateAlarmAL2());
    }

    @Override
    public void onClick_Power(ContextClockradio context) {
        if (context.getFrequency() == 0){
            context.setState(new StateRadio(100));
        }else if (!stateRadio) {
            context.setState(new StateRadio(context.getFrequency()));
            stateRadio = true;
        }
    }


    //Tråd som hele tiden tjekker, om alarmen skal starte
    private Runnable startAlarm = new Runnable() {

        @Override
        public void run() {
            mTime = mContext.getTime();
            snoozeTime1 = mContext.getSnoozeTime1();
            snoozeTime2 = mContext.getSnoozeTime2();

            if (alarmTime1 != null && alarm1Running) {
                if (mTime.getHours() == alarmTime1.getHours() && mTime.getMinutes() == alarmTime1.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    alarm1Running = false;
                    mContext.alarmPlaying = true;
                    mContext.setState(new StateAlarmPlaying());
                    return;
                }
            }
            if (alarmTime2 != null && alarm2Running) {
                if (mTime.getHours() == alarmTime2.getHours() && mTime.getMinutes() == alarmTime2.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    alarm2Running = false;
                    mContext.alarmPlaying = true;
                    mContext.setState(new StateAlarmPlaying());
                    return;
                }
            }
            if (snoozeTime1 != null) {
                if (mTime.getHours() == snoozeTime1.getHours() && mTime.getMinutes() == snoozeTime1.getMinutes()) {   //(mTime.getHours() == snoozeTime1.getHours() && snoozeTime1.getMinutes() == snoozeTime1.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    mContext.alarmPlaying = true;
                    mContext.setState(new StateAlarmPlaying());
                    return;
                }
            }
            if (snoozeTime2 != null) {
                if (mTime.getHours() == snoozeTime2.getHours() && mTime.getMinutes() == snoozeTime2.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    mContext.alarmPlaying = true;
                    mContext.setState(new StateAlarmPlaying());
                    return;
                }else aHandler.postDelayed(startAlarm, delay);
            } else aHandler.postDelayed(startAlarm, delay);
        }
    };

    //Stopper alarmen
    private void stopAlarm() {
        aHandler.removeCallbacks(mSetTime);
    }


    //Start alarmen
    @Override
    public void  onClick_AL1(ContextClockradio context){

        alarmTime1 = context.getAlarm1();



        if (alarmTime1 != null) {
            context.ui.turnOnLED(2);
            if (!alarmRunning) {
                alarmRunning = true;
                alarm1Running = true;
                startAlarm.run();
            } else if (!alarm2Running){
                alarmRunning = false;
                stopAlarm();
            }
        }
    }
    //Start alarmen
    @Override
    public void  onClick_AL2(ContextClockradio context){


        alarmTime2 = context.getAlarm2();

        if (alarmTime2 != null) {
            context.ui.turnOnLED(5);
            if (!alarmRunning) {
                alarmRunning = true;
                alarm2Running = true;
                startAlarm.run();
            } else if (!alarm1Running){
                alarmRunning = false;
                stopAlarm();
            }
        }
    }
}
