package com.example.eadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eadmin.Adapter.UploadListAdapter;
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

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.paperdb.Paper;

public class AdminAddPhoneActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 1;
//    private RecyclerView mUploadList;

    private List<String> fileNameList;
    private List<String> fileDoneList;

    private ArrayList<String> ImageList;

    private UploadListAdapter uploadListAdapter;

    private StorageReference mStorage;

    private String Image1, Image2;
    private Uri UImage1, UImage2;

    String CategoryName, ProductName, Description, Company, Quantity, ProductKeyward, storeCurruntDate, storeCurruntTime;
    String ColorT1 = "A", ColorT2 = "A", ColorT3 = "A", ColorT4 = "A", ColorT5 = "A", ColorT6 = "A", ColorT7 = "A", ColorT8 = "A", ColorT9 = "A", ColorT10 = "A";
    String ColorT1Pri = "A", ColorT2Pri = "A", ColorT3Pri = "A", ColorT4Pri = "A", ColorT5Pri = "A", ColorT6Pri = "A", ColorT7Pri = "A",
            ColorT8Pri = "A", ColorT9Pri = "A", ColorT10Pri = "A";
    String OriColorT1Pri = "A", OriColorT2Pri = "A", OriColorT3Pri = "A", OriColorT4Pri = "A", OriColorT5Pri = "A", OriColorT6Pri = "A", OriColorT7Pri = "A",
            OriColorT8Pri = "A", OriColorT9Pri = "A", OriColorT10Pri = "A";
    String Table_Name, Table_Detail, Table_Brand;
    EditText productName, productDescription, productKeyeard, productCatagory, productQuantity, productCompany;
    EditText Color1, Color2, Color3, Color4, Color5, Color6, Color7, Color8, Color9, Color10;
    EditText Color1Pri, Color2Pri, Color3Pri, Color4Pri, Color5Pri, Color6Pri, Color7Pri, Color8Pri, Color9Pri, Color10Pri;
    EditText OriColor1Pri, OriColor2Pri, OriColor3Pri, OriColor4Pri, OriColor5Pri, OriColor6Pri, OriColor7Pri, OriColor8Pri, OriColor9Pri, OriColor10Pri;

    EditText Table_N, Table_D, Table_B;
    ImageView picPhoto1, picPhoto2;
    private String AAp1, AAp2, AAp3, AAp4, AAp5;
    String Write = "AS";
    private Uri AP1, AP2, AP3, AP4, AP5;
    Button addProduct, AddPic;
    DatabaseReference ProductRef;
    String productRandomKey, downloadImageUri1, downloadImageUri2;
    StorageReference ProductImageRef;
    int dd = 0, ii = 0;
    private Dialog LoadingBar;
    private String Chh, CatChh;
    private int A = 0, C = 0;
    private static final int GallaryPic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_phone);

        Chh = getIntent().getStringExtra("loik");

        mStorage = FirebaseStorage.getInstance().getReference();

        fileNameList = new ArrayList<>();
        fileDoneList = new ArrayList<>();

        ImageList = new ArrayList<String>();

        Paper.init(AdminAddPhoneActivity.this);

        productName = findViewById(R.id.ProductName);
        productDescription = findViewById(R.id.ProductDescription);
        productKeyeard = findViewById(R.id.ProductKeyward);
        productCatagory = findViewById(R.id.ProductCategory);
        productCompany = findViewById(R.id.ProductCompany);
        productQuantity = findViewById(R.id.ProductQuantity);

        AddPic = findViewById(R.id.ButtonIm);

        picPhoto1 = findViewById(R.id.PicPhoto1);
        picPhoto2 = findViewById(R.id.PicPhoto2);

        Table_N = findViewById(R.id.name_T_A);
        Table_D = findViewById(R.id.Detail_T_A);
        Table_B = findViewById(R.id.brand_T_A);

        Color1 = findViewById(R.id.ProductColor1);
        Color2 = findViewById(R.id.ProductColor2);
        Color3 = findViewById(R.id.ProductColor3);
        Color4 = findViewById(R.id.ProductColor4);
        Color5 = findViewById(R.id.ProductColor5);
        Color6 = findViewById(R.id.ProductColor6);
        Color7 = findViewById(R.id.ProductColor7);
        Color8 = findViewById(R.id.ProductColor8);
        Color9 = findViewById(R.id.ProductColor9);
        Color10 = findViewById(R.id.ProductColor10);

        Color1Pri= findViewById(R.id.ProductColor1Pri);
        Color2Pri= findViewById(R.id.ProductColor2Pri);
        Color3Pri= findViewById(R.id.ProductColor3Pri);
        Color4Pri= findViewById(R.id.ProductColor4Pri);
        Color5Pri= findViewById(R.id.ProductColor5Pri);
        Color6Pri= findViewById(R.id.ProductColor6Pri);
        Color7Pri= findViewById(R.id.ProductColor7Pri);
        Color8Pri= findViewById(R.id.ProductColor8Pri);
        Color9Pri= findViewById(R.id.ProductColor9Pri);
        Color10Pri = findViewById(R.id.ProductColor10Pri);

        OriColor1Pri= findViewById(R.id.ProductColor1PriOri);
        OriColor2Pri= findViewById(R.id.ProductColor2PriOri);
        OriColor3Pri= findViewById(R.id.ProductColor3PriOri);
        OriColor4Pri= findViewById(R.id.ProductColor4PriOri);
        OriColor5Pri= findViewById(R.id.ProductColor5PriOri);
        OriColor6Pri= findViewById(R.id.ProductColor6PriOri);
        OriColor7Pri= findViewById(R.id.ProductColor7PriOri);
        OriColor8Pri= findViewById(R.id.ProductColor8PriOri);
        OriColor9Pri= findViewById(R.id.ProductColor9PriOri);
        OriColor10Pri = findViewById(R.id.ProductColor10PriOri);


        if(!Chh.equals("Other")){
            productCatagory.setVisibility(View.INVISIBLE);
        }

        LoadingBar = new Dialog(AdminAddPhoneActivity.this);
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);
        addProduct = findViewById(R.id.AddProduct);

        CategoryName = getIntent().getStringExtra("category");
        ProductImageRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products");


        AddPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ActivityCompat.checkSelfPermission(AdminAddPhoneActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){

                    ActivityCompat.requestPermissions(AdminAddPhoneActivity.this,
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

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ValidateProduct();
            }
        });

    }

    private void ValidateProduct() {

        try {

            ProductName = productName.getText().toString();
            Description = productDescription.getText().toString();
            ProductKeyward = productKeyeard.getText().toString();
            Quantity = productQuantity.getText().toString();
            Table_Name = Table_N.getText().toString();
            Table_Detail = Table_D.getText().toString();
            Table_Brand = Table_B.getText().toString();
            CatChh = productCatagory.getText().toString();
            Company = productCompany.getText().toString();

            ColorT1 = Color1.getText().toString();
            ColorT2 = Color2.getText().toString();
            ColorT3 = Color3.getText().toString();
            ColorT4 = Color4.getText().toString();
            ColorT5 = Color5.getText().toString();
            ColorT6 = Color6.getText().toString();
            ColorT7 = Color7.getText().toString();
            ColorT8 = Color8.getText().toString();
            ColorT9 = Color9.getText().toString();
            ColorT10 = Color10.getText().toString();

            ColorT1Pri = Color1Pri.getText().toString();
            ColorT2Pri = Color2Pri.getText().toString();
            ColorT3Pri = Color3Pri.getText().toString();
            ColorT4Pri = Color4Pri.getText().toString();
            ColorT5Pri = Color5Pri.getText().toString();
            ColorT6Pri = Color6Pri.getText().toString();
            ColorT7Pri = Color7Pri.getText().toString();
            ColorT8Pri = Color8Pri.getText().toString();
            ColorT9Pri = Color9Pri.getText().toString();
            ColorT10Pri= Color10Pri.getText().toString();

            OriColorT1Pri = OriColor1Pri.getText().toString();
            OriColorT2Pri = OriColor2Pri.getText().toString();
            OriColorT3Pri = OriColor3Pri.getText().toString();
            OriColorT4Pri = OriColor4Pri.getText().toString();
            OriColorT5Pri = OriColor5Pri.getText().toString();
            OriColorT6Pri = OriColor6Pri.getText().toString();
            OriColorT7Pri = OriColor7Pri.getText().toString();
            OriColorT8Pri = OriColor8Pri.getText().toString();
            OriColorT9Pri = OriColor9Pri.getText().toString();
            OriColorT10Pri = OriColor10Pri.getText().toString();


            if(Chh.equals("Other")){

                if(CatChh.isEmpty()){
                    productCatagory.setError("Please Enter");
                }else{
                    Chh = CatChh;
                }

            }

            if (TextUtils.isEmpty(ProductName)) {
                productName.setError("Please Enter");
            } else if (TextUtils.isEmpty(Description)) {
                productDescription.setError("Please Enter");
            } else if (TextUtils.isEmpty(ProductKeyward)) {
                productKeyeard.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Name)) {
                Table_N.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Brand)) {
                Table_B.setError("Please Enter");
            } else if (TextUtils.isEmpty(Table_Detail)) {
                Table_D.setError("Please Enter");
            } else if (TextUtils.isEmpty(Company)) {
                productCompany.setError("Please Enter");
            } else if (TextUtils.isEmpty(Quantity)) {
                productQuantity.setError("Please Enter");
            } else {

                if (ColorT1.isEmpty()) {
                    Color1.setError("Please Enter");
                    Color1Pri.setError("Please Enter");
                    OriColor1Pri.setError("Please Enter");
                }
                if (ColorT2.isEmpty()) {
                    ColorT2 = "A";
                    ColorT2Pri = "A";
                    OriColorT2Pri = "A";
                }
                if (ColorT3.isEmpty()) {
                    ColorT3 = "A";
                    ColorT3Pri = "A";
                    OriColorT3Pri = "A";
                }
                if (ColorT4.isEmpty()) {
                    ColorT4 = "A";
                    ColorT4Pri = "A";
                    OriColorT4Pri = "A";
                }
                if (ColorT5.isEmpty()) {
                    ColorT5 = "A";
                    ColorT5Pri = "A";
                    OriColorT5Pri = "A";
                }
                if (ColorT6.isEmpty()) {
                    ColorT6 = "A";
                    ColorT6Pri = "A";
                    OriColorT6Pri = "A";
                }
                if (ColorT7.isEmpty()) {
                    ColorT7 = "A";
                    ColorT7Pri = "A";
                    OriColorT7Pri = "A";
                }
                if (ColorT8.isEmpty()) {
                    ColorT8 = "A";
                    ColorT8Pri = "A";
                    OriColorT8Pri = "A";
                }
                if (ColorT9.isEmpty()) {
                    ColorT9 = "A";
                    ColorT9Pri = "A";
                    OriColorT9Pri = "A";
                }
                if (ColorT10.isEmpty()) {
                    ColorT10 = "A";
                    ColorT10Pri = "A";
                    OriColorT10Pri = "A";
                }

                if (!ColorT1.isEmpty() && TextUtils.isEmpty(ColorT1Pri)) {
                    Color1Pri.setError("Please Enter");
                }
                if (!ColorT2.isEmpty() && TextUtils.isEmpty(ColorT2Pri)) {
                    Color2Pri.setError("Please Enter");
                }
                if (!ColorT3.isEmpty() && TextUtils.isEmpty(ColorT3Pri)) {
                    Color3Pri.setError("Please Enter");
                }
                if (!ColorT4.isEmpty() && TextUtils.isEmpty(ColorT4Pri)) {
                    Color4Pri.setError("Please Enter");
                }
                if (!ColorT5.isEmpty() && TextUtils.isEmpty(ColorT5Pri)) {
                    Color5Pri.setError("Please Enter");
                }
                if (!ColorT6.isEmpty() && TextUtils.isEmpty(ColorT6Pri)) {
                    Color6Pri.setError("Please Enter");
                }
                if (!ColorT7.isEmpty() && TextUtils.isEmpty(ColorT7Pri)) {
                    Color7Pri.setError("Please Enter");
                }
                if (!ColorT8.isEmpty() && TextUtils.isEmpty(ColorT8Pri)) {
                    Color8Pri.setError("Please Enter");
                }
                if (!ColorT9.isEmpty() && TextUtils.isEmpty(ColorT9Pri)) {
                    Color9Pri.setError("Please Enter");
                }
                if (!ColorT10.isEmpty() && TextUtils.isEmpty(ColorT10Pri)) {
                    Color10Pri.setError("Please Enter");
                }



                if (!ColorT1.isEmpty() && TextUtils.isEmpty(OriColorT1Pri)) {
                    OriColor1Pri.setError("Please Enter");
                }
                if (!ColorT2.isEmpty() && TextUtils.isEmpty(OriColorT2Pri)) {
                    OriColor2Pri.setError("Please Enter");
                }
                if (!ColorT3.isEmpty() && TextUtils.isEmpty(OriColorT3Pri)) {
                    OriColor3Pri.setError("Please Enter");
                }
                if (!ColorT4.isEmpty() && TextUtils.isEmpty(OriColorT4Pri)) {
                    OriColor4Pri.setError("Please Enter");
                }
                if (!ColorT5.isEmpty() && TextUtils.isEmpty(OriColorT5Pri)) {
                    OriColor5Pri.setError("Please Enter");
                }
                if (!ColorT6.isEmpty() && TextUtils.isEmpty(OriColorT6Pri)) {
                    OriColor6Pri.setError("Please Enter");
                }
                if (!ColorT7.isEmpty() && TextUtils.isEmpty(OriColorT7Pri)) {
                    OriColor7Pri.setError("Please Enter");
                }
                if (!ColorT8.isEmpty() && TextUtils.isEmpty(OriColorT8Pri)) {
                    OriColor8Pri.setError("Please Enter");
                }
                if (!ColorT9.isEmpty() && TextUtils.isEmpty(OriColorT9Pri)) {
                    OriColor9Pri.setError("Please Enter");
                }
                if (!ColorT10.isEmpty() && TextUtils.isEmpty(OriColorT10Pri)) {
                    OriColor10Pri.setError("Please Enter");
                }

                else {

                    LoadingBar.show();
                    AddImage1(ColorT1, ColorT2, ColorT3, ColorT4, ColorT5, ColorT6, ColorT7, ColorT8, ColorT9, ColorT10);
                }
            }

        }
        catch (Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void AddImage1(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                           String colorT8, String colorT9, String colorT10) {

        try{

            final StorageReference filePath = ProductImageRef.child(UImage1.getLastPathSegment().substring(0, 4) + UUID.randomUUID()
                    .toString().substring(0,4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage1);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(AdminAddPhoneActivity.this,"Error! " + massage,Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminAddPhoneActivity.this,"Product Image Upload Successfully",Toast.LENGTH_LONG).show();

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
                            if(task.isSuccessful()){

                                String i1 = task.getResult().toString();
                                Toast.makeText(AdminAddPhoneActivity.this,"Getting Product Image 1 Uri Successfully" + i1,Toast.LENGTH_LONG).show();

                                AddImage2(colorT1, colorT2, colorT3, colorT4, colorT5, colorT6, colorT7, colorT8, colorT9, colorT10, i1);
                            }
                        }
                    });
                }
            });

        }catch(Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void AddImage2(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                           String colorT8, String colorT9, String colorT10, String downloadImageUri1) {

        try{

            final StorageReference filePath = ProductImageRef.child(UImage2.getLastPathSegment().substring(0, 4) + UUID.randomUUID().toString().substring(0,4) + ".jpg");

            final UploadTask uploadTask = filePath.putFile(UImage2);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String massage = e.toString();
                    Toast.makeText(AdminAddPhoneActivity.this,"Error! " + massage,Toast.LENGTH_LONG).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AdminAddPhoneActivity.this,"Product Image Upload Successfully",Toast.LENGTH_LONG).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                LoadingBar.dismiss();
                                throw task.getException();
                            }

                            downloadImageUri2 = filePath.getDownloadUrl().toString();
                            return filePath.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if(task.isSuccessful()){

                                String i2 = task.getResult().toString();
                                Toast.makeText(AdminAddPhoneActivity.this,"Getting Product Image 2 Uri Successfully" + i2,Toast.LENGTH_LONG).show();

                                SaveProductInfo(colorT1, colorT2, colorT3, colorT4, colorT5, colorT6, colorT7, colorT8, colorT9, colorT10, downloadImageUri1, i2);
                            }
                        }
                    });
                }
            });

        }catch(Exception e){
            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    private void SaveProductInfo(String colorT1, String colorT2, String colorT3, String colorT4, String colorT5, String colorT6, String colorT7,
                                 String colorT8, String colorT9, String colorT10, String image1, String image2) {

            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat currentDate = new SimpleDateFormat("yyyy-MM-dd");
            storeCurruntDate = currentDate.format(calendar.getTime());

            SimpleDateFormat currentTime = new SimpleDateFormat(" hh:mm:ss a");
            storeCurruntTime = currentTime.format(calendar.getTime());

            productRandomKey = storeCurruntDate + storeCurruntTime;

            String UID = UUID.randomUUID().toString().substring(0, 35);

            HashMap<String, Object> productMap = new HashMap<>();

            productMap.put("Pid", productRandomKey);
            productMap.put("Date", storeCurruntDate);
            productMap.put("Time", storeCurruntTime);
            productMap.put("Description", Description);
            productMap.put("TName", Table_Name);
            productMap.put("TDetail", Table_Detail);
            productMap.put("TBrand", Table_Brand);
            productMap.put("Keyward", ProductKeyward);
            productMap.put("Company", Company);
            productMap.put("No1", colorT1);
            productMap.put("No2", colorT2);
            productMap.put("No3", colorT3);
            productMap.put("No4", colorT4);
            productMap.put("No5", colorT5);
            productMap.put("No6", colorT6);
            productMap.put("No7", colorT7);
            productMap.put("No8", colorT8);
            productMap.put("No9", colorT9);
            productMap.put("No10", colorT10);
            productMap.put("No1Price", ColorT1Pri);
            productMap.put("No2Price", ColorT2Pri);
            productMap.put("No3Price", ColorT3Pri);
            productMap.put("No4Price", ColorT4Pri);
            productMap.put("No5Price", ColorT5Pri);
            productMap.put("No6Price", ColorT6Pri);
            productMap.put("No7Price", ColorT7Pri);
            productMap.put("No8Price", ColorT8Pri);
            productMap.put("No9Price", ColorT9Pri);
            productMap.put("No10Price", ColorT10Pri);
            productMap.put("No1PriceOri", OriColorT1Pri);
            productMap.put("No2PriceOri", OriColorT2Pri);
            productMap.put("No3PriceOri", OriColorT3Pri);
            productMap.put("No4PriceOri", OriColorT4Pri);
            productMap.put("No5PriceOri", OriColorT5Pri);
            productMap.put("No6PriceOri", OriColorT6Pri);
            productMap.put("No7PriceOri", OriColorT7Pri);
            productMap.put("No8PriceOri", OriColorT8Pri);
            productMap.put("No9PriceOri", OriColorT9Pri);
            productMap.put("No10PriceOri", OriColorT10Pri);
            productMap.put("Category", Chh);
            productMap.put("Quantity", Quantity);
            productMap.put("S1", "10");
            productMap.put("S2", "2");
            productMap.put("S3", "4");
            productMap.put("S4", "3");
            productMap.put("S5", "9");
            productMap.put("Image1", image1);
            productMap.put("Image2", image2);
            productMap.put("ProductName", ProductName);

            ProductRef.child(productRandomKey).updateChildren(productMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(AdminAddPhoneActivity.this, AdminMainActivity.class);
                                startActivity(i);
                                LoadingBar.dismiss();
                                Toast.makeText(AdminAddPhoneActivity.this, "Product is added successfully.......", Toast.LENGTH_LONG).show();
                            } else {
                                String massage = task.getException().toString();
                                Toast.makeText(AdminAddPhoneActivity.this, "Error...." + massage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });

    }


    private void OpenGallary() {
        dd++;
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, GallaryPic);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK){

            if(data.getClipData() != null){

                int totalItemsSelected = data.getClipData().getItemCount();

                List<Bitmap> bitmaps = new ArrayList<>();
                ClipData clipData = data.getClipData();

                if(clipData != null) {
                    for (int i = 0; i < clipData.getItemCount(); i++) {
                        Uri imageUri = clipData.getItemAt(i).getUri();

                        try {
                            InputStream is = getContentResolver().openInputStream(imageUri);
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            bitmaps.add(bitmap);
                        } catch (FileNotFoundException e) {
                            Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{

                    Uri imageUri = data.getData();

                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                        bitmaps.add(bitmap);
                    } catch (FileNotFoundException e) {
                        Toast.makeText(AdminAddPhoneActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


                int o = 0;

                for (Bitmap bm : bitmaps) {
                    o++;
                    addProduct.setVisibility(View.VISIBLE);

                    if(o == 1){
                        UImage1 = getImageUri(AdminAddPhoneActivity.this, bm);
                        Toast.makeText(this, UImage1.toString(), Toast.LENGTH_SHORT).show();
                        picPhoto1.setImageBitmap(bm);
                    } else if(o == 2){
                        UImage2 = getImageUri(AdminAddPhoneActivity.this, bm);
                        Toast.makeText(this, UImage2.toString(), Toast.LENGTH_SHORT).show();
                        picPhoto2.setImageBitmap(bm);
                    }

                }

            } else if (data.getData() != null){
                Toast.makeText(AdminAddPhoneActivity.this, "Selected Single File", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return  Uri.parse(path);
    }

    public String getImageString(Bitmap inImage){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        byte[] b = bytes.toByteArray();
        String path = Base64.encodeToString(b, Base64.DEFAULT);
        return path;
    }

}