package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.app.icesi.proyectoappsmoviles.R;

public class RegisterEmployeeActivity extends AppCompatActivity {

    Button btn_register;

    EditText txtName,txtLastName,txtAddress,txtEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);
    }
}
