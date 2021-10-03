package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import khan.solution.Fragments.AdminChickenFragment;
import khan.solution.Fragments.AdminMuttonFragment;
import khan.solution.Fragments.AdminOrderFragment;
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

            case R.id.chicken_admin:
            fragment=new AdminChickenFragment();
            break;

            case R.id.mutton_admin:
            fragment=new AdminMuttonFragment();
            break;

            case R.id.order_admin:
            fragment=new AdminOrderFragment();
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