package com.example.hrida.qr_car_scanner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.app.AlertDialog.Builder;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private EditText ID,FName,LName,CarModule,CheckUp;
    private Button Add,Delete,View,ViewAll,Scan,Modify;
    private ImageView iv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ID = (EditText) findViewById(R.id.editID);
        FName = (EditText) findViewById(R.id.editFName);
        LName = (EditText) findViewById(R.id.editLName);
        CarModule = (EditText) findViewById(R.id.editCar);
        CheckUp = (EditText) findViewById(R.id.editLastCheck);

        iv1 = (ImageView)findViewById(R.id.iv1);

        Add = (Button) findViewById(R.id.btnAdd);
        Delete = (Button) findViewById(R.id.btnDelete);
        View = (Button) findViewById(R.id.btnView);
        ViewAll = (Button) findViewById(R.id.btnViewAll);
        Scan = (Button) findViewById(R.id.btnScan);
        Modify = (Button) findViewById(R.id.btnModify);

        Add.setOnClickListener(this);
        Delete.setOnClickListener(this);
        View.setOnClickListener(this);
        ViewAll.setOnClickListener(this);
        Scan.setOnClickListener(this);
        Modify.setOnClickListener(this);
    }

    public void showMessage(String title, String message){
        Builder builder = new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    private void clearText(){
        ID.setText("");
        FName.setText("");
        LName.setText("");
        CarModule.setText("");
        CheckUp.setText("");
        ID.requestFocus();
    }

    public void onClick(View v) {

        if(v == Add)
        {
            if(ID.getText().toString().trim().length() == 0
                    || FName.getText().toString().trim().length() == 0
                    || LName.getText().toString().trim().length() == 0
                    || CarModule.getText().toString().trim().length() == 0
                    || CheckUp.getText().toString().trim().length() == 0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            else {
                ShopDatabase db = new ShopDatabase(MainActivity.this);
                db.setKey("HRida");
                try {
                    int id = Integer.parseInt(ID.getText().toString());
                    String Fname = FName.getText().toString();
                    String Lname = LName.getText().toString();
                    String CarMod = CarModule.getText().toString();
                    String Check = CheckUp.getText().toString();
                    db.addCustomer(new Customer(id, Fname, Lname, CarMod, Check));
                } catch (Exception ex) {
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                clearText();
            }
        }
        if(v == ViewAll)
        {
            startActivity(new Intent(MainActivity.this, GetAllCustomers.class));
        }
        if(v == View)
        {
            if(ID.getText().toString().trim().length() == 0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            try {
                int id = Integer.parseInt(ID.getText().toString());
                ShopDatabase db = new ShopDatabase(MainActivity.this);
                db.getCustomerName(id);
                db.updateImage(iv1);
            }
            catch (Exception ex) {
                Log.w("error", ex.getMessage());
            }
        }
        if(v == Delete)
        {
            if(ID.getText().toString().trim().length() == 0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            try {
                ShopDatabase db = new ShopDatabase(MainActivity.this);
                int id = Integer.parseInt(ID.getText().toString());
                db.setKey("HRida");
                db.deleteData(id);
            }
            catch (Exception ex) {
                Log.w("error", ex.getMessage());
            }
        }
        if(v == Scan)
        {
            IntentIntegrator integrator = new IntentIntegrator(this);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan");
            integrator.setCameraId(0);
            integrator.setBeepEnabled(false);
            integrator.setBarcodeImageEnabled(false);
            integrator.initiateScan();
        }
        if(v == Modify)
        {

            if(ID.getText().toString().trim().length() == 0)
            {
                showMessage("Error", "Please enter ID");
                return;
            }
            try {
                ShopDatabase db = new ShopDatabase(MainActivity.this);
                int id = Integer.parseInt(ID.getText().toString());
                String Fname = FName.getText().toString();
                String Lname = LName.getText().toString();
                String CarMod = CarModule.getText().toString();
                String Check = CheckUp.getText().toString();
                db.setKey("HRida");
                db.updateData(id, new temp(Fname, Lname, CarMod, Check));
            }
            catch (Exception ex) {
                Log.w("error", ex.getMessage());
            }
            clearText();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents()==null){
                Toast.makeText(this, "You cancelled the scanning", Toast.LENGTH_LONG).show();
            }
            else {
                String sentence = result.getContents();
                String[] words = sentence.split(" ");
                ID.setText(words[0]);
                FName.setText(words[1]);
                LName.setText(words[2]);
                CarModule.setText(words[3]);
                CheckUp.setText(words[4]);
                Toast.makeText(this, "Success",Toast.LENGTH_LONG).show();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
