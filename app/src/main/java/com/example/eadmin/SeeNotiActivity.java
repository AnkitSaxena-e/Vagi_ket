package com.example.eadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.eadmin.Modal.NotificationModalClass;
import com.example.eadmin.View_Holder.NotiViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class SeeNotiActivity extends AppCompatActivity {

    private RecyclerView Noti_Fragment;
    private RecyclerView.LayoutManager notiManager;
    private StorageReference ProductImageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_noti);

        Noti_Fragment = findViewById(R.id.noti_recy);
        Noti_Fragment.setHasFixedSize(true);
        Noti_Fragment.setItemViewCacheSize(20);
        Noti_Fragment.setDrawingCacheEnabled(true);
        Noti_Fragment.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        notiManager = new LinearLayoutManager(SeeNotiActivity.this);
        Noti_Fragment.setLayoutManager(notiManager);

        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Notifica Pic");

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

                        notiViewHolder.dele_Noti.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                notiRef.child(notificationModalClass.getPid()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(notificationModalClass.getPid());
                                        ref.delete();
                                        Toast.makeText(SeeNotiActivity.this, "Removed", Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }
                        });

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