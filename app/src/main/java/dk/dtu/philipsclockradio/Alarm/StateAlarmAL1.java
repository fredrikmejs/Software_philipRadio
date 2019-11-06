package dk.dtu.philipsclockradio.Alarm;

import java.util.Date;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateAlarmAL1 extends StateAdapter {

    private Date mAlarmTime;

    public StateAlarmAL1(){}


    @Override
    public void onEnterState(ContextClockradio context) {
        context.ui.turnOffTextBlink();
        context.setTime(context.getTime());
        mAlarmTime = context.getAlarm1();
        if (null == mAlarmTime){
            //To go around a nullpointer, had to give mAlarm a value
            mAlarmTime = context.getTime();
            mAlarmTime.setTime(mAlarmTime.getTime() + 3600000);
            context.setAlarm1(mAlarmTime);
            context.updateDisplayAlarm1();
        }else context.updateDisplayAlarm1();
    }

    @Override
    public void onExitState(ContextClockradio context) {

    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        mAlarmTime.setTime(mAlarmTime.getTime() + 3600000);
        context.setAlarm1(mAlarmTime);
        context.updateDisplayAlarm1();
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        mAlarmTime.setTime(mAlarmTime.getTime() + 60000);
        context.setAlarm1(mAlarmTime);
        context.updateDisplayAlarm1();
    }

    @Override
    public void onClick_AL1(ContextClockradio context) {
        context.ui.turnOnLED(2);
        context.setAlarm1(mAlarmTime);
        context.setState(new StateStandby(context.getTime()));
    }



}
