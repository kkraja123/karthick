package com.vss.app.location;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    ProgressBar loading;
    Button btnLogin;
    EditText edtEmail, edtPassword;
    String url = "http://www.platosys.com/mindgame/tranquila.php?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loading = findViewById(R.id.login_progress);
        btnLogin = findViewById(R.id.btn_login1);
        edtEmail = findViewById(R.id.edt_login_username);
        edtPassword = findViewById(R.id.edt_login_password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tryToLogin();
            }
        });

    }

    private void tryToLogin() {
        final String email = edtEmail.getText().toString();
        final String password = edtPassword.getText().toString();

        String tag_string_req = "string_req";
        StringRequest strReq = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{

                    Log.e("Poststatus",response);
                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");

                    if(status.toLowerCase().equals("true")){

                        JSONObject Jasonobject = jsonObject.getJSONObject("data");
                        Toast.makeText(LoginActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();
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

                    }else if(status.toLowerCase().equals("false")){
                        Toast.makeText(LoginActivity.this, jsonObject.getString("data"), Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    Log.e("catchfile",e.toString());
                    e.printStackTrace();
                }

            }
        },new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("responseeroor", "Error: " + error.getMessage());
                Toast.makeText(LoginActivity.this, "Login failed! Please try again...", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                //params.put("userId",userId);
                params.put("email",email);
                params.put("password",password);
               // params.put("token",token);
                params.put("requestcode", "106");
                Log.e("ParamsReg",params.toString());
                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }
}

