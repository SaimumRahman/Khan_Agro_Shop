package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.databinding.CustomerFoodPanelBottomNavigationActivityBinding;

public class CustomerFoodPanel_BottomNavigation_Activity extends AppCompatActivity {

    private CustomerFoodPanelBottomNavigationActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=CustomerFoodPanelBottomNavigationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



    }
}