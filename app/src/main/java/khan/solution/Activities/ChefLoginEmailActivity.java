package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import khan.solution.databinding.ChefLoginEmailActivityBinding;

public class ChefLoginEmailActivity extends AppCompatActivity {

    private ChefLoginEmailActivityBinding binding;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChefLoginEmailActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();

        binding.loginBtnCL.setOnClickListener(v ->{
            if (isValid()){
                final ProgressDialog dialog=new ProgressDialog(ChefLoginEmailActivity.this);
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.setMessage("Signing in please wait ....");
                dialog.show();

                auth.signInWithEmailAndPassword(binding.emailCL.getText().toString(),binding.passwordCL.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){

                                dialog.dismiss();

                                if (auth.getCurrentUser().isEmailVerified()){
                                   dialog.dismiss();
                                    Toast.makeText(ChefLoginEmailActivity.this,"Congratulations! You are Logging in",Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(ChefLoginEmailActivity.this,ChefFoodPanel_BottomNavigation_Activity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(ChefLoginEmailActivity.this,"Email not Verified",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ChefLoginEmailActivity.this,"ERROR Authentications",Toast.LENGTH_SHORT).show();
                            }
                        });

            }
            else {
                Toast.makeText(ChefLoginEmailActivity.this,"Invalid Email or Password",Toast.LENGTH_SHORT).show();

            }
        });

        binding.createAccountCL.setOnClickListener(v ->{
            startActivity(new Intent(ChefLoginEmailActivity.this,ChefRegisterActivity.class));
            finish();
        });

        binding.forgetPasswordCL.setOnClickListener(v ->{

            startActivity(new Intent(ChefLoginEmailActivity.this,ChefForgetPassowordActivity.class));
            finish();

        });

        binding.phoneLoginBtnCL.setOnClickListener(v->{
            startActivity(new Intent(ChefLoginEmailActivity.this,ChefLoginPhoneActivity.class));
            finish();
        });

    }

    private boolean isValid(){

        boolean isValid=true;

        if (TextUtils.isEmpty(binding.emailCL.getText().toString()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailCL.getText().toString()).matches()){
            Toast.makeText(ChefLoginEmailActivity.this,"Please Enter Email ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.passwordCL.getText().toString())
                && binding.passwordCL.getText().toString().length()>8){
            Toast.makeText(ChefLoginEmailActivity.this,"Please Enter Password ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        return isValid;
}
}
