package com.example.eadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.Notification;
import android.os.Bundle;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.eadmin.Adapter.MySingleton;
import com.example.eadmin.Modal.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import android.util.Log;
import android.view.View;
import android.view.textclassifier.ConversationActions;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendAllNotiActivity extends AppCompatActivity {

    private JsonObjectRequest jsonObjectRequest;

    final private String FCM_API = "https://fcm.googleapis.com/fcm/send";
    final private String serverKey = "key=" + "AAAAYHeVQtI:APA91bFwMJ88aIQJW5SF-zU7s_6_WQFinREWW4c4YeH5cL1-mft1WA1t8PYag0sqPb68BkK39b8qeDGNYBEJnc6TJ4DB86pjetCF7Zmiv21Le8dBoZ1sntJ3r_A5BjBWIpa12JI6Vjip";

    final private String contentType = "application/json";
    final String TAG = "NOTIFICATION TAG";
    private List<Users> UList;
    private List<String> TokenList;

    private Dialog ialog;
    String NOTIFICATION_TITLE;
    String NOTIFICATION_MESSAGE;
    String TOPIC;

    private EditText Noti_Topic, Noti_Text;
    private Button Noti_Send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_all_noti);

        Noti_Topic = findViewById(R.id.NotiTopic);
        Noti_Text = findViewById(R.id.NotiE);
        Noti_Send = findViewById(R.id.NotiButt);

        ialog = new Dialog(SendAllNotiActivity.this);
        ialog.setContentView(R.layout.new_a);
        ialog.setCancelable(true);


        Noti_Send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Text = Noti_Text.getText().toString();
                String Headding = Noti_Topic.getText().toString();

                String Topic = "AllAnda";

                ialog.show();

                GetTokens(Topic, Headding, Text);

//                sendNotificationss(Topic, To, Le);

            }
        });

    }

    private void GetTokens(String topic, String headding, String text) {

        DatabaseReference ProductRef = FirebaseDatabase.getInstance().getReference().child("User");

        if (!ProductRef.equals(null)) {

            ProductRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    if (dataSnapshot.exists()) {

                        UList = new ArrayList<>();

                        for (DataSnapshot ss : dataSnapshot.getChildren()) {
                            UList.add(ss.getValue(Users.class));
                        }

                        for (int g = 0; g < UList.size(); g++) {

                            String h = UList.get(g).getToken();
                            TokenList.add(h);

                        }

                        SendAll(topic, headding, text, TokenList);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

    }

    private void SendAll(String topic, String headding, String text, List<String> tokenList) {




    }

    private void sendNotificationss(String to, String ti, String me) {

        TOPIC = to; //topic must match with what the receiver subscribed to
        NOTIFICATION_TITLE = ti;
        NOTIFICATION_MESSAGE = me;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        try {
            notifcationBody.put("title", NOTIFICATION_TITLE);
            notifcationBody.put("message", NOTIFICATION_MESSAGE);

            notification.put("topic", TOPIC);
            notification.put("data", notifcationBody);
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        sendNotification(notification);

    }

    private void sendNotification(JSONObject notification) {

        jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(SendAllNotiActivity.this, "Response  " + response, Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onResponse: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SendAllNotiActivity.this, "Request error", Toast.LENGTH_LONG).show();
                        Log.i(TAG, "onErrorResponse: Didn't work");
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", serverKey);
                params.put("Content-Type", contentType);
                return params;
            }
        };

        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);

        Toast.makeText(this, "Send Successfully", Toast.LENGTH_SHORT).show();

        ialog.dismiss();

    }

}