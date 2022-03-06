package com.example.euser.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.euser.Adapter.ProductImageSlider;
import com.example.euser.ConfermOrderActivity;
import com.example.euser.HomeActivity;
import com.example.euser.Modal.Product;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import io.paperdb.Paper;


public class ProductDataFragment extends Fragment {

    private TextView AddNUser, AddNPhone, ManagePhones, BuyPP, CartPP;
    private RelativeLayout re_kk;

    private TextView productName, productDescription, productPrice, productPriceOri;
    private TextView ComCom;
    private String Color = "A", SnR = "A", SR1 = "A", Col1;
    private TextView Name_T, Detail_T, Brand_T;
    private TextView Num1, Num2, Num3, Num4, Num5, Num6, Num7, Num8, Num9, Num10;
    private String SNum1, SNum2, SNum3, SNum4, SNum5, SNum6, SNum7, SNum8, SNum9, SNum10, SNum = "AA";
    private String T1Pri = "A", T2Pri = "A", T3Pri = "A", T4Pri = "A", T5Pri = "A", T6Pri = "A", T7Pri = "A",
            T8Pri = "A", T9Pri = "A", T10Pri = "A", FiPrice = "A";

    private String OriT1Pri = "A", OriT2Pri = "A", OriT3Pri = "A", OriT4Pri = "A", OriT5Pri = "A", OriT6Pri = "A", OriT7Pri = "A",
            OriT8Pri = "A", OriT9Pri = "A", OriT10Pri = "A", OriFiPrice = "A";

    private String ProductId = "", Check = "", OrdrN = "", Type = "";
    private int p = 0, k = 0;
    private String GTC = "A", FromCart = "A";
    private String Image1, Image2;
    private String OON, type, Total_Star, AColor, APrice, ASnR, TTSS;
    private String[] ProductID = {"ABCDEFG"};
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private String[] urls;
    private CirclePageIndicator indicator;
    private TextView SetDesiValue, SetTotalRating;
    private int s1, s2, s3, s4, s5;
    private ArrayList<Product> PList;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private float Total_S;
    private ElegantNumberButton PQuantity;
    private TextView TDRating, TNRating, RText1, RText2, RText3, RText4, RText5;
    private ProgressBar PRating1, PRating2, PRating3, PRating4, PRating5;
    private RatingBar Total_Rating;
    private SmileRating smileRating;
    private TextView submitRating;
    private ImageView expandedImageView;
    private ArrayList<String> ImageList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_product_data, container, false);

        try {


            ImageView A = getActivity().findViewById(R.id.Noti_C);

            A.setImageResource(R.drawable.ic_baseline_notifications_24);

            ImageView S = getActivity().findViewById(R.id.See_C);

            S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

            TextView Fot = getActivity().findViewById(R.id.dot);

            Fot.setVisibility(View.VISIBLE);


            Paper.init(getActivity());

            TDRating = root.findViewById(R.id.rating_seeA);
            TNRating = root.findViewById(R.id.rating_see_total_logA);

            submitRating = root.findViewById(R.id.submit_Rating_ButtonA);
            smileRating = root.findViewById(R.id.submit_RatingA);

            SetDesiValue = root.findViewById(R.id.setDasiReadingA);
            SetTotalRating = root.findViewById(R.id.setTotalRatingA);

            Total_Rating = root.findViewById(R.id.ratingBar_seeA);

            RText1 = root.findViewById(R.id.ratingtext1A);
            RText2 = root.findViewById(R.id.ratingtext2A);
            RText3 = root.findViewById(R.id.ratingtext3A);
            RText4 = root.findViewById(R.id.ratingtext4A);
            RText5 = root.findViewById(R.id.ratingtext5A);

            PRating1 = root.findViewById(R.id.progressBar1A);
            PRating2 = root.findViewById(R.id.progressBar2A);
            PRating3 = root.findViewById(R.id.progressBar3A);
            PRating4 = root.findViewById(R.id.progressBar4A);
            PRating5 = root.findViewById(R.id.progressBar5A);

            Num1 = root.findViewById(R.id.NumNum1);
            Num2 = root.findViewById(R.id.NumNum2);
            Num3 = root.findViewById(R.id.NumNum3);
            Num4 = root.findViewById(R.id.NumNum4);
            Num5 = root.findViewById(R.id.NumNum5);
            Num6 = root.findViewById(R.id.NumNum6);
            Num7 = root.findViewById(R.id.NumNum7);
            Num8 = root.findViewById(R.id.NumNum8);
            Num9 = root.findViewById(R.id.NumNum9);
            Num10 = root.findViewById(R.id.NumNum10);

            Name_T = root.findViewById(R.id.name_TA);
            Detail_T = root.findViewById(R.id.Detail_TA);
            Brand_T = root.findViewById(R.id.brand_TA);

            ComCom = root.findViewById(R.id.Comp);

            PQuantity = root.findViewById(R.id.Element_Bar);

            BuyPP = root.findViewById(R.id.BuyPPPP);
            CartPP = root.findViewById(R.id.CartPPPP);

            re_kk = root.findViewById(R.id.countainer_kkA);

            mPager = root.findViewById(R.id.viewPagerShow_Product_Data);

            indicator = root.findViewById(R.id.indicator_Product_Data);

            productName = root.findViewById(R.id.Product_Name_dataA);
            productDescription = root.findViewById(R.id.Product_Description_dataA);
            productPrice = root.findViewById(R.id.Product_Price_dataA);
            productPriceOri = root.findViewById(R.id.Product_Price_dataAOri);

            expandedImageView = root.findViewById(R.id.expanded_imageA);

            ProductId = Paper.book().read(Prevalant.ProductId);

            FromCart = Paper.book().read(Prevalant.CheckFromCart);

            type = Paper.book().read(Prevalant.CheckAdmin).toString();

            SeeSubmitButton(ProductId);

            shortAnimationDuration = getResources().getInteger(
                    android.R.integer.config_shortAnimTime);

        } catch (Exception e) {
            Toast.makeText(getActivity(), "lklklk" + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        getProductData(ProductId);

        CheckCart(ProductId);

        submitRating.setOnClickListener(v -> {
            @BaseRating.Smiley int smiley = smileRating.getSelectedSmile();
            switch (smiley) {
                case SmileRating.TERRIBLE:
                    p = 1;
                    TakeReading(ProductId, p);
                    break;
                case SmileRating.BAD:
                    p = 2;
                    TakeReading(ProductId, p);
                    break;
                case SmileRating.OKAY:
                    p = 3;
                    TakeReading(ProductId, p);
                    break;
                case SmileRating.GOOD:
                    p = 4;
                    TakeReading(ProductId, p);
                    break;
                case SmileRating.GREAT:
                    p = 5;
                    TakeReading(ProductId, p);
                    break;
                default:
                    Toast.makeText(getActivity(), "Please set your Ratings", Toast.LENGTH_SHORT).show();
            }
        });

        return root;

    }

    private void CheckCart(String productId) {

        DatabaseReference re = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        re.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    if(FromCart.equals("Cart")){
                        GTC = "Cart";
                        CartPP.setText("Edit");
                        BuyPP.setVisibility(View.GONE);
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                100
                        );
                        CartPP.setLayoutParams(params);
                    } else{
                        GTC = "Go";
                        CartPP.setText("Go to Cart");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void SeeSubmitButton(String productId) {

        DatabaseReference updateRatingT = FirebaseDatabase.getInstance().getReference()
                .child("Raing").child(Paper.book().read(Prevalant.UserIdA));

        updateRatingT.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        int i = ProductID.length;
                        ProductID = addS(i, ProductID, Objects.requireNonNull(snapshot.getValue()).toString());
                    }

                    int j = ProductID.length;
                    j--;

                    for (int i = 0; i <= j; i++) {
                        if (productId.equals(ProductID[i])) {
                            submitRating.setVisibility(View.INVISIBLE);
                        }
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void TakeReading(String productId, int p) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    Product product = dataSnapshot.getValue(Product.class);

                    assert product != null;
                    String Ss1 = product.getS1();
                    String Ss2 = product.getS2();
                    String Ss3 = product.getS3();
                    String Ss4 = product.getS4();
                    String Ss5 = product.getS5();

                    int ss1 = Integer.parseInt(Ss1);
                    int ss2 = Integer.parseInt(Ss2);
                    int ss3 = Integer.parseInt(Ss3);
                    int ss4 = Integer.parseInt(Ss4);
                    int ss5 = Integer.parseInt(Ss5);

                    if (p == 1) {
                        ss1++;
                    } else if (p == 2) {
                        ss2++;
                    } else if (p == 3) {
                        ss3++;
                    } else if (p == 4) {
                        ss4++;
                    } else if (p == 5) {
                        ss5++;
                    }

                    String R1 = String.valueOf(ss1);
                    String R2 = String.valueOf(ss2);
                    String R3 = String.valueOf(ss3);
                    String R4 = String.valueOf(ss4);
                    String R5 = String.valueOf(ss5);

                    if (k == 0) {
                        updateRating(productId, R1, R2, R3, R4, R5);
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

    }

    private void updateRating(String productId, String r1, String r2, String r3, String r4, String r5) {

        k++;

        final DatabaseReference URating = FirebaseDatabase.getInstance().getReference().child("Products").child(productId);

        HashMap<String, Object> updateR = new HashMap<String, Object>();

        updateR.put("S1", r1);
        updateR.put("S2", r2);
        updateR.put("S3", r3);
        updateR.put("S4", r4);
        updateR.put("S5", r5);

        URating.updateChildren(updateR).addOnCompleteListener(task -> {

            DatabaseReference updateRating = FirebaseDatabase.getInstance().getReference()
                    .child("Raing").child(Paper.book().read(Prevalant.UserIdA));

            final String SaveCurruntTime, SaveCurruntDate, Time;

            Calendar calendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            SaveCurruntDate = currentDate.format(calendar.getTime());

            @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
            SaveCurruntTime = currentTime.format(calendar.getTime());

            Time = SaveCurruntDate + SaveCurruntTime;

            HashMap<String, Object> UpdateR = new HashMap<>();

            UpdateR.put(Time, productId);

            updateRating.updateChildren(UpdateR).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getActivity(), "Thanks for the Feedback", Toast.LENGTH_SHORT).show();
                    submitRating.setVisibility(View.INVISIBLE);
                }
            });
        });
    }

    public static String[] addS(int n, String arr[], String s) {

        int h;

        String newPID[] = new String[n + 1];

        for (h = 0; h < n; h++) {
            newPID[h] = arr[h];
        }

        newPID[n] = s;

        return newPID;

    }

    private void getProductData(String productId) {

        DatabaseReference productRef = FirebaseDatabase.getInstance().getReference().child("Products");

        productRef.child(productId).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    PList = new ArrayList<>();

                    String gggggg = Objects.requireNonNull(dataSnapshot.child("ProductName").getValue()).toString();
                    String ggggll = Objects.requireNonNull(dataSnapshot.child("Description").getValue()).toString();

                    String ggggll_N = Objects.requireNonNull(dataSnapshot.child("TName").getValue()).toString();
                    String ggggll_D = Objects.requireNonNull(dataSnapshot.child("TDetail").getValue()).toString();
                    String ggggll_B = Objects.requireNonNull(dataSnapshot.child("TBrand").getValue()).toString();

                    productName.setText(gggggg);
                    productDescription.setText(ggggll);

                    if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {

                        BuyPP.setText("!Out Of Stock!");

                    }

                    Image1 = Objects.requireNonNull(dataSnapshot.child("Image1").getValue()).toString();
                    Image2 = Objects.requireNonNull(dataSnapshot.child("Image2").getValue()).toString();

                    String S1 = Objects.requireNonNull(dataSnapshot.child("S1").getValue()).toString();
                    String S2 = Objects.requireNonNull(dataSnapshot.child("S2").getValue()).toString();
                    String S3 = Objects.requireNonNull(dataSnapshot.child("S3").getValue()).toString();
                    String S4 = Objects.requireNonNull(dataSnapshot.child("S4").getValue()).toString();
                    String S5 = Objects.requireNonNull(dataSnapshot.child("S5").getValue()).toString();

                    String N1 = Objects.requireNonNull(dataSnapshot.child("No1").getValue()).toString();
                    String N2 = Objects.requireNonNull(dataSnapshot.child("No2").getValue()).toString();
                    String N3 = Objects.requireNonNull(dataSnapshot.child("No3").getValue()).toString();
                    String N4 = Objects.requireNonNull(dataSnapshot.child("No4").getValue()).toString();
                    String N5 = Objects.requireNonNull(dataSnapshot.child("No5").getValue()).toString();
                    String N6 = Objects.requireNonNull(dataSnapshot.child("No6").getValue()).toString();
                    String N7 = Objects.requireNonNull(dataSnapshot.child("No7").getValue()).toString();
                    String N8 = Objects.requireNonNull(dataSnapshot.child("No8").getValue()).toString();
                    String N9 = Objects.requireNonNull(dataSnapshot.child("No9").getValue()).toString();
                    String N10 = Objects.requireNonNull(dataSnapshot.child("No10").getValue()).toString();

                    T1Pri = Objects.requireNonNull(dataSnapshot.child("No1Price").getValue()).toString();
                    T2Pri = Objects.requireNonNull(dataSnapshot.child("No2Price").getValue()).toString();
                    T3Pri = Objects.requireNonNull(dataSnapshot.child("No3Price").getValue()).toString();
                    T4Pri = Objects.requireNonNull(dataSnapshot.child("No4Price").getValue()).toString();
                    T5Pri = Objects.requireNonNull(dataSnapshot.child("No5Price").getValue()).toString();
                    T6Pri = Objects.requireNonNull(dataSnapshot.child("No6Price").getValue()).toString();
                    T7Pri = Objects.requireNonNull(dataSnapshot.child("No7Price").getValue()).toString();
                    T8Pri = Objects.requireNonNull(dataSnapshot.child("No8Price").getValue()).toString();
                    T9Pri = Objects.requireNonNull(dataSnapshot.child("No9Price").getValue()).toString();
                    T10Pri = Objects.requireNonNull(dataSnapshot.child("No10Price").getValue()).toString();

                    OriT1Pri = Objects.requireNonNull(dataSnapshot.child("No1PriceOri").getValue()).toString();
                    OriT2Pri = Objects.requireNonNull(dataSnapshot.child("No2PriceOri").getValue()).toString();
                    OriT3Pri = Objects.requireNonNull(dataSnapshot.child("No3PriceOri").getValue()).toString();
                    OriT4Pri = Objects.requireNonNull(dataSnapshot.child("No4PriceOri").getValue()).toString();
                    OriT5Pri = Objects.requireNonNull(dataSnapshot.child("No5PriceOri").getValue()).toString();
                    OriT6Pri = Objects.requireNonNull(dataSnapshot.child("No6PriceOri").getValue()).toString();
                    OriT7Pri = Objects.requireNonNull(dataSnapshot.child("No7PriceOri").getValue()).toString();
                    OriT8Pri = Objects.requireNonNull(dataSnapshot.child("No8PriceOri").getValue()).toString();
                    OriT9Pri = Objects.requireNonNull(dataSnapshot.child("No9PriceOri").getValue()).toString();
                    OriT10Pri = Objects.requireNonNull(dataSnapshot.child("No10PriceOri").getValue()).toString();

                    String Quan = Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString();

                    int PQ = Integer.parseInt(Quan);


                    Num1.setText(N1);
                    Num2.setText(N2);
                    Num3.setText(N3);
                    Num4.setText(N4);
                    Num5.setText(N5);
                    Num6.setText(N6);
                    Num7.setText(N7);
                    Num8.setText(N8);
                    Num9.setText(N9);
                    Num10.setText(N10);

                    PQuantity.setRange(1, PQ);

                    SNum1 = N1;
                    SNum2 = N2;
                    SNum3 = N3;
                    SNum4 = N4;
                    SNum5 = N5;
                    SNum6 = N6;
                    SNum7 = N7;
                    SNum8 = N8;
                    SNum9 = N9;
                    SNum10 = N10;

                    Num1.setBackgroundResource(R.drawable.num_s);

                    SNum = SNum1;
                    FiPrice = T1Pri;
                    productPrice.setText("₹" + T1Pri);
                    productPriceOri.setText("₹" + OriT1Pri);

                    if (N1.equals("A")) {
                        Num1.setVisibility(View.INVISIBLE);
                    }

                    if (N2.equals("A")) {
                        Num2.setVisibility(View.INVISIBLE);
                    }

                    if (N3.equals("A")) {
                        Num3.setVisibility(View.INVISIBLE);
                    }

                    if (N4.equals("A")) {
                        Num4.setVisibility(View.INVISIBLE);
                    }

                    if (N5.equals("A")) {
                        Num5.setVisibility(View.INVISIBLE);
                    }

                    if (N6.equals("A")) {
                        Num6.setVisibility(View.INVISIBLE);
                    }

                    if (N7.equals("A")) {
                        Num7.setVisibility(View.INVISIBLE);
                    }

                    if (N8.equals("A")) {
                        Num8.setVisibility(View.INVISIBLE);
                    }

                    if (N9.equals("A")) {
                        Num9.setVisibility(View.INVISIBLE);
                    }

                    if (N10.equals("A")) {
                        Num10.setVisibility(View.INVISIBLE);
                    }

                    Col1 = Objects.requireNonNull(dataSnapshot.child("Company").getValue()).toString();

                    Name_T.setText(ggggll_N);
                    Detail_T.setText(ggggll_D);
                    Brand_T.setText(ggggll_B);

                    s1 = Integer.parseInt(S1);
                    s2 = Integer.parseInt(S2);
                    s3 = Integer.parseInt(S3);
                    s4 = Integer.parseInt(S4);
                    s5 = Integer.parseInt(S5);

                    int Upper = (s1) + (s2 * 2) + (s3 * 3) + (s4 * 4) + (s5 * 5);

                    int Lower = s1 + s2 + s3 + s4 + s5;

                    BigInteger U = new BigInteger(String.valueOf(Upper));
                    BigInteger L = new BigInteger(String.valueOf(Lower));

                    float Ul = U.floatValue();
                    float Ll = L.floatValue();

                    Total_S = Ul / Ll;
                    Total_Star = new DecimalFormat("#.#").format(Total_S);

                    SetDesiValue.setText(Total_Star);
                    SetTotalRating.setText(Lower + " rating");

                    TDRating.setText(Total_Star);
                    TNRating.setText(String.valueOf(Lower));

                    TTSS = Total_Star;

                    Total_Rating.setRating(Total_S);

                    RText1.setText(S1);
                    RText2.setText(S2);
                    RText3.setText(S3);
                    RText4.setText(S4);
                    RText5.setText(S5);

                    int New1 = (s1 - 1) * 100;
                    BigInteger rb1 = (BigInteger.valueOf(New1)).divide(BigInteger.valueOf(100));
                    int RB1 = rb1.intValue();
                    PRating1.setProgress(RB1);

                    int New2 = (s2 - 1) * 100;
                    BigInteger rb2 = (BigInteger.valueOf(New2)).divide(BigInteger.valueOf(100));
                    int RB2 = rb2.intValue();
                    PRating2.setProgress(RB2);

                    int New3 = (s3 - 1) * 100;
                    BigInteger rb3 = (BigInteger.valueOf(New3)).divide(BigInteger.valueOf(100));
                    int RB3 = rb3.intValue();
                    PRating3.setProgress(RB3);

                    int New4 = (s4 - 1) * 100;
                    BigInteger rb4 = (BigInteger.valueOf(New4)).divide(BigInteger.valueOf(100));
                    int RB4 = rb4.intValue();
                    PRating4.setProgress(RB4);

                    int New5 = (s5 - 1) * 100;
                    BigInteger rb5 = (BigInteger.valueOf(New5)).divide(BigInteger.valueOf(100));
                    int RB5 = rb5.intValue();
                    PRating5.setProgress(RB5);

                    ComCom.setText(Col1);

                    urls = new String[]{
                            Image1,
                            Image2
                    };

                    Flip();

                    if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {
                        BuyPP.setText("!Out Of Stock!");
                    }

                    BuyPP.setOnClickListener(v -> {

                        if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {

                            Toast.makeText(getActivity(), "Product is Out Of Stock", Toast.LENGTH_SHORT).show();

                        } else {

                            if (SNum.equals("AA")) {

                                Num1.setError("Please Select");

                            } else {

                                AddProB(gggggg, SNum, Col1, FiPrice, PQuantity.getNumber(), Image1);

                            }
                        }
                    });

                    CartPP.setOnClickListener(v -> {


                        if (Objects.requireNonNull(dataSnapshot.child("Quantity").getValue()).toString().equals("0")) {

                            Toast.makeText(getActivity(), "Product is Out Of Stock", Toast.LENGTH_SHORT).show();

                        } else if (GTC.equals("Go")) {

                            Intent i = new Intent(getActivity(), HomeActivity.class);
                            Paper.book().write(Prevalant.FAD, "CartA");
                            i.putExtra("eeee", "CaA");
                            startActivity(i);

                        } else if(GTC.equals("Cart")){

                            AddProCart(gggggg, SNum, Col1, FiPrice, PQuantity.getNumber(), Image1);

                        } else {

                            if (SNum.equals("AA")) {

                                Num1.setError("Please Select");

                            } else {

                                AddProC(gggggg, SNum, Col1, FiPrice, PQuantity.getNumber(), Image1);

                            }
                        }

                    });

                    Num1.setOnClickListener(v -> {

                        SNum = SNum1;
                        FiPrice = T1Pri;
                        productPrice.setText("₹" + T1Pri);
                        productPriceOri.setText("₹" + OriT1Pri);

                        Num1.setBackgroundResource(R.drawable.num_s);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num2.setOnClickListener(v -> {

                        SNum = SNum2;
                        FiPrice = T2Pri;
                        productPrice.setText("₹" + T2Pri);
                        productPriceOri.setText("₹" + OriT2Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.num_s);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num3.setOnClickListener(v -> {

                        SNum = SNum3;
                        FiPrice = T3Pri;
                        productPrice.setText("₹" + T3Pri);
                        productPriceOri.setText("₹" + OriT3Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.num_s);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num4.setOnClickListener(v -> {

                        SNum = SNum4;
                        FiPrice = T4Pri;
                        productPrice.setText("₹" + T4Pri);
                        productPriceOri.setText("₹" + OriT4Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.num_s);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num5.setOnClickListener(v -> {

                        SNum = SNum5;
                        FiPrice = T5Pri;
                        productPrice.setText("₹" + T5Pri);
                        productPriceOri.setText("₹" + OriT5Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.num_s);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num6.setOnClickListener(v -> {

                        SNum = SNum6;
                        FiPrice = T6Pri;
                        productPrice.setText("₹" + T6Pri);
                        productPriceOri.setText("₹" + OriT6Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.num_s);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num7.setOnClickListener(v -> {

                        SNum = SNum7;
                        FiPrice = T7Pri;
                        productPrice.setText("₹" + T7Pri);
                        productPriceOri.setText("₹" + OriT7Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.num_s);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num8.setOnClickListener(v -> {

                        SNum = SNum8;
                        FiPrice = T8Pri;
                        productPrice.setText("₹" + T8Pri);
                        productPriceOri.setText("₹" + OriT8Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.num_s);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num9.setOnClickListener(v -> {

                        SNum = SNum9;
                        FiPrice = T9Pri;
                        productPrice.setText("₹" + T9Pri);
                        productPriceOri.setText("₹" + OriT9Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.num_s);
                        Num10.setBackgroundResource(R.drawable.nun_n);

                    });

                    Num10.setOnClickListener(v -> {

                        SNum = SNum10;
                        FiPrice = T10Pri;
                        productPrice.setText("₹" + T10Pri);
                        productPriceOri.setText("₹" + OriT10Pri);

                        Num1.setBackgroundResource(R.drawable.nun_n);
                        Num2.setBackgroundResource(R.drawable.nun_n);
                        Num3.setBackgroundResource(R.drawable.nun_n);
                        Num4.setBackgroundResource(R.drawable.nun_n);
                        Num5.setBackgroundResource(R.drawable.nun_n);
                        Num6.setBackgroundResource(R.drawable.nun_n);
                        Num7.setBackgroundResource(R.drawable.nun_n);
                        Num8.setBackgroundResource(R.drawable.nun_n);
                        Num9.setBackgroundResource(R.drawable.nun_n);
                        Num10.setBackgroundResource(R.drawable.num_s);

                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void AddProCart(String name, String sNum, String company, String pricee, String s, String image1) {

        DatabaseReference reftg = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        HashMap<String, Object> PPRR = new HashMap<>();

        PPRR.put("PName", name);
        PPRR.put("PNum", sNum);
        PPRR.put("PCom", company);
        PPRR.put("PPri", pricee);
        PPRR.put("PQut", s);
        PPRR.put("PPid", ProductId);
        PPRR.put("PImage", image1);

        reftg.child(ProductId).updateChildren(PPRR).addOnCompleteListener(task -> {

            Toast.makeText(getActivity(), "Product Added to Cart Successfully", Toast.LENGTH_SHORT).show();

            Intent i = new Intent(getActivity(), HomeActivity.class);
            Prevalant.BackPresses.add("HomeA");
            i.putExtra("eeee", "Cart");
            startActivity(i);

        });

    }

    private void AddProC(String name, String sNum, String company, String pricee, String s, String image1) {

        DatabaseReference reftg = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        HashMap<String, Object> PPRR = new HashMap<>();

        PPRR.put("PName", name);
        PPRR.put("PNum", sNum);
        PPRR.put("PCom", company);
        PPRR.put("PPri", pricee);
        PPRR.put("PQut", s);
        PPRR.put("PPid", ProductId);
        PPRR.put("PImage", image1);

        reftg.child(ProductId).updateChildren(PPRR).addOnCompleteListener(task -> {

            Toast.makeText(getActivity(), "Product Added to Cart Successfully", Toast.LENGTH_SHORT).show();

        });

    }

    private void AddProB(String name, String sNum, String company, String pricee, String s, String image1) {

        DatabaseReference reftg = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Buy").child(Paper.book().read(Prevalant.BNum));

        DatabaseReference reftgj = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        HashMap<String, Object> PPRR = new HashMap<>();

        int TTPP = Integer.parseInt(pricee) * Integer.parseInt(s);

        int PPPP = TTPP + 15;

        PPRR.put("PName", name);
        PPRR.put("PNum", sNum);
        PPRR.put("PCom", company);
        PPRR.put("PPri", pricee);
        PPRR.put("PQut", s);
        PPRR.put("PPid", ProductId);
        PPRR.put("PImage", image1);

        if (!reftg.equals(null)) {
            reftg.removeValue();
        }

        reftg.child(ProductId).updateChildren(PPRR).addOnCompleteListener(task -> {

            reftgj.child(ProductId).updateChildren(PPRR).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Intent i = new Intent(getActivity(), ConfermOrderActivity.class);
                    i.putExtra("PPID", ProductId);
                    i.putExtra("TotalPrice", String.valueOf(PPPP));
                    i.putExtra("BuyF", "Buy");
                    i.putExtra("From", "A");
                    i.putExtra("IM", "A");
                    startActivity(i);
                }
            });

        });

    }

    private void Flip() {

        try {
            mPager.setAdapter(new ProductImageSlider(getActivity(), urls));

            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;

            indicator.setRadius(5 * density);

            NUM_PAGES = urls.length;

            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (currentPage == NUM_PAGES) {
                        currentPage = 0;
                    }
                    mPager.setCurrentItem(currentPage++, true);
                }
            };
            Timer swipeTimer = new Timer();
            swipeTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 5000, 5000);

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int pos) {

                }
            });
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void zoomImageFromThumb(final View thumbView, String imageResId) {

        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        Picasso.get().load(imageResId).into(expandedImageView);

        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        thumbView.getGlobalVisibleRect(startBounds);
        re_kk.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {

            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat(expandedImageView,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(shortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                currentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                currentAnimator = null;
            }
        });
        set.start();
        currentAnimator = set;

        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentAnimator != null) {
                    currentAnimator.cancel();
                }

                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(shortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        currentAnimator = null;
                    }
                });
                set.start();
                currentAnimator = set;
            }
        });
    }

}
