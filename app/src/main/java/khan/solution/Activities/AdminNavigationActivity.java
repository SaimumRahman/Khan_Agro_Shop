package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import khan.solution.Fragments.AdminHomeFragment;
import khan.solution.Fragments.AdminPostProduct;
import khan.solution.R;
import khan.solution.databinding.AdminNavigationActivityBinding;

public class AdminNavigationActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private AdminNavigationActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=AdminNavigationActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.adminBottomNavigation.setOnNavigationItemSelectedListener(this);



    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment=null;

        switch (item.getItemId()){
            case R.id.home_admin:
            fragment=new AdminHomeFragment();
            break;
            case R.id.post_dish_admin:
                fragment=new AdminPostProduct(AdminNavigationActivity.this);
                break;

        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {

        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, fragment).commit();
            return true;
        }
        return false;
    }
}