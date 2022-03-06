package com.example.euser.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euser.Adapter.SearchAdapter;
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

public class GalleryFragment extends Fragment {

    private SearchView searchViewA;
    private RecyclerView ReSear;
    private ArrayList<Product> PList, FList;
    private int j = 0;
    private RecyclerView.LayoutManager layoutManagerSer;
    private DatabaseReference refgty;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);


        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);


        searchViewA = root.findViewById(R.id.search_view_search);

        ReSear = root.findViewById(R.id.Search_Search);
        ReSear.setHasFixedSize(true);
        ReSear.setItemViewCacheSize(20);
        ReSear.setDrawingCacheEnabled(true);
        ReSear.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerSer = new LinearLayoutManager(getActivity());
        ReSear.setLayoutManager(layoutManagerSer);

        ShowPP();

        Prevalant.SuspendUser = new ArrayList<>();

        return root;
    }

    private void ShowPP() {

        refgty = FirebaseDatabase.getInstance().getReference().child("Products");

        if(!refgty.equals(null)) {

            refgty.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        PList = new ArrayList<>();
                        FList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            PList.add(ss.getValue(Product.class));
                        }

                        SearchAdapter adapter = new SearchAdapter(getActivity(), PList, "S");
                        ReSear.setAdapter(adapter);

                        if (!searchViewA.equals(null)) {

                            searchViewA.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    return false;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    searchIt(newText);
                                    return false;
                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        ShowPP();

    }

    private void searchIt(String newText) {

        ArrayList<Product> Newlist = new ArrayList<>();

        for(Product product : PList){
            if(product.getKeyward().toLowerCase().contains(newText.toLowerCase())){
                Newlist.add(product);
            }
        }
        SearchAdapter adapter = new SearchAdapter(getActivity(), Newlist, "S");
        ReSear.setAdapter(adapter);
    }

}