package com.example.euser;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euser.Fragments.CartFragment;
import com.example.euser.Fragments.CategoriesFragment;
import com.example.euser.Fragments.GalleryFragment;
import com.example.euser.Fragments.HomeFragment;
import com.example.euser.Fragments.NotificationFragment;
import com.example.euser.Fragments.OrderFragment;
import com.example.euser.Fragments.ProductDataFragment;
import com.example.euser.Fragments.ProfileFragment;
import com.example.euser.Fragments.SeeCategoryFragment;
import com.example.euser.Fragments.SlideshowFragment;
import com.example.euser.Prevalant.Prevalant;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import java.util.HashMap;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActionBarDrawerToggle toggle;
    private DrawerLayout drawer;
    private String ee;
    private NavigationView navigationView;
    private boolean doubleclick = false, yy = false;
    private Fragment fragment;
    private AppBarLayout layout;
    private TextView Dot;
    private ImageView Cart, NatificationC;
    private Toolbar toolbar;
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ee = getIntent().getStringExtra("eeee");

        Paper.init(HomeActivity.this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cart = findViewById(R.id.See_C);
        NatificationC = findViewById(R.id.Noti_C);

        Dot = findViewById(R.id.dot);
        toolbar.setTitle("Home");

        Window window = HomeActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#22BFC1"));

        fragment = new HomeFragment();

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(HomeActivity.this);

        toggle = new ActionBarDrawerToggle(HomeActivity.this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        try {
            Objects.requireNonNull(getSupportActionBar()).setElevation(0);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        NatificationC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Paper.book().read(Prevalant.UID).equals("NotiA")){

                    toolbar.setTitle("Home");
                    layout.setBackgroundColor(getResources().getColor(R.color.smoke));
                    Paper.book().write(Prevalant.UID, "HomeA");
                    FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_fragment_Home, new HomeFragment());
                    ft.addToBackStack(null);
                    ft.commit();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new HomeFragment()).commit();
//                    NatificationC.setImageResource(R.drawable.ic_baseline_notifications_24);

                } else {

                    toolbar.setTitle("Notification");
                    layout.setBackgroundColor(getResources().getColor(R.color.smoke));
                    Paper.book().write(Prevalant.UID, "NotiA");
                    FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_fragment_Home, new NotificationFragment());
                    ft.addToBackStack(null);
                    ft.commit();
//                    getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new NotificationFragment()).commit();
//                    NatificationC.setImageResource(R.drawable.ic_baseline_home_24);

                }
            }
        });

        try {

            View headerview = navigationView.getHeaderView(0);
            TextView usernameTextView = headerview.findViewById(R.id.textview_navnav);
            CircleImageView profileImageView = headerview.findViewById(R.id.imageView_navnav);
            String Name = Paper.book().read(Prevalant.UserNameA);
            Picasso.get().load(Paper.book().read(Prevalant.UserImageA).toString()).placeholder(R.drawable.profile).into(profileImageView);
            usernameTextView.setText(Name);

            profileImageView.setOnClickListener(v -> {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);

            });

            usernameTextView.setOnClickListener(v -> {

                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                drawer.closeDrawer(GravityCompat.START);

            });

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        Cart.setOnClickListener(v -> {
            GoCart();
        });

        switch (ee) {
            case "SearchA":
                toolbar.setTitle("Search");
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new GalleryFragment()).commit();
                FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frame_fragment_Home, new GalleryFragment());
                ft.addToBackStack(null);
                ft.commit();
                break;
            case "SettingA":
                toolbar.setTitle("Setting");
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new SlideshowFragment()).commit();
                FragmentTransaction f = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                f.replace(R.id.frame_fragment_Home, new ProfileFragment());
                f.addToBackStack(null);
                f.commit();
                break;
            case "OrderA":
                toolbar.setTitle("Order");
                FragmentTransaction fta = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                fta.replace(R.id.frame_fragment_Home, new OrderFragment());
                fta.addToBackStack(null);
                fta.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new OrderFragment()).commit();
                break;
            case "CategoryA":
                toolbar.setTitle("Category");
                FragmentTransaction ftq = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ftq.replace(R.id.frame_fragment_Home, new CategoriesFragment());
                ftq.addToBackStack(null);
                ftq.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CategoriesFragment()).commit();
                break;
            case "SeeCategoryA":
                toolbar.setTitle("Product");
                FragmentTransaction ftr = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ftr.replace(R.id.frame_fragment_Home, new SeeCategoryFragment());
                ftr.addToBackStack(null);
                ftr.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new SeeCategoryFragment()).commit();
                break;
            case "ProductA":
                toolbar.setTitle("Product");
                FragmentTransaction ftg = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ftg.replace(R.id.frame_fragment_Home, new ProductDataFragment());
                ftg.addToBackStack(null);
                ftg.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProductDataFragment()).commit();
                break;
            case "ProfileA":
                toolbar.setTitle("Profile");
                FragmentTransaction ftt = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ftt.replace(R.id.frame_fragment_Home, new ProfileFragment());
                ftt.addToBackStack(null);
                ftt.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new ProfileFragment()).commit();
                break;
            case "CartA":
                GoCart();
                break;
            default:
                FragmentTransaction ftj = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
                ftj.replace(R.id.frame_fragment_Home, new HomeFragment());
                ftj.addToBackStack(null);
                ftj.commit();
//                getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new HomeFragment()).commit();
                break;
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        updateToken();

    }

    public void GoCart(){

        if(Paper.book().read(Prevalant.UID).equals("CartA")){

            toolbar.setTitle("Home");
            Paper.book().write(Prevalant.UID, "HomeA");
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, new HomeFragment());
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new HomeFragment()).commit();
//            Cart.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        } else {
            toolbar.setTitle("Cart");
            Paper.book().write(Prevalant.UID, "CartA");
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, new CartFragment());
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, new CartFragment()).commit();
//            Cart.setImageResource(R.drawable.ic_baseline_home_24);

        }



    }

    private void updateToken() {

        String refreshToken= FirebaseInstanceId.getInstance().getToken();

        DatabaseReference rtgv = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Token", refreshToken);
        rtgv.updateChildren(hashMap);

        TakeUser();

    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Cart = snapshot.child("Cart").getValue().toString();
                    String Buy = snapshot.child("Buy").getValue().toString();

                    Paper.book().write(Prevalant.BNum, Buy);
                    Paper.book().write(Prevalant.CNum, Cart);

                    CheckNotification();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CheckNotification() {

        final DatabaseReference cartListRefABC = FirebaseDatabase.getInstance().getReference().child("OrderList").child(Paper.book().read(Prevalant.UserIdA))
                .child("Cart").child(Paper.book().read(Prevalant.CNum));

        cartListRefABC.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    Dot.setVisibility(View.VISIBLE);
                }else{
                    Dot.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        fragment = null;

        if (menuItem.getItemId() == R.id.nav_home) {
            fragment = new HomeFragment();
            toolbar.setTitle("Home");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_gallery) {
            fragment = new GalleryFragment();
            toolbar.setTitle("Search");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_order) {
            fragment = new OrderFragment();
            toolbar.setTitle("Order");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_categories) {
            fragment = new CategoriesFragment();
            toolbar.setTitle("Category");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_cart) {
            GoCart();
            drawer.closeDrawer(GravityCompat.START);
        } else if (menuItem.getItemId() == R.id.nav_slideshow) {
            fragment = new ProfileFragment();
            toolbar.setTitle("Setting");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        } else if (menuItem.getItemId() == R.id.nav_logout) {
            Paper.book().destroy();
            drawer.closeDrawer(GravityCompat.START);
            yy = true;
            Intent i = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(i);
        } else {
            fragment = new HomeFragment();
            toolbar.setTitle("Home");
            Prevalant.BackPresses.add("Off");
            drawer.closeDrawer(GravityCompat.START);
            FragmentTransaction ft = (HomeActivity.this).getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_fragment_Home, fragment);
            ft.addToBackStack(null);
            ft.commit();
//            getSupportFragmentManager().beginTransaction().replace(R.id.frame_fragment_Home, fragment).commit();
        }

        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            int c = getSupportFragmentManager().getBackStackEntryCount();

            if(c == 1){

                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();

                if (doubleclick) {
                    Intent hi = new Intent(Intent.ACTION_MAIN);
                    hi.addCategory(Intent.CATEGORY_HOME);
                    hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(hi);
                    return;
                }

                this.doubleclick = true;
                Toast.makeText(HomeActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

                new Handler().postDelayed(() -> doubleclick = false, 2000);

            } else {
                getSupportFragmentManager().popBackStack();
                Toast.makeText(this, "Back Necha wala", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onPause() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onPause();
        }
    }
}