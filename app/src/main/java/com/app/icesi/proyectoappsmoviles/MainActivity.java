package com.app.icesi.proyectoappsmoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

//Esta clase pertenece a la primera vista en donde se escoge el usuario para iniciar la app.
public class MainActivity extends AppCompatActivity {


    private Button btn_employee,btn_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_employee=findViewById(R.id.btn_employee);
        btn_employee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        btn_client=findViewById(R.id.btn_client);
        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }
}
