package com.example.hrida.qr_car_scanner;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GetAllCustomers extends AppCompatActivity {

    private ListView lsAllCustomers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_customers);
        lsAllCustomers = (ListView) findViewById(R.id.lsAllCustomers);
        ShopDatabase db = new ShopDatabase(this);
        db.updateAllCustomers(lsAllCustomers);
    }
}
