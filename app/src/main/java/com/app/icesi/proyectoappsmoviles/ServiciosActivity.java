package com.app.icesi.proyectoappsmoviles;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class ServiciosActivity extends AppCompatActivity {

    CheckBox cb,cb0,cb1,cb2,cb3,cb4,cb5,cb6,cb7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

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



    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.cb:
                if (checked){

                }
                // Put some meat on the sandwich
                else

                    // Remove the meat
                    break;
            case R.id.cb1:
                if (checked){

                }
                // Cheese me
                else
                    // I'm lactose intolerant
                    break;
                // TODO: Veggie sandwich
        }
    }
}
