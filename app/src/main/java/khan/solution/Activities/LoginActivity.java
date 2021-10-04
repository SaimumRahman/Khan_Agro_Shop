package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = LoginActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Customer");

        Paper.init(LoginActivity.this);

        binding.loginbtns.setOnClickListener(v ->{

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()){
                        for (DataSnapshot snapshot1:snapshot.getChildren()){
                            Customer customer=snapshot1.getValue(Customer.class);
                            String email=customer.getUser_details();

                            if (email.equals(binding.userAuthEdt.getText().toString())){

                                if (binding.rememberCheck.isChecked()){
                                    Paper.book().write(Prevelent.userdetailskey,email);
                                    Paper.book().write(Prevelent.userid,customer.getUser_id());
                                }

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


        binding.registerTv.setOnClickListener(v ->{

            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            finish();

        });


    }

//    private void rememberme(){
//        binding.rememberCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//               if (compoundButton.isChecked()){
//                   SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//                   SharedPreferences.Editor editor=sharedPreferences.edit();
//                   editor.putString("remember","true");
//                   editor.apply();
//               }
//               else if (!compoundButton.isChecked()){
//                   SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//                   SharedPreferences.Editor editor=sharedPreferences.edit();
//                   editor.putString("remember","false");
//                   editor.apply();
//               }
//
//            }
//        });


    @Override
    protected void onStart() {
        super.onStart();
//        SharedPreferences sharedPreferences=getSharedPreferences("checkbox",MODE_PRIVATE);
//        String checked=sharedPreferences.getString("remember","");
//
//        if (checked.equals("true")){
//            startActivity(new Intent(LoginActivity.this,CustomerNavigationActivity.class));
//        }else if (checked.equals("false")){
//            Toast.makeText(LoginActivity.this, "Please login", Toast.LENGTH_SHORT).show();
//
//        }
    }
}