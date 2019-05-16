package com.app.icesi.proyectoappsmoviles.client_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.employee_activities.CalendarEmpRegActivity;
import com.app.icesi.proyectoappsmoviles.employee_activities.RegisterEmployeeActivity;

public class CalendarClientRegActivity extends AppCompatActivity {

    CalendarView calendarViewClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_client_reg);

        calendarViewClient= (CalendarView)findViewById(R.id.calendarViewEmployee);
        calendarViewClient.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String date= dayOfMonth+"/"+month+"/"+year;
                Intent i= new Intent(CalendarClientRegActivity.this,RegisterEmployeeActivity.class);
                i.putExtra("dateClient",date);
                startActivity(i);
            }
        });

    }
}
