package dk.dtu.philipsclockradio.Sleep;

import dk.dtu.philipsclockradio.ContextClockradio;

import dk.dtu.philipsclockradio.StateAdapter;

public class StateSleep extends StateAdapter {


    @Override
    public void onEnterState(ContextClockradio context) {
        context.ui.turnOnLED(3);
        context.setState(new StateSleepOn());

    }


    @Override
    public void onClick_Sleep(ContextClockradio context) {
        super.onClick_Sleep(context);
    }
}