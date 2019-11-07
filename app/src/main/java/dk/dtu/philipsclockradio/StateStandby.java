package dk.dtu.philipsclockradio;

import android.os.Handler;
import java.util.Date;

import dk.dtu.philipsclockradio.Alarm.StateAlarmAL1;
import dk.dtu.philipsclockradio.Alarm.StateAlarmAL2;
import dk.dtu.philipsclockradio.Radio.StateRadio;
import dk.dtu.philipsclockradio.Sleep.StateSleepOn;

public class StateStandby extends StateAdapter {

    private Date mTime, alarmTime1, alarmTime2, snoozeTime1, snoozeTime2;
    private static Handler mHandler = new Handler();
    private static Handler aHandler = new Handler();
    private Date defaultDate = new Date(2019,1,1,0,0);
    private ContextClockradio mContext;
    private long delay = 10000;
    private boolean stateRadio = false, alarmRunning =  false;

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
                mHandler.postDelayed(mSetTime, 60000);
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

    Runnable startAlarm = new Runnable() {

        @Override
        public void run() {
            mTime = mContext.getTime();
            if (alarmTime1 != null){
                if (mTime.getHours() == alarmTime1.getHours() && mTime.getMinutes()== alarmTime1.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    mContext.setState(new StateSleepOn());
                }
            } else if (alarmTime2 != null) {
                if (mTime.getHours() == alarmTime2.getHours() && mTime.getMinutes() == alarmTime2.getMinutes()) {
                    aHandler.removeCallbacks(startAlarm);
                    mContext.setState(new StateSleepOn());
                   }
                }else aHandler.postDelayed(startAlarm,delay);
            }
        };

    void stopAlarm() {
        aHandler.removeCallbacks(mSetTime);
    }

    @Override
    public void  onClick_AL1(ContextClockradio context){

        alarmTime1 = context.getAlarm1();
        alarmTime2 = context.getAlarm2();

        if (alarmTime1 != null || alarmTime2 != null) {
            context.ui.turnOnLED(2);
            if (!alarmRunning) {
                startAlarm.run();
                alarmRunning = true;
            } else {
                alarmRunning = false;
                stopAlarm();
            }

        }
    }

    @Override
    public void  onClick_AL2(ContextClockradio context){


        alarmTime1 = context.getAlarm1();
        alarmTime2 = context.getAlarm2();

        if (alarmTime1 != null || alarmTime2 != null) {
            context.ui.turnOnLED(5);
            //Date aa = new Date(2019,1,1,14,3);
            //alarmTime2.setTime(aa.getTime());

            if (!alarmRunning) {
                startAlarm.run();
                alarmRunning = true;
            } else {
                alarmRunning = false;
                stopAlarm();
            }
        }
    }

    @Override
    public void onClick_Snooze(ContextClockradio context){
        alarmTime1 = context.getAlarm1();
        alarmTime2 = context.getAlarm2();

        if (alarmTime1 != null)
            snoozeTime1.setTime(alarmTime1.getTime() + (60000*9));

        if (alarmTime2 != null)
            snoozeTime2.setTime(alarmTime2.getTime() + (60000*9));
        if (!alarmRunning){
            startAlarm.run();
        }
    }
}
