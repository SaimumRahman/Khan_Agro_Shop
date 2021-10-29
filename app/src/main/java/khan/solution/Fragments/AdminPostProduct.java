package khan.solution.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

import khan.solution.Model.DishPost;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentAdminPostProductBinding;

public class AdminPostProduct extends Fragment {

    private FragmentAdminPostProductBinding binding;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String postId,saveCurrentDateMs,saveCurrentTimeMs;
    private Uri imageuri,mCropimageuri;
    private  Context context;
    private StorageReference storageReference,reference;


    public AdminPostProduct(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentAdminPostProductBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Post Product");
        context=getContext().getApplicationContext();

        firebaseDatabase=FirebaseDatabase.getInstance();

        storageReference=FirebaseStorage.getInstance().getReference("Dish_Image");

        firebaseAuth=FirebaseAuth.getInstance();
        postId= UUID.randomUUID().toString();


        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDateMs = currentDate.format(callForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTimeMs = currentTime.format(callForDate.getTime());

        binding.imageButton.setOnClickListener(v  ->{

            if (binding.chickenCheck.isChecked()){
                databaseReference=firebaseDatabase.getReference("Dish_Post").child("Chicken");
                onSelectImageClick(v);
            }
            else if (binding.muttonCheck.isChecked())
            {
                databaseReference=firebaseDatabase.getReference("Dish_Post").child("Mutton");
                onSelectImageClick(v);
            }
            else {
                Toast.makeText(getContext(), "Please check one box", Toast.LENGTH_SHORT).show();
            }

        });
        binding.adminSubmitBtn.setOnClickListener(v ->{
            upload();
        });

        return binding.getRoot();

    }

    private void upload(){

        if (TextUtils.isEmpty(binding.priceEdt.getText().toString())
            && TextUtils.isEmpty(binding.detailsEdt.getText().toString())
                && TextUtils.isEmpty(binding.quantityEdt.getText().toString())

        )
        {
            Toast.makeText(context,"Please fillup form",Toast.LENGTH_SHORT).show();
        }

        else {
            uploadImage();

            final HashMap<String, Object> postHash = new HashMap<>();
            postHash.put("Post_Id", postId);
            postHash.put("Dish_Price", binding.priceEdt.getText().toString());
            postHash.put("Dish_Description", binding.detailsEdt.getText().toString());
            postHash.put("Dish_Quantity", binding.quantityEdt.getText().toString());


            postHash.put("Dish_Date", saveCurrentDateMs);
            postHash.put("Dish_Time", saveCurrentTimeMs);

            databaseReference.child(postId).updateChildren(postHash).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Dish Added Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Dish NOT Added Successfully", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void onSelectImageClick(View v) {

      CropImage.startPickImageActivity(getContext(),this);
    }

    @Override
    @SuppressLint("NewApi")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            imageuri = CropImage.getPickImageResultUri(getContext(), data);

            if (CropImage.isReadExternalStoragePermissionsRequired(getContext(), imageuri)) {
                mCropimageuri = imageuri;
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

            } else {
                Toast.makeText(context, "ELSW Cropping activity enteredffan", Toast.LENGTH_SHORT).show();
                startCropImageActivity(imageuri);
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                binding.imageButton.setImageURI(result.getUri());
                Toast.makeText(context, "Cropped Successfully", Toast.LENGTH_SHORT).show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(context, "Cropping failed" + result.getError(), Toast.LENGTH_SHORT).show();
            }
        }


        super.onActivityResult(requestCode,resultCode,data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (mCropimageuri != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCropImageActivity(mCropimageuri);
        } else {
            Toast.makeText(context, "cancelling,required permission not granted", Toast.LENGTH_SHORT).show();
        }
    }

    private void startCropImageActivity(Uri imageuri) {
        //Toast.makeText(context, "Cropping activity enteredffan", Toast.LENGTH_SHORT).show();
        CropImage.activity(imageuri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(context,this);

    }

    private void uploadImage(){

        if(imageuri!=null){

            reference=storageReference.child(postId);
            reference.putFile(imageuri).addOnSuccessListener(taskSnapshot -> {
            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    final HashMap<String,Object>uris=new HashMap<>();
                    uris.put("Dish_Image_Uri",uri.toString());

                    databaseReference.child(postId).updateChildren(uris).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Dish Added Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Dish NOT Added Successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
            });

        }

    }
}
