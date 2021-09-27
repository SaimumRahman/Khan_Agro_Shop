package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import khan.solution.Fragments.AdminHomeFragment;
import khan.solution.Fragments.AdminPostProduct;
import khan.solution.Fragments.CustomerCartFragment;
import khan.solution.Fragments.CustomerHomeFragment;
import khan.solution.R;
import khan.solution.databinding.ActivityCustomerNavigationBinding;

public class CustomerNavigationActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityCustomerNavigationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.customerBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;

            switch (item.getItemId()){
                case R.id.home_customer:
                    fragment=new CustomerHomeFragment();
                    break;
                case R.id.cart_customer:
                    fragment=new CustomerCartFragment();

            }
            return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer, fragment).commit();
            return true;
        }
        return false;
    }
}