package com.Deals.Offers.FreePromos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class PostsFragment extends Fragment {


    final int[][] a=new int[5][100];//for types of deals
    final int[][] b=new int[3][100];//for types of categories
    public Button steald,lootd,bestd,priced,noned,ele,kit,fas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LayoutInflater lf = getActivity().getLayoutInflater();
        View view =lf.inflate(R.layout.fragment_posts,container, false);

//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//
//        DatabaseReference myRef = database.getReference("promos");
//        final DatabaseReference myReflikes= database.getReference("likes");
//        myRef.keepSynced(true);
//        myReflikes.keepSynced(true);
//        //intialization for condition check
//        a[0][0]=000;
//        a[1][0]=000;
//        a[2][0]=000;
//        a[3][0]=000;
//        a[4][0]=000;
//        b[0][0]=000;
//        b[1][0]=000;
//        b[2][0]=000;
//        final ArrayList<HashMap<String, String>> al = new ArrayList<>();//for storing promos
//        final ArrayList<String> postkeys=new ArrayList<>();
//
//        final cadpt ad = new cadpt(getActivity(), al);//adapter
//        final ListView lv = (ListView)view.findViewById(R.id.listread);
//        lv.setAdapter(ad);
//
//        ///manage all the following buttons anywhere else in code
//        lootd=(Button)view.findViewById(R.id.loo);//loot button clicked
//        lootd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,0,lv,0);
//            }
//        });//for getting loot deals
//
//        steald=(Button)view.findViewById(R.id.steal);
//        steald.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,1,lv,0);
//            }
//        });//for getting steal deals
//
//        bestd=(Button)view.findViewById(R.id.best);
//        bestd.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,2,lv,0);
//            }
//        }); //for getting best deals
//
//
//        priced=(Button)view.findViewById(R.id.price);
//        priced.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,3,lv,0);
//            }
//        }); //for getting price drops
//
//        noned=(Button)view.findViewById(R.id.none);
//        noned.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,4,lv,0);
//            }
//        });//for deal tyep as none
//
//        ele=(Button)view.findViewById(R.id.elec);
//        ele.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,0,lv,1);
//            }
//        });//for electronics catogory
//
//        kit=(Button)view.findViewById(R.id.kitch);
//        kit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,2,lv,1);
//            }
//        });//for kitchen category
//
//
//        fas=(Button)view.findViewById(R.id.fash);
//        fas.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dealv(al,1,lv,1);
//            }
//        });//for fashion category
//
//        ///NOTE::::: copy paste the category code for generating more categorys
//        /// like electronics etc the code exp is alrdy available above
//
//
//        if(x!=100){
//            dealv(al,x,lv,y);
//        }
////        TextView txtListChild = (TextView)getView().findViewById(R.id.lblListItem);
////        txtListChild.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                TextView t = (TextView)v;
////                if(t.getText()=="Electronics"){
////                    dealv(al,0,lv,1);
////                }
////                else if(t.getText()=="Kitchenware"){
////                    dealv(al,2,lv,1);
////                }
////                else if(t.getText()=="Fashion"){
////                    dealv(al,1,lv,1);
////                }
////                else if(t.getText()=="Loot deal"){
////                    dealv(al,0,lv,0);
////                }
////                else if(t.getText()=="Steal deal"){
////                    dealv(al,1,lv,0);
////                }
////                else if(t.getText()=="Best deal"){
////                    dealv(al,2,lv,0);
////                }
////                else if(t.getText()=="Price drop"){
////                    dealv(al,3,lv,0);
////                }
////                else if(t.getText()=="None"){
////                    dealv(al,4,lv,0);
////                }
////                else{
////
////                }
////
////            }
////        });
//        ExpandableListView elv = (ExpandableListView)view.findViewById(R.id.left_drawer);
//
//        elv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
//
//                if(groupPosition==0&&childPosition==0){
//                    Log.e("0-0","======clicked");
//                }
//                else if(groupPosition==0&&childPosition==1){
//
//                }
//                else if(groupPosition==0&&childPosition==2){
//
//                }
//                else if(groupPosition==1&&childPosition==0){
//
//                }
//                else if(groupPosition==1&&childPosition==1){
//
//                }
//                else if(groupPosition==1&&childPosition==2){
//
//                }
//                else if(groupPosition==1&&childPosition==3){
//
//                }
//                else if(groupPosition==1&&childPosition==4){
//
//                }
//                return true;
//            }
//        });
//
//        myRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                HashMap<String, String> k, l;
//                k = (HashMap<String, String>) dataSnapshot.getValue();
//                k.put("likes","0");
//                k.put("dislikes","0");
//                al.add(k);
//                String p=k.get("dealtype");//to get the deal type to store in indexes
//                String q=k.get("category");//to get category type to store in indexes
//                postkeys.add(k.get("pushkey"));
//                int j=al.indexOf(k);
//                int w=postkeys.indexOf(k.get("pushkey"));
//                Log.e("pk index is---"+w,"value is"+k.get("pushkey"));
//                dealverify(p,j);//for storing the index in respective deals array
//                catverify(q,j);//for storing the index in respective categories array
//                ad.notifyDataSetChanged();//notifing adapter chnage
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                HashMap<String, String> k, l;
//                k = (HashMap<String, String>) dataSnapshot.getValue();
//                postkeys.remove(k.get("pushkey"));
//                al.remove(k);
//                myReflikes.child(k.get("pushkey")).removeValue();
//                ad.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //when a specified item in list is clicked
//                //its not fully written just use ui to display whatever u want i just passes the req object retrive data from it if needed
//                HashMap<String,String> item = (HashMap<String,String>)parent.getItemAtPosition(position);
//                Intent intent = new Intent(getActivity(), Details.class);
//                intent.putExtra("hashmap",item);//passing item clicked to next intent use it their to display info u need
//                startActivity(intent);
//            }
//        });
//
//        myReflikes.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Log.e(al.size()+"--","sizes---"+postkeys.size());
//                String key=dataSnapshot.getKey().toString();
//                int index=postkeys.indexOf(key);
//                HashMap<String,String> k;
//                if(index!=-1){
//                    k=al.get(index);
//                    long z=dataSnapshot.getChildrenCount();
//                    k.put("likes",(z)+"");
//                    Log.e("for key--",""+k.get("pushkey"));
//                    Log.e("dis likes in ac--",""+(z));
//                    al.set(index,k);
//                    ad.notifyDataSetChanged();
//                }
//
//                //int z=Integer.parseInt(k.get("likes"));
//
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                long p=dataSnapshot.getChildrenCount();
//
//                String key=dataSnapshot.getKey().toString();
//                int index=postkeys.indexOf(key);
//                HashMap<String,String> k=al.get(index);
//                //int z=Integer.parseInt(k.get("likes"));
//                k.put("likes",(p)+"");
//                Log.e("for key--",""+k.get("pushkey"));
//                Log.e("dis likes in ac--",""+(p));
//                al.set(index,k);
//                ad.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                String key=dataSnapshot.getKey().toString();
//                int index=postkeys.indexOf(key);
//                if(index!=-1){
//                    HashMap<String,String> k=al.get(index);
//                    int z=Integer.parseInt(k.get("likes"));
//                    k.put("likes",(0)+"");
//                    Log.e("dis likes in ac--",""+(0));
//                    al.set(index,k);
//                    ad.notifyDataSetChanged();
//                }
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        return view;

    }

//    public void hey(ArrayList<HashMap<String,String>> alo, ListView lv){
//        cadpt ad2 = new cadpt(getActivity(), alo);//creating a adapter for click of category or deal type
//        lv.setAdapter(ad2);//setting adapter
//    }
//    public void dealv(ArrayList<HashMap<String,String>> al,int i,ListView lv,int z){
//        ArrayList<HashMap<String, String>> alo = new ArrayList<>();
//        if(z==0){   // checking at which index we shld add the incoming index(Deals)
//            for(int j=0;a[i][j]!=000;j++){
//                alo.add(al.get(a[i][j]));
//            }
//        }
//        else if(z==1){  //checking at which index we shld add the incoming index(Categories)
//            for(int j=0;b[i][j]!=000;j++){
//                alo.add(al.get(b[i][j]));
//            }
//        }
//        hey(alo,lv);//described above
//    }
//    public void dealverify(String s,int j){ //to match with req deal
//        switch (s){
//            case "Loot deal":
//                dealdo(0,j,0);
//                break;
//            case "Steal deal":
//                dealdo(1,j,0);
//                break;
//            case "Best deal":
//                dealdo(2,j,0);
//                break;
//            case "Price drop":
//                dealdo(3,j,0);
//                break;
//            case "None":
//                dealdo(4,j,0);
//                break;
//        }
//    }
//    public void catverify(String s,int j){// to match with req category
//        switch (s){
//            case "Electronics":
//                dealdo(0,j,1);
//                break;
//            case "Fashion":
//                dealdo(1,j,1);
//                break;
//            case "KichenWare":
//                dealdo(2,j,1);
//                break;
//        }
//    }
//    public void dealdo(int k,int j,int x){ //to update the indexes
//        int i=0;
//        if(x==0){
//            while(a[k][i]!=000){
//                i++;
//            }
//            a[k][i]=j;
//            a[k][i+1]=000;
//        }
//        else if(x==1){
//            while(b[k][i]!=000){
//                i++;
//            }
//            b[k][i]=j;
//            b[k][i+1]=000;
//        }
//    }
//


}



