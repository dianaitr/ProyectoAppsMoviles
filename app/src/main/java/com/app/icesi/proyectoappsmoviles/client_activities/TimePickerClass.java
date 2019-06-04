package com.app.icesi.proyectoappsmoviles.client_activities;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerClass extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

public interface TimePickerListener{
    void onTimeSet(TimePicker timePicker, int hour, int min);
}
TimePickerListener mListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener=(TimePickerListener) context;

        }catch (Exception e){
            throw new ClassCastException(getActivity().toString()+"Excepcion de la hora");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal=Calendar.getInstance();
        int hour=cal.get(Calendar.HOUR);
        int minute=cal.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(),this,hour,minute, DateFormat.is24HourFormat(getContext()));
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        mListener.onTimeSet(timePicker,i,i1);
    }
}
