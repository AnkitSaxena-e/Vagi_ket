package com.example.euser;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.euser.Adapter.MySingleton;
import com.example.euser.Modal.Product;
import com.example.euser.Modal.SearchCategoryModal;
import com.example.euser.Prevalant.OrderPrevalent;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.View_Holder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;


public class ConfermOrderActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText Y_Full_Name, Y_Addres, Y_Number, Y_City, Y_Pin, Y_State;
    private NumberPicker H_Pic, M_Pic;
    private Button Confirm, Place, SDT, Set_Time;
    private TextView Change_Total, rem1, SetDT, rem2, tere, Time_AP;
    private RelativeLayout reR;
    private String Total_Price = "", PPIIDDDD = "", Set_Hour, Set_Minute, Set_AP, BuyFF, From = "", II = "";
    private Uri IMAGE;
    private String Name, Number, Address, City, Pin, type, State, CheckAddOrEn, downloadImageUri;
    private RecyclerView ReVA;
    private RecyclerView.LayoutManager layoutManagerAddress;
    private ProgressDialog LoadingBar;
    private FirebaseRecyclerOptions<SearchCategoryModal> options;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapter;
    private String FinalT = "ASAP";
    private DatabaseReference UAddref;
    private int i = 0;
    private int k = 0;
    private int j = 5;
    private int h = 0;
    private Dialog LoadingBarA, TimeDialog;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private ImageView back;
    private String De;
    private int m = 0;
    private int n = 0;
    private int f = 0;
    private int d = 0;
    private int Day, Month, Year, Hour, Minute, myDay, myMonth, myYear, myHour, myMinute;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAaG8jxA0:APA91bHr9CESgTqyuMCv0x5YQ5pEVgoW9C0pu3tdArtOKVBRKXMTV9YVPRbdXtUItQcfaI-q_5JT3kKzbD2QblnN1Dm17cWQFzLIoQbWzRz0jqxsoBnmh0MBIK1AuraD-5AH9qMq05Ll";

    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    private JsonObjectRequest jsonObjectRequest;

    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conferm_order);

        Paper.init(ConfermOrderActivity.this);

        Total_Price = getIntent().getStringExtra("TotalPrice");
        PPIIDDDD = getIntent().getStringExtra("PPID");
        BuyFF = getIntent().getStringExtra("BuyF");
        From = getIntent().getStringExtra("From");
        II = getIntent().getStringExtra("IM");

        LoadingBar = new ProgressDialog(ConfermOrderActivity.this);

        back = findViewById(R.id.back_sett_con_order);

        SDT = findViewById(R.id.SelectDeliveryTime);
        SetDT = findViewById(R.id.ShowDeliveryTime);

        De = Paper.book().read(Prevalant.DN);

        LoadingBarA = new Dialog(ConfermOrderActivity.this);
        LoadingBarA.setContentView(R.layout.loading_dialog);
        LoadingBarA.setCancelable(false);

        TimeDialog = new Dialog(ConfermOrderActivity.this);
        TimeDialog.setContentView(R.layout.time_dialog);
        TimeDialog.setCancelable(false);

        H_Pic = TimeDialog.findViewById(R.id.Hour_Picker);
        M_Pic = TimeDialog.findViewById(R.id.Minute_Picker);
        Time_AP = TimeDialog.findViewById(R.id.time_AP);

        Set_Time = TimeDialog.findViewById(R.id.Set_Time);

        Y_Full_Name = findViewById(R.id.Full_Name_Conferm);
        Y_Addres = findViewById(R.id.Address_Conferm);
        Y_Number = findViewById(R.id.Phone_Conferm);
        Y_City = findViewById(R.id.City_Conferm);
        Y_State = findViewById(R.id.State_Conferm);
        Y_Pin = findViewById(R.id.Pin_Conferm);

        Change_Total = findViewById(R.id.total_Price);

        rem1 = findViewById(R.id.remove11);
        rem2 = findViewById(R.id.remove21);

        reR = findViewById(R.id.kiuyt);
        tere = findViewById(R.id.lopl);

        type = Paper.book().read(Prevalant.CheckAdmin);
        Place = findViewById(R.id.Place_Button);

        Window window = ConfermOrderActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        if (De.equals("No")) {
            Change_Total.setText("Hello! " + Paper.book().read(Prevalant.UserNameA) + "Your total \n Amount is Rs." + Total_Price + "/-" + "\n" + "Delivery Charge = ₹15");
        } else {
            int o = Integer.parseInt(Total_Price) - 15;
            Change_Total.setText("Hello! " + Paper.book().read(Prevalant.UserNameA) + "Your total \n Amount is Rs." + String.valueOf(o) + "/-" + "\n" + "Delivery Charge = Free)");
        }

        DT();

        Place.setOnClickListener(v -> {

            Name = Y_Full_Name.getText().toString();
            Number = Y_Number.getText().toString();
            Address = Y_Addres.getText().toString();
            City = Y_City.getText().toString();
            State = Y_State.getText().toString();
            Pin = Y_Pin.getText().toString();

            Checkll(Name, Number, Address, City, State, Pin);

        });

        ReVA = findViewById(R.id.Recycler_Address);
//        RecyclerViewAddress.setHasFixedSize(true);
        ReVA.setItemViewCacheSize(20);
        ReVA.setDrawingCacheEnabled(true);
        ReVA.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAddress = new LinearLayoutManager(ConfermOrderActivity.this, LinearLayoutManager.VERTICAL, false);
        ReVA.setLayoutManager(layoutManagerAddress);

        back.setOnClickListener(v -> {

            Intent i = new Intent(ConfermOrderActivity.this, HomeActivity.class);
            i.putExtra("eeee", "ProductA");
            startActivity(i);

        });

    }

    private void DT() {

        Date currentTime = Calendar.getInstance().getTime();

        SetDT.setText("20 to 30 Min.");

        SDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                Year = c.get(Calendar.YEAR);
                Month = c.get(Calendar.MONTH);
                Day = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(ConfermOrderActivity.this, ConfermOrderActivity.this, Year, Month, Day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

    }

    private void onTimeSet() {

        Calendar c = Calendar.getInstance();

        H_Pic.setMaxValue(12);
        H_Pic.setMinValue(1);
        H_Pic.setWrapSelectorWheel(false);

        M_Pic.setMaxValue(59);
        M_Pic.setMinValue(0);
        M_Pic.setWrapSelectorWheel(false);

        if(Day == c.get(Calendar.DAY_OF_MONTH) && Month == c.get(Calendar.MONTH)){

            int Hour = c.get(Calendar.HOUR);
            int Minute = c.get(Calendar.MINUTE);



        } else {

        }

        Time_AP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Time_AP.getText().equals("AM")){
                    Time_AP.setText("PM");
                    Set_AP = "PM";
                } else{
                    Time_AP.setText("AM");
                    Set_AP = "AM";
                }
            }
        });



        String Final = Set_Hour + ":" + Set_Minute + " " + Day + "/" + Month + "/" + Year + " " + Time_AP;

        SetDT.setText(Final);

        FinalT = Final;

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        myYear = year;
        myMonth = month + 1;
        myDay = dayOfMonth;
        Calendar c = Calendar.getInstance();
        Hour = c.get(Calendar.HOUR);
        Minute = c.get(Calendar.MINUTE);

        timePickerDialog = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

                myHour = hourOfDay;
                myMinute = minute;

                String Final = myHour + ":" + myMinute + " " + myDay + "/" + myMonth + "/" + myYear;

                SetDT.setText(Final);

                FinalT = Final;

            }
        }, Hour, Minute, true);

        if(Day == c.get(Calendar.DAY_OF_MONTH) && Month == c.get(Calendar.MONTH)){

            if(Minute + 30 > 59){
                timePickerDialog.setMinTime(Hour + 1, Minute - 30, 0);
                timePickerDialog.show(getFragmentManager(), "Time");
            } else {
                timePickerDialog.setMinTime(Hour, Minute + 30, 0);
                timePickerDialog.show(getFragmentManager(), "Time");
            }

        } else {
            timePickerDialog.show(getFragmentManager(), "Time");
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        UAddref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        options =
                new FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UAddref, SearchCategoryModal.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int i, @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress());
                        holder.CatPin.setText(modal.getPin());
                        holder.CatEditButton.setVisibility(View.INVISIBLE);
                        holder.CatRemoveButton.setVisibility(View.INVISIBLE);

                        holder.itemView.setOnClickListener(v -> {

                            String Name = modal.getName();
                            String Number = modal.getNumber();
                            String Address = modal.getAddress();
                            String City = modal.getCity();
                            String State = modal.getState();
                            String Pin = modal.getPin();
                            String ll;

                            if (De.equals("No")) {
                                ll = Total_Price;
                            } else {
                                int o = Integer.parseInt(Total_Price) - 15;
                                ll = String.valueOf(o);
                            }

                            Paper.book().write(OrderPrevalent.NameP, Name);
                            Paper.book().write(OrderPrevalent.NumberP, Number);
                            Paper.book().write(OrderPrevalent.AddressP, Address);
                            Paper.book().write(OrderPrevalent.CityP, City);
                            Paper.book().write(OrderPrevalent.PinP, Pin);
                            Paper.book().write(OrderPrevalent.TotalPrice, ll);
                            Paper.book().write(OrderPrevalent.StateP, State);
                            Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));

                            if (From.equals("A")) {
                                GoCheck();
                            } else {
                                ImageOrder(Name, Number, Address, City, State, Pin);
                            }

                        });
                    }

                    @NonNull
                    @Override
                    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_resource_file, parent, false);
                        return new CategoryViewHolder(view);
                    }
                };
        ReVA.setAdapter(adapter);
        adapter.startListening();
    }

    private void CheckAddNoACO(String name, String number, String address, String city, String state, String pin) {

        DatabaseReference UserRefAddNo = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        UserRefAddNo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ArrayList<Product> pp = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        pp.add(ss.getValue(Product.class));
                    }

                    int kl = pp.size();

                    userInfoSavedCOCO(name, number, address, city, state, pin, kl);

                } else {

                    int kl = 0;

                    userInfoSavedCOCO(name, number, address, city, state, pin, kl);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void userInfoSavedCOCO(String name, String number, String address, String city, String state, String pin, int kl) {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        if (kl < 3) {

            j++;

            Time = SaveCurruntDate + SaveCurruntTime;

            HashMap<String, Object> map = new HashMap<>();

            map.put("Name", Y_Full_Name.getText().toString());
            map.put("Address", Y_Addres.getText().toString());
            map.put("Number", Y_Number.getText().toString());
            map.put("PID", Time);
            map.put("Pin", Y_Pin.getText().toString());
            map.put("City", Y_City.getText().toString());
            map.put("State", Y_State.getText().toString());

            Userref.child(Time).updateChildren(map).addOnCompleteListener(task -> {

                String jj;

                if (De.equals("No")) {
                    jj = Total_Price;
                } else {
                    int o = Integer.parseInt(Total_Price) - 15;
                    jj = String.valueOf(o);
                }

                Paper.book().write(OrderPrevalent.NameP, name);
                Paper.book().write(OrderPrevalent.NumberP, number);
                Paper.book().write(OrderPrevalent.AddressP, address);
                Paper.book().write(OrderPrevalent.CityP, city);
                Paper.book().write(OrderPrevalent.PinP, pin);
                Paper.book().write(OrderPrevalent.TotalPrice, jj);
                Paper.book().write(OrderPrevalent.StateP, state);
                Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));

                if (From.equals("A")) {
                    GoCheck();
                } else {
                    ImageOrder(name, number, address, city, state, pin);
                }

            });
        }

        String yy;

        if (De.equals("No")) {
            yy = Total_Price;
        } else {
            int o = Integer.parseInt(Total_Price) - 15;
            yy = String.valueOf(o);
        }

        Paper.book().write(OrderPrevalent.NameP, name);
        Paper.book().write(OrderPrevalent.NumberP, number);
        Paper.book().write(OrderPrevalent.AddressP, address);
        Paper.book().write(OrderPrevalent.CityP, city);
        Paper.book().write(OrderPrevalent.PinP, pin);
        Paper.book().write(OrderPrevalent.TotalPrice, yy);
        Paper.book().write(OrderPrevalent.UserID, Paper.book().read(Prevalant.UserIdA));
        Paper.book().write(OrderPrevalent.StateP, state);

        if (From.equals("A")) {
            GoCheck();
        } else {
            ImageOrder(name, number, address, city, state, pin);
        }

    }

    private void GoCheck() {

        String qq;

        if (De.equals("No")) {
            qq = Total_Price;
        } else {
            int o = Integer.parseInt(Total_Price) - 15;
            qq = String.valueOf(o);
        }

        int tp = Integer.parseInt(qq);

        if(tp >= 50){
            LoadingBar.dismiss();

            Intent i = new Intent(ConfermOrderActivity.this, ReviewOrderActivity.class);
            i.putExtra("PPPP", PPIIDDDD);
            i.putExtra("BuyFFF", BuyFF);
            i.putExtra("FT", FinalT);
            startActivity(i);
        } else {
            Toast.makeText(this, "Minimum Order Amount is ₹50", Toast.LENGTH_SHORT).show();
        }

    }

    private void Checkll(String name, String number, String address, String city, String state, String pin) {

        if (TextUtils.isEmpty(name)) {
            Y_Full_Name.setError("Please Enter Your Name");
        } else if (TextUtils.isEmpty(address)) {
            Y_Addres.setError("Please Enter Your Address");
        } else if (TextUtils.isEmpty(number)) {
            Y_Number.setError("Please Enter Your Number");
        } else if (TextUtils.isEmpty(city)) {
            Y_City.setError("Please Enter Your City");
        } else if (TextUtils.isEmpty(pin)) {
            Y_Pin.setError("Please Enter Your Pin");
        } else if (TextUtils.isEmpty(state)) {
            Y_State.setError("Please Enter Your State");
        } else {
            CheckAddNoACO(name, number, address, city, state, pin);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //    @Override
//    public void onBackPressed() {
//
//        if (BuyFF.equals("Cart")) {
//            Intent i = new Intent(ConfermOrderActivity.this, HomeActivity.class);
//            i.putExtra("eeee", "CartA");
//            startActivity(i);
//        } else {
//            Intent i = new Intent(ConfermOrderActivity.this, HomeActivity.class);
//            i.putExtra("eeee", "ProductA");
//            startActivity(i);
//        }
//
//
//    }

    private void ImageOrder(String name, String number, String address, String city, String state, String pin) {

        final String SaveCurruntTime, SaveCurruntDate, Time;

        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
        SaveCurruntDate = currentDate.format(calendar.getTime());

        @SuppressLint("SimpleDateFormat") SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
        SaveCurruntTime = currentTime.format(calendar.getTime());

        Time = SaveCurruntDate + SaveCurruntTime;

        final HashMap<String, Object> ordersMap = new HashMap<>();
        ordersMap.put("TotalAmount", "A");
        ordersMap.put("Name", name);
        ordersMap.put("PhoneNumber", number);
        ordersMap.put("Address", address);
        ordersMap.put("City", city);
        ordersMap.put("Pin", pin);
        ordersMap.put("Buy", "Doc");
        ordersMap.put("Return", "A");
        ordersMap.put("ONO", Prevalant.currentOnlineUser.getNumber());
        ordersMap.put("PID", Time);
        ordersMap.put("Doc", II);
        ordersMap.put("PNO", "A");
        ordersMap.put("Date", SaveCurruntDate);
        ordersMap.put("Time", SaveCurruntTime);
        ordersMap.put("DeliveryDetail", "--/--/--");

        final DatabaseReference orderRefGG = FirebaseDatabase.getInstance().getReference().child("Order").child(Time);

        orderRefGG.updateChildren(ordersMap).addOnCompleteListener(task -> {

            GetToken();

            Toast.makeText(ConfermOrderActivity.this, "Your Order Has Been Placed Successfully", Toast.LENGTH_LONG).show();
            Intent i = new Intent(ConfermOrderActivity.this, OrderPlacedDocActivity.class);
            LoadingBarA.dismiss();
            startActivity(i);
        });

    }

    private void GetToken() {

        DatabaseReference fh = FirebaseDatabase.getInstance().getReference().child("AdminTokens");

        fh.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    try {

                        AId((Map<String, Object>) snapshot.getValue());

                    } catch (Exception e) {

                        Toast.makeText(ConfermOrderActivity.this, "gg" + e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void AId(Map<String, Object> value) {

        ArrayList<String> Tomby = new ArrayList<>();

        for (Map.Entry<String, Object> en : value.entrySet()) {

            Map SPro = (Map) en.getValue();

            String oo = String.valueOf(SPro.get("Token"));

            Toast.makeText(this, "T - " + oo, Toast.LENGTH_SHORT).show();

            Tomby.add(oo);

        }

        Hathi(Tomby);

    }

    private void Hathi(ArrayList<String> tomby) {

        int Poppy = tomby.size();
        Toast.makeText(this, "No - " + Poppy, Toast.LENGTH_SHORT).show();

        for (int peela = 0; peela < Poppy; peela++) {

            String geela = tomby.get(peela);

            Toast.makeText(this, "Geela " + geela, Toast.LENGTH_SHORT).show();

            String To = geela;
            String Ti = "Order Alert";
            String Me = "You Have An Order....Please Check It..";

            sendNotificationss(To, Ti, Me);

        }

    }

    private void sendNotificationss(String to, String ti, String me) {

        Toast.makeText(this, "Geela " + to, Toast.LENGTH_SHORT).show();

        TOPIC = to; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = ti;
        NOTIFICATION_MESSAGE = me;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("to", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                response -> {
                    Log.i(TAG, "onResponse: " + response.toString());

                    try {
                        Toast.makeText(ConfermOrderActivity.this, "Rsponce " + response.get("success").toString(), Toast.LENGTH_SHORT).show();

                        String jj = response.get("success").toString();

                    } catch (JSONException e) {
                        Toast.makeText(ConfermOrderActivity.this, "2 " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                },
                error -> {
                    Toast.makeText(ConfermOrderActivity.this, "Request error", Toast.LENGTH_LONG).show();
                    Log.i(TAG, "onErrorResponse: Didn't work");
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

}