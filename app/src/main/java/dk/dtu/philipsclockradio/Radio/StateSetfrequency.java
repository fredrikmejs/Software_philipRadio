package dk.dtu.philipsclockradio.Radio;


import java.util.Date;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;
import dk.dtu.philipsclockradio.Unmuted.StateAlarmPlaying;

public class StateSetfrequency extends StateAdapter {

    private double mfrequency, AMRadio = 0, FMRadio = 0;
    int mode = 1;
    Date alarmTime1,alarmTime2,snoozeTime1,snoozeTime2;


    StateSetfrequency() { }

    @Override
    public void onEnterState(ContextClockradio context){
        context.ui.toggleRadioPlaying();
        mfrequency = context.getFrequency();
        FMRadio = mfrequency;
        if (context.getAlarm1() != null)
            alarmTime1 = context.getAlarm1();

        if (context.getAlarm2() != null)
            alarmTime2 = context.getAlarm2();

        if (context.getSnoozeTime1() != null)
            snoozeTime1 = context.getSnoozeTime1();

        if (context.getSnoozeTime2() != null)
            snoozeTime2 = context.getSnoozeTime2();


    }


    @Override
    public void onExitState(ContextClockradio context){
        //Der er en fejl med LED'en eneste måde at kontrollere nr 1 på.
        context.ui.turnOffLED(1);
        context.ui.toggleRadioPlaying();

    }


    @Override
    public void onClick_Hour(ContextClockradio context) {

        //skifter fra den laveste til den højeste frekvens
        if (mode == 2) {
            if (mfrequency == 0.153) {
                mfrequency = 30;
                AMRadio = mfrequency;
                context.setFrequency(mfrequency);
            } else{
                mfrequency -= 1.3;
                AMRadio = mfrequency;
                context.setFrequency(mfrequency);
            }

            if (mfrequency <= 1){
                mfrequency = 0.153;
                AMRadio = mfrequency;
                context.setFrequency(mfrequency);
            }
        }

        if (mode == 1){
            if (mfrequency <= 90){
                mfrequency = 108;
                FMRadio = mfrequency;
                context.setFrequency(mfrequency);
            } else {
                mfrequency -= 1.3;
                FMRadio = mfrequency;
                context.setFrequency(mfrequency);
            }

        }

        context.updateDisplayFrequency();
    }

    @Override
    public void onClick_Min(ContextClockradio context) {

        //skifter fra den laveste til den højeste frekvens
        if (mode == 2) {
            if (mfrequency >= 28.7) {
                mfrequency = 0.153;
                AMRadio = mfrequency;
                context.setFrequency(mfrequency);
            } else{
                mfrequency += 1.3;
                AMRadio = mfrequency;
                context.setFrequency(mfrequency);
            }

        }

        if (mode == 1){
            if (mfrequency >= 105){
                mfrequency = 87.5;
                FMRadio = mfrequency;
                context.setFrequency(mfrequency);
            } else{
                mfrequency += 1.5;
                FMRadio = mfrequency;
                context.setFrequency(mfrequency);
        }

    }

        context.updateDisplayFrequency();
    }

    @Override
    public void onClick_Power(ContextClockradio context) {

    if (mode == 2){
        mode = 1;
    } else mode++;

    if (mode == 1){
        if (FMRadio != 0){
            mfrequency = FMRadio;
            context.setFrequency(mfrequency);
        } else{
            mfrequency = 95.3;
            context.setFrequency(mfrequency);

        }

    } else if(mode == 2){
        if (AMRadio != 0){
            mfrequency = AMRadio;
            context.setFrequency(mfrequency);
        } else {
            mfrequency = 15.5;
            context.setFrequency(mfrequency);
        }

    }
        context.updateDisplayFrequency();
    }

    @Override
    public void onLongClick_Power(ContextClockradio context) {
        context.stateRadio = false;
        context.setMode(mode);
        context.setState(new StateStandby(context.getTime()));

    }

    @Override
    public void onLongClick_Preset(ContextClockradio context) {
        context.ui.turnOnTextBlink();

        context.setFrequency(mfrequency);
        context.setState(new StateFrequencySaving());
    }


    @Override
    public void onClick_Snooze(ContextClockradio context) {
        alarmTime1 = context.getAlarm1();
        alarmTime2 = context.getAlarm2();
        context.ui.turnOffTextBlink();
        if (alarmTime1 != null) {
            snoozeTime1.setTime(alarmTime1.getTime() + (60000 * 9));
            context.setSnoozeTime1(snoozeTime1);
        }
        if (alarmTime2 != null){
            snoozeTime2.setTime(alarmTime2.getTime() + (60000 * 9));
            context.setSnoozeTime2(snoozeTime2);
        }
        context.setState(new StateStandby(context.getTime()));
    }
    @Override
    public void onClick_AL1(ContextClockradio context) {
        if (context.alarmPlaying)
            context.setState(new StateAlarmPlaying());
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        if (context.alarmPlaying)
            context.setState(new StateAlarmPlaying());
    }
}
