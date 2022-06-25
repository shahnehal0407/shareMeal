package com.nehal.myfirstapp.ngo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nehal.myfirstapp.DonationList;
import com.nehal.myfirstapp.R;

import java.util.ArrayList;

public class NgoHistoryAdapter extends RecyclerView.Adapter<NgoHistoryAdapter.NgoHistoryViewHolder>  {

    Context context;
    ArrayList<DonationList> list;
    private FirebaseAuth firebaseAuth;
    String userID;
    private DatabaseReference databaseReference;

    public NgoHistoryAdapter(Context context, ArrayList<DonationList> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NgoHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.ngo_history, parent, false);
        return new NgoHistoryAdapter.NgoHistoryViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull  NgoHistoryAdapter.NgoHistoryViewHolder holder, int position) {


        DonationList donationList = list.get(position);

        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        holder.ngoid.setText(donationList.getNgoid());
        holder.donatorname.setText(donationList.getfName());
        holder.donatorno.setText(donationList.getKey());

        holder.date.setText(donationList.getNgodate());
        holder.time.setText(donationList.getNgotime());

        String iddetail = donationList.getKey();
        Log.d("TAG","keysofuser"+iddetail);

        databaseReference = FirebaseDatabase.getInstance().getReference("Donations").child("Images");

        holder.seeMorebtn.setVisibility(View.VISIBLE);
        holder.seeMorebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context.getApplicationContext(),ShowImagesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("id",iddetail);
                context.getApplicationContext().startActivity(i);
            }
        });







    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NgoHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView ngoid,donatorname,donatorno,date,time;
        LinearLayout seeMorebtn;

        public NgoHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            ngoid = itemView.findViewById(R.id.ngoid);
            donatorname = itemView.findViewById(R.id.donatorname);
            donatorno = itemView.findViewById(R.id.donatorno);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            seeMorebtn = itemView.findViewById(R.id.seeMorebtn);



        }
    }

}
