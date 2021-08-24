package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

import khan.solution.databinding.ChefPhoneVerifyActivityBinding;

public class ChefPhoneVerifyActivity extends AppCompatActivity {

    private ChefPhoneVerifyActivityBinding binding;
    private String phoneNumber,verificationId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ChefPhoneVerifyActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        phoneNumber=getIntent().getStringExtra("phoneNumber").trim();

        auth=FirebaseAuth.getInstance();

        binding.resendOtpBtnCPV.setVisibility(View.INVISIBLE);
        binding.txtCPV.setVisibility(View.INVISIBLE);

        sendingOtp(phoneNumber);

        binding.verifyBtnCPV.setOnClickListener(v ->{

            String code=binding.otpEdtPhoneCPV.getText().toString().trim();
            binding.resendOtpBtnCPV.setVisibility(View.INVISIBLE);

            if (code.isEmpty() && code.length()<6){
                Toast.makeText(ChefPhoneVerifyActivity.this,"Error Code",Toast.LENGTH_SHORT).show();
                binding.otpEdtPhoneCPV.requestFocus();
            }

            verifyCode(code);

        });

        new CountDownTimer(60000,1000){

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                binding.txtCPV.setVisibility(View.VISIBLE);
                binding.txtCPV.setText("Resend COde within "+ l/1000+"Seconds");
            }

            @Override
            public void onFinish() {
                binding.resendOtpBtnCPV.setVisibility(View.VISIBLE);
                binding.txtCPV.setVisibility(View.INVISIBLE);
            }
        }.start();

        binding.resendOtpBtnCPV.setOnClickListener(v ->{
            binding.resendOtpBtnCPV.setVisibility(View.INVISIBLE);
            resendOTP(phoneNumber);

            new CountDownTimer(60000,1000){

                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long l) {
                    binding.txtCPV.setVisibility(View.VISIBLE);
                    binding.txtCPV.setText("Resend COde within "+ l/1000+"Seconds");
                }

                @Override
                public void onFinish() {
                    binding.resendOtpBtnCPV.setVisibility(View.VISIBLE);
                    binding.txtCPV.setVisibility(View.INVISIBLE);
                }
            }.start();

        });


    }

    private void resendOTP(String phoneNumber) {

        sendingOtp(phoneNumber);

    }

    private void sendingOtp(String number) {
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onCodeSent(String s,
                                           @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {

                    verificationId=s;

                    }

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                        String code=phoneAuthCredential.getSmsCode();
                        if(code!=null){
                            binding.txtCPV.setText(code);
                            verifyCode(code);
                        }

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(ChefPhoneVerifyActivity.this,"ERROR Verifying", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyCode(String codes){

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,codes);
        linkCredentials(credential);

    }

    private void linkCredentials(PhoneAuthCredential credential) {

        auth.getCurrentUser().linkWithCredential(credential).addOnCompleteListener(ChefPhoneVerifyActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(ChefPhoneVerifyActivity.this, SignOptionActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(ChefPhoneVerifyActivity.this,"ERROR LINKING CREDENTIALS", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}