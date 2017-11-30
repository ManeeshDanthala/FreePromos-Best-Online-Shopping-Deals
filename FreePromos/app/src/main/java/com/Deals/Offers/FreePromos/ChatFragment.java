package com.Deals.Offers.FreePromos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.regex.Pattern;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


public class ChatFragment extends Fragment {
    private ImageButton send;
    private static final int GALLERY_INTENT = 2;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference myRef, myref3;
    HashMap<String, String> msg;
    ImageButton imgbutton;
    EditText sending;
    ListView lchat;
    private static final Pattern urlPattern = Pattern.compile(
            "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                    + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                    + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
            Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReferenceFromUrl("gs://bestdeals2-25493.appspot.com");

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_chat, container, false);

        LinearLayout verification_layout=(LinearLayout) v.findViewById(R.id.verification_button);
        LinearLayout send_layout=(LinearLayout) v.findViewById(R.id.texting);

        if(checkIfEmailVerified()!=1){
            send_layout.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "please verify email to participate in chat", Toast.LENGTH_LONG);
        }
        else{
            verification_layout.setVisibility(View.GONE);
        }

        SharedPreferences prefs = this.getActivity().getSharedPreferences("profiledata", MODE_PRIVATE);
        final SharedPreferences.Editor editore = prefs.edit();


        Button send_verify=(Button) v.findViewById(R.id.verify_bt);
        send_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getActivity());
                }
                builder.setTitle("send email verification?")
                        .setMessage("You need to relogin to app after email verification")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                sendVerificationEmail(editore);
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                sending.setText("");
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });

        //all permissions
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        myref3 = database.getReference("chat");
        myref3.keepSynced(true);



        final ArrayList<HashMap<String, String>> chatmsgs = new ArrayList<>();

        msg = new HashMap<>();

        final String usernameee = prefs.getString("username", "username failed");



        //adapters
        final chatadapter ch = new chatadapter(this.getActivity(), chatmsgs, usernameee);
        lchat = (ListView) v.findViewById(R.id.chatlist);
        lchat.setAdapter(ch);

        //defination
        send = (ImageButton) v.findViewById(R.id.sendbutton1);
        sending = (EditText) v.findViewById(R.id.sendingtext);
        imgbutton = (ImageButton) v.findViewById(R.id.camerabutton);


        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("clicked syucc1111", "");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                Log.e("clicked syucces", "");
                startActivityForResult(intent, GALLERY_INTENT);
            }
        });


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                        if (sending.getText().toString().equals("")) {

                        } else {
                            final FirebaseUser user = mAuth.getCurrentUser();
                            String adminname = getResources().getString(R.string.admin);
                            String ko = sending.getText().toString();
                            if (!usernameee.equals(adminname)) {
                                String[] parts = sending.getText().toString().split(" ");

                                // get every part
                                for (String item : parts) {
                                    Log.e("in for======", "");
                                    if (urlPattern.matcher(item).matches()) {
                                        //it's a good url
                                        ko = ko.replace("/", ",");
                                        Log.e("ko---", ko);

                                    } else {
                                        // it isn't a url
                                        System.out.print(item + " ");
                                    }
                                }
                            }
                            msg.put("message", ko);
                            sending.setText("");
                            msg.put("username", usernameee);
                            msg.put("time", new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
                            msg.put("id", user.getUid().substring(0, 7));
                            Log.e("check1-",user.getUid().substring(0, 7));
                            myRef.child("chat").push().setValue(msg);
                        }
                    } else {
                        Toast.makeText(getActivity(), "please verify email to participate in chat", Toast.LENGTH_SHORT);
                    }
            }
        });

        myref3.limitToLast(100).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> k = (HashMap<String, String>) dataSnapshot.getValue();
                chatmsgs.add(k);
                ch.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, final Intent data) { //uploading pic
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onactiv is called Main", "");
        SharedPreferences prefs = getActivity().getSharedPreferences("profiledata", MODE_PRIVATE);
        final String uid = prefs.getString("id", "456");
        final String usernameee = prefs.getString("username", "username failed");
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {


            //for confirmation of uploading
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(getActivity(), android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(getActivity());
            }
            builder.setTitle("Confirm Send")
                    .setMessage("Send Image??")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with sending

                            //data
                            Uri uri = data.getData();
                            final String timenow = (new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())).toString();
                            StorageReference filepath = storageRef.child(uid + "-" + timenow);

                            //uploading
                            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getActivity(), "upload done", Toast.LENGTH_LONG);
                                    storageRef.child(uid + "-" + timenow).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // Got the download URL for 'users/me/profile.png'
                                            Log.e("uchat-", uri + ""); /// The string(file link) that you need
                                            final FirebaseUser user = mAuth.getCurrentUser();
                                            msg = new HashMap<>();
                                            msg.put("image", uri + "");
                                            msg.put("username", usernameee);
                                            msg.put("time", new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime()));
                                            msg.put("id", user.getUid().substring(0, 5));
                                            myRef.child("chat").push().setValue(msg);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Handle any errors
                                            Log.e("fail in up of chat img", "");
                                        }
                                    });
                                }
                            });

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    protected boolean isOnline() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (activeNetwork.isConnected())
                    haveConnectedWifi = true;
            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                if (activeNetwork.isConnected())
                    haveConnectedMobile = true;
            }
        }

        return haveConnectedWifi || haveConnectedMobile;
    }

    private void sendVerificationEmail(final SharedPreferences.Editor editor) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // email sent
                            Toast.makeText(getActivity(), "Verification mail sent please verify and relogin to participate in group chat", Toast.LENGTH_SHORT).show();
                            final Prefs prefr;
                            prefr = Application.getApp().getPrefs();
                            prefr.setLoginStatus(true);
                            prefr.setspb(true);

                            prefr.setLoginStatus(false);

                            editor.clear();
                            editor.commit();
                            startActivity(new Intent(getActivity(),LoginActivity.class));
                            // after email is sent just logout the user and finish this activity
                        } else {
                            // email not sent, so display message and restart the activity or do whatever you wish to do
                            Toast.makeText(getActivity(), "email not sent. please try later", Toast.LENGTH_SHORT).show();
                            //restart this activity

                        }
                    }
                });
    }

    private int checkIfEmailVerified() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user.isEmailVerified()) {
            return 1;
            // user is verified, so you can finish this activity or send user to activity which you want.
            //Toast.makeText(getActivity(), "Email is verified successfully. Now can participate in group chat", Toast.LENGTH_SHORT).show();
        } else {
            // email is not verified, so just prompt the message to the user and restart this activity.
            // NOTE: don't forget to log out the user.
            Toast.makeText(getActivity(), "Please verify email to Chat", Toast.LENGTH_SHORT).show();
            //restart this activity
            return 0;

        }
    }
}