package com.bitgymup.gymup;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class NewUser extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private EditText etName, etSurname, etDocument, etAddress, etCity, etPostalcode, etCountry, etPhone, etEmail, etMobile, etGender, etHeight, etWeight, etcUsername, etcPassword, etStatus, etComments;
    private Button btnSubmit;

    ProgressDialog progreso;

    private RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        //Code de los spinners para combo

        Spinner spinner = findViewById(R.id.spnGender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genderList, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etDocument = (EditText) findViewById(R.id.etDocument);
        etAddress = (EditText) findViewById(R.id.etAddress);
        etCity = (EditText) findViewById(R.id.etCity);
        etPostalcode = (EditText) findViewById(R.id.etPostalcode);
        etCountry = (EditText) findViewById(R.id.etCountry);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etMobile = (EditText) findViewById(R.id.etMobile);
    //    etGender = (EditText) findViewById(R.id.etGender);
        etHeight = (EditText) findViewById(R.id.etHeight);
        etWeight = (EditText) findViewById(R.id.etWeight);
        etcUsername = (EditText) findViewById(R.id.etcUsername);
        etcPassword = (EditText) findViewById(R.id.etcPassword);
        etStatus = (EditText) findViewById(R.id.etStatus);
        etComments = (EditText) findViewById(R.id.etComments);

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        request = Volley.newRequestQueue(this);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarWebService();
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
                        "&postalcode="+etPostalcode.getText().toString()+
                        "&country="+etCountry.getText().toString()+
                        "&phone="+etPhone.getText().toString()+
                        "&email="+etEmail.getText().toString()+
                        "&mobile="+etMobile.getText().toString()+
                        "&gender="+etGender.getText().toString()+
                        "&height="+etHeight.getText().toString()+
                        "&weight="+etWeight.getText().toString()+
                        "&cusername="+etcUsername.getText().toString()+
                        "&cpassword="+etcPassword.getText().toString()+
                        "&status="+etStatus.getText().toString()+
                        "&comments="+etComments.getText().toString();

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
                                etPostalcode.setText(" ");
                                etCountry.setText(" ");
                                etPhone.setText(" ");
                                etEmail.setText(" ");
                                etMobile.setText(" ");
                                etGender.setText(" ");
                                etHeight.setText(" ");
                                etWeight.setText(" ");
                                etcUsername.setText(" ");
                                etcPassword.setText(" ");
                                etStatus.setText(" ");
                                etComments.setText(" ");

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


        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String etGender = adapterView.getItemAtPosition(i).toString();
        Toast.makeText(adapterView.getContext(), etGender, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}