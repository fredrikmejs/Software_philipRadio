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
        context.ui.turnOnTextBlink();
        mAlarmTime = context.getAlarm1();
        if (null == mAlarmTime){
            //Nødt til at give den en værdi, for at undgå at få en nullpointer exception
            mAlarmTime = new Date(2019,1,1,0,0);
            context.setAlarm1(mAlarmTime);
            context.updateDisplayAlarm1();
        }else context.updateDisplayAlarm1();
    }

    @Override
    public void onExitState(ContextClockradio context) {
    context.ui.turnOffTextBlink();
    }

    @Override
    public void onClick_Hour(ContextClockradio context) {
        //Tilføjere en time til tiden
        mAlarmTime.setTime(mAlarmTime.getTime() + 3600000);
        context.setAlarm1(mAlarmTime);
        context.updateDisplayAlarm1();
        }

    @Override
    public void onClick_Min(ContextClockradio context) {
        //Tilføjer et minut til tiden
        mAlarmTime.setTime(mAlarmTime.getTime() + 60000);
        context.setAlarm1(mAlarmTime);
        context.updateDisplayAlarm1();
    }

    @Override
    public void onClick_AL1(ContextClockradio context) {
        context.setAlarm1(mAlarmTime);
        context.setState(new StateStandby(context.getTime()));
    }



}
