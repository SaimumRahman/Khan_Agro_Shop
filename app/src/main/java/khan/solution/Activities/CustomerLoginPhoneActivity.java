package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

import khan.solution.databinding.CustomerLoginEmailActivityBinding;
import khan.solution.databinding.CustomerLoginPhoneActivityBinding;

public class CustomerLoginPhoneActivity extends AppCompatActivity {

    private CustomerLoginPhoneActivityBinding binding;
    private FirebaseAuth auth;
    private String number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CustomerLoginPhoneActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        binding.sendOTPCSLP.setOnClickListener(v ->{
            number=binding.countryCodePickerCSLP.getSelectedCountryCodeWithPlus()+binding.phoneEdtCSLP.getText().toString();
            Intent intent=new Intent(CustomerLoginPhoneActivity.this,OtpSendingLCus.class);
            intent.putExtra("number",number);
            startActivity(intent);
            finish();
        });

        binding.emailSignInCSLP.setOnClickListener(v->{
            startActivity(new Intent(CustomerLoginPhoneActivity.this,CustomerRegisterActivity.class));
            finish();
        });

    }
}