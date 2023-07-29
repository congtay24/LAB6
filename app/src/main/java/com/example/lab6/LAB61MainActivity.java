package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LAB61MainActivity extends AppCompatActivity {
    Button btn1, btn2;
    TextView tvKq;
    Context context = this;

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab61_main);
        btn1 = findViewById(R.id.lab61_btn);
        btn2 = findViewById(R.id.lab61_btn2);
        tvKq = findViewById(R.id.lab61_tv);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSringVoley();
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJson_ObjectOfArray();
            }
        });
    }

    private void getSringVoley() {
        // b1 tạo repuest
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        //b2 chuyển url
        String url = "https://www.google.com/";

        //b3 truyển tham số
        //StringRequest(phương thưc, url, thành công, thất bại)
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // đưa kết quả ra màn hình
                        tvKq.setText("Kết quả" + response.substring(0, 1000));
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKq.setText("Kết quả" + error.getMessage());
            }
        });
        requestQueue.add(stringRequest);
    }

    private void getJson_ObjectOfArray() {
        //0 tạo hàm đợi
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        //1  url
        String url = "https://batdongsanabc.000webhostapp.com/mob403lab6/array_json_new.json";
        //2 tạo repuest(xác định loại request)

        // trường hợp này là arrayRepuest
        //JsonArrayRequest(url,thành công,thất bại)
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //kq mảng các đối tượng
                        // for để đọc các đối tương
                        String strKq = "";
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                String id = object.getString("id");
                                String name = object.getString("name");
                                String email = object.getString("email");
                                JSONObject phone = object.getJSONObject("phone");
                                String mobile = phone.getString("mobile");
                                String home = phone.getString("home");
                                // nối chuỗi
                                strKq += "id: " + id + "\n\n";
                                strKq += "name: " + name + "\n\n";
                                strKq += "email: " + email + "\n\n";
                                strKq += "mobile: " + mobile + "\n\n";
                                strKq += "home: " + home + "\n\n";

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        tvKq.setText(strKq);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                tvKq.setText(error.getMessage());
            }
        });
        //3 truyền tham số(nếu có)

        // 4 xử lý request
        requestQueue.add(jsonArrayRequest);
    }
}