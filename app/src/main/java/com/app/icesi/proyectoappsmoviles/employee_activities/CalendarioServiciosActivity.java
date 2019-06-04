package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.app.icesi.proyectoappsmoviles.R;

public class CalendarioServiciosActivity extends AppCompatActivity {
    CalendarView cvServicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_servicios);

        cvServicio= (CalendarView)findViewById(R.id.calendario_Servicio);
        cvServicio.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String date= dayOfMonth+"/"+month+"/"+year;
                Intent i= new Intent(CalendarioServiciosActivity.this, ServiciosActivity.class);
                i.putExtra("date",date);
                startActivity(i);
            }
        });

    }
}
