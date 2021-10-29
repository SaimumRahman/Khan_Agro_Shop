package khan.solution.Activities;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Patterns;
import android.widget.CompoundButton;
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
import khan.solution.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Customer");
        auth=FirebaseAuth.getInstance();

        binding.registerTv.setOnClickListener(v ->{
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();
        });

        binding.loginbtns.setOnClickListener(v ->{

            if (!TextUtils.isEmpty(binding.userAuthEdt.getText().toString())
            ){
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            Customer customer=snapshot1.getValue(Customer.class);
                            if (binding.userAuthEdt.getText().toString().equals(customer.getUser_details())){

                                startActivity(new Intent(LoginActivity.this, CustomerNavigationActivity.class));
                                finish();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
            else {
                Toast.makeText(LoginActivity.this,"Please Enter Details Correctly",Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        
        FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
        
        if (user!=null){
            
            startActivity(new Intent(LoginActivity.this,CustomerNavigationActivity.class));
            finish();
            
        }
        else{
            Toast.makeText(this, "Please Login or Register", Toast.LENGTH_SHORT).show();
        }
        
    }
}