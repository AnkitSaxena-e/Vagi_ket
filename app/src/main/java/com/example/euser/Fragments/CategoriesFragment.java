package com.example.euser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.euser.CategoryActivity;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;

import io.paperdb.Paper;

public class CategoriesFragment extends Fragment {

    private RelativeLayout Ven, IVS, FoC, OxM, Cot, RyT, UrB, FeT, Otr;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_categories, container, false);


        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);


        Ven = root.findViewById(R.id.Admin_Venflon_AAAA);
        IVS = root.findViewById(R.id.Admin_Iv_Set_AAAA);
        FoC = root.findViewById(R.id.Admin_Foley_Catheter_AAAA);
        OxM = root.findViewById(R.id.Admin_Oxygen_Mask_AAAA);
        Cot = root.findViewById(R.id.Admin_Cotton_AAAA);
        RyT = root.findViewById(R.id.Admin_Ryles_Tube_AAAA);
        UrB = root.findViewById(R.id.Admin_Uro_Bag_AAAA);
        FeT = root.findViewById(R.id.Admin_Feeding_Tube_AAAA);
        Otr = root.findViewById(R.id.Admin_Other_AAAA);

        Ven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "FAV");

            }
        });

        IVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "HAS");

            }
        });

        FoC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "FF");

            }
        });

        OxM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "EFV");

            }
        });

        Cot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "OFV");

            }
        });

        RyT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "CAS");

            }
        });

        UrB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "FBB");

            }
        });

        FeT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "FV");

            }
        });

        Otr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ft.addToBackStack(null);
                ft.commit();
                Paper.book().write(Prevalant.CatType, "Other");

            }
        });

        return root;
    }
}
