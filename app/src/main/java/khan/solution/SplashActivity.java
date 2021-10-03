package khan.solution;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import khan.solution.Activities.AdminLoginActivity;
import khan.solution.Activities.AdminNavigationActivity;
import khan.solution.Activities.LoginActivity;
import khan.solution.Activities.RegisterActivity;
import khan.solution.Model.Customer;
import khan.solution.databinding.SplashActivityBinding;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityBinding binding;
    private FirebaseAuth auth,mAuth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SplashActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();


            binding.adminTv.setOnClickListener(v ->{
                startActivity(new Intent(SplashActivity.this, AdminLoginActivity.class));
                finish();
            });





        new Handler().postDelayed(new Runnable() {

        @Override

            public void run() {


              startActivity(new Intent(SplashActivity.this,RegisterActivity.class));
              finish();
        }


        }, 5*1000); // wait for 5 seconds

    }




}