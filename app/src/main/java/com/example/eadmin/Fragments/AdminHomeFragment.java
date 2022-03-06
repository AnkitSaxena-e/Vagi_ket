package com.example.eadmin.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.eadmin.ADActivity;
import com.example.eadmin.AddNotiActivity;
import com.example.eadmin.AdminAddCatActivity;
import com.example.eadmin.AdminFHShowActivity;
import com.example.eadmin.AdminSeeProductPhoneActivity;
import com.example.eadmin.CreateNewBillActivity;
import com.example.eadmin.LoginActivity;
import com.example.eadmin.Prevalant.Prevalant;
import com.example.eadmin.R;
import com.example.eadmin.SendAllNotiActivity;

import io.paperdb.Paper;

public class AdminHomeFragment extends Fragment {

    private Button CreateBill, AddNPhone, ManagePhones, ManageBanners, ManageFPhones, SetNoti, SNotii, LogoutA;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home_admin, container, false);

        Paper.init(getActivity());

        AddNPhone = root.findViewById(R.id.AdminAddNewPhone);
        ManagePhones = root.findViewById(R.id.AdminManagePhones);
        ManageBanners = root.findViewById(R.id.AdminManageBanner);
        ManageFPhones = root.findViewById(R.id.AdminManageFPhones);
        CreateBill = root.findViewById(R.id.AdminNewB);
        SNotii = root.findViewById(R.id.AdminSendNotiAll);
        LogoutA = root.findViewById(R.id.AdminManageLogout);
        SetNoti = root.findViewById(R.id.AdminSetNotiAll);

        AddNPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminAddCatActivity.class);
                startActivity(i);
            }
        });

        ManagePhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminSeeProductPhoneActivity.class);
                Paper.book().write(Prevalant.CheckAdmin, "Admin");
                startActivity(i);
            }
        });

        ManageBanners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ADActivity.class);
                startActivity(i);
            }
        });

        SetNoti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AddNotiActivity.class);
                startActivity(i);
            }
        });

        ManageFPhones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), AdminFHShowActivity.class);
                startActivity(i);
            }
        });

        CreateBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateNewBillActivity.class);
                startActivity(i);
            }
        });

        SNotii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), SendAllNotiActivity.class);
                startActivity(i);
            }
        });

        LogoutA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        return root;
    }

}
