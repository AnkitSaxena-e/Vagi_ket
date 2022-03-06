package com.example.euser.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euser.Adapter.SearchAdapter;
import com.example.euser.CategoryActivity;
import com.example.euser.Modal.Product;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.paperdb.Paper;
import io.reactivex.internal.operators.flowable.FlowableRange;

public class SeeCategoryFragment extends Fragment {

    private RecyclerView FiPhonb;
    private RecyclerView.LayoutManager layoutManagerFiPhonb;
    private String type;
    private int h;
    private ImageView back;
    private ArrayList<Product> PList, QList, RList, FList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_see_category, container, false);

        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);

        type = Paper.book().read(Prevalant.CatType);

        FiPhonb = root.findViewById(R.id.reCattttLL_frag);
        FiPhonb.setHasFixedSize(true);
        FiPhonb.setItemViewCacheSize(20);
        FiPhonb.setDrawingCacheEnabled(true);
        FiPhonb.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerFiPhonb = new LinearLayoutManager(getActivity());
        FiPhonb.setLayoutManager(layoutManagerFiPhonb);

        String LL = Paper.book().read(Prevalant.CheckH);
        String oo = Paper.book().read(Prevalant.FAD);

        if(oo.equals("HomeA")){

            Paper.book().write(Prevalant.FAD, "A");
            Paper.book().write(Prevalant.CheckH, "A");

        }

        if(oo.equals("A") && LL.equals("SCA")){

            Paper.book().write(Prevalant.FAD, "A");
            Paper.book().write(Prevalant.CheckH, "A");

        }

        GetListfi(type);

        return root;

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

                        SearchAdapter adapter = new SearchAdapter(getActivity(), QList, "SCA");

                        FiPhonb.setAdapter(adapter);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

    }

}
