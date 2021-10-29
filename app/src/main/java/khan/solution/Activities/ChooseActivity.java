package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;
import khan.solution.Model.Customer;
import khan.solution.Model.Prevelent;
import khan.solution.databinding.ActivityChooseBinding;

public class ChooseActivity extends AppCompatActivity {

    private ActivityChooseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.adminbtn.setOnClickListener(v -> {

            startActivity(new Intent(ChooseActivity.this, AdminLoginActivity.class));
            finish();

        });

        binding.startbtn.setOnClickListener(v ->{
            startActivity(new Intent(ChooseActivity.this, RegisterActivity.class));
            //finish();
        });

    }


}