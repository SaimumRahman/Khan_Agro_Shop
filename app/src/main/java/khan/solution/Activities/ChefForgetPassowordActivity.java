package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.ChefForgetPassowordActivityBinding;

public class ChefForgetPassowordActivity extends AppCompatActivity {

    private ChefForgetPassowordActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChefForgetPassowordActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}