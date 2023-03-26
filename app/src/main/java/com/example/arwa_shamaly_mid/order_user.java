package com.example.arwa_shamaly_mid;

import static com.example.arwa_shamaly_mid.MainActivity.editToken;
import static com.example.arwa_shamaly_mid.MainActivity.sharedToken;
import static com.example.arwa_shamaly_mid.MainActivity.sharedTokenKey;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.arwa_shamaly_mid.databinding.ActivityOrderUserBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class order_user extends AppCompatActivity {
    ActivityOrderUserBinding binding;
    String url = "https://studentucas.awamr.com/api/order/un/complete/user";
    String urlLogOut = "https://studentucas.awamr.com/api/auth/logout";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (sharedToken.getString(sharedTokenKey,"").isEmpty()){
            binding.btnLogout.setVisibility(View.GONE);
        }

        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        if (!sharedToken.getString(sharedTokenKey, "").isEmpty()) {
            getOrderList();
        }
    }

    private void logOut() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlLogOut,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                editToken.putString(sharedTokenKey,"");
                editToken.commit();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String token = sharedToken.getString(sharedTokenKey,"");
                map.put("Accept","application/json");
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }

    private void getOrderList() {

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    Model model = new Model();

                    JSONArray jsonArrayData= response.getJSONArray("data");
                    ArrayList<Data> dataArrayList =new ArrayList<>();

                    for (int i = 0; i <jsonArrayData.length() ; i++) {
                        String order = jsonArrayData.getString(i).toString();
                        JSONObject jsonObjectData =new JSONObject(order);

                        String phone=jsonObjectData.getString("phone");
                        String details=jsonObjectData.getString("details");

                        JSONArray photoOrderJsonArray =jsonObjectData.getJSONArray("photo_order");
                        ArrayList<PhotoOrder> photo_order =new ArrayList<>();

                        for (int j = 0; j < photoOrderJsonArray.length(); j++) {
                            JSONObject photoOrderJsonObject= new JSONObject(photoOrderJsonArray.getString(i).toString());

                            String photo =photoOrderJsonObject.getString("photo");
                            PhotoOrder photoOrder =new PhotoOrder();
                            photoOrder.setPhoto(photo);
                            photo_order.add(photoOrder);

                        }
                        User user =new User();
                        user.setPhone(phone);
                        user.setDetails(details);
                        Data data = new Data();
                        data.setUser(user);
                        data.setPhotoOrderArrayList(photo_order);
                        dataArrayList.add(data);
                    }

                    model.setDataArrayList(dataArrayList);

                    Adapter adapter =new Adapter(dataArrayList,getBaseContext());
                    binding.rc.setAdapter(adapter);
                    binding.rc.setLayoutManager(new LinearLayoutManager(getBaseContext(),
                            RecyclerView.VERTICAL,false));

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String token = sharedToken.getString(sharedTokenKey,"");
                Map<String, String> map =new HashMap<>();
                map.put("Accept","application/json");
                map.put("Authorization","Bearer "+token);
                return map;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }
}