package com.example.euser.View_Holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euser.Interface.itermClickListner;
import com.example.euser.R;

public class NotiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView noti_Text;
    public ImageView noti_Image;
    private itermClickListner itermClickListner;

    public NotiViewHolder(@NonNull View itemView) {
        super(itemView);

        noti_Image = itemView.findViewById(R.id.noImage);
        noti_Text = itemView.findViewById(R.id.noText);

    }

    @Override
    public void onClick(View v) {
        com.example.euser.Interface.itermClickListner.onClick(v, getAdapterPosition(), false);
    }


    public void setItermClickListner(itermClickListner itermClickListner) {
        this.itermClickListner = itermClickListner;
    }

}
