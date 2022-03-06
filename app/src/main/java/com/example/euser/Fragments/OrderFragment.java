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

import com.example.euser.Adapter.OrderAdapter;
import com.example.euser.Modal.Order;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;

public class OrderFragment extends Fragment {

    private RecyclerView R_PhoneS;
    private RecyclerView.LayoutManager layoutManagerSS;
    private ArrayList<Order> PList, QList, RList, FFList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);

        Paper.init(getActivity());

        R_PhoneS = root.findViewById(R.id.Order_Searchoo);
        R_PhoneS.setHasFixedSize(true);
        R_PhoneS.setItemViewCacheSize(20);
        R_PhoneS.setDrawingCacheEnabled(true);
        R_PhoneS.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSS = new LinearLayoutManager(getActivity());
        R_PhoneS.setLayoutManager(layoutManagerSS);

        OrderLe();

        return root;
    }

    private void OrderLe() {

        DatabaseReference OrREF = FirebaseDatabase.getInstance().getReference().child("Order");

        OrREF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    PList = new ArrayList<>();
                    QList = new ArrayList<>();
                    RList = new ArrayList<>();
                    FFList = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        PList.add(ss.getValue(Order.class));
                    }

                    for (int g = 0; g < PList.size(); g++) {

                        String t = Paper.book().read(Prevalant.UserIdA);
                        String h = PList.get(g).getByUser();

                        if (h.equals(t)) {
                            QList.add(PList.get(g));
                        }

                    }

                    for(int o = QList.size() - 1; o >= 0; o--){

                        RList.add(QList.get(o));

                    }

                    OrderAdapter orderAdapter = new OrderAdapter(getActivity(), RList);
                    R_PhoneS.setAdapter(orderAdapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}
