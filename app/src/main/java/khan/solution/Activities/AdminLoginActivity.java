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

import khan.solution.Model.Admin;
import khan.solution.databinding.ActivityAdminLoginBinding;

public class AdminLoginActivity extends AppCompatActivity {

    private ActivityAdminLoginBinding binding;
    private DatabaseReference databaseReference;
    private String username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        userandpass();

        binding.adminLoginBtn.setOnClickListener(v ->{
            if(username.equals(binding.adminUserNameEdt.getText().toString())
                && password.equals(binding.adminpasswordEdt.getText().toString())
            ){
                startActivity(new Intent(AdminLoginActivity.this,AdminNavigationActivity.class));
                finish();
                Toast.makeText(getApplicationContext(),"Logged IN",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"INCORRECT",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void userandpass(){

        databaseReference=FirebaseDatabase.getInstance().getReference("Admin");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Admin admin=snapshot.getValue(Admin.class);
                username=admin.getUsername();
                password=admin.getPassword();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}