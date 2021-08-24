package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.DeliveryLoginPhoneActivityBinding;
import khan.solution.databinding.DeliveryRegisterActivityBinding;

public class DeliveryLoginPhoneActivity extends AppCompatActivity {

    private DeliveryLoginPhoneActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DeliveryLoginPhoneActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}