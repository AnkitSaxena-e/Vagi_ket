package com.example.euser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.euser.Adapter.SearchAdapter;
import com.example.euser.Modal.Product;
import com.example.euser.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class CategoryActivity extends AppCompatActivity {

    private RecyclerView FiPhonb;
    private RecyclerView.LayoutManager layoutManagerFiPhonb;
    private String CCCC;
    private int h;
    private ImageView back;
    private ArrayList<Product> PList, QList, RList, FList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CCCC = getIntent().getStringExtra("loik");

        back = findViewById(R.id.back_sett_cat);

        FiPhonb = findViewById(R.id.reCattttLL);
        FiPhonb.setHasFixedSize(true);
        FiPhonb.setItemViewCacheSize(20);
        FiPhonb.setDrawingCacheEnabled(true);
        FiPhonb.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhonb = new LinearLayoutManager(CategoryActivity.this);
        FiPhonb.setLayoutManager(layoutManagerFiPhonb);
        Paper.book().write(Prevalant.CheckH, "A");

        Window window = CategoryActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        GetListfi(CCCC);

        back.setOnClickListener(v -> {

            Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
            i.putExtra("eeee", "CA");
            startActivity(i);

        });

    }

    private void GetListfi(String j) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");

        if (!ProductRef.equals(null))

            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        QList = new ArrayList<>();
                        RList = new ArrayList<>();
                        FList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        for (int g = 0; g < PList.size(); g++) {

                            String h = PList.get(g).getCategory();

                            if (h.equals(j)) {
                                QList.add(PList.get(g));
                            }
                        }

                        SearchAdapter adapter = new SearchAdapter(CategoryActivity.this, QList, "type");

                        FiPhonb.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

//    @Override
//    public void onBackPressed() {
//        Intent i = new Intent(CategoryActivity.this, HomeActivity.class);
//        i.putExtra("eeee", "CategoryA");
//        startActivity(i);
//    }

}