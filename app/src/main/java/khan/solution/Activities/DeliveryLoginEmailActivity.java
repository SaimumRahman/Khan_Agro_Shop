package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.CustomerRegisterActivityBinding;
import khan.solution.databinding.DeliveryLoginEmailActivityBinding;

public class DeliveryLoginEmailActivity extends AppCompatActivity {

    private DeliveryLoginEmailActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DeliveryLoginEmailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}