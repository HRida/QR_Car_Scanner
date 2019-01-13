package com.example.hrida.qr_car_scanner;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopDatabase {
    private ArrayList<Customer> AllCustomers;
    private Context context;
    private String key;

    //CONSTRUCTOR
    public ShopDatabase(Context context) {
        this.context = context;
        AllCustomers = new ArrayList<Customer>();
    }

    //DB ACCESS KEY
    public void setKey(String key) {
        this.key = key;
    }

    //IMAGE FETCHING
    public void updateImage(final ImageView iv) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://qr-car.000webhostapp.com/getImage.php";

        ImageRequest request = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.FIT_CENTER, Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }

    //ADDING CUSTOMER
    public void addCustomer(Customer c) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        final int id = c.getId();
        final String Fname = c.getFName();
        final String Lname = c.getLName();
        final String CarModule = c.getCarType();
        final String CheckUpDate = c.getCheck();

        String url = "https://qr-car.000webhostapp.com/save.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(id) );
                params.put("Fname", Fname);
                params.put("Lname", Lname);
                params.put("CarModule", CarModule);
                params.put("CheckUpDate", CheckUpDate);
                params.put("key", key);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //Get All Data
    public void updateAllCustomers(final ListView lsAllCustomers) {
        String url = "https://qr-car.000webhostapp.com/getAllCustomers.php";
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        // Request a json response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest( url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray ja) {
                        try {
                            for (int i = 0;i < ja.length();i++) {
                                JSONObject st = ja.getJSONObject(i);
                                int id = Integer.parseInt(st.getString("id"));
                                String Fname = st.getString("Fname");
                                String Lname = st.getString("Lname");
                                String CarModule = st.getString("CarModule");
                                String CheckUpDate = st.getString("CheckUpDate");
                                AllCustomers.add(new Customer(id, Fname, Lname, CarModule, CheckUpDate));
                            }
                            lsAllCustomers.setAdapter(new ArrayAdapter<Customer>(context,
                                    android.R.layout.simple_list_item_1, AllCustomers));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    //GET CUSTOMER NAME
    public void getCustomerName(int id) {
        String url = "https://qr-car.000webhostapp.com/getCustomerName.php?id="+id;

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);
        // Request a json response from the provided URL.
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String sentence) {
                        if(sentence.equals("Record not found")){
                            Toast.makeText(context, sentence, Toast.LENGTH_SHORT).show();
                        }else{
                            String[] words = sentence.split(" ");
                            Toast.makeText(context, "Customer name: " + words[0] + " " + words[1] + " " + words[2] + " " + words[3], Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("error", error.toString());
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }

    //UPDATE CUSTOMER INFO
    public void updateData(final int id, temp t){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        final String Fname = t.getFName();
        final String Lname = t.getLName();
        final String CarModule = t.getCarType();
        final String CheckUpDate = t.getCheck();

        String url = "https://qr-car.000webhostapp.com/update.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(id) );
                params.put("Fname", Fname);
                params.put("Lname", Lname);
                params.put("CarModule", CarModule);
                params.put("CheckUpDate", CheckUpDate);
                params.put("key", key);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    //DELETE CUSTOMER
    public void deleteData(final int id){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(context);

        String url = "https://qr-car.000webhostapp.com/delete.php";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "That didn't work!", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id",String.valueOf(id) );
                params.put("key", key);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public ArrayList<Customer> getAllCustomers() {
        return AllCustomers;
    }
}
