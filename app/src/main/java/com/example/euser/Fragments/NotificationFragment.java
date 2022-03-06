package com.example.euser.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euser.HomeActivity;
import com.example.euser.Modal.NotificationModalClass;
import com.example.euser.R;
import com.example.euser.View_Holder.CartResourceViewHolder;
import com.example.euser.View_Holder.NotiViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class NotificationFragment extends Fragment {

    RecyclerView Noti_Fragment;
    RecyclerView.LayoutManager notiManager;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notification, container, false);

        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_home_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);

        Noti_Fragment = root.findViewById(R.id.noti_recy);
        Noti_Fragment.setHasFixedSize(true);
        Noti_Fragment.setItemViewCacheSize(20);
        Noti_Fragment.setDrawingCacheEnabled(true);
        Noti_Fragment.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        notiManager = new LinearLayoutManager(getActivity());
        Noti_Fragment.setLayoutManager(notiManager);

        return root;

    }

    @Override
    public void onStart() {
        super.onStart();

        DatabaseReference notiRef = FirebaseDatabase.getInstance().getReference().child("Notification");

        FirebaseRecyclerOptions<NotificationModalClass> notiOp =
                new FirebaseRecyclerOptions.Builder<NotificationModalClass>().setQuery(notiRef, NotificationModalClass.class).build();

        FirebaseRecyclerAdapter<NotificationModalClass, NotiViewHolder> notiAdapter =
                new FirebaseRecyclerAdapter<NotificationModalClass, NotiViewHolder>(notiOp) {
                    @Override
                    protected void onBindViewHolder(@NonNull NotiViewHolder notiViewHolder, int i, @NonNull NotificationModalClass notificationModalClass) {

                        notiViewHolder.noti_Text.setText(notificationModalClass.getNotiText());

                        Picasso.get().load(notificationModalClass.getNotiImage()).centerCrop().fit().into(notiViewHolder.noti_Image);

                    }

                    @NonNull
                    @Override
                    public NotiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.noti_layout, parent, false);
                        return new NotiViewHolder(view);
                    }
                };

        Noti_Fragment.setAdapter(notiAdapter);
        notiAdapter.startListening();

    }
}
