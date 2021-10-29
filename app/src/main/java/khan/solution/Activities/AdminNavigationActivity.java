package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import khan.solution.Fragments.AdminChickenFragment;
import khan.solution.Fragments.AdminLogoutFragment;
import khan.solution.Fragments.AdminMuttonFragment;
import khan.solution.Fragments.AdminOrderFragment;
import khan.solution.Fragments.AdminPostProduct;
import khan.solution.Fragments.CustomerChickenFragment;
import khan.solution.Fragments.LogoutFragment;
import khan.solution.Fragments.OrderShipmentFragment;
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

        Fragment fr=new AdminChickenFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, fr).commit();

        binding.log.setOnClickListener(v ->{
            Fragment frs=new LogoutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, frs).commit();
        });

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

            case R.id.shippingorder:
                fragment=new OrderShipmentFragment();
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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater=getMenuInflater();
//        inflater.inflate(R.menu.logout_menu,menu);
//        return super.onCreateOptionsMenu(menu);
//
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//
//        switch (item.getItemId()){
//            case R.id.logout_menu:
//
//                Fragment fr=new LogoutFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_admin, fr).commit();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}