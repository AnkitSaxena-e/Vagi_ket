package com.example.eadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

public class AddNotiActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
    private ImageView A_Image;
    private EditText A_Text;
    private Button Add_Noti, See_Noti;

    String productRandomKey, downloadImageUri1;
    StorageReference ProductImageRef;
    int dd = 0, ii = 0;
    private Dialog LoadingBar;
    private String storeCurruntDate, storeCurruntTime;
    private static final int GallaryPic = 1;
    private Uri UImage1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_noti);

        A_Image = findViewById(R.id.noImage);
        A_Text = findViewById(R.id.noText);

        Add_Noti = findViewById(R.id.noAdd);
        See_Noti = findViewById(R.id.SeeNoti);

        LoadingBar = new Dialog(AddNotiActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Notifica Pic");

        See_Noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AddNotiActivity.this, SeeNotiActivity.class);
                startActivity(i);
            }
        });

        Add_Noti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Text = A_Text.getText().toString();

                if(TextUtils.isEmpty(Text)){
                    A_Text.setError("Please Enter");
                } else if(UImage1.equals(null)){
                    Toast.makeText(AddNotiActivity.this, "Add Image", Toast.LENGTH_SHORT).show();
                } else {

                    LoadingBar.show();
                    AddNoti(Text);

                }
            }
        });

        A_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(AddNotiActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(AddNotiActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                    return;
                }

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), RESULT_LOAD_IMAGE);

            }
        });

    }

    private void AddNoti(String text) {

        try {

            final StorageReference filePath = ProductImageRef.child(UImage1.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0, 4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage1);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(AddNotiActivity.this, "Error! " + massage, Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddNotiActivity.this, "Product Image Upload Successfully", Toast.LENGTH_LONG).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                LoadingBar.dismiss();
                                throw task.getException();
                            }

                            downloadImageUri1 = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {

                                String i1 = task.getResult().toString();

                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Notification");

                                Calendar calendar = Calendar.getInstance();

                                SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
                                storeCurruntDate = currentDate.format(calendar.getTime());

                                SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
                                storeCurruntTime = currentTime.format(calendar.getTime());

                                productRandomKey = storeCurruntDate + storeCurruntTime;

                                HashMap<String, Object> productMap = new HashMap<>();

                                productMap.put("Pid", productRandomKey);
                                productMap.put("NotiImage", i1);
                                productMap.put("NotiText", text);

                                ref.child(productRandomKey).updateChildren(productMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        LoadingBar.dismiss();
                                        Toast.makeText(AddNotiActivity.this, "Done", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        }
                    });
                }
            });

        } catch (Exception e) {
            Toast.makeText(AddNotiActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            UImage1 = data.getData();
            A_Image.setImageURI(UImage1);

        } else {
            Toast.makeText(AddNotiActivity.this, "Error try Again....", Toast.LENGTH_SHORT).show();
        }

    }

}