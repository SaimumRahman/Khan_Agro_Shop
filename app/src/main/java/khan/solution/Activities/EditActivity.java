package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import khan.solution.Model.DishPost;
import khan.solution.databinding.ActivityEditBinding;

public class EditActivity extends AppCompatActivity {

    private ActivityEditBinding binding;
    private String image,price,details,quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        image= getIntent().getStringExtra("image");
        price= getIntent().getStringExtra("price");
        details= getIntent().getStringExtra("details");
        quantity= getIntent().getStringExtra("quantity");

        Glide.with(this).load(image).into(binding.imageButton);
        binding.detailsEdt.setText(details);
        binding.priceEdt.setText(price);
        binding.quantityEdt.setText(quantity);
        binding.quantityEdt.setText(quantity);


    }
}