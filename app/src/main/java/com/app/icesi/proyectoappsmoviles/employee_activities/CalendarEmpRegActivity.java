package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CalendarView;

import com.app.icesi.proyectoappsmoviles.R;

public class CalendarEmpRegActivity extends AppCompatActivity {

    CalendarView calendarViewEmployee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_emp_reg);

        calendarViewEmployee= (CalendarView)findViewById(R.id.calendarViewEmployee);
        calendarViewEmployee.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange( CalendarView view, int year, int month, int dayOfMonth) {
                String date= dayOfMonth+"/"+month+"/"+year;
                Intent i= new Intent(CalendarEmpRegActivity.this,RegisterEmployeeActivity.class);
                i.putExtra("date",date);
                startActivity(i);
            }
        });
    }
}
