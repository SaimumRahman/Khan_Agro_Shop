package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import khan.solution.databinding.ChefRegisterActivityBinding;
import khan.solution.databinding.CustomerLoginEmailActivityBinding;

public class CustomerLoginEmailActivity extends AppCompatActivity {

    private CustomerLoginEmailActivityBinding binding;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CustomerLoginEmailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth= FirebaseAuth.getInstance();

        binding.loginBtnCusL.setOnClickListener(v ->{
            if (isValid()){
                final ProgressDialog dialog=new ProgressDialog(CustomerLoginEmailActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.setMessage("Signing in please wait ....");
                dialog.show();

                auth.signInWithEmailAndPassword(binding.emailCusL.getText().toString(),binding.passwordCusL.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){

                                dialog.dismiss();

                                if (auth.getCurrentUser().isEmailVerified()){
                                    dialog.dismiss();
                                    Toast.makeText(CustomerLoginEmailActivity.this,"Congratulations! You are Logging in",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(CustomerLoginEmailActivity.this,ChefFoodPanel_BottomNavigation_Activity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(CustomerLoginEmailActivity.this,"Email not Verified",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(CustomerLoginEmailActivity.this,"ERROR Authentications",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            else {
                Toast.makeText(CustomerLoginEmailActivity.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();

            }
        });

        binding.createAccountCusL.setOnClickListener(v ->{
            startActivity(new Intent(CustomerLoginEmailActivity.this,CustomerRegisterActivity.class));
            finish();
        });

        binding.forgetPasswordCusL.setOnClickListener(v ->{

            startActivity(new Intent(CustomerLoginEmailActivity.this,ChefForgetPassowordActivity.class));
            finish();

        });

        binding.phoneLoginBtnCusL.setOnClickListener(v->{
            startActivity(new Intent(CustomerLoginEmailActivity.this,CustomerLoginPhoneActivity.class));
            finish();
        });

    }

    private boolean isValid(){

        boolean isValid=true;

        if (TextUtils.isEmpty(binding.emailCusL.getText().toString()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailCusL.getText().toString()).matches()){
            Toast.makeText(CustomerLoginEmailActivity.this,"Please Enter Email ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.passwordCusL.getText().toString())
                && binding.passwordCusL.getText().toString().length()>8){
            Toast.makeText(CustomerLoginEmailActivity.this,"Please Enter Password ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        return isValid;
    }
}
