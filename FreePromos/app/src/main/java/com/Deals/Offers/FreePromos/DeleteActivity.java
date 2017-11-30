package com.Deals.Offers.FreePromos;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;

public class DeleteActivity extends AppCompatActivity {

    final int[][] a = new int[6][100];//for types of deals
    final int[][] b = new int[9][100];
    int wid;
    int hei;
    private StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("promos");
        final DatabaseReference myReflikes = database.getReference("likes");

        final ArrayList<HashMap<String, String>> alv = new ArrayList<>();//for storing promos
        final ArrayList<String> postkeys = new ArrayList<>();

        myRef.keepSynced(true);
        myReflikes.keepSynced(true);

        a[0][0] = 000;
        a[1][0] = 000;
        a[2][0] = 000;
        a[3][0] = 000;
        a[4][0] = 000;
        a[5][0] = 000;
        b[0][0] = 000;
        b[1][0] = 000;
        b[2][0] = 000;
        b[3][0] = 000;
        b[4][0] = 000;
        b[5][0] = 000;
        b[6][0] = 000;
        b[7][0] = 000;
        b[8][0] = 000;

        wid = getResources().getDisplayMetrics().widthPixels / 2;
        hei = (getResources().getDisplayMetrics().heightPixels / 5);

        final cadptdt ad1 = new cadptdt(this, alv, wid, hei);//adapter
        final GridView lv = (GridView) findViewById(R.id.listread2);
        lv.setAdapter(ad1);
        lv.setAdapter(ad1);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HashMap<String, String> k;
                k = (HashMap<String, String>) dataSnapshot.getValue();
                if (k.get("v") != null) {
                    k.put("likes", "0");
                    k.put("dislikes", "0");
                    alv.add(k);

                    postkeys.add(k.get("p"));
                    int j = alv.indexOf(k);
                    int w = postkeys.indexOf(k.get("p"));
                    Log.e("pk index is---" + w, "value is" + k.get("p"));
                    //dealverify(p, j);
                    // dealdo(p,j,0);//for storing the index in respective deals array
                    //catverify(q, j);//for storing the index in respective categories array
                    //dealdo(q,j,1);
                    ad1.notifyDataSetChanged();//notifing adapter chnage
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap<String, String> k;
                k = (HashMap<String, String>) dataSnapshot.getValue();
                postkeys.remove(k.get("p"));
                alv.remove(k);
                myReflikes.child(k.get("p")).removeValue();
                ad1.notifyDataSetChanged();
                startActivity(new Intent(DeleteActivity.this, MainActivity.class));
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myReflikes.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.e(alv.size() + "--", "sizes---" + postkeys.size());
                String key = dataSnapshot.getKey();
                int index = postkeys.indexOf(key);
                HashMap<String, String> k;
                if (index != -1) {
                    k = alv.get(index);
                    long z = dataSnapshot.getChildrenCount();
                    k.put("likes", (z) + "");
                    Log.e("for key--", "" + k.get("p"));
                    Log.e("dis likes in ac--", "" + (z));
                    alv.set(index, k);
                    ad1.notifyDataSetChanged();
                }

                //int z=Integer.parseInt(k.get("likes"));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                long p = dataSnapshot.getChildrenCount();

                String key = dataSnapshot.getKey();
                int index = postkeys.indexOf(key);
                HashMap<String, String> k = alv.get(index);
                //int z=Integer.parseInt(k.get("likes"));
                k.put("likes", (p) + "");
                Log.e("for key--", "" + k.get("p"));
                Log.e("dis likes in ac--", "" + (p));
                alv.set(index, k);
                ad1.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                int index = postkeys.indexOf(key);
                if (index != -1) {
                    HashMap<String, String> k = alv.get(index);
                    int z = Integer.parseInt(k.get("likes"));
                    k.put("likes", (0) + "");
                    Log.e("dis likes in ac--", "" + (0));
                    alv.set(index, k);
                    ad1.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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

    public void removingpost(final View view) {

        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(DeleteActivity.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(DeleteActivity.this);
        }
        builder.setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        final DatabaseReference myRef = database.getReference("promos");
                        //
                        final LinearLayout vwParentRow = (LinearLayout) view.getParent();
                        TextView child = (TextView) vwParentRow.getChildAt(2);
                        final String key = child.getText().toString();

                        TextView child2 = (TextView) vwParentRow.getChildAt(3);
                        String u2 = child2.getText().toString();
                        final FirebaseStorage mFirebaseStorage = FirebaseStorage.getInstance().getReference().getStorage();
                        StorageReference photoRef = mFirebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/" + key + "?alt=media&token=" + u2);
//                        StorageReference deleteFile = mStorageRef.child("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+key+"?alt=media&token="+u2);
                        photoRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                TextView child23 = (TextView) vwParentRow.getChildAt(5);
                                String u23 = child23.getText().toString();
                                if (!u23 .equals("")) {
                                    StorageReference photoRef4 = mFirebaseStorage.getReferenceFromUrl("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+key+"pr"+"?alt=media&token=" + u23);

                                    Log.e("u23 print-",u23+"");
                                    photoRef4.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            myRef.child(key).removeValue();
                                            Toast.makeText(DeleteActivity.this, "Deal deleted", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Uh-oh, an error occurred!
                                            Log.d("Deal is not deleted", "onFailure: did not delete file");
                                            Toast.makeText(DeleteActivity.this, "Deal not deleted due to provide image", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                                else{
                                    myRef.child(key).removeValue();
                                    Toast.makeText(DeleteActivity.this, "Deal deleted", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Uh-oh, an error occurred!
                                Log.d("Deal is not deleted", "onFailure: did not delete file");
                                Toast.makeText(DeleteActivity.this, "Deal not deleted", Toast.LENGTH_SHORT).show();
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
