package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.CustomerLoginEmailActivityBinding;
import khan.solution.databinding.CustomerLoginPhoneActivityBinding;

public class CustomerLoginPhoneActivity extends AppCompatActivity {

    private CustomerLoginPhoneActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CustomerLoginPhoneActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}