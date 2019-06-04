package com.app.icesi.proyectoappsmoviles.client_activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;

public class DialogCalificarAEmpleado extends DialogFragment {

    private TextView mActionOk, mActionCancel, cal_num;
    private EditText mComments;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState ) {

        cal_num = getView().findViewById(R.id.cal_num);
        mComments = getView().findViewById(R.id.cal_comentarios);
        mActionOk = getView().findViewById(R.id.cal_btn_ok);
        mActionCancel = getView().findViewById(R.id.cal_btn_cancelar);

        mActionCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                getDialog().dismiss();
            }
        });

        return null;
    }
}
