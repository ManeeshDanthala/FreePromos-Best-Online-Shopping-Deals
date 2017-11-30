package com.Deals.Offers.FreePromos;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class EditProfile extends AppCompatActivity {

    int PICK_IMAGE_REQUEST = 111;
    Uri filePath;
    de.hdodenhof.circleimageview.CircleImageView img35;
    FirebaseDatabase database;
    DatabaseReference myRef;
    ProgressDialog pd;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bestdeals2-25493.appspot.com");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        img35=(de.hdodenhof.circleimageview.CircleImageView) findViewById(R.id.imgView33);
        final EditText user35=(EditText) findViewById(R.id.username33);
        final EditText phone35=(EditText) findViewById(R.id.no33);
        Button change35=(Button) findViewById(R.id.b33);

        final SharedPreferences prefs = getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        final String user36=prefs.getString("username","null");
        String phone36=prefs.getString("phone","-");
        String picurl36=prefs.getString("profileurl","null");


        if(!picurl36.equals("null")){
            Glide.with(EditProfile.this)
                    .load(picurl36)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(img35);
        }

        user35.setText(user36);
        phone35.setText(phone36);

        pd = new ProgressDialog(this);
        pd.setMessage("Updating....");

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");






        img35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
            }
        });
        change35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String user40=user35.getText().toString();
                final String phone40=phone35.getText().toString();

                if(TextUtils.isEmpty(user40) || TextUtils.isEmpty(phone40)){    //check for empty
                    Log.v("empty","fileds");
                    Toast.makeText(EditProfile.this,"fileds_empty",Toast.LENGTH_LONG).show();
                }
                else if(phone40.length()!=10){
                    Toast.makeText(EditProfile.this,"enter valid phoneno",Toast.LENGTH_LONG).show();
                }
                else if(!user36.equalsIgnoreCase("Admin") && user40.equalsIgnoreCase("Admin") ){
                    Toast.makeText( EditProfile.this,"Username cant be Admin for end users",Toast.LENGTH_LONG).show();
                }
                else{
                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(filePath!=null) {
                        pd.show();

                        StorageReference childRef = storageRef.child(user.getUid());

                        //uploading the image
                        UploadTask uploadTask = childRef.putFile(filePath);

                        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                pd.dismiss();
                                myRef.child(user.getUid()).child("name").setValue(user40);
                                myRef.child(user.getUid()).child("phone-no").setValue(phone40);

                                final SharedPreferences.Editor editor = prefs.edit();
                                editor.putString("username", user40);
                                editor.putString("phone",phone40);

                                 storageRef.child(user.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        // Got the download URL for 'users/me/profile.png'
                                        Log.e("details-u-",uri.toString()); /// The string(file link) that you need
                                        editor.putString("profileurl",uri.toString());
                                        editor.commit();
                                        Intent i = new Intent(EditProfile.this, Details.class); //not yet decided to give to which intent we will decide after
                                        //creating the next page perfectly we will giev intent okay??okay
                                        Toast.makeText(EditProfile.this, "Changes Done", Toast.LENGTH_SHORT).show();
                                        startActivity(i);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle any errors
                                        Log.e("uri--","failed");
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                pd.dismiss();
                                Toast.makeText(EditProfile.this, "Changes Failed " + e, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {

                        myRef.child(user.getUid()).child("name").setValue(user40);
                        myRef.child(user.getUid()).child("phone-no").setValue(phone40);


                        final SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("username", user40);
                        editor.putString("phone",phone40);

                        editor.commit();
                        Intent i = new Intent(EditProfile.this, Details.class); //not yet decided to give to which intent we will decide after
                        //creating the next page perfectly we will giev intent okay??okay
                        Toast.makeText(EditProfile.this, "Changes Done", Toast.LENGTH_SHORT).show();
                        startActivity(i);

                    }
                }
            }
        });

    }
    public void uploadingimage(String uid){
        if(filePath != null) {
            pd.show();

            StorageReference childRef = storageRef.child(uid);

            //uploading the image
            UploadTask uploadTask = childRef.putFile(filePath);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    pd.dismiss();
                    Toast.makeText(EditProfile.this, "Upload successful", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    pd.dismiss();
                    Toast.makeText(EditProfile.this, "Upload Failed -> " + e, Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(EditProfile.this, "Select an image", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                img35.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
