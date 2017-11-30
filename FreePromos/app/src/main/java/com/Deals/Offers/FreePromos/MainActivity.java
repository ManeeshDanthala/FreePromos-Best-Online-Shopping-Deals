package com.Deals.Offers.FreePromos;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.Deals.Offers.FreePromos.R.menu.main;

//this is the main activity too long ...wait    how the hell we have to write
// wait we have fragments
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//yo

    volatile ArrayList<HashMap<String,String>> ppppp;
    List<String> listDataHeader;
    HashMap<String,List<String>> listDataChild;
    private static final int GALLERY_INTENT=2;
    final int[][] a=new int[6][10000];//for types of deals
    final int[][] b=new int[9][10000];//for types of categories
    public static int x=100,y=100;
    String sharelinkperminent="";
    List<String> myArrayList=new ArrayList<String>();
    ExpandableListView  expListView;
    String imppp="";
    int wid,hei;
    ExpandListAdapter listAdapter;
    private FirebaseAuth mAuth;
    boolean doubleBackToExitPressedOnce= false;
    //    FirebaseStorage storage = FirebaseStorage.getInstance();
//    StorageReference storageRef = storage.getReferenceFromUrl("gs://bestdeals2-25493.appspot.com");
    private void prepareListData(List<String> listDataHeader, Map<String,
            List<String>> listDataChild) {


        // Adding child data
        listDataHeader.add("Categories");
//        listDataHeader.add("Deals");



        // Adding child data
        List<String> top = new ArrayList<String>();
        top.add("Home");//0
        top.add("Electronics");//1
        top.add("Fashion");//2
        top.add("Appliances");//3
        top.add("Recharge");//4
        top.add("Food");//5
        top.add("Grocery&Pantry");//6
        top.add("Movies");//7
        top.add("Travel");//8
        top.add("Others");//9

//        List<String> mid = new ArrayList<String>();
//        mid.add("Loot deal");//0
//        mid.add("Steal deal");//1
//        mid.add("Best deal");//2
//        mid.add("Price drop");//3
//        mid.add("Others");//4


        listDataChild.put(listDataHeader.get(0), top); // Header, Child data
        //  listDataChild.put(listDataHeader.get(1), mid);

    }
    private void enableExpandableList() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        expListView = (ExpandableListView) findViewById(R.id.left_drawer);

        prepareListData(listDataHeader, listDataChild);
        listAdapter = new ExpandListAdapter(this, listDataHeader, listDataChild);
        // setting list adapter
        expListView.setAdapter(listAdapter);

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });
        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });


        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                // Temporary code:

                // till here
                Toast.makeText(
                        getApplicationContext(),
                        listDataHeader.get(groupPosition)
                                + " : "
                                + listDataChild.get(
                                listDataHeader.get(groupPosition)).get(
                                childPosition), Toast.LENGTH_SHORT)
                        .show();
                return false;
            }
        });}


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            displayView(item.getItemId(),1);
            return true;


        }

    };

    private void setListViewHeight(ExpandableListView listView,
                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }
    private boolean viewIsAtHome;

    //onclick method of image on posts fragment
    public void gotodetails(View view){
        Intent k = new Intent(this,Postdetails.class);
        startActivity(k);
    }

    public void displayALLPOSTs(){
        cadpt ad3 = new cadpt(this, al,wid,hei);
        TextView no_posts2=(TextView) findViewById(R.id.no_posts);
        if(!al.isEmpty()){
            //creating a adapter for click of category or deal type
            no_posts2.setVisibility(View.GONE);
            //   lv.setAdapter(ad);
        }
        else{
            no_posts2.setVisibility(View.VISIBLE);
            no_posts2.setText("No Deals in this category at the moment");
            // lv.setAdapter(ad);
        }
        lv.setAdapter(ad3);
    }
    public void displayView(int viewId,int flag) {

        Fragment fragment = null;
        String title = getString(R.string.app_name);
        LinearLayout lvvv = (LinearLayout)findViewById(R.id.bannerlayout1);
//        GridView gv = (GridView) findViewById(R.id.listread);


        switch (viewId) {
            case R.id.navigation_Groupchat:
                fragment = new ChatFragment();
                lvvv.setVisibility(View.GONE);
                viewIsAtHome = false;
                break;
            case R.id.navigation_Posts_sample:
                if(flag==1){displayALLPOSTs();}
                fragment = new PostsFragment();
                lvvv.setVisibility(View.VISIBLE);
                viewIsAtHome = true;
                break;

            case R.id.navigation_notifications:
                fragment = new MainFragment();
                lvvv.setVisibility(View.GONE);
                viewIsAtHome = false;
                break;

        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content, fragment);
            ft.commit();
        }


    }
    //yo
//cheyyana ippudule vaddu le ipud clg srt ainaka chedam

//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //uploading pic
//        super.onActivityResult(requestCode, resultCode, data);
//        Log.e("onactiv is called Main","");
//        SharedPreferences prefs = this.getSharedPreferences("profiledata", Context.MODE_PRIVATE);
//        final String uid= prefs.getString("id", "456");
//
//        if(requestCode==GALLERY_INTENT && resultCode==RESULT_OK){
//            Uri uri=data.getData();
//             final String timenow=(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime())).toString();
//            StorageReference filepath=storageRef.child(uid+"-"+timenow);
//            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                @Override
//                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                    Toast.makeText(MainActivity.this,"upload done",Toast.LENGTH_LONG);
//                    storageRef.child(uid+"-"+timenow).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                        @Override
//                        public void onSuccess(Uri uri) {
//                            // Got the download URL for 'users/me/profile.png'
//                            Log.e("uchat-",uri+""); /// The string(file link) that you need
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception exception) {
//                            // Handle any errors
//                            Log.e("fail in up of chat img","");
//                        }
//                    });
//                }
//            });
//        }
//    }

    final ArrayList<HashMap<String, String>> al = new ArrayList<>();//for storing promos
    final ArrayList<String> postkeys=new ArrayList<>();
    ArrayList<HashMap<String, String>> bannerlist = new ArrayList<>();
    GridView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference bannerRef = database.getReference("banner");
        bannerRef.keepSynced(true);
        //SliderMethod(bannerlist);
        //sliderm(bannerlist);
        Log.e("it is after ban method","");
        bannerRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final HashMap<String,String> bannerhash=(HashMap<String,String>)dataSnapshot.getValue();
                bannerhash.put("keyis",dataSnapshot.getKey());
                bannerlist.add(bannerhash);
                stickbanner(bannerlist);
                //
                //bannerlist.add(bannerhash);
                //SliderMethod(bannerlist);
                //sliderm(bannerlist);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                HashMap<String,String> bannerhash=(HashMap<String,String>)dataSnapshot.getValue();
                bannerhash.put("keyis",dataSnapshot.getKey());
                bannerlist.remove(bannerhash);
                stickbanner(bannerlist);
                //bannerhash.put("keyis",dataSnapshot.getKey());
                //bannerlist.remove(bannerhash);
                //SliderMethod(bannerlist);
                //sliderm(bannerlist);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





//
        FirebaseMessaging.getInstance().subscribeToTopic("deals_notification");

        GridView kvb =(GridView) findViewById(R.id.listread);
        LinearLayout ll=(LinearLayout) findViewById(R.id.postlayout);
        ImageView iv =(ImageView) findViewById(R.id.img25);
        wid = getResources().getDisplayMetrics().widthPixels/2;
        hei=(getResources().getDisplayMetrics().heightPixels/5);
        Log.e("wid--",""+(wid*2));
        Log.e("hei--",(5*hei)+"");
//        try {
//           // kvb.setLayoutParams(new FrameLayout.LayoutParams(width, hei));
//            iv.setLayoutParams(new RelativeLayout.LayoutParams(wid, hei));
//            ll.setLayoutParams(new RelativeLayout.LayoutParams(wid, hei));
//        }catch (Exception e){
//            Log.e("layout--",e.getMessage());
//        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        double wi=(double)width/(double)dm.xdpi;
        double hi=(double)height/(double)dm.ydpi;
        double x = Math.pow(wi,2);
        double y = Math.pow(hi,2);
        double screenInches = Math.sqrt(x+y);
        Log.e("screen size---",screenInches+"");







        TextView tv2 = (TextView) findViewById(R.id.nav_profile);
        TextView share = (TextView) findViewById(R.id.nav_share_app);
        TextView set = (TextView) findViewById(R.id.nav_settings);
        TextView wri = (TextView) findViewById(R.id.write);
        TextView dele = (TextView) findViewById(R.id.delete);
        TextView logout = (TextView) findViewById(R.id.logout);
        TextView home3=(TextView) findViewById(R.id.nav_home);
        TextView liked3=(TextView) findViewById(R.id.nav_liked);
        TextView about3=(TextView) findViewById(R.id.aboutus);



        DatabaseReference sharelink= database.getReference("shareapplink");
        sharelink.keepSynced(true);
        sharelink.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //HashMap<String,String> bsg=(HashMap<String, String>)
                sharelinkperminent=(String)dataSnapshot.getValue();
                Log.e("sahre link",sharelinkperminent);
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


        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ab=sharelinkperminent;
                if(!ab.equals("")){
                    if (!ab.startsWith("http://") && !ab.startsWith("https://")){
                        ab = "http://" + ab;
                    }
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, ab);
                    sendIntent.setType("text/plain");
                    startActivity(sendIntent);
                }
            }
        });






        LinearLayout logoutlayout2=(LinearLayout) findViewById(R.id.logoutlayout);
        LinearLayout sharelayout2=(LinearLayout) findViewById(R.id.sharelayout);
        if(screenInches<4.9){
            sharelayout2.setVisibility(View.GONE);
        }
        if(screenInches<5.15){
            logoutlayout2.setVisibility(View.GONE);
        }


//        tv2.setTypeface(null, Typeface.BOLD);
//        share.setTypeface(null, Typeface.BOLD);
//        set.setTypeface(null, Typeface.BOLD);
//        wri.setTypeface(null,Typeface.BOLD);
//        dele.setTypeface(null,Typeface.BOLD);
//        logout.setTypeface(null,Typeface.BOLD);


        // adding expandable listview to left navigation drawer
        enableExpandableList();

        about3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,About_Page.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        dele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent d = new Intent(MainActivity.this,DeleteActivity.class);
                startActivity(d);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,Details.class);
                startActivity(p);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        wri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent p = new Intent(MainActivity.this,WriteActivity.class);
                startActivity(p);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });
        final Prefs pref;
        pref = Application.getApp().getPrefs();
        pref.setLoginStatus(true);
        pref.setspb(true);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(MainActivity.this);
                }
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to Logout?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with logout
                                pref.setLoginStatus(false);
                                SharedPreferences myPrefs = getSharedPreferences("profiledata", MODE_PRIVATE);
                                SharedPreferences.Editor editor = myPrefs.edit();
                                editor.clear();
                                editor.commit();
                                startActivity(new Intent(MainActivity.this,LoginActivity.class));
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
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        mAuth = FirebaseAuth.getInstance();
        SharedPreferences prefs = getSharedPreferences("profiledata", Context.MODE_PRIVATE);
        String sp=prefs.getString("id", "notfound");



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.user100);
        de.hdodenhof.circleimageview.CircleImageView imagepro2=(de.hdodenhof.circleimageview.CircleImageView)hView.findViewById(R.id.etuserimage);
        nav_user.setText(prefs.getString("username","username"));

        if(!prefs.getString("profileurl","null").equals("null")){
            Glide.with(MainActivity.this)
                    .load(prefs.getString("profileurl","null"))
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imagepro2);
            Log.e("in pro pic-","if");
            Log.e("--",prefs.getString("profileurl","null"));
        }



        Log.e("uid--",sp);

        LinearLayout wr = (LinearLayout) findViewById(R.id.writelayout);
        LinearLayout de = (LinearLayout) findViewById(R.id.deletelayout);
//        if(!sp.equals("7MEliPW0jzXza4kA73SR27PYbCL2")){
//            wr.setVisibility(View.GONE);
//            de.setVisibility(View.GONE);
//
//        }
        switch (sp){
            case "HmNhhcpNSCOY0An015JD6bIBCZq1":
                break;
            case "euoiIdejw5MutrbydNWfVM4cYVL2":
                break;
            case "jCP7FhY5x9RAfgHgIJDuPiYL4Yh1":
                break;
            default:
                wr.setVisibility(View.GONE);
                de.setVisibility(View.GONE);
                break;
        }

//        if(!sp.equals("7MEliPW0jzXza4kA73SR27PYbCL2")){
//            wr.setVisibility(View.GONE);
//            de.setVisibility(View.GONE);
//        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view); //already decalred
        navigationView.setNavigationItemSelectedListener(this);


        DatabaseReference myRef = database.getReference("promos");
        final DatabaseReference myReflikes = database.getReference("likes");
        myRef.keepSynced(true);
        myReflikes.keepSynced(true);
        //intialization for condition check
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

        //ppppp=reverse(al);
        final cadpt ad = new cadpt(this, al,wid,hei);//adapter
        //cad1=ad;
        lv = (GridView) findViewById(R.id.listread);
        TextView no_posts2=(TextView) findViewById(R.id.no_posts);
        if(!al.isEmpty()){
            //creating a adapter for click of category or deal type

            no_posts2.setVisibility(View.GONE);
            lv.setAdapter(ad);
        }
        else{
            no_posts2.setVisibility(View.VISIBLE);
            no_posts2.setText("No Deals in this category at the moment");
            lv.setAdapter(ad);
        }



        ///manage all the following buttons anywhere else in code
//        lootd=(Button) findViewById(R.id.loo);//loot button clicked
//        lootd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,0,lv,0);
//            }
//        });//for getting loot deals
//
//        steald=(Button) findViewById(R.id.steal);
//        steald.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,1,lv,0);
//            }
//        });//for getting steal deals
//
//        bestd=(Button) findViewById(R.id.best);
//        bestd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,2,lv,0);
//            }
//        }); //for getting best deals
//
//
//        priced=(Button) findViewById(R.id.price);
//        priced.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,3,lv,0);
//            }
//        }); //for getting price drops
//
//        noned=(Button) findViewById(R.id.none);
//        noned.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,4,lv,0);
//            }
//        });//for deal tyep as none
//
//        ele=(Button) findViewById(R.id.elec);
//        ele.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,0,lv,1);
//            }
//        });//for electronics catogory
//
//        kit=(Button) findViewById(R.id.kitch);
//        kit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,2,lv,1);
//            }
//        });//for kitchen category
//
//        fas=(Button) findViewById(R.id.fash);
//        fas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,1,lv,1);
//            }
//        });//for fashion category


        ///NOTE::::: copy paste the category code for generating more categorys
        /// like electronics etc the code exp is alrdy available above


        ExpandableListView elv = (ExpandableListView) findViewById(R.id.left_drawer);
//        LinearLayout c1 =(LinearLayout) elv.getExpandableListAdapter().getChild(0,0);
//        TextView cat=(TextView)c1.getChildAt(0);
////        Drawable img = getContext().getResources().getDrawable( R.drawable.amazon_icon );
////        img.setBounds( 0, 0, 60, 60 );
//        cat.setCompoundDrawablesWithIntrinsicBounds( R.drawable.amazon_icon, 0, 0, 0);


        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);

                if (groupPosition == 0 && childPosition == 1) {
                    Log.e("0-0", "======clicked");
                    dealv(al, 0, lv, 1);
                } else if (groupPosition == 0 && childPosition == 2) {
                    dealv(al, 1, lv, 1);
                } else if (groupPosition == 0 && childPosition == 3) {
                    dealv(al, 2, lv, 1);
                }else if (groupPosition == 0 && childPosition == 4) {
                    dealv(al, 3, lv, 1);
                }else if (groupPosition == 0 && childPosition == 5) {
                    dealv(al, 4, lv, 1);
                }else if (groupPosition == 0 && childPosition == 6) {
                    dealv(al, 5, lv, 1);
                }else if (groupPosition == 0 && childPosition == 7) {
                    dealv(al, 6, lv, 1);
                }else if(groupPosition==0 && childPosition == 0){
                    //dealv();
                    TextView no_posts2=(TextView) findViewById(R.id.no_posts);
                    if(!al.isEmpty()){
                        //creating a adapter for click of category or deal type

                        no_posts2.setVisibility(View.GONE);
                        lv.setAdapter(ad);
                    }
                    else{
                        no_posts2.setVisibility(View.VISIBLE);
                        no_posts2.setText("No Deals in this category at the moment");
                        lv.setAdapter(ad);
                    }

                }
                else if (groupPosition == 0 && childPosition == 8) {
                    dealv(al, 7, lv, 1);
                }
                else if (groupPosition == 0 && childPosition == 9) {
                    dealv(al, 8, lv, 1);
                }
//                else if (groupPosition == 1 && childPosition == 0) {
//                    dealv(al, 0, lv, 0);
//                } else if (groupPosition == 1 && childPosition == 1) {
//                    dealv(al, 1, lv, 0);
//                } else if (groupPosition == 1 && childPosition == 2) {
//                    dealv(al, 2, lv, 0);
//                } else if (groupPosition == 1 && childPosition == 3) {
//                    dealv(al, 3, lv, 0);
//                } else if(groupPosition == 1 && childPosition == 4) {
//                    dealv(al, 4, lv, 0);
//                }
                navigation.getMenu().getItem(0).setChecked(true);
                displayView(R.id.navigation_Posts_sample,0);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        home3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView no_posts2=(TextView) findViewById(R.id.no_posts);
                if(!al.isEmpty()){
                    //creating a adapter for click of category or deal type
                    no_posts2.setVisibility(View.GONE);
                    //   lv.setAdapter(ad);
                }
                else{
                    no_posts2.setVisibility(View.VISIBLE);
                    no_posts2.setText("No Deals in this category at the moment");
                    // lv.setAdapter(ad);
                }
                lv.setAdapter(ad);

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.getMenu().getItem(0).setChecked(true);
                displayView(R.id.navigation_Posts_sample,0);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });

        liked3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs54=getSharedPreferences("yourPrefsKey", Context.MODE_PRIVATE);
                retrivedata(prefs54);
                final ArrayList<HashMap<String, String>> alaa = new ArrayList<>();
                for(HashMap<String,String> ij:al){
                    if(myArrayList.contains(ij.get("p"))){
                        alaa.add(ij);
                    }
                }
                final cadpt kl = new cadpt(MainActivity.this, alaa,wid,hei);//adapter

                TextView no_posts2=(TextView) findViewById(R.id.no_posts);
                if(!alaa.isEmpty()){
                    //creating a adapter for click of category or deal type

                    no_posts2.setVisibility(View.GONE);

                    //   lv.setAdapter(ad);
                }
                else{
                    no_posts2.setVisibility(View.VISIBLE);
                    no_posts2.setText("No Liked Posts right now");
                    // lv.setAdapter(ad);
                }
                lv.setAdapter(kl);
                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.getMenu().getItem(0).setChecked(true);
                displayView(R.id.navigation_Posts_sample,0);
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }
        });



        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final HashMap<String, String> k;
                k = (HashMap<String, String>) dataSnapshot.getValue();
                if(k.get("v")!=null){
                    k.put("likes", "0");
                    k.put("dislikes", "0");

                    FirebaseStorage storage = FirebaseStorage.getInstance();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://bestdeals2-25493.appspot.com");

                   /* final ArrayList<String> arrayList=new ArrayList<String>();
                    storageRef.child(k.get("p")).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Got the download URL for 'users/me/profile.png'
                            Log.e("u-",uri.toString()); /// The string(file link) that you need
                            k.put("url",uri.toString());

                            //z(uri.toString());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                            Log.e("uri--","failed");
                        }
                    });*/
                    //Log.e("in main-",k.get("url"));
                    al.add(k);
                    int p = Integer.parseInt(k.get("v").charAt(2)+"");//to get the deal type to store in indexes
                    int q = Integer.parseInt(k.get("v").charAt(0)+"");//to get category type to store in indexes
                    postkeys.add(k.get("p"));
                    int j = al.indexOf(k);
                    int w = postkeys.indexOf(k.get("p"));
                    Log.e("pk index is---" + w, "value is" + k.get("p"));
                    //dealverify(p, j);
                    dealdo(p,j,0);//for storing the index in respective deals array
                    //catverify(q, j);//for storing the index in respective categories array
                    dealdo(q,j,1);
                    TextView no_posts2=(TextView) findViewById(R.id.no_posts);
                    if(!al.isEmpty()){
                        //creating a adapter for click of category or deal type

                        no_posts2.setVisibility(View.GONE);
                     //   lv.setAdapter(ad);
                    }
                    else{
                        no_posts2.setVisibility(View.VISIBLE);
                        no_posts2.setText("No Deals in this category at the moment");
                       // lv.setAdapter(ad);
                    }

                    ad.notifyDataSetChanged();//notifing adapter chnage

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
                al.remove(k);
                myReflikes.child(k.get("p")).removeValue();
                ad.notifyDataSetChanged();
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
                Log.e(al.size() + "--", "sizes---" + postkeys.size());
                String key = dataSnapshot.getKey().toString();
                int index = postkeys.indexOf(key);
                HashMap<String, String> k;
                if (index != -1) {
                    k = al.get(index);
                    long z = dataSnapshot.getChildrenCount();
                    k.put("likes", (z) + "");
                    Log.e("for key--", "" + k.get("p"));
                    Log.e("dis likes in ac--", "" + (z));
                    al.set(index, k);
                    ad.notifyDataSetChanged();
                }

                //int z=Integer.parseInt(k.get("likes"));

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                long p = dataSnapshot.getChildrenCount();

                String key = dataSnapshot.getKey().toString();
                int index = postkeys.indexOf(key);
                HashMap<String, String> k = al.get(index);
                //int z=Integer.parseInt(k.get("likes"));
                k.put("likes", (p) + "");
                Log.e("for key--", "" + k.get("p"));
                Log.e("dis likes in ac--", "" + (p));
                al.set(index, k);
                ad.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey().toString();
                int index = postkeys.indexOf(key);
                if (index != -1) {
                    HashMap<String, String> k = al.get(index);
                    int z = Integer.parseInt(k.get("likes"));
                    k.put("likes", (0) + "");
                    Log.e("dis likes in ac--", "" + (0));
                    al.set(index, k);
                    ad.notifyDataSetChanged();
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


    /* @Override
     public void onBackPressed() {
         DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
         if (drawer.isDrawerOpen(GravityCompat.START)) {
             drawer.closeDrawer(GravityCompat.START);
         } else {
             super.onBackPressed();
         }
     }
     */
    //ff
    public void hey(ArrayList<HashMap<String,String>> alo, GridView lv){
        //ppppp=reverse(alo);
        cadpt ad2 = new cadpt(this, alo,wid,hei);
        TextView no_posts2=(TextView) findViewById(R.id.no_posts);
        if(!alo.isEmpty()){
            //creating a adapter for click of category or deal type

            no_posts2.setVisibility(View.GONE);
        }
        else{
            no_posts2.setVisibility(View.VISIBLE);
            no_posts2.setText("No Deals in this category at the moment");
        }

        lv.setAdapter(ad2);//setting adapter
    }
    public void dealv(ArrayList<HashMap<String,String>> al,int i,GridView lv,int z){
        ArrayList<HashMap<String, String>> alo = new ArrayList<>();
        if(z==0){   // checking at which index we shld add the incoming index(Deals)
            for(int j=0;a[i][j]!=000;j++){
                alo.add(al.get(a[i][j]));
            }

        }
        else if(z==1){  //checking at which index we shld add the incoming index(Categories)
            for(int j=0;b[i][j]!=000;j++){
                alo.add(al.get(b[i][j]));
            }

        }
        hey(alo,lv);//described above
    }
    public void dealverify(String s,int j){ //to match with req deal
        switch (s){
            case "Loot deal":
                dealdo(0,j,0);
                break;
            case "Steal deal":
                dealdo(1,j,0);
                break;
            case "Best deal":
                dealdo(2,j,0);
                break;
            case "Price drop":
                dealdo(3,j,0);
                break;
            case "None":
                dealdo(4,j,0);
                break;
        }
    }
    public void catverify(String s,int j){// to match with req category
        switch (s){
            case "Electronics":
                dealdo(0,j,1);
                break;
            case "Fashion":
                dealdo(1,j,1);
                break;
            case "KichenWare":
                dealdo(2,j,1);
                break;
        }
    }
    public void dealdo(int k,int j,int x){ //to update the indexes
        int i=0;
        if(x==0){
            while(a[k][i]!=000){
                i++;
            }
            a[k][i]=j;
            a[k][i+1]=000;
        }
        else if(x==1){
            while(b[k][i]!=000){
                i++;
            }
            b[k][i]=j;
            b[k][i+1]=000;
        }
    }

    //ff
    @Override
    public void onBackPressed() {
        //int count = getFragmentManager().getBackStackEntryCount();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if (!viewIsAtHome) { //if the current view is not the News fragment
            BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
            navigation.getMenu().getItem(0).setChecked(true);
            displayView(R.id.navigation_Posts_sample,0); //display the News fragment
        } else {
            //If view is in News fragment, exit application
            if (doubleBackToExitPressedOnce) {
                moveTaskToBack(true);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }

            }, 2000);
        }
//        if(count==0){
//            super.onBackPressed();
//        }
//        else{
//            getFragmentManager().popBackStack();
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
         if(id== R.id.action_logout){
             AlertDialog.Builder builder;
             if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                 builder = new AlertDialog.Builder(MainActivity.this, android.R.style.Theme_Material_Dialog_Alert);
             } else {
                 builder = new AlertDialog.Builder(MainActivity.this);
             }
             builder.setTitle("Logout")
                     .setMessage("Are you sure you want to Logout?")
                     .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int which) {
                             // continue with logout
                             final Prefs pref;
                             pref = Application.getApp().getPrefs();
                             pref.setLoginStatus(false);
                             SharedPreferences myPrefs = getSharedPreferences("profiledata", MODE_PRIVATE);
                             SharedPreferences.Editor editor = myPrefs.edit();
                             editor.clear();
                             editor.commit();
                             startActivity(new Intent(MainActivity.this,LoginActivity.class));
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

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        GridView lv = (GridView)findViewById(R.id.listread);
        if (id == R.id.details) {
            // Handle the camera action
            Intent j = new Intent(this,Details.class);
            startActivity(j);
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        } else if (id == R.id.Posts) {
            Intent j = new Intent(this,Posts.class);
            startActivity(j);

        } else if (id == R.id.settings) {
            Intent j = new Intent(this,Settings.class);
            startActivity(j);

        } else if (id == R.id.nav_share) {

            dealv(al,0,lv,1);

        } else if (id == R.id.nav_extra) {

        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public ArrayList<HashMap<String,String>> reverse(ArrayList<HashMap<String,String>> alo) {
        ArrayList<HashMap<String,String>> list=new ArrayList<HashMap<String,String>>();
        Log.e("entered-","s");
        for(int i = 0, j = alo.size() - 1; i < j; i++) {
            list.add(i, alo.get(j));
            Log.e("reverse--",alo.get(j).get("r"));
        }
        return list;
    }
    public void z(String k){
        imppp=k;
    }

    public void retrivedata(SharedPreferences prefs){
        Set<String> set = prefs.getStringSet("yourKey", null);
        if(set!=null){
            myArrayList=new ArrayList<String>(set);
        }
    }



    public void stickbanner(final ArrayList<HashMap<String,String>> alpp){

        if(alpp.size()<=0){
            LinearLayout lp=(LinearLayout) findViewById(R.id.bannerlayout1);
            lp.setVisibility(View.GONE);
            Log.e("no layout","banner");
        }
        else{
            if(alpp.size()==1){
                Log.e("1 layout","banner");
                ImageView impv=(ImageView) findViewById(R.id.bannerimg1);
                Glide.with(getBaseContext())
                        .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+alpp.get(0).get("keyis")+"?alt=media&token="+alpp.get(0).get("furl"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(impv);
                impv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ab=alpp.get(0).get("link");
                        if (!ab.startsWith("http://") && !ab.startsWith("https://")){
                            ab = "http://" + ab;
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ab));
                        startActivity(i);
                    }
                });
                ImageView impv2=(ImageView) findViewById(R.id.bannerimg2);
                impv2.setVisibility(View.GONE);
            }
            else{
                Log.e("2 layout","banner");
                ImageView impv=(ImageView) findViewById(R.id.bannerimg1);
                Glide.with(getBaseContext())
                        .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+alpp.get(alpp.size()-1).get("keyis")+"?alt=media&token="+alpp.get(alpp.size()-1).get("furl"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(impv);
                impv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ab=alpp.get(alpp.size()-1).get("link");
                        if (!ab.startsWith("http://") && !ab.startsWith("https://")){
                            ab = "http://" + ab;
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ab));
                        startActivity(i);
                    }
                });
                ImageView impv2=(ImageView) findViewById(R.id.bannerimg2);
                Glide.with(getBaseContext())
                        .load("https://firebasestorage.googleapis.com/v0/b/bestdeals2-25493.appspot.com/o/"+alpp.get(alpp.size()-2).get("keyis")+"?alt=media&token="+alpp.get(alpp.size()-2).get("furl"))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(impv2);
                impv2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String ab=alpp.get(alpp.size()-2).get("link");
                        if (!ab.startsWith("http://") && !ab.startsWith("https://")){
                            ab = "http://" + ab;
                        }
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(ab));
                        startActivity(i);
                    }
                });
                impv2.setVisibility(View.VISIBLE);
            }
        }
    }

}

