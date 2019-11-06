package dk.dtu.philipsclockradio.Radio;


import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateSetfrequency extends StateAdapter {

    double mfrequency, mode = 1, AMRadio = 0, FMRadio = 0;

    StateSetfrequency() { }

    @Override
    public void onEnterState(ContextClockradio context){
        context.ui.toggleRadioPlaying();
        mfrequency = context.getFrequency();



    }

    @Override
    public void onExitState(ContextClockradio context){
        //Der er en fejl med LED'en eneste måde at kontrollere nr 1 på.
        context.ui.turnOffLED(1);
        context.ui.turnOffLED(0);

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
        context.setState(new StateStandby(context.getTime()));

    }




}
