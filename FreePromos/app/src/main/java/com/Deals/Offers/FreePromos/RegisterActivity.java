package com.Deals.Offers.FreePromos;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef;

    private de.hdodenhof.circleimageview.CircleImageView ci;
    private Button bt;
    private EditText e;
    private EditText p;
    private EditText u;
    private EditText num;

    //test
    ImageView imgView;
    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    ProgressDialog pd;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bestdeals2-25493.appspot.com");    //change the url according to your firebase app


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");

        e=(EditText) findViewById(R.id.email2); //id's
        p=(EditText) findViewById(R.id.password2);
        u=(EditText) findViewById(R.id.username);
        bt=(Button) findViewById(R.id.b2);
        num=(EditText) findViewById(R.id.no);

        //testing
       // chooseImg = (Button)findViewById(R.id.chooseImg);
       // imgView = (ImageView)findViewById(R.id.imgView);

        //ci = (de.hdodenhof.circleimageview.CircleImageView)findViewById(R.id.imgView);
        pd = new ProgressDialog(this);
        pd.setMessage("Signing up....");

//        ci.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_PICK);
//                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
//            }
//        });



        bt.setOnClickListener(new View.OnClickListener() {      //signup clicked
            @Override
            public void onClick(View v) {
                signupc();
            }
        });
    }

//    public void uploadingimage(String uid){
//        if(filePath != null) {
//            pd.show();
//
//            StorageReference childRef = storageRef.child(uid);
//
//            //uploading the image
//            UploadTask uploadTask = childRef.putFile(filePath);
//
//            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    pd.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                    pd.dismiss();
//                    Toast.makeText(RegisterActivity.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        else {
//            Toast.makeText(RegisterActivity.this, "Select an image", Toast.LENGTH_SHORT).show();
//        }
//    }

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

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            filePath = data.getData();
//
//            try {
//                //getting image from gallery
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//
//                //Setting image to ImageView
//                imgView.setImageBitmap(bitmap);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    protected void signupc(){       //method for signingup
        String email=e.getText().toString();
        String password=p.getText().toString();
        final String phoneno=num.getText().toString();
        final String name=u.getText().toString();

        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name)){    //check for empty
            Log.v("empty","fileds");
            Toast.makeText(RegisterActivity.this,"fileds_empty",Toast.LENGTH_LONG).show();
        }
//        else if(filePath == null){
//            Toast.makeText(RegisterActivity.this,"select profile pic",Toast.LENGTH_LONG).show();
//        }
        else if(name.equalsIgnoreCase("Admin")){
            Toast.makeText(RegisterActivity.this,"Please select other username",Toast.LENGTH_LONG).show();
        }
        else{
            pd.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { //if success
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            Log.d("singup ", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Signup failed", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                               // if(filePath != null) {

                                myRef.child(user.getUid()).child("name").setValue(name);
                                if(!phoneno.equals("")){
                                    myRef.child(user.getUid()).child("phone-no").setValue(phoneno);
                                }
                                else{
                                    myRef.child(user.getUid()).child("phone-no").setValue("-");
                                }

                                AlertDialog.Builder builder;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                    builder = new AlertDialog.Builder(RegisterActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                                } else {
                                    builder = new AlertDialog.Builder(RegisterActivity.this);
                                }
                                builder.setTitle("Do you want to participate in group chat?")
                                        .setMessage("You need to click on verification link sent to your email to participate in chat")
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // continue with delete
                                                sendVerificationEmail();
                                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(i);
                                                Toast.makeText(RegisterActivity.this, "account successfully created", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                // do nothing
                                                sendVerificationEmail();
                                                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                                                startActivity(i);
                                                Toast.makeText(RegisterActivity.this, "account successfully created", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .show();




                            }
                            // ...
                        }
                    });
        }
    }
    private void sendVerificationEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                           //Toast.makeText(RegisterActivity.this, "Verification mail sent please verify and relogin to participate in group chat", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                            // after email is sent just logout the user and finish this activity
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            //Toast.makeText(RegisterActivity.this, "email not sent. please try later", Toast.LENGTH_SHORT).show();
                            //restart this activity

                        }
                    }
                });
    }
}
