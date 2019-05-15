package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RegisterEmployeeActivity extends AppCompatActivity {

    Button btn_register,btn_calendar;

    EditText txtName,txtLastName,txtAddress,txtEmail,txtCC,txtTel;
    TextView txtDateOfBirth;

    RadioGroup rdSex;
    String sexSelected="";
    RadioButton rdAcceptTermsCond;

    ListView listViewServices;
    ArrayList listServices = new ArrayList();

    CheckBox cb,cb0,cb1,cb2,cb3,cb4,cb5,cb6,cb7;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);


        txtName=findViewById(R.id.txtName);
        txtLastName=findViewById(R.id.txtLastNames);
        txtAddress=findViewById(R.id.txtAdress);
        txtEmail=findViewById(R.id.txtEmail);
        txtDateOfBirth=findViewById(R.id.txtDateOfBirth);
        txtDateOfBirth.setText(getIntent().getStringExtra("date"));
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterEmployeeActivity.this,CalendarEmpRegActivity.class);
                startActivity(i);
            }
        });
        txtCC=findViewById(R.id.txtCC);
        txtTel=findViewById(R.id.txtTel);



        rdSex= (RadioGroup) findViewById(R.id.rdSex);
        rdSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.rdFemale){
                    sexSelected="F";

                }else if(checkedId==R.id.rdMale){
                    sexSelected="M";

                }
            }
        });
        rdAcceptTermsCond= (RadioButton)findViewById(R.id.rdAcceptTermsCond);
        cb= findViewById(R.id.cb);
        //TODO - checkboxes


        btn_register=findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.getText().equals("") || txtLastName.getText().equals("")
                        || txtAddress.getText().equals("")
                        || txtCC.getText().equals("")
                        || txtEmail.getText().equals("")
                        || txtTel.getText().equals("")){
                        validacion();
                }else{

                    //TODO - registro en el firebase

                }
            }
        });





    }

    /**
     * Metodo para listar los servicios en la listView
     */
    private void listServicesInListView() {


            //databaseReference.child("Servicios").addValueEventListener(new ValueEventListener() {
                //@Override
              //  public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //listServices.clear();
                    //for(DataSnapshot objSnapshot: dataSnapshot.getChildren()){
                      //  Service p= objSnapshot.getValue(Service.class);
                        //listServices.add(p);
                        //arrayAdapterSuperheroe =new ArrayAdapter<Superheroe>(MainActivity.this,
                         //       android.R.layout.simple_list_item_1, listSuper);
                        //listServicesInListView.setAdapter(arrayAdapterSuperheroe);
                    //}
                //}

                //@Override
               // public void onCancelled(@NonNull DatabaseError databaseError) {

                //}
            //});

    }

    public void validacion(){
        String name= txtName.getText().toString();
        String lastName= txtLastName.getText().toString();
        String address= txtAddress.getText().toString();
        String birth= txtDateOfBirth.getText().toString();
        String genre=sexSelected;
        String email= txtEmail.getText().toString();
        String tel= txtTel.getText().toString();


        if(name.equals("")){
            txtName.setError("Required");
        }
        if(lastName.equals("")){
            txtLastName.setError("Required");
        }
        if(address.equals("")){
            txtName.setError("Required");
        }
        if(birth.equals("")){
            txtName.setError("Required");
        }
        if(genre.equals("")){
            txtName.setError("Required");
        }
        if(email.equals("")){
            txtName.setError("Required");
        }
        if(tel.equals("")){
            txtName.setError("Required");
        }
        if(name.equals("")){
            txtName.setError("Required");
        }

    }
}
