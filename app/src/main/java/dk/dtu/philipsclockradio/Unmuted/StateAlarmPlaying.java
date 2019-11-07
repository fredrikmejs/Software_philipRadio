package dk.dtu.philipsclockradio.Unmuted;

import android.os.Handler;

import java.util.Date;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateAlarmPlaying extends StateAdapter {

    private Date currentTime, alarmTime1, alarmTime2;
    private ContextClockradio mcontext;
    private long delay = 60000;

    Handler mHandler = new Handler();

    @Override
    public void onEnterState(ContextClockradio context) {
        this.mcontext = context;


    }

    @Override
    public void onExitState(ContextClockradio context) {

    }

    @Override
    public void onClick_Power(ContextClockradio context) {

    }

    @Override
    public void onClick_AL1(ContextClockradio context) {

    }

    @Override
    public void onClick_AL2(ContextClockradio context) {

    }

    @Override
    public void onClick_Snooze(ContextClockradio context) {

    }



}
