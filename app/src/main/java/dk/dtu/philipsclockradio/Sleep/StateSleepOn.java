package dk.dtu.philipsclockradio.Sleep;

import android.os.Handler;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateSleepOn extends StateAdapter {
    private int mSleep, index;
    private long exitTime;
    private ContextClockradio mcontext;

    public StateSleepOn(){}

    private Handler mhandler = new Handler();

    private int[] sleepArr = {120, 90, 60, 30, 15, 0};



    @Override
    public void onEnterState(ContextClockradio context) {
        context.updateDisplaySleep();


        if (context.getSleepValue() == 0){
            context.ui.turnOffLED(3);
        } else context.ui.turnOnLED(3);
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

    //Handler som køre i baggrunden, så når, der er gået 5 sekunder  går den tilbage til standby
    Runnable mExitSleep = new Runnable() {

        @Override
        public void run() {
            long current = System.currentTimeMillis();
            long delay = exitTime - current;
            if (current >= exitTime){
                mhandler.removeCallbacks(mExitSleep);
                mcontext.setState(new StateStandby(mcontext.getTime()));
            } else mhandler.postDelayed(mExitSleep, delay);
        }
    };

    private void exitSleep(){

        exitTime = System.currentTimeMillis() + 5000;

    }


}
