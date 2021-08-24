package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import khan.solution.databinding.ChefLoginPhoneActivityBinding;

public class ChefLoginPhoneActivity extends AppCompatActivity {

    private ChefLoginPhoneActivityBinding binding;
    private FirebaseAuth auth;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChefLoginPhoneActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        binding.sendOTPCLP.setOnClickListener(v ->{
            number=binding.countryCodePickerCLP.getSelectedCountryCodeWithPlus()+binding.phoneEdtCLP.getText().toString();
            Intent intent=new Intent(ChefLoginPhoneActivity.this,ChefSendOTPActivity.class);
            intent.putExtra("number",number);
            startActivity(intent);
            finish();
        });

        binding.emailSignInCLP.setOnClickListener(v->{
            startActivity(new Intent(ChefLoginPhoneActivity.this,ChefLoginEmailActivity.class));
            finish();
        });
    }
}