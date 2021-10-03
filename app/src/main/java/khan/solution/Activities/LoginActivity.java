package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

import khan.solution.Model.Customer;
import khan.solution.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Customer");

        binding.loginbtns.setOnClickListener(v ->{

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            Customer customer=snapshot1.getValue(Customer.class);
                            String email=customer.getUser_details();

                            if (email.equals(binding.userAuthEdt.getText().toString())){

                                startActivity(new Intent(LoginActivity.this,CustomerNavigationActivity.class));
                                finish();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, "Please Register", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });



    }
}