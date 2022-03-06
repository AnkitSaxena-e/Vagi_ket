package com.example.eadmin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminAddCatActivity extends AppCompatActivity {

    private Button Ven, IVS, FoC, OxM, Cot, RyT, UrB, FeT, BTS, MdS, Nki, Vtr, CBg, Vol, Otr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_cat);

        Ven = findViewById(R.id.Admin_Venflon_AAAA);
        IVS = findViewById(R.id.Admin_Iv_Set_AAAA);
        FoC = findViewById(R.id.Admin_Foley_Catheter_AAAA);
        OxM = findViewById(R.id.Admin_Oxygen_Mask_AAAA);
        Cot = findViewById(R.id.Admin_Cotton_AAAA);
        RyT = findViewById(R.id.Admin_Ryles_Tube_AAAA);
        UrB = findViewById(R.id.Admin_Uro_Bag_AAAA);
        FeT = findViewById(R.id.Admin_Feeding_Tube_AAAA);
        Otr = findViewById(R.id.Admin_Other_AAAA);

        Ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "FAV");
                startActivity(i);

            }
        });

        IVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "HAS");
                startActivity(i);

            }
        });

        FoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "FF");
                startActivity(i);

            }
        });

        OxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "EFV");
                startActivity(i);

            }
        });

        Cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "OFV");
                startActivity(i);

            }
        });

        RyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "CAS");
                startActivity(i);

            }
        });

        UrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "FBB");
                startActivity(i);

            }
        });

        FeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "FV");
                startActivity(i);

            }
        });

        Otr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AdminAddCatActivity.this, AdminAddPhoneActivity.class);
                i.putExtra("loik", "Other");
                startActivity(i);

            }
        });
    }
}