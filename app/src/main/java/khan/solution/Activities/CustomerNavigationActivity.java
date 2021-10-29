package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import khan.solution.Fragments.CustomerCartFragment;
import khan.solution.Fragments.CustomerChickenFragment;
import khan.solution.Fragments.CustomerShippingFragment;
import khan.solution.Fragments.CustomerMuttonFragment;
import khan.solution.Fragments.LogoutFragment;
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

        Fragment fr=new CustomerChickenFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_customer, fr).commit();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment=null;

            switch (item.getItemId()){

                case R.id.chicken_customer:
                    fragment=new CustomerChickenFragment();
                    break;

                case R.id.mutton_customer:
                    fragment=new CustomerMuttonFragment();
                    break;

                case R.id.cart_customer:
                    fragment=new CustomerCartFragment();
                    break;

                case R.id.shipped_customer:
                    fragment=new CustomerShippingFragment();
                    break;
                case R.id.logoutcustomer:
                    fragment=new LogoutFragment();
                    break;

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