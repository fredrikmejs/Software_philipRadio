package dk.dtu.philipsclockradio.Radio;

import java.util.ArrayList;

import dk.dtu.philipsclockradio.ContextClockradio;
import dk.dtu.philipsclockradio.StateAdapter;

public class StateFrequencySaving extends StateAdapter {


    private int mode, index = 0;
    private ArrayList<Double> radioChannelsFM = new ArrayList<>();
    private ArrayList<Double> radioChannelsAM = new ArrayList<>();
    private double mfrequency;

    @Override
    public void onClick_Preset(ContextClockradio context){
        context.ui.turnOffTextBlink();
        context.updateDisplaySavedFrequency(context.getFrequency());

        context.setState(new StateSetfrequency());
    }

    @Override
    public void onLongClick_Preset(ContextClockradio context){
        //Går igennem arrayet og finder alle favoritterne
        if (mode == 1){
            //Henter alle de gemte frekvenser og sikre mig mod gentagelser
            radioChannelsFM.removeAll(radioChannelsFM);
            radioChannelsFM.addAll(context.getSaveFrequencyFM());

            mfrequency = radioChannelsFM.get(index);
            context.updateDisplaySavedFrequency(radioChannelsFM.get(index));


            if (index == radioChannelsFM.size() - 1) {
                index = 0;
            } else index++;

        }else if (mode == 2){
            //Henter alle de gemte frekvenser og sikre mig mod gentagelser
            radioChannelsAM.removeAll(radioChannelsAM);
            radioChannelsAM.addAll(context.getSaveFrequencyAM());

            mfrequency = radioChannelsAM.get(index);
            context.updateDisplaySavedFrequency(radioChannelsAM.get(index));


            if (index == radioChannelsAM.size() - 1) {
                index = 0;
            } else index++;
        }
    }


    @Override
    public void onEnterState(ContextClockradio context) {
        context.ui.turnOnTextBlink();
        mode = context.getMode();
        mfrequency = context.getFrequency();
        if (mode == 0){
            mode = 1;
        }

        if (mode == 1) {
            //Sikre mig mod gentagelser
            if (!radioChannelsFM.contains(mfrequency) && mfrequency != 0) {
                radioChannelsFM.add(mfrequency);
                context.SetSaveFrequencyFM(mfrequency);
            }
        }else if (mode == 2){
            //Sikre mig mod gentagelser
            if (!radioChannelsAM.contains(mfrequency) && mfrequency != 0) {
                radioChannelsAM.add(mfrequency);
                context.SetSaveFrequencyAM(mfrequency);
            }
        }
    }
}
