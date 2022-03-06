package com.example.euser.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.euser.AboutActivity;
import com.example.euser.ChangePasswardUserActivity;
import com.example.euser.HomeActivity;
import com.example.euser.LoginActivity;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;
import com.example.euser.UserCostomerSupportActivity;

import io.paperdb.Paper;

public class SlideshowFragment extends Fragment {

    private TextView logOut, seeAccount, chngePassward, aboutS, costomerSupport;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);


        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);

        Paper.init(getActivity());
        logOut = root.findViewById(R.id.Logout_Setting_first);
        seeAccount = root.findViewById(R.id.yourAccount);
        chngePassward = root.findViewById(R.id.changePasswardL);
        aboutS = root.findViewById(R.id.aboutL);
        costomerSupport = root.findViewById(R.id.costomerSupport);

        seeAccount.setOnClickListener(v -> {

            FragmentTransaction ft = ((AppCompatActivity) getActivity()).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, new ProfileFragment());
            ft.addToBackStack(null);
            ft.commit();

        });

        chngePassward.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ChangePasswardUserActivity.class);
            startActivity(i);
        });

        costomerSupport.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserCostomerSupportActivity.class);
            startActivity(i);
        });

        aboutS.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AboutActivity.class);
            startActivity(i);
        });

        logOut.setOnClickListener(v -> {
            Paper.book().destroy();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        return root;
    }
}