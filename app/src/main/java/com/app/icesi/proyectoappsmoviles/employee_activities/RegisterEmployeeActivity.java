package com.app.icesi.proyectoappsmoviles.employee_activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.icesi.proyectoappsmoviles.DatePickerFragment;
import com.app.icesi.proyectoappsmoviles.LoginActivity;
import com.app.icesi.proyectoappsmoviles.R;
import com.app.icesi.proyectoappsmoviles.client_activities.PerfilClienteActivity;
import com.app.icesi.proyectoappsmoviles.model.Usuario;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RegisterEmployeeActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Button btn_next,btn_calendar;

    EditText txtName,txtLastName,txtEmail,txtCC,txtTel, txtPassword, txtRePassword;
    TextView txtDateOfBirth,txtAddress;
    ImageButton btn_ubicacion;

    RadioGroup rdSex;
    String sexSelected="";
    CheckBox cbAcceptTermsCond;

    FirebaseAuth auth;
    FirebaseDatabase rtdb;

    String userType;

    private static final int REQUEST_CODE = 11;
    private LocationManager locationManager;
    private Location myLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_employee);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_CODE);

        userType=getIntent().getExtras().getString("userType");

        auth = FirebaseAuth.getInstance();
        rtdb = FirebaseDatabase.getInstance();

        txtName=findViewById(R.id.txtName);
        txtLastName=findViewById(R.id.txtLastNames);
        txtAddress=findViewById(R.id.txtAdress);
        txtEmail=findViewById(R.id.txtEmail);
        txtPassword=findViewById(R.id.txtPassword_Empleado);
        txtRePassword=findViewById(R.id.txt_Re_Password_Empleado);
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

        btn_ubicacion=findViewById(R.id.btn_ubicacion);
        btn_ubicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(RegisterEmployeeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(RegisterEmployeeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.

                    Toast.makeText(RegisterEmployeeActivity.this, "Not Enough Permission", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String provider = "";
                    if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) provider=LocationManager.NETWORK_PROVIDER;
                    else if  (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) provider= LocationManager.GPS_PROVIDER;
                    if (provider!=""){
                        Toast.makeText(RegisterEmployeeActivity.this, "Obteniendo dirección GPS...", Toast.LENGTH_LONG).show();
                        locationManager.requestLocationUpdates(provider, 10, 10, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {
                                myLocation = location;
                                txtAddress.setText(getAddress(myLocation));
                            }

                            @Override
                            public void onStatusChanged(String provider, int status, Bundle extras) {

                            }

                            @Override
                            public void onProviderEnabled(String provider) {

                            }

                            @Override
                            public void onProviderDisabled(String provider) {

                            }
                        });
                    } else {
                        Toast.makeText(RegisterEmployeeActivity.this, "Por favor enciende el GPS", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

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
        cbAcceptTermsCond= (CheckBox) findViewById(R.id.cbAcceptTermsCond);

        btn_next=findViewById(R.id.btn_next);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtName.getText().equals("") || txtLastName.getText().equals("")
                        || txtAddress.getText().equals("")
                        || txtCC.getText().equals("")
                        || txtEmail.getText().equals("")
                        || txtTel.getText().equals("")
                        || txtPassword.getText().equals("") || txtRePassword.getText().equals("")){
                        validacion();
                }else{

                    auth.createUserWithEmailAndPassword(txtEmail.getText().toString().trim(), txtPassword.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            //TODO - registro en el firebase
                            Usuario usuario = new Usuario();
                            usuario.setUid(auth.getCurrentUser().getUid());
                            usuario.setNombres(txtName.getText().toString());
                            usuario.setApellidos(txtLastName.getText().toString());
                            usuario.setCedula(txtCC.getText().toString());
                            usuario.setCorreo(txtEmail.getText().toString());
                            usuario.setTelefono(txtTel.getText().toString());
                            usuario.setLatitude(myLocation.getLatitude());
                            usuario.setLongitude(myLocation.getLongitude());
                            usuario.setCalificacion(0);
                            usuario.setActivo(false);
                            usuario.setGenero(sexSelected);
                            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
                            Date date = null;
                            try {
                                date = format.parse(txtAddress.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            usuario.setFecha_nacimiento(date);

                            if(userType.equals("employee")){
                                rtdb.getReference().child("usuarios").child("colaboradores").child(auth.getCurrentUser().getUid()).setValue(usuario);
                                Intent i= new Intent(RegisterEmployeeActivity.this,ServiciosActivity.class);
                                startActivity(i);
                            }else{
                                rtdb.getReference().child("usuarios").child("clientes").child(auth.getCurrentUser().getUid()).setValue(usuario);
                                Intent i= new Intent(RegisterEmployeeActivity.this, PerfilClienteActivity.class);
                                i.putExtra("id", auth.getCurrentUser().getUid());
                                startActivity(i);
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(RegisterEmployeeActivity.this, "Hubo un error", Toast.LENGTH_SHORT).show();
                            Log.e(">>>", "registro problem: " + e.getMessage());

                        }
                    });




                }
            }
        });

    }


    public void validacion(){
        String name= txtName.getText().toString();
        String lastName= txtLastName.getText().toString();
        String address= txtAddress.getText().toString();
        String birth= txtDateOfBirth.getText().toString();
        String email= txtEmail.getText().toString();
        String tel= txtTel.getText().toString();
        String pass = txtPassword.getText().toString();
        String repass = txtRePassword.getText().toString();
        String cc = txtCC.getText().toString();

        if(name.equals("")){
            txtName.setError("Requerido");
        }
        if(lastName.equals("")){
            txtLastName.setError("Requerido");
        }
        if(address.equals("")){
            txtAddress.setError("Requerido");
        }
        if(birth.equals("")){
            txtDateOfBirth.setError("Requerido");
        }
        if(email.equals("")){
            txtEmail.setError("Requerido");
        }
        if(tel.equals("")){
            txtTel.setError("Requerido");
        }
        if(pass.equals("")){
            txtPassword.setError("Requerido");
        }
        if(repass.equals("")){
            txtRePassword.setError("Requerido");
        }
        if(cc.equals("")){
            txtCC.setError("Requerido");
        }
        if(!cbAcceptTermsCond.isChecked()){
            cbAcceptTermsCond.setError("Debes aceptar los términos y condiciones");
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

    public String getAddress(Location location) {
        String direccion = "";
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            Address address = addressList.get(0);
            direccion = address.getAddressLine(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return direccion;
    }
}
