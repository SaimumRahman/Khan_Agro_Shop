package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import khan.solution.R;
import khan.solution.databinding.ChefFoodPanelBottomNavigationActivityBinding;

public class ChefFoodPanel_BottomNavigation_Activity extends AppCompatActivity {

    private ChefFoodPanelBottomNavigationActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChefFoodPanelBottomNavigationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}