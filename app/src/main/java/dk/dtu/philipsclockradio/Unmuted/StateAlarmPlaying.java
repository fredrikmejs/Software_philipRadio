package dk.dtu.philipsclockradio.Unmuted;


import java.util.Date;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;


public class StateAlarmPlaying extends StateAdapter {

    private Date alarmTime1,alarmTime2,snoozeTime1,snoozeTime2;
private ContextClockradio mContext;

    @Override
    public void onEnterState(ContextClockradio context) {
        context.ui.turnOnTextBlink();
        this.mContext = context;
        alarmTime1 = mContext.getAlarm1();

        alarmTime2 = mContext.getAlarm2();

        //Sikre mig den ikke pointer til null. -> nullpointer exceptions
        snoozeTime1 = alarmTime1;
        snoozeTime2 = alarmTime2;
    }

    @Override
    public void onExitState(ContextClockradio context) {
    context.ui.turnOffTextBlink();
    }

    @Override
    public void onClick_AL1(ContextClockradio context) {
        context.setState(new StateStandby(context.getTime()));
    }

    @Override
    public void onClick_AL2(ContextClockradio context) {
        context.setState(new StateStandby(context.getTime()));
    }


    //Tilføjer de 9 minutter, når der bliver trykket på snooze
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
}
