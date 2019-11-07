package dk.dtu.philipsclockradio.Alarm;

import java.util.Date;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateAlarmAL2 extends StateAdapter {

    private Date mAlarmTime;

    public StateAlarmAL2(){}


    @Override
    public void onEnterState(ContextClockradio context) {
        context.ui.turnOnTextBlink();
        mAlarmTime = context.getAlarm2();
        if (null == mAlarmTime){
            //Sætter tidspunktet til at være 00:00, når man skal indstille en alarm.
            mAlarmTime = new Date(2019,1,1,0,0);
            context.setAlarm2(mAlarmTime);
            context.updateDisplayAlarm2();
        }else context.updateDisplayAlarm2();
    }

    @Override
    public void onExitState(ContextClockradio context) {
    context.ui.turnOffTextBlink();
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        mAlarmTime.setTime(mAlarmTime.getTime() + 3600000);
        context.setAlarm2(mAlarmTime);
        context.updateDisplayAlarm2();;
    }

    @Override
    public void onClick_Min(ContextClockradio context) {
        mAlarmTime.setTime(mAlarmTime.getTime() + 60000);
        context.setAlarm2(mAlarmTime);
        context.updateDisplayAlarm2();
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        context.setAlarm2(mAlarmTime);
        context.setState(new StateStandby(context.getTime()));
    }



}

