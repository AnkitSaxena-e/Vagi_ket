package com.example.euser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.euser.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import io.paperdb.Paper;

public class OrderPlacedActivity extends AppCompatActivity {

    private String ON, TotalPrice, Op, CCN, PPI, OO;
    private TextView or, TH, ST;
    private ImageView TTHH;
    private boolean o = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_placed);

        TotalPrice = getIntent().getStringExtra("RP");
        CCN = getIntent().getStringExtra("CN");
        PPI = getIntent().getStringExtra("PIDD");

        or = findViewById(R.id.orderId);
        TH = findViewById(R.id.ssssssss);
        TTHH = findViewById(R.id.contiImage);
        ST = findViewById(R.id.yy);

        OO = Paper.book().read(Prevalant.DNR);

        or.setText(ON);

        Window window = OrderPlacedActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        if(!o){
            if(o){
                return;
            }else {
                o = true;
                UpCo(TotalPrice);
            }
        }

        TH.setOnClickListener(v -> {

            Intent i  = new Intent(OrderPlacedActivity.this, HomeActivity.class);
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });

        TTHH.setOnClickListener(v -> {

            Intent i  = new Intent(OrderPlacedActivity.this, HomeActivity.class);
            i.putExtra("eeee", "OPA");
            startActivity(i);

        });
    }

    private void UpCo(String totalPrice) {

        DatabaseReference User = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        User.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    String hh = dataSnapshot.child("Reward").getValue().toString();

                    int tp = Integer.parseInt(totalPrice);
                    int kl = tp - 15;
                    int jk = kl/10;

                    Op = String.valueOf(jk);

                    int pp = Integer.parseInt(hh) + Integer.parseInt(Op);

                    String oo = String.valueOf(pp);

                    DatabaseReference rty = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

                    HashMap<String, Object> lo = new HashMap<>();

                    lo.put("Reward", oo);

                    rty.updateChildren(lo).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            TH.setVisibility(View.VISIBLE);
                            TTHH.setVisibility(View.VISIBLE);

                            Paper.book().write(Prevalant.DNR, "jj");

                            ST.setText("Yaayyy.. You Got " + Op + " ");

                            DatabaseReference reftgj = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                                    .child("Cart").child(CCN).child(PPI);

                            reftgj.removeValue();

                        }
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(OrderPlacedActivity.this, HomeActivity.class);
        Paper.book().write(Prevalant.FAD, "HomeA");
        i.putExtra("eeee", "HA");
        startActivity(i);
    }

}