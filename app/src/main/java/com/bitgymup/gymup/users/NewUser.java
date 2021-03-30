package com.bitgymup.gymup.users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bitgymup.gymup.GymList;
import com.bitgymup.gymup.R;

import org.json.JSONObject;

import java.sql.Date;
import java.util.Calendar;

public class NewUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText etName, etSurname, etDocument, etAddress, etCity, etCountry, etPhone, etEmail, etMobile, etHeight, etWeight, etcUsername, etcPassword, etStatus, etComments, etBirthday, etGender;
    private String selectedGender, selectedDate, selected_spinner;
    private Button btnSubmit, dateButton;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner;

    ProgressDialog progreso;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;



    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        dateButton = findViewById(R.id.btnSelectDate);
        dateButton.setText(getTodaysDate());
        //Para elegir la fecha
        initDatePicker();

        //Code de los spinners para combo

        spinner = findViewById(R.id.spnGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genderList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        etName       = findViewById(R.id.etName);
        etSurname    = findViewById(R.id.etSurname);
        etDocument   = findViewById(R.id.etDocument);
        etHeight     = findViewById(R.id.etHeight);
        etWeight     = findViewById(R.id.etWeight);
        etcUsername  = findViewById(R.id.etcUsername);
        etcPassword  = findViewById(R.id.etcPassword);
        etEmail      = findViewById(R.id.etEmail);
        etMobile     = findViewById(R.id.etMobile);
        etCountry    = findViewById(R.id.etCountry);
        etPhone      = findViewById(R.id.etPhone);
        etAddress    = findViewById(R.id.etAddress);
        etCity       = findViewById(R.id.etCity);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        request = Volley.newRequestQueue(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                selected_spinner = spinner.getSelectedItem().toString();
                cargarWebService();
                Intent selectGym = new Intent(getApplicationContext(), GymList.class);
                startActivity(selectGym);

            }



        });

    }


    private void cargarWebService() {

        progreso= new ProgressDialog(NewUser.this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://gymup.zonahosting.net/gymphp/RegistroClientsWS.php?name=" + etName.getText().toString()+
                "&surname="+etSurname.getText().toString()+
                "&document="+etDocument.getText().toString()+
                "&address="+etAddress.getText().toString()+
                "&city="+etCity.getText().toString()+
                "&birthday="+selectedDate+
                "&country="+etCountry.getText().toString()+
                "&phone="+etPhone.getText().toString()+
                "&email="+etEmail.getText().toString()+
                "&mobile="+etMobile.getText().toString()+
                "&gender="+selected_spinner+
                "&height="+etHeight.getText().toString()+
                "&weight="+etWeight.getText().toString()+
                "&cusername="+etcUsername.getText().toString()+
                "&cpassword="+etcPassword.getText().toString();

        url = url.replace(" ","%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progreso.hide();
                        Toast.makeText(getApplicationContext(),"Exito al guardar :) "+ response.toString(), Toast.LENGTH_LONG).show();
                        etName.setText(" ");
                        etSurname.setText(" ");
                        etDocument.setText(" ");
                        etAddress.setText(" ");
                        etCity.setText(" ");
                        etCountry.setText(" ");
                        etPhone.setText(" ");
                        etEmail.setText(" ");
                        etMobile.setText(" ");
                        etHeight.setText(" ");
                        etWeight.setText(" ");
                        etcUsername.setText(" ");
                        etcPassword.setText(" ");

                        etBirthday.setText(" ");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getApplicationContext(),"Error :( "+error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Error",error.toString());

            }
        });
        request.add(jsonObjectRequest);

    }
    //para el Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long l) {

        String selectedGender = adapter.getItemAtPosition(position).toString();
        Toast.makeText(adapter.getContext(), selectedGender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Para la seleccion de fecha
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                {
                    month = month + 1;
                    String date = makeDateString(day, month, year);
                    dateButton.setText(date);
                    selectedDate = year + "-" + month + "-" + day;
                }
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "ENE";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "ABR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AGO";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DIC";

        //por si falla
        return "ENE";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}
