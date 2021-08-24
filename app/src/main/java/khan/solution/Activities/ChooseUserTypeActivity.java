package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import khan.solution.R;
import khan.solution.databinding.ChooseUserTypeActivityBinding;

public class ChooseUserTypeActivity extends AppCompatActivity {

    private ChooseUserTypeActivityBinding binding;
    private String signUpType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChooseUserTypeActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        signUpType=getIntent().getExtras().getString("Home").trim();

        binding.chefConnectbtn.setOnClickListener(v ->{
            if (signUpType.equals("Email")){

                startActivity(new Intent(ChooseUserTypeActivity.this,ChefLoginEmailActivity.class));
                finish();

            }

            if (signUpType.equals("Phone")){

                startActivity(new Intent(ChooseUserTypeActivity.this,ChefLoginPhoneActivity.class));
                finish();

            }

            if (signUpType.equals("SignUp")){

                startActivity(new Intent(ChooseUserTypeActivity.this,ChefRegisterActivity.class));
                finish();

            }
        });

        binding.customerConnectbtn.setOnClickListener(v ->{
            if (signUpType.equals("Email")){

                startActivity(new Intent(ChooseUserTypeActivity.this,CustomerLoginEmailActivity.class));
                finish();

            }

            if (signUpType.equals("Phone")){

                startActivity(new Intent(ChooseUserTypeActivity.this,CustomerLoginPhoneActivity.class));
                finish();

            }

            if (signUpType.equals("SignUp")){

                startActivity(new Intent(ChooseUserTypeActivity.this,CustomerRegisterActivity.class));
                finish();

            }
        });

        binding.deliveryConnectbtn.setOnClickListener(v ->{
            if (signUpType.equals("Email")){

                startActivity(new Intent(ChooseUserTypeActivity.this,DeliveryLoginEmailActivity.class));
                finish();

            }

            if (signUpType.equals("Phone")){

                startActivity(new Intent(ChooseUserTypeActivity.this,DeliveryLoginPhoneActivity.class));
                finish();

            }

            if (signUpType.equals("SignUp")){

                startActivity(new Intent(ChooseUserTypeActivity.this,DeliveryRegisterActivity.class));
                finish();

            }
        });


    }
}