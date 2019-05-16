package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.icesi.proyectoappsmoviles.DatePickerFragment;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.ServiciosActivity;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class RegisterEmployeeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btn_next,btn_calendar;

    EditText txtName,txtLastName,txtAddress,txtEmail,txtCC,txtTel;
    TextView txtDateOfBirth;

    RadioGroup rdSex;
    String sexSelected="";
    RadioButton rdAcceptTermsCond;

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
        btn_calendar= findViewById(R.id.btn_calendar);
        btn_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
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


        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
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
                    Intent i= new Intent(RegisterEmployeeActivity.this,ServiciosActivity.class);
                    Usuario u= new Usuario();
                    u.setNombres(txtName.getText().toString());
                    u.setApellidos(txtLastName.getText().toString());
                    u.setCedula(txtCC.getText().toString());
                    //u.setUbicacion(txtAddress.getText().toString());
                    u.setCorreo(txtEmail.getText().toString());
                    u.setTelefono(txtTel.getText().toString());
                    //i.putExtra("userToCreate",u);
                    startActivity(i);


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
            txtAddress.setError("Required");
        }
        if(birth.equals("")){
            txtDateOfBirth.setError("Required");
        }
        if(genre.equals("")){
            //rdSex.setError("Required");
        }
        if(email.equals("")){
            txtEmail.setError("Required");
        }
        if(tel.equals("")){
            txtName.setError("Required");
        }
        if(name.equals("")){
            txtName.setError("Required");
        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar c= Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        txtDateOfBirth.setText(currentDateString);

    }
}
