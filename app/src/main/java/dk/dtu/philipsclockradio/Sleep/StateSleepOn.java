package dk.dtu.philipsclockradio.Sleep;

import android.os.Handler;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateSleepOn extends StateAdapter {
    private int mSleep, index;
    long exitTime, delay, current;
    private ContextClockradio mcontext;

    StateSleepOn(){}

    private Handler mhandler = new Handler();

    private int sleepArr[] = {120,90,60,30,15,0};



    @Override
    public void onEnterState(ContextClockradio context) {
    exitSleep();
    this.mcontext = context;
    mExitSleep.run();
    mSleep = context.getSleepValue();


    }

    @Override
    public void onClick_Sleep(ContextClockradio context) {

        exitSleep();
        mSleep = sleepArr[index];

        if (mSleep == 0){
            context.ui.turnOffLED(3);
            context.setSleepValue(mSleep);
            context.updateDisplaySleep();
        } else{
            context.ui.turnOnLED(3);
            context.setSleepValue(mSleep);
            context.updateDisplaySleep();
        }

        if (index == sleepArr.length-1){
            index = 0;
        } else index++;
    }

    Runnable mExitSleep = new Runnable() {

        @Override
        public void run() {
            current = System.currentTimeMillis();
            delay = exitTime - current;
            if (current >= exitTime){
                mhandler.removeCallbacks(mExitSleep);
                mcontext.setState(new StateStandby(mcontext.getTime()));
            } else mhandler.postDelayed(mExitSleep,delay);
        }
    };

    private void exitSleep(){

        exitTime = System.currentTimeMillis() + 5000;

    }


}
