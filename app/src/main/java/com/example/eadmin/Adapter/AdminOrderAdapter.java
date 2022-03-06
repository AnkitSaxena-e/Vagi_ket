 package com.example.eadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eadmin.AdminSeeOrderActivity;
import com.example.eadmin.Modal.Order;
import com.example.eadmin.R;

import java.util.ArrayList;

public class AdminOrderAdapter extends RecyclerView.Adapter<AdminOrderAdapter.Viewholder>{

    private ArrayList<Order> als;
    private Context context;

    public AdminOrderAdapter(FragmentActivity activity, ArrayList<Order> PList) {

        als = PList;
        context = activity;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_order_details, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder Holder, int position) {

        String R = als.get(position).getReturn();

        if(R.equals("Return")){

            Holder.Change_Back.setBackgroundColor(Color.parseColor("#FF0000"));

        }

        if(R.equals("Complete")){

            Holder.Change_Back.setBackgroundColor(Color.parseColor("#008000"));

        }

        if(R.equals("Cancel")){

            Holder.Change_Back.setBackgroundColor(Color.parseColor("#f64c73"));

        }

        if(R.equals("Done")){

            Holder.Change_Back.setBackgroundColor(Color.parseColor("#5a98ec"));

        }

        Holder.tytProductFrom.setText(als.get(position).getName());
        Holder.tytProductTo.setText(als.get(position).getAddress());
        Holder.tytProductPrice.setText(als.get(position).getTotalAmount());

        Holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdminSeeOrderActivity.class);
                i.putExtra("uid", als.get(position).getPID());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return als.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        public TextView tytProductFrom, tytProductTo, tytProductPrice;
        public RelativeLayout Change_Back;

        public Viewholder(@NonNull View itemView) {
            super(itemView);

            tytProductFrom = itemView.findViewById(R.id.O_username);
            tytProductTo = itemView.findViewById(R.id.O_number);
            tytProductPrice = itemView.findViewById(R.id.O_price);
            Change_Back = itemView.findViewById(R.id.change_back);

        }
    }
}
