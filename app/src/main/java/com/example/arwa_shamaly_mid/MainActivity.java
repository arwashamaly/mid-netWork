package com.example.arwa_shamaly_mid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.arwa_shamaly_mid.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    String url = "https://studentucas.awamr.com/api/auth/login/user";
    public static SharedPreferences sharedToken;
    public static SharedPreferences.Editor editToken;
    public String failTokenName ="tokenShared";
    public static String sharedTokenKey = "token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedToken= getSharedPreferences(failTokenName,MODE_PRIVATE);
        editToken = sharedToken.edit();

        if (!sharedToken.getString(sharedTokenKey,"").isEmpty()){
            Intent intent = new Intent(getBaseContext(),order_user.class);
            startActivity(intent);
            finish();
        }
        binding.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),order_user.class);
                startActivity(intent);
            }
        });
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginResponse(binding.etPass.getText().toString()
                        ,binding.etEmail.getText().toString());
            }
        });
    }
    private void loginResponse(String password , String email) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                    String token = jsonObjectData.getString("token");

                    editToken.putString(sharedTokenKey,token);
                    editToken.commit();

                    Intent intent = new Intent(getBaseContext(),order_user.class);
                    startActivity(intent);
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("email",email);
                map.put("password",password);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Accept","application/json");
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }

}