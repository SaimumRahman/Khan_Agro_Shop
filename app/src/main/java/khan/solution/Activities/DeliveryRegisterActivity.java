package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.DeliveryRegisterActivityBinding;

public class DeliveryRegisterActivity extends AppCompatActivity {

    private DeliveryRegisterActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=DeliveryRegisterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}