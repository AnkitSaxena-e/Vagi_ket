package com.example.euser.Fragments;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.euser.AboutActivity;
import com.example.euser.ChangePasswardUserActivity;
import com.example.euser.EditAddressActivity;
import com.example.euser.HomeActivity;
import com.example.euser.LoginActivity;
import com.example.euser.Modal.SearchCategoryModal;
import com.example.euser.Prevalant.Prevalant;
import com.example.euser.R;
import com.example.euser.UserCostomerSupportActivity;
import com.example.euser.View_Holder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private static final int RESULT_LOAD_IMAGE = 1;
    private TextView profileName, profileNumber, profileRefCode, Co;
    private RecyclerView RecyclerViewAddressP;
    private RecyclerView.LayoutManager layoutManagerAddressP;
    private CircleImageView profile_Image;
    private TextView addAddress;
    private ImageView changeDP;
    private Animator currentAnimator;
    private int shortAnimationDuration;
    private boolean Coin_isexpended = false, Ref_isexpended = false, Add_isexpended = false, CP_isexpended = false, About_isexpended = false, CS_isexpended = false;
    private String AddressNo;
    private FirebaseRecyclerOptions<SearchCategoryModal> options;
    private FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder> adapter;
    private DatabaseReference UserRefAddNoR;
    private DatabaseReference UserAddressref;
    private String ImageShow;
    private ImageView Im_Con;

    private RelativeLayout Coin_P, Ref_P, Add_P, CP_P, About_P, CS_P, Log_P;
    private LinearLayout Coin_E_L, Ref_E_L, Add_E_L, CP_E_L, About_E_L, CS_E_L, Log_E_L;
    private ImageView Coin_I, Ref_I, Add_I, CP_I, About_I, CS_I, Log_I;

    private Dialog ImageSeeDialog;
    Uri ImageUri;
    private String myUri = "";
    StorageTask uploadTask;
    StorageReference profilePictureRef;
    private String checker = "";
    private Dialog LoadingBar;
    private Button Yes_Con, No_Con;
    private ScrollView SC;
    private ImageView expandedImageView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        Paper.init(getActivity());

        ImageView A = getActivity().findViewById(R.id.Noti_C);

        A.setImageResource(R.drawable.ic_baseline_notifications_24);

        ImageView S = getActivity().findViewById(R.id.See_C);

        S.setImageResource(R.drawable.ic_baseline_add_shopping_cart_24);

        TextView Fot = getActivity().findViewById(R.id.dot);

        Fot.setVisibility(View.VISIBLE);

        Coin_P = root.findViewById(R.id.coin_press);
        Ref_P = root.findViewById(R.id.ref_press);
        Add_P = root.findViewById(R.id.add_press);
        CP_P = root.findViewById(R.id.cp_press);
        About_P = root.findViewById(R.id.about_press);
        CS_P = root.findViewById(R.id.cs_press);
        Log_P = root.findViewById(R.id.log_press);

        Coin_E_L = root.findViewById(R.id.expanLayCoin);
        Ref_E_L = root.findViewById(R.id.expanLayRef);
        Add_E_L = root.findViewById(R.id.expanLayAdd);
        CP_E_L = root.findViewById(R.id.expanLayCP);
        About_E_L = root.findViewById(R.id.expanLayAbout);
        CS_E_L = root.findViewById(R.id.expanLayCS);
        Log_E_L = root.findViewById(R.id.expanLayLog);

        Coin_I = root.findViewById(R.id.image_coin);
        Ref_I = root.findViewById(R.id.image_ref);
        Add_I = root.findViewById(R.id.image_add);
        CP_I = root.findViewById(R.id.image_cp);
        About_I = root.findViewById(R.id.image_about);
        CS_I = root.findViewById(R.id.image_cs);
        Log_I = root.findViewById(R.id.image_log);

        profileName = root.findViewById(R.id.profile_name);
        profileNumber = root.findViewById(R.id.profile_number);
        profile_Image = root.findViewById(R.id.user_profile_image_profile);
        profileRefCode = root.findViewById(R.id.refeCode);

        profilePictureRef = FirebaseStorage.getInstance().getReference().child("Profile Picture");

        LoadingBar = new Dialog(getActivity());
        LoadingBar.setContentView(R.layout.loading_dialog);
        LoadingBar.setCancelable(false);

        SC = root.findViewById(R.id.countainer_pr);

        addAddress = root.findViewById(R.id.updateProfile);
        changeDP = root.findViewById(R.id.changePhoto);
        Co = root.findViewById(R.id.yooy);

        expandedImageView = root.findViewById(R.id.expanded_imageproo);

//        Window window = ProfileActivity.this.getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//        window.setStatusBarColor(android.graphics.Color.parseColor("#093145"));

        Coin_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Coin_isexpended == true){

                    Coin_E_L.setVisibility(View.GONE);
                    Coin_I.setImageResource(R.drawable.for_arrow);
                    Coin_isexpended = false;

                } else {

                    Coin_E_L.setVisibility(View.VISIBLE);
                    Coin_I.setImageResource(R.drawable.bot_arrow);
                    Coin_isexpended = true;

                }
            }
        });

        Ref_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Ref_isexpended == true){

                    Ref_E_L.setVisibility(View.GONE);
                    Ref_I.setImageResource(R.drawable.for_arrow);
                    Ref_isexpended = false;

                } else {

                    Ref_E_L.setVisibility(View.VISIBLE);
                    Ref_I.setImageResource(R.drawable.bot_arrow);
                    Ref_isexpended = true;

                }
            }
        });

        Add_P.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Add_isexpended == true){

                    Add_E_L.setVisibility(View.GONE);
                    Add_I.setImageResource(R.drawable.for_arrow);
                    Add_isexpended = false;

                } else {

                    Add_E_L.setVisibility(View.VISIBLE);
                    Add_I.setImageResource(R.drawable.bot_arrow);
                    Add_isexpended = true;

                }
            }
        });

        CP_P.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), ChangePasswardUserActivity.class);
            startActivity(i);
        });

        CS_P.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), UserCostomerSupportActivity.class);
            startActivity(i);
        });

        About_P.setOnClickListener(v -> {
            Intent i = new Intent(getActivity(), AboutActivity.class);
            startActivity(i);
        });

        Log_P.setOnClickListener(v -> {
            Paper.book().destroy();
            Intent i = new Intent(getActivity(), LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });

        RecyclerViewAddressP = root.findViewById(R.id.addressSee);
        RecyclerViewAddressP.setItemViewCacheSize(20);
        RecyclerViewAddressP.setDrawingCacheEnabled(true);
        RecyclerViewAddressP.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_AUTO);
        layoutManagerAddressP = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        RecyclerViewAddressP.setLayoutManager(layoutManagerAddressP);

        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        ImageSeeDialog = new Dialog(getActivity());
        ImageSeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ImageSeeDialog.setCancelable(false);
        ImageSeeDialog.setContentView(R.layout.image_conferm_dialog);

        Yes_Con = ImageSeeDialog.findViewById(R.id.con_Yes_POPO);
        No_Con = ImageSeeDialog.findViewById(R.id.con_No_POPO);
        Im_Con = ImageSeeDialog.findViewById(R.id.con_Image_POPO);

        onStar();

        UpdateProfile();

        profile_Image.setOnClickListener(v -> zoomImageFromThumb(profile_Image, ImageShow));

        changeDP.setOnClickListener(v -> {

            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
                return;
            }

            try {

                Intent k = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(k, RESULT_LOAD_IMAGE);

            } catch (Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        addAddress.setOnClickListener(v -> AddAddre());

        return root;

    }

    private void uploadImage(Uri imageUri) {

        try {

            LoadingBar.show();

            if (imageUri != null) {
                final StorageReference fileRef = profilePictureRef.child(Paper.book().read(Prevalant.UserIdA) + ".jpg");
                uploadTask = fileRef.putFile(imageUri);

                uploadTask.continueWithTask(new Continuation() {
                    @Override
                    public Object then(@NonNull Task task) throws Exception {

                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return fileRef.getDownloadUrl();
                    }
                }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {

                    if (task.isSuccessful()) {

                        try {
                            Uri doewnloadUri = task.getResult();
                            myUri = doewnloadUri.toString();

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("User");

                            HashMap<String, Object> map = new HashMap<>();

                            map.put("Image", myUri);
                            ref.child(Paper.book().read(Prevalant.UserIdA)).updateChildren(map);

                            LoadingBar.dismiss();

                        } catch (Exception b) {
                            Toast.makeText(getActivity(), b.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        LoadingBar.dismiss();
                        Toast.makeText(getActivity(), "Error...Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void UpdateProfile() {

        DatabaseReference User = FirebaseDatabase.getInstance().getReference().child("User").child(Paper.book().read(Prevalant.UserIdA));

        User.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    profileName.setText(dataSnapshot.child("Name").getValue().toString());
                    profileNumber.setText(dataSnapshot.child("Number").getValue().toString());
                    profileRefCode.setText(dataSnapshot.child("RCode").getValue().toString());
                    Co.setText("You Have " + dataSnapshot.child("Reward").getValue().toString() + " ");
                    if (dataSnapshot.child("Image").exists()) {
                        ImageShow = dataSnapshot.child("Image").getValue().toString();
                        Picasso.get().load(dataSnapshot.child("Image").getValue().toString()).centerCrop().fit().into(profile_Image);
                    }
                    CheckAddNo();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void CheckAddNo() {

        DatabaseReference UserRefAddNo = FirebaseDatabase.getInstance().getReference().child("UAddressI")
                .child(Paper.book().read(Prevalant.UserIdA));

        UserRefAddNo.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    ArrayList<SearchCategoryModal> hh = new ArrayList<>();

                    for (DataSnapshot ss : dataSnapshot.getChildren()) {
                        hh.add(ss.getValue(SearchCategoryModal.class));
                    }

                    AddressNo = String.valueOf(hh.size());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void onStar() {

        UserAddressref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        options =
                new FirebaseRecyclerOptions.Builder<SearchCategoryModal>()
                        .setQuery(UserAddressref, SearchCategoryModal.class)
                        .build();

        adapter =
                new FirebaseRecyclerAdapter<SearchCategoryModal, CategoryViewHolder>(options) {
                    @SuppressLint("SetTextI18n")
                    @Override
                    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int i, @NonNull final SearchCategoryModal modal) {

                        holder.CatName.setText(modal.getName());
                        holder.CatNumber.setText(modal.getNumber());
                        holder.CatAddress.setText(modal.getAddress() + ", " + modal.getCity() + ", " + modal.getState());
                        holder.CatPin.setText(modal.getPin());

                        holder.CatEditButton.setOnClickListener(v -> {
                            Intent il = new Intent(getActivity(), EditAddressActivity.class);
                            il.putExtra("PID", modal.getPID());
                            startActivity(il);
                        });

                        holder.CatRemoveButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                UserAddressref.child(modal.getPID()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        Toast.makeText(getActivity(), "Remove Successfully", Toast.LENGTH_SHORT).show();

                                        Intent i = new Intent(getActivity(), HomeActivity.class);
                                        i.putExtra("eeee", "PA");
                                        LoadingBar.dismiss();
                                        startActivity(i);

                                    }
                                });
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

        RecyclerViewAddressP.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {

            ImageUri = data.getData();

            ImageSeeDialog.show();
            Im_Con.setImageURI(ImageUri);

            Yes_Con.setOnClickListener(v -> {

                LoadingBar.show();
                ImageSeeDialog.dismiss();

                uploadImage(ImageUri);

            });

            No_Con.setOnClickListener(v -> ImageSeeDialog.dismiss());

//            Uri I = data.getData();
//
//            String[] fPC = { MediaStore.Images.Media.DATA };
//
//            Cursor c = getContentResolver().query(I, fPC, null, null, null);
//
//            c.moveToFirst();
//
//            int cI = c.getColumnIndex(fPC[0]);
//            String pP = c.getString(cI);
//            c.close();
//
//            Im_Con.setImageBitmap(BitmapFactory.decodeFile(pP));
//
//            Yes_Con.setOnClickListener(v -> {
//
//                LoadingBar.show();
//                ImageSeeDialog.dismiss();
//
//                uploadImage(ImageUri);
//
//            });
//
//            No_Con.setOnClickListener(v -> ImageSeeDialog.dismiss());

//            if(data.getClipData() != null){
//
//                Toast.makeText(ProfileActivity.this, "Please Select a single Image", Toast.LENGTH_SHORT).show();
//
//            } else if (data.getData() != null){
//
//                ImageUri = data.getData();
//
//                ImageSeeDialog.show();
//                Im_Con.setImageURI(ImageUri);
//
//                Yes_Con.setOnClickListener(v -> {
//
//                    LoadingBar.show();
//                    ImageSeeDialog.dismiss();
//
//                    uploadImage(ImageUri);
//
//                });
//
//                No_Con.setOnClickListener(v -> ImageSeeDialog.dismiss());
//
//            }
        } else {
            Toast.makeText(getActivity(), "Error try Again....", Toast.LENGTH_SHORT).show();
        }

    }


    private void AddAddre() {

        DatabaseReference Userref = FirebaseDatabase.getInstance().getReference().child("UAddressI").child(Paper.book().read(Prevalant.UserIdA));

        Userref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {

                    ArrayList<SearchCategoryModal> lolp = new ArrayList<>();

                    for (DataSnapshot gg : dataSnapshot.getChildren()) {
                        lolp.add(gg.getValue(SearchCategoryModal.class));
                    }

                    int ooooo = lolp.size();

                    if (ooooo == 3) {
                        Toast.makeText(getActivity(), "You Can Only Add 3 Addresses", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent i = new Intent(getActivity(), EditAddressActivity.class);
                        i.putExtra("PID", "AS");
                        startActivity(i);
                    }

                } else {
                    Intent i = new Intent(getActivity(), EditAddressActivity.class);
                    i.putExtra("PID", "AS");
                    startActivity(i);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void zoomImageFromThumb(final View thumbView, String imageResId) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (currentAnimator != null) {
            currentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.
//        expandedImageView.setImageResource(imageResId);
        Picasso.get().load(imageResId).into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);

        SC.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
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

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(view -> {
            if (currentAnimator != null) {
                currentAnimator.cancel();
            }

            // Animate the four positioning/sizing properties in parallel,
            // back to their original values.
            AnimatorSet set1 = new AnimatorSet();
            set1.play(ObjectAnimator
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
            set1.setDuration(shortAnimationDuration);
            set1.setInterpolator(new DecelerateInterpolator());
            set1.addListener(new AnimatorListenerAdapter() {
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
            set1.start();
            currentAnimator = set1;
        });
    }

}
