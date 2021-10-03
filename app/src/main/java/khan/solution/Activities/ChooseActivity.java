package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import khan.solution.databinding.ActivityChooseBinding;

public class ChooseActivity extends AppCompatActivity {

    private ActivityChooseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityChooseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnadmin.setOnClickListener(v ->{

            startActivity(new Intent(ChooseActivity.this,AdminLoginActivity.class));
            finish();

        });

        binding.btnCustomer.setOnClickListener(v ->{

            startActivity(new Intent(ChooseActivity.this,RegisterActivity.class));
            finish();

        });

    }
}