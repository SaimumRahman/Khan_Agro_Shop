package khan.solution;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import khan.solution.Activities.AdminLoginActivity;
import khan.solution.databinding.SplashActivityBinding;

public class SplashActivity extends AppCompatActivity {

    private SplashActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=SplashActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new Handler().postDelayed(new Runnable() {

        @Override

            public void run() {

                Intent i = new Intent(SplashActivity.this, AdminLoginActivity.class);

                startActivity(i);

                // close this activity

                finish();

            }

        }, 5*1000); // wait for 5 seconds

    }
}