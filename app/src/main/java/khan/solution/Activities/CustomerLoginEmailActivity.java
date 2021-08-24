package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.ChefRegisterActivityBinding;
import khan.solution.databinding.CustomerLoginEmailActivityBinding;

public class CustomerLoginEmailActivity extends AppCompatActivity {

    private CustomerLoginEmailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CustomerLoginEmailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}