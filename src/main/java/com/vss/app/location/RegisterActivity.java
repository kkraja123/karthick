package com.vss.app.location;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    Button btnLogin, btnRegister;
    EditText edtUserName, edtFirstName, edtLastName, edtEmail, edtPassword;
    String url = "http://www.platosys.com/mindgame/tranquila.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        edtFirstName = findViewById(R.id.edt_first_name);
        edtLastName = findViewById(R.id.edt_last_name);
        edtUserName = findViewById(R.id.edt_user_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_pwd);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
//                startActivity(intent);
                register();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    private void register() {
        final String userName = edtUserName.getText().toString();
        final String firstName = edtFirstName.getText().toString();
        final String lastName = edtLastName.getText().toString();
        final String emailId = edtEmail.getText().toString();
        final String passWord = edtPassword.getText().toString();

        String tag_string_req = "string_req";
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    Log.e("Poststatus", response);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if (status.toLowerCase().equals("true")) {

                        JSONObject Jasonobject = jsonObject.getJSONObject("data");
                        Toast.makeText(RegisterActivity.this, "success", Toast.LENGTH_SHORT).show();
//                        session.setId(Jasonobject.getInt("id"));
//                        session.setFirstname(Jasonobject.getString("firstname"));
//                        session.setLastname(Jasonobject.getString("lastname"));
//                        session.setUsername(Jasonobject.getString("username"));
//                        session.setEmail(Jasonobject.getString("email"));
//                        //session.setPassword(mSignInPassword);
//                        session.setCannabisType(Jasonobject.getString("cannabis_type"));
//                        session.setGender(Jasonobject.getString("gender"));
//                        session.setImageurl(Jasonobject.getString("image_url"));
//                        session.setCountry(Jasonobject.getString("country"));
//                        session.setCard(Jasonobject.getString("card"));
//                        session.setDate(Jasonobject.getString("expdate"));
//                        session.setEmailNotify(Jasonobject.getString("email_notify"));
//                        session.setPushNotify(Jasonobject.getString("push_notify"));
//                        session.setCannabisNotify(Jasonobject.getString("cannabis_notify"));
//                        //session.setLogin(true);

                    } else if (status.toLowerCase().equals("false")) {
                        Toast.makeText(RegisterActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Log.e("catchfile", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseeroor", "Error: " + error.getMessage());
                Toast.makeText(RegisterActivity.this, "Register failed! Please try again...", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                //params.put("userId",userId);
                params.put("username", userName);
                params.put("firstname", firstName);
                params.put("lastname", lastName);
                params.put("email", emailId);
                params.put("password", passWord);
                // params.put("token",token);
                params.put("requestcode", "100");
                Log.e("ParamsReg", params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}

