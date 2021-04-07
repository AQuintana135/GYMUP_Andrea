package com.bitgymup.gymup.users;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bitgymup.gymup.LogIn;
import com.bitgymup.gymup.R;

import org.json.JSONObject;

public class UserUpdatePassword extends AppCompatActivity {

    private Button btnSubmit;
    private String password1, password2, username;
    private EditText etcPassword, etcPassword2;
    private TextView etcUserName;
    private RequestQueue request;
    ProgressDialog progreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_update_password);

        SharedPreferences userId1 = getSharedPreferences("user_login", Context.MODE_PRIVATE);
        username = userId1.getString("username", "");
        request = Volley.newRequestQueue(this);

        etcUserName  = findViewById(R.id.tvUsername);
        etcUserName.setText("Usuario: "+username);
        etcPassword  = findViewById(R.id.etcPasswordEdit);
        etcPassword2 = findViewById(R.id.etRepeatPasswordEdit);
        btnSubmit    = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener(){
               @Override
               public void onClick(View v) {
                   if (!validatePassword()){
                       Toast.makeText(getApplicationContext(), "Corrija los datos ingresados.", Toast.LENGTH_LONG).show();
                   }else {
                       progreso = new ProgressDialog(UserUpdatePassword.this);
                       progreso.setMessage("Cargando...");
                       progreso.show();

                       String url = "http://gymup.zonahosting.net/gymphp/client_updatepassword.php?user=" + username.trim()   +
                               "&password=" + etcPassword.getText().toString().trim();

                       url = url.replace(" ", "%20");

                       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                               new Response.Listener<JSONObject>() {
                                   @Override
                                   public void onResponse(JSONObject response) {
                                       progreso.hide();
                                       Toast.makeText(getApplicationContext(), "Exito al guardar :) ", Toast.LENGTH_LONG).show();
                                       Intent intent = new Intent(getApplicationContext(), LogIn.class);
                                       startActivity(intent);
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


               }
           }
        );

    }

    private boolean validatePassword() {
        String password1 = etcPassword.getText().toString();
        String password2 = etcPassword2.getText().toString();
        if (password1.isEmpty() || password2.isEmpty()) {
            etcPassword.setError("Debe completar este campo.");
            etcPassword2.setError("Debe completar este campo.");
            return false;
        } else if (password1.equals(password2)) {
            etcPassword.setError(null);
            etcPassword2.setError(null);
            return true;
        } else {
            etcPassword2.setError("Las contraseñas no coinciden");
            return false;
        }
    }


}