package com.example.eadmin.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eadmin.AdminUserInfoDetailActivity;
import com.example.eadmin.Modal.Users;
import com.example.eadmin.Prevalant.Prevalant;
import com.example.eadmin.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class AdminUserReAdapter extends RecyclerView.Adapter<AdminUserReAdapter.ViewHol> {

    private ArrayList<Users> aa;
    private Context context;

    public AdminUserReAdapter(FragmentActivity activity, ArrayList<Users> PListAA) {

        context = activity;
        aa = PListAA;

    }

    @NonNull
    @Override
    public ViewHol onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.testt, parent, false);
        ViewHol viewHol = new ViewHol(v);
        return viewHol;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHol holder, int position) {

        holder.AUName.setText(aa.get(position).getName());
        holder.AUNumber.setText(aa.get(position).getNumber());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, AdminUserInfoDetailActivity.class);
                Paper.book().write(Prevalant.ACAdmin, aa.get(position).getNumber());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return aa.size();
    }

    public class ViewHol extends RecyclerView.ViewHolder{

        public TextView AUName, AUNumber;

        public ViewHol(@NonNull View itemView) {
            super(itemView);

            AUName = itemView.findViewById(R.id.name_user_OC);
            AUNumber = itemView.findViewById(R.id.num_user_OC);

        }

    }

}
