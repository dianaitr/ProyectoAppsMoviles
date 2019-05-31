package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.employee_activities.RegisterEmployeeActivity;

public class CalendarioCreacionBuscarServicio extends AppCompatActivity {
    CalendarView calendarViewCreacionBuscarServicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_creacion_buscar_servicio);
        calendarViewCreacionBuscarServicio= (CalendarView)findViewById(R.id.calendarViewEmployee);
        calendarViewCreacionBuscarServicio.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String date= dayOfMonth+"/"+month+"/"+year;
                Intent i= new Intent(CalendarioCreacionBuscarServicio.this, CreacionBuscarServicioActivity.class);
                i.putExtra("dateClient",date);
                startActivity(i);
                finish();
            }
        });

    }
}
