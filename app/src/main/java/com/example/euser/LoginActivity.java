package com.example.euser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.euser.Adapter.CounteryData;
import com.example.euser.Modal.Users;
import com.example.euser.Prevalant.Prevalant;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Map;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {

    private MaterialEditText Phone, Passward;
    private TextView Forgot_Passward;
    private Button Login;
    private Dialog LoadingBar;
    private Spinner spinner;
    private ImageButton show;
    private String Image1 = "AS", Image2 = "AS",Image3 = "AS", Image4 = "AS", Image5 = "AS";
    private String parantName = "User";
    private String IIMM = "AS", Sus = "No";
    private boolean doubleclick = false;
    private int i = 0, llll = 0;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 12345;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        Paper.init(LoginActivity.this);

        LoadingBar = new Dialog(LoginActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        Phone = findViewById(R.id.Number_Login);
        Passward = findViewById(R.id.Passward_Login);
        show = findViewById(R.id.Show);

        Login = findViewById(R.id.Join_login);
        Forgot_Passward = findViewById(R.id.ForgotP);
        Paper.init(this);

        Prevalant.SuspendUser = new ArrayList<>();

        if(ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.INTERNET)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_SMS)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.RECEIVE_SMS)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SEND_SMS)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                + ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW)
                != PackageManager.PERMISSION_GRANTED){

            if(ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.INTERNET)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.ACCESS_NETWORK_STATE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.RECEIVE_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.SEND_SMS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, Manifest.permission.SYSTEM_ALERT_WINDOW))
            {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);

                builder.setTitle("Grant Those Permissions");
                builder.setMessage("Internet, Read and Write Storage and Send SMS");
                builder.setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.SYSTEM_ALERT_WINDOW
                        },ASK_MULTIPLE_PERMISSION_REQUEST_CODE));

                builder.setNegativeButton("Cancel", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }else{
                ActivityCompat.requestPermissions(LoginActivity.this,
                        new String[]{
                                Manifest.permission.INTERNET,
                                Manifest.permission.ACCESS_NETWORK_STATE,
                                Manifest.permission.READ_SMS,
                                Manifest.permission.RECEIVE_SMS,
                                Manifest.permission.SEND_SMS,
                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.SYSTEM_ALERT_WINDOW
                        },ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }else{
            Toast.makeText(getApplicationContext(), "Permission is already Granted", Toast.LENGTH_SHORT).show();
        }

        spinner = findViewById(R.id.spinnerCountriesL);
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, CounteryData.countryNames));

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Phone.setBackgroundResource(R.drawable.edit_text_back);
                spinner.setBackgroundResource(R.drawable.edit_text_back);
                Passward.setBackgroundResource(R.drawable.lineline);
                show.setBackgroundResource(R.drawable.lineline);

            }
        });

        Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Phone.setBackgroundResource(R.drawable.lineline);
                spinner.setBackgroundResource(R.drawable.lineline);
                Passward.setBackgroundResource(R.drawable.edit_text_back);
                show.setBackgroundResource(R.drawable.edit_text_back);

            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Phone.setBackgroundResource(R.drawable.lineline);
                spinner.setBackgroundResource(R.drawable.lineline);
                Passward.setBackgroundResource(R.drawable.edit_text_back);
                show.setBackgroundResource(R.drawable.edit_text_back);

            }
        });

        String defaul = "India";
        ArrayAdapter myAdapter = (ArrayAdapter) spinner.getAdapter();
        int position = myAdapter.getPosition(defaul);
        spinner.setSelection(position);

        TakeUser();

        Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());

        Login.setOnClickListener(v -> LoginAccount(parantName));

        show.setOnClickListener(v -> {
            if(i == 0){
                Passward.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                show.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.ic_visibility));
                i++;
            }

            else{
                Passward.setTransformationMethod(PasswordTransformationMethod.getInstance());
                show.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.ic_visibility_off));
                i = 0;
            }
        });

        Forgot_Passward.setOnClickListener(v -> {

            String Number = Phone.getText().toString();
            String code = CounteryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String PhoneNum = "+" + code + Number;

            if(PhoneNum.isEmpty()){
                Phone.setError("Please Enter Your UserId");
            }

            else {

                ForgotPassward(PhoneNum);

            }
        });

        Paper.book().write(Prevalant.CheckH, "A");

        String UserPhoneKey = Paper.book().read(Prevalant.userPhoneKey);
        String UserPassward = Paper.book().read(Prevalant.userPasswardKey);
        String UserType = Paper.book().read(Prevalant.CheckAdmin);

        if(UserPhoneKey != "" && UserPassward != ""){
            if(!TextUtils.isEmpty(UserPhoneKey) && !TextUtils.isEmpty(UserPassward)){

                LoadingBar.show();

                parantName = UserType;

                goToAdd(UserPhoneKey, UserPassward);
            }
        }
    }

    public void TakeUser() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("User");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        CId((Map<String, Object>) snapshot.getValue());

                    } catch (Exception e) {

                        Toast.makeText(LoginActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void CId(Map<String, Object> snapshot) {

        for (Map.Entry<String, Object> en : snapshot.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Suspend"));

            if (oo.equals("Suspended")) {

                Sus = "Susp";
            }
        }
    }

    private void ForgotPassward(String phoneNum) {

        DatabaseReference trf = FirebaseDatabase.getInstance().getReference().child("User").child(phoneNum);

        trf.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    String Nuum = snapshot.child("Number").getValue().toString();

                    Intent i = new Intent(LoginActivity.this, changePassward.class);
                    i.putExtra("PhoneNumber", Nuum);
                    i.putExtra("UserId", phoneNum);
                    startActivity(i);

                }
                else{

                    Toast.makeText(LoginActivity.this, "This Account Does Not Exist..", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(LoginActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void LoginAccount(String parantName) {

        try {

            String Number = Phone.getText().toString();
            String code = CounteryData.countryAreaCodes[spinner.getSelectedItemPosition()];
            String phoneNumber = "+" + code + Number;
            String passward = Passward.getText().toString();

            if (TextUtils.isEmpty(phoneNumber)) {
                Phone.setError("Please Enter User Id");
            } else if (TextUtils.isEmpty(passward)) {
                Passward.setError("Please Enter Your Phone Password");
            } else if(passward.length() < 8) {
                Passward.setError("Minimum 8 Characters Required");
            } else {

                LoadingBar.show();
                goToAdd(phoneNumber, passward);

            }
        }
        catch (Exception e){
            Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void CheckFireBase(final String Number, final String passward, final String image1, final String image2, final String image3,
                               final String image4, final String image5){

        try {
            Paper.book().write(Prevalant.userPhoneKey, Number);
            Paper.book().write(Prevalant.userPasswardKey, passward);
            Paper.book().write(Prevalant.CheckAdmin, parantName);

            Paper.book().write(Prevalant.AD1, image1);
            Paper.book().write(Prevalant.AD2, image2);
            Paper.book().write(Prevalant.AD3, image3);
            Paper.book().write(Prevalant.AD4, image4);
            Paper.book().write(Prevalant.AD5, image5);

            final DatabaseReference RootRef;
            RootRef = FirebaseDatabase.getInstance().getReference();

            RootRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.child(parantName).child(Number).exists()) {

                        Users userdata = dataSnapshot.child(parantName).child(Number).getValue(Users.class);

                        String NNAA = dataSnapshot.child(parantName).child(Number).child("Name").getValue().toString();
                        String NNUU = dataSnapshot.child(parantName).child(Number).child("Number").getValue().toString();

                        String Buy = dataSnapshot.child(parantName).child(Number).child("Buy").getValue().toString();
                        String Cart = dataSnapshot.child(parantName).child(Number).child("Cart").getValue().toString();

                        String Dt = dataSnapshot.child(parantName).child(Number).child("Deli").getValue().toString();

                        Paper.book().write(Prevalant.BNum, Buy);
                        Paper.book().write(Prevalant.CNum, Cart);

                        if(dataSnapshot.child(parantName).child(Number).child("Image").exists()){
                            IIMM = dataSnapshot.child(parantName).child(Number).child("Image").getValue().toString();
                        }

                        if (userdata.getNumber().equals(Number)) {

                            if (userdata.getPassward().equals(passward)) {

                                if(!Sus.equals("Susp")){
                                    Toast.makeText(LoginActivity.this, "Logged in Successfully", Toast.LENGTH_LONG).show();
                                    LoadingBar.dismiss();
                                    Paper.book().write(Prevalant.UserNameA, NNAA);
                                    Paper.book().write(Prevalant.UserImageA, IIMM);
                                    Paper.book().write(Prevalant.UserNumberA, NNUU);
                                    Paper.book().write(Prevalant.UserIdA, Number);
                                    Paper.book().write(Prevalant.ACAdmin, "No");
                                    Paper.book().write(Prevalant.FAD, "HomeA");
                                    Paper.book().write(Prevalant.DN, Dt);
                                    Paper.book().write(Prevalant.DNR, "Dt");

                                    Intent i = new Intent(LoginActivity.this, HomeActivity.class);
                                    Prevalant.currentOnlineUser = userdata;
                                    Paper.book().write(Prevalant.userName, userdata);
                                    Paper.book().write(Prevalant.FAD, "HomeA");
                                    Paper.book().write(Prevalant.UID, "HomeA");
                                    Paper.book().write(Prevalant.CheckAdmin, "User");
                                    i.putExtra("eeee", "LA");
                                    startActivity(i);
                                }else{
                                    Toast.makeText(LoginActivity.this, "!Your Account is Suspended....!", Toast.LENGTH_SHORT).show();
                                    LoadingBar.dismiss();
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_LONG).show();
                                LoadingBar.dismiss();
                                Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Account with this " + Number + " id does not exsit", Toast.LENGTH_LONG).show();
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "Please Try Again", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Account with this " + Number + " id does not exsit", Toast.LENGTH_LONG).show();
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Please Create a new Account", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        catch (Exception e){
            Toast.makeText(LoginActivity.this,e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void goToAdd(final String phoneNumber, final String passward) {

        DatabaseReference ADref = FirebaseDatabase.getInstance().getReference().child("Ads");

        ADref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Image1 = dataSnapshot.child("Image1").getValue().toString();
                    Image2 = dataSnapshot.child("Image2").getValue().toString();
                    Image3 = dataSnapshot.child("Image3").getValue().toString();
                    Image4 = dataSnapshot.child("Image4").getValue().toString();
                    Image5 = dataSnapshot.child("Image4").getValue().toString();

                }
                CheckFireBase(phoneNumber, passward, Image1, Image2, Image3, Image4, Image5);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(doubleclick){
            Intent hi = new Intent(Intent.ACTION_MAIN);
            hi.addCategory(Intent.CATEGORY_HOME);
            hi.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(hi);
            return;
        }

        this.doubleclick = true;
        Toast.makeText(LoginActivity.this, "Press Again to Exit", Toast.LENGTH_LONG).show();

        new Handler().postDelayed(() -> doubleclick = false, 2000);
    }
}