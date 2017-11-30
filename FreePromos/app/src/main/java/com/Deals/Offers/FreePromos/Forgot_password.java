package com.Deals.Offers.FreePromos;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot_password extends AppCompatActivity {

    private EditText email_a;
    private Button reset_b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        email_a=(EditText) findViewById(R.id.email_addr);
        reset_b=(Button) findViewById(R.id.reset_pass);

        reset_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email_a.getText().toString().trim().equals("")){
                    FirebaseAuth.getInstance().sendPasswordResetEmail(email_a.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d("reset", "Email sent.");
                                        Toast.makeText(Forgot_password.this,"Email sent.please reset password",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Forgot_password.this,LoginActivity.class));
                                    }
                                    else{
                                        Toast.makeText(Forgot_password.this,"There is some problem.Please try later",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(Forgot_password.this,LoginActivity.class));
                                    }
                                }
                            });
                }
                else{
                    Toast.makeText(Forgot_password.this,"Enter email",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
