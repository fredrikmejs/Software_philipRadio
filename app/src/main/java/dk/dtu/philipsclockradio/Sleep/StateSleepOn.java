package dk.dtu.philipsclockradio.Sleep;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;

public class StateSleepOn extends StateAdapter {
    private int mSleep, index;

    StateSleepOn(){}


    int sleepArr[] = {120,90,60,30,15,0};
    int sleepValue;


    @Override
    public void onEnterState(ContextClockradio context) {
    mSleep = context.getSleepValue();

    }

    @Override
    public void onExitState(ContextClockradio context) {
    context.ui.turnOffLED(3);

    }

    @Override
    public void onClick_Sleep(ContextClockradio context) {

        mSleep = sleepArr[index];

        if (mSleep == 0){
            context.setSleepValue(mSleep);
            context.updateDisplaySleep();
        }



        index++;


    }
}
