package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.model.Usuario;

public class ServiciosActivity extends AppCompatActivity {

    CheckBox cb,cb0,cb1,cb2,cb3,cb4,cb5,cb6,cb7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        Bundle bundle = getIntent().getExtras();
        if (bundle!=null){

        }

        cb= findViewById(R.id.cb);
        cb0 = findViewById(R.id.cb0);
        cb1 = findViewById(R.id.cb1);
        cb2 = findViewById(R.id.cb2);
        cb3 = findViewById(R.id.cb3);
        cb4 = findViewById(R.id.cb4);
        cb5 = findViewById(R.id.cb5);
        cb6 = findViewById(R.id.cb6);
        cb7 = findViewById(R.id.cb7);
        //TODO - checkboxes
    }

}
