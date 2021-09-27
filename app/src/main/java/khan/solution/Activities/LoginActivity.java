package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import khan.solution.Model.Customer;
import khan.solution.databinding.LoginActivityBinding;

public class LoginActivity extends AppCompatActivity {

    private LoginActivityBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        reference=database.getReference("Customer");

        currentUserId=auth.getCurrentUser().getEmail();

        binding.loginbtns.setOnClickListener(v ->{

            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            Customer customer=snapshot1.getValue(Customer.class);
                            String dbemail=customer.getUser_details();

                            if (dbemail.equals(dbemail)){

                                Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();

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