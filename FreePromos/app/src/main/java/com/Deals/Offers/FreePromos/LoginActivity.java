package com.Deals.Offers.FreePromos;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int RC_SIGN_IN=1;
    private Button loginbutton,pqr;
    private EditText e,p;
    private ImageButton google_sign_in_button;
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG="login123";
    DatabaseReference myRef;
    ProgressDialog pd;
    FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("users");


        e=(EditText) findViewById(R.id.etusername);
        p=(EditText) findViewById(R.id.etpassword);
        loginbutton=(Button) findViewById(R.id.login);
        google_sign_in_button=(ImageButton) findViewById(R.id.googlesignin);


        TextView forgot_password=(TextView) findViewById(R.id.forgot_pass);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,Forgot_password.class));
            }
        });


        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(LoginActivity.this, v); // MainActivity is the name of the class and v is the View parameter used in the button listener method onClick.
                loginc();
            }
        });


        TextView t = (TextView)findViewById(R.id.Signup);
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoregister();
            }
        });

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient =new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_LONG).show();
            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();



        pd = new ProgressDialog(this);
        pd.setMessage("Logging in....");

        google_sign_in_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();

            }
        });

        final Prefs pre;
        pre = Application.getApp().getPrefs();
        if(pre.isSpb()){
            signout();
        }

        pre.setspb(false);

    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    private void signout(){
        mAuth.signOut();

        // Google sign out
        try {
            Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(

                    new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            //  updateUI(null);
                        }
                    });
        }catch(Exception e){
            Log.e("signout--",e.getMessage());
        }
    }

    /*public void gotomain(View view){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }*/

    //for signup page
    public void gotoregister(){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                pd.show();
                GoogleSignInAccount account = result.getSignInAccount();

                firebaseAuthWithGoogle(account);

            } else {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void loginc(){
        final String email=e.getText().toString();
        String password=p.getText().toString();
        if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
            Log.v("empty","fileds");
            Toast.makeText(LoginActivity.this,"Please enter email and password",Toast.LENGTH_LONG).show();
        }
        else{
            pd.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("in loginc1", "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        pd.dismiss();
                        Log.w("in loginc2", "signInWithEmail:failed", task.getException());
                        Toast.makeText(LoginActivity.this,"Invalid email or password",Toast.LENGTH_SHORT).show();
                    }
                    else{

                        //storing user data
                        final FirebaseUser user = mAuth.getCurrentUser();
                        SharedPreferences prefs = getSharedPreferences("profiledata",Context.MODE_PRIVATE);
                        final  SharedPreferences.Editor editor = prefs.edit();
                        editor.putString("id",user.getUid());
                        editor.putString("email",email);
                        final ArrayList<String> als=new ArrayList<>();

                        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.e("username in login--",""+dataSnapshot.child("name").getValue().toString());
                                editor.putString("username",dataSnapshot.child("name").getValue().toString());
                                editor.putString("phone",dataSnapshot.child("phone-no").getValue().toString());

                                MyFirebaseInstanceIdService myFirebaseInstanceIdService=new MyFirebaseInstanceIdService();
                                String tok=myFirebaseInstanceIdService.getRecentToken();
                                Log.e("login token",tok);
                                editor.putString("token", tok);
                                editor.commit();
                                Intent i = new Intent(LoginActivity.this, MainActivity.class); //not yet decided to give to which intent we will decide after
                                //creating the next page perfectly we will giev intent okay??okay
                                startActivity(i);
                                Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.e("cancelled","---false");
                            }
                        });

                        myRef.child(user.getUid().toString()).child("dummy").setValue("l");



                    }
                    // ...
                }
            });

        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            String n = user.getDisplayName().toString();
                            String phoneno = "NA";
                            myRef.child(user.getUid()).child("name").setValue(n);
                            myRef.child(user.getUid()).child("phone-no").setValue(phoneno);


                            //displaying
                            Log.e("id", user.getUid());
                            Log.e("email", user.getEmail());
                            Log.e("username", n);


                            //storing
                            SharedPreferences prefs = getSharedPreferences("profiledata", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("id", user.getUid());
                            editor.putString("logn","google");
                            editor.putString("email", user.getEmail());
                            editor.putString("profileurl", user.getPhotoUrl().toString());
                            //Log.e("login-",prefs.getString("profileurl","null"));
                            editor.putString("username", n);
                           // Log.e("size is--", prefs.getAll().size() + "");
                            MyFirebaseInstanceIdService myFirebaseInstanceIdService=new MyFirebaseInstanceIdService();
                            String tok=myFirebaseInstanceIdService.getRecentToken();
                            //Log.e("login token",tok);
                            editor.putString("token", tok);
                            editor.commit();

                            Log.e("size is--", prefs.getAll().size() + "");

                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            pd.dismiss();//now we will give ot to mainactivity okay wait before that
                            startActivity(i);
                            Toast.makeText(LoginActivity.this,"Successfully logged in",Toast.LENGTH_SHORT).show();
                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }
    @Override
    public void onBackPressed(){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

}//okay now to home is it??
//which contains all fragments and navigation bar??yeah okay open wait
//mention its name here we will put intent here now only if need we will chnage it later

