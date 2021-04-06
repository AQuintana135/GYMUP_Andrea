package com.bitgymup.gymup.users;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bitgymup.gymup.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

public class EditUserProfile extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText etName, etSurname, etDocument, etAddress, etCity, etCountry, etPhone, etEmail, etMobile, etHeight, etWeight, etBirthday, etGender;
    private TextView etcUsername;
    private String selectedGender, selectedDate, selected_spinner, username;
    private Button btnSubmit, dateButton, btnUpdateData;
    private DatePickerDialog datePickerDialog;
    private Spinner spinner;
    private String id, url;

    ProgressDialog progreso;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        request = Volley.newRequestQueue(this);

        etName        = findViewById(R.id.etNameEdit);
        etSurname     = findViewById(R.id.etSurnameEdit);
        etDocument    = findViewById(R.id.etDocumentEdit);
        etHeight      = findViewById(R.id.etHeightEdit);
        etWeight      = findViewById(R.id.etWeightEdit);
        etcUsername   = findViewById(R.id.etcUsernameEdit);
        etEmail       = findViewById(R.id.etEmailEdit);
        etMobile      = findViewById(R.id.etMobileEdit);
        etCountry     = findViewById(R.id.etCountryEdit);
        etPhone       = findViewById(R.id.etPhoneEdit);
        etAddress     = findViewById(R.id.etAddressEdit);
        etCity        = findViewById(R.id.etCityEdit);
        btnUpdateData = findViewById(R.id.btnUpdateData);

        SharedPreferences userId1 = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        username = userId1.getString("username", "");
        etcUsername.setText(username);

        spinner = findViewById(R.id.spnGenderEdit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genderList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        loadUserData(username);

        //Para elegir la fecha
      // initDatePicker();

        btnUpdateData.setOnClickListener(new View.OnClickListener(){
                 @Override
                 public void onClick(View v) {
                     if (!validateEmail() | !validateName() | !validateLastName()| !validateDocument()) {
                        // Toast.makeText(getApplicationContext(), "Corrija los datos ingresados.", Toast.LENGTH_LONG).show();
                     }else {
                         updateProfileData(v, username);
                         goToUserProfile(v);
                         //  Toast.makeText(getApplicationContext(), "TA BIEN", Toast.LENGTH_LONG).show();
                     }

                 }
             }
        );

    }

    public void goToUserProfile(View view){
        Intent userProfile = new Intent(this, UserProfile.class);
        startActivity(userProfile);
    }

    private void loadUserData(String username) {
        String url = "http://gymup.zonahosting.net/gymphp/getClientsDataWS.php?username=" + username.trim();
        progreso = new ProgressDialog(EditUserProfile.this);
        progreso.setMessage("Cargando...");
        progreso.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                try {
                    jsonObject  = response.getJSONObject(0);

                    String id       = jsonObject.optString("id");
                    String name     = jsonObject.optString("name");
                    String surname  = jsonObject.optString("surname");
                    String email    = jsonObject.optString("email");
                    String phone    = jsonObject.optString("phone");
                    String height   = jsonObject.optString("height");
                    String weight   = jsonObject.optString("weight");
                    String document = jsonObject.optString("document");
                    String address  = jsonObject.optString("address");
                    String city     = jsonObject.optString("city");
                    String country  = jsonObject.optString("country");
                    String mobile   = jsonObject.optString("mobile");
                    String gender   = jsonObject.optString("gender");
                    String username = jsonObject.optString("username");
                    String birthday = jsonObject.optString("birthday");

                    etName.setText(name);
                    etSurname.setText(surname);
                    etDocument.setText(document);
                    etAddress.setText(address);
                    etCity.setText(city);
                    etCountry.setText(country);
                    etPhone.setText(phone);
                    etMobile.setText(mobile);
                    etEmail.setText(email);
                    etMobile.setText(mobile);
                    etHeight.setText(height);
                    etWeight.setText(weight);
                    etcUsername.setText(username);
                   // etGender.setText(gender);
                   // dateButton.setText(getBirthdayDate(birthday));
                    progreso.hide();
                } catch (JSONException e) {
                    e.printStackTrace();
                } ;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }

    private void updateProfileData(View v, String username){

        progreso = new ProgressDialog(EditUserProfile.this);
        progreso.setMessage("Cargando...");
        progreso.show();

        String url = "http://gymup.zonahosting.net/gymphp/client_update.php?user="+ username +
                "&address="     + etAddress.getText().toString() +
               // "&cliBirthday=" + selectedDate +
                "&cliCity="     + etCity.getText().toString() +
                "&cliCountry="  + etCountry.getText().toString() +
                "&cliDocument=" + etDocument.getText().toString() +
                "&cliEmail="    + etEmail.getText().toString() +
               // "&cliGender="   + selected_spinner +
                "&cliHeight="   + etHeight.getText().toString() +
                "&cliMobile="   + etMobile.getText().toString() +
                "&cliName="     + etName.getText().toString() +
                "&cliSurname="  + etSurname.getText().toString() +
                "&cliPhone="    + etPhone.getText().toString() +
                "&cliWeight="   + etWeight.getText().toString();

        url = url.replace(" ", "%20");

        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progreso.hide();
                        Toast.makeText(getApplicationContext(), "Exito al guardar :) " , Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progreso.hide();
                Toast.makeText(getApplicationContext(), "Error :( " + error.toString(), Toast.LENGTH_SHORT).show();
                Log.i("Error", error.toString());

            }
        });
        request.add(jsonObjectRequest);
    }


    //para el Spinner
    @Override
    public void onItemSelected(AdapterView<?> adapter, View view, int position, long l) {
        String selectedGender = adapter.getItemAtPosition(position).toString();
       // Toast.makeText(adapter.getContext(), selectedGender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //Para la seleccion de fecha
 /*   private String getBirthdayDate(String birthday)
    {
        String[] parts = birthday.split("-");
        String year2  = parts[0]; //
        String month2 = parts[1]; //
        String day2   = parts[2];
        Calendar cal = Calendar.getInstance();
        int year = Integer.parseInt(year2);
        int month = Integer.parseInt(month2);
     //   month = month + 1;
        int day = Integer.parseInt(day2);
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
    }*/

    //Validar campos
    private boolean validateName() {
        String nameInput = etName.getText().toString().trim();
        if (nameInput.isEmpty()) {
            etName.setError("Debe completar este campo.");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }

    private boolean validateLastName() {
        String lastNameInput = etSurname.getText().toString().trim();
        if (lastNameInput.isEmpty()) {
            etSurname.setError("Debe completar este campo.");
            return false;
        }else {
            etSurname.setError(null);
            return true;
        }
    }

    private boolean validateDocument() {
        String documentInput = etDocument.getText().toString().trim();
        if (documentInput.isEmpty()) {
            etDocument.setError("Debe completar este campo.");
            return false;
        } else {
            etDocument.setError(null);
            return true;
        }
    }

    private boolean validateEmail() {
        String emailInput = etEmail.getText().toString().trim();
        if (emailInput.isEmpty()) {
            etEmail.setError("Debe completar este campo.");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            etEmail.setError("Ingrese un email válido.");
            return false;
        } else {
            etEmail.setError(null);
            return true;
        }
    }
}