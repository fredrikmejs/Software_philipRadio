package dk.dtu.philipsclockradio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import dk.dtu.philipsclockradio.Radio.StateRadio;
import dk.dtu.philipsclockradio.Unmuted.StateAlarmPlaying;

//
public class ContextClockradio {
    private State currentState;
    private Date mTime, alarm1, alarm2;
    private String mDisplayText;
    public boolean isClockRunning = false;
    private double frequency;
    Date snoozeTime1, snoozeTime2;
    public boolean stateRadio = false, alarmPlaying = false;
    private int sleepValue, mode;
    private ArrayList<Double> radioChannelsFM = new ArrayList<>(), radioChannelsAM = new ArrayList<>();

    public static MainUI ui;

    public ContextClockradio(MainUI context){
        ui = context;

        //Sætter tiden til 12.00, hvis tiden ikke er sat endnu
        if(mTime == null){
            Calendar date = Calendar.getInstance();
            date.set(2019, 1, 1, 2, 1);
            mTime = date.getTime();
        }

        //Når app'en starter, så går vi ind i Standby State
        currentState = new StateStandby(mTime);
        currentState.onEnterState(this);
    }

    //setState er når vi skifter State
    public  void setState(final State newState) {
        currentState.onExitState(this);
        currentState = newState;
        currentState.onEnterState(this);
        System.out.println("Current state: "+ newState.getClass().getSimpleName());
    }

    //Opdaterer kontekst time state og UI
    public void setTime(Date time){
        mTime = time;
        if(currentState.getClass().getSimpleName().equals("StateStandby")){
            updateDisplayTime();
        }
    }

    //Gettere og settere

    public int getMode(){return mode;}
    public void setMode(int mode){this.mode = mode;}
    public int getSleepValue(){
        return sleepValue;
        }

    public void setSleepValue(int sleep){ sleepValue = sleep;}


    public ArrayList<Double> getSaveFrequencyFM(){ return radioChannelsFM;}

    public void SetSaveFrequencyFM(double frequency){ radioChannelsFM.add(frequency);}

    public ArrayList<Double> getSaveFrequencyAM(){ return radioChannelsAM;}

    public void SetSaveFrequencyAM(double frequency){ radioChannelsAM.add(frequency);}

    public void setFrequency(double RadioFrequency){
        frequency = RadioFrequency;
        if (currentState.getClass().getSimpleName().equals("StateRadio")){
            updateDisplayFrequency();
        }
    }

    public void setSnoozeTime1(Date date){snoozeTime1 = date;}
    public Date getSnoozeTime1(){return snoozeTime1;}

    public void setSnoozeTime2(Date date){snoozeTime2 = date;}
    public Date getSnoozeTime2(){return snoozeTime2;}


    //Update UI
    public void updateDisplayAlarm1(){
        mDisplayText = alarm1.toString().substring(11,16);
        ui.setDisplayText(mDisplayText);
    }

    public void updateDisplayAlarm2(){
        mDisplayText = alarm2.toString().substring(11,16);
        ui.setDisplayText(mDisplayText);
    }

    public void updateDisplayFrequency(){
        mDisplayText = String.valueOf(frequency);
        ui.setDisplayText(mDisplayText);
    }

    public void updateDisplaySavedFrequency(double frequency){
        mDisplayText = String.valueOf(frequency);
        ui.setDisplayText(mDisplayText);
    }

    public void updateDisplayTime(){
        mDisplayText = mTime.toString().substring(11,16);
        ui.setDisplayText(mDisplayText);
    }

    public void updateDisplaySleep(){
        if (sleepValue == 0){
            mDisplayText = "OFF";
        }else mDisplayText = String.valueOf(sleepValue);

        ui.setDisplayText(mDisplayText);
    }

    public Date getAlarm1(){
        return alarm1;
    }

    public Date getAlarm2(){
        return alarm2;
    }

    public void setAlarm1(Date date){
        alarm1 = date;
    }

    public void setAlarm2(Date date){
        alarm2 = date;
    }

    public Date getTime(){
        return mTime;
    }

    public double getFrequency() {return frequency;}


    //Disse metoder bliver kaldt fra UI tråden
    public void onClick_Hour() {


        currentState.onClick_Hour(this);
    }

    public void onClick_Min() {

        currentState.onClick_Min(this);
    }

    public void onClick_Preset() {

        currentState.onClick_Preset(this);
    }

    public void onClick_Power() {

        currentState.onClick_Power(this);
        }

    public void onClick_Sleep() {
        currentState.onClick_Sleep(this);
    }
    public void onClick_AL2() {
        currentState.onClick_AL2(this);
    }

    public void onClick_AL1() {
        currentState.onClick_AL1(this);
    }


    public void onClick_Snooze() {
        currentState.onClick_Snooze(this);
    }

    public void onLongClick_Hour(){

        currentState.onLongClick_Hour(this);
    }

    public void onLongClick_Min(){
        currentState.onLongClick_Min(this);
    }

    public void onLongClick_Preset(){

        currentState.onLongClick_Preset(this);
    }

    public void onLongClick_Power(){
        currentState.onLongClick_Power(this);
    }

    public void onLongClick_Sleep(){
        currentState.onLongClick_Sleep(this);
    }

    public void onLongClick_AL1(){
        currentState.onLongClick_AL1(this);
    }

    public void onLongClick_AL2(){
        currentState.onLongClick_AL2(this);
    }

    public void onLongClick_Snooze(){
        currentState.onLongClick_Snooze(this);
    }
}