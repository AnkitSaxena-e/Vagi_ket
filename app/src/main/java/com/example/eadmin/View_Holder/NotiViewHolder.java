package com.example.eadmin.View_Holder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.eadmin.Interface.itermClickListner;
import com.example.eadmin.R;

public class NotiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView noti_Text;
    public ImageView noti_Image;
    public Button dele_Noti;
    private itermClickListner itermClickListner;

    public NotiViewHolder(@NonNull View itemView) {
        super(itemView);

        noti_Image = itemView.findViewById(R.id.noImage);
        noti_Text = itemView.findViewById(R.id.noText);
        dele_Noti = itemView.findViewById(R.id.deleNoti);

    }

    @Override
    public void onClick(View v) {
        com.example.eadmin.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }


    public void setItermClickListner(itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }

}
