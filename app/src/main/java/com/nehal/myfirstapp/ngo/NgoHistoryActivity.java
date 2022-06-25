package com.nehal.myfirstapp.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.nehal.myfirstapp.DonationList;
import com.nehal.myfirstapp.R;

import java.util.ArrayList;

public class NgoHistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<DonationList> list;
    NgoHistoryAdapter ngoHistoryAdapter;

    private StorageReference storageReference;
    FirebaseAuth fAuth;
    String curreuntUser;
    String key;


    private UploadListAdapter uploadListAdapter;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_history);

        recyclerView = findViewById(R.id.allhistory);



        fAuth = FirebaseAuth.getInstance();
        curreuntUser = fAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("Donations");


        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        Log.d("TAG","newsss"+curreuntUser);

        list = new ArrayList<>();
        ngoHistoryAdapter = new NgoHistoryAdapter(this,list);
        recyclerView.setAdapter(ngoHistoryAdapter);

//        databaseReference.orderByChild("state").equalTo(3).addValueEventListener(new ValueEventListener() {
//        databaseReference.orderByChild("read_sender").equalTo("false_"+datum.getEmployer().getEmployerId());
        ////////////////////retrive data to list View////////////////////////////////////////////////////
        databaseReference.orderByChild("connectingkey").equalTo(curreuntUser+3).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {


//                    Log.d("GetDonorId","DataSnapshot :"+dataSnapshot.getKey());

                    String key = dataSnapshot.getKey();

//                 key = dataSnapshot.getKey();

                    Log.d("GetDonorId","DataSnapshot :"+key);

                    DonationList donationList = dataSnapshot.getValue(DonationList.class);
                    list.add(donationList);
//                    list.add(dataSnapshot.getKey());
                }
                ngoHistoryAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    public void backaction(View view) {
        Intent intent = new Intent(NgoHistoryActivity.this,NgoHomeActivity.class);
        startActivity(intent);
    }
}
