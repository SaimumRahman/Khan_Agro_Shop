package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

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
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Paper.init(ChooseActivity.this);

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Customer");

        binding.btnadmin.setOnClickListener(v ->{

            startActivity(new Intent(ChooseActivity.this,AdminLoginActivity.class));
            finish();

        });

        binding.btnCustomer.setOnClickListener(v ->{

            String key= Paper.book().read(Prevelent.userdetailskey);
            if (key!=null){
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()){
                            for (DataSnapshot snapshot1:snapshot.getChildren()){
                                Customer customer=snapshot1.getValue(Customer.class);
                                String email=customer.getUser_details();

                                if (email.equals(key)){

                                    startActivity(new Intent(ChooseActivity.this,CustomerNavigationActivity.class));
                                    finish();

                                }
                                else {
                                    Toast.makeText(ChooseActivity.this, "Please Register", Toast.LENGTH_SHORT).show();

                                }
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

            startActivity(new Intent(ChooseActivity.this,LoginActivity.class));
            finish();

        });

    }
}