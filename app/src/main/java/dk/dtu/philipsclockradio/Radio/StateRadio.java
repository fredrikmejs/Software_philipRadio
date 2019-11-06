package dk.dtu.philipsclockradio.Radio;


import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;
import dk.dtu.philipsclockradio.StateStandby;

public class StateRadio extends StateAdapter {

    double mfrequency;

    public StateRadio(double frequency){ mfrequency = frequency;

    }
    @Override
    public void onEnterState(ContextClockradio context) {
        context.setFrequency(mfrequency);
        context.ui.turnOnLED(1);
        context.updateDisplayFrequency();
        context.setState(new StateSetfrequency());

    }

    @Override
    public void onLongClick_Power(ContextClockradio context) {
        context.setState(new StateStandby(context.getTime()));


    }



}