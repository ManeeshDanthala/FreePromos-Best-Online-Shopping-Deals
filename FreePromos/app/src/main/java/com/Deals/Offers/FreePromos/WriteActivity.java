package com.Deals.Offers.FreePromos;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class WriteActivity extends AppCompatActivity {
    private EditText title_n,desc_n;
    private Button notify_b;
    private String desc_s,title_s,time_s;
    private HashMap<String,String> mapper12;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //  FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notifications");
        myRef.keepSynced(true);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        notify_b=(Button) findViewById(R.id.notify_button);
        desc_n=(EditText)findViewById(R.id.desc_notify);
        title_n=(EditText) findViewById(R.id.title_notify);

        notify_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                desc_s=desc_n.getText().toString();
                title_s=title_n.getText().toString();
                time_s=new Date().getTime()+"";

                if(TextUtils.isEmpty(title_s) || TextUtils.isEmpty(desc_s)) {
                    Toast.makeText(WriteActivity.this, "fileds_empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    mapper12=new HashMap<String, String>();
                    mapper12.put("t",title_s);//title
                    mapper12.put("n",desc_s);//notification
                    mapper12.put("i",time_s);//time

                    myRef.push().setValue(mapper12);
                    startActivity(new Intent(WriteActivity.this,MainActivity.class));
                }
            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                this.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
