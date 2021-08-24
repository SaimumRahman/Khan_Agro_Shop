package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

import khan.solution.databinding.OtpSendingLcusActivityBinding;

public class OtpSendingLCus extends AppCompatActivity {

    private OtpSendingLcusActivityBinding binding;
    private String phoneNumber,verificationId;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=OtpSendingLcusActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        phoneNumber=getIntent().getStringExtra("number").trim();

        auth= FirebaseAuth.getInstance();

        binding.resendOtpBtnCusPV.setVisibility(View.INVISIBLE);
        binding.txtCusPV.setVisibility(View.INVISIBLE);

        sendingOtp(phoneNumber);

        binding.verifyBtnCusPV.setOnClickListener(v ->{

            String code=binding.otpEdtPhoneCusPV.getText().toString().trim();
            binding.resendOtpBtnCusPV.setVisibility(View.INVISIBLE);

            if (code.isEmpty() && code.length()<6){
                Toast.makeText(OtpSendingLCus.this,"Error Code",Toast.LENGTH_SHORT).show();
                binding.otpEdtPhoneCusPV.requestFocus();
            }

            verifyCode(code);

        });

        new CountDownTimer(60000,1000){

            @SuppressLint("SetTextI18n")
            @Override
            public void onTick(long l) {
                binding.txtCusPV.setVisibility(View.VISIBLE);
                binding.txtCusPV.setText("Resend COde within "+ l/1000+"Seconds");
            }

            @Override
            public void onFinish() {
                binding.resendOtpBtnCusPV.setVisibility(View.VISIBLE);
                binding.txtCusPV.setVisibility(View.INVISIBLE);
            }
        }.start();

        binding.resendOtpBtnCusPV.setOnClickListener(v ->{
            binding.resendOtpBtnCusPV.setVisibility(View.INVISIBLE);
            resendOTP(phoneNumber);

            new CountDownTimer(60000,1000){

                @SuppressLint("SetTextI18n")
                @Override
                public void onTick(long l) {
                    binding.txtCusPV.setVisibility(View.VISIBLE);
                    binding.txtCusPV.setText("Resend COde within "+ l/1000+"Seconds");
                }

                @Override
                public void onFinish() {
                    binding.resendOtpBtnCusPV.setVisibility(View.VISIBLE);
                    binding.txtCusPV.setVisibility(View.INVISIBLE);
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
                            binding.txtCusPV.setText(code);
                            verifyCode(code);
                        }

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {
                        Toast.makeText(OtpSendingLCus.this,"ERROR Verifying", Toast.LENGTH_SHORT).show();
                    }
                })
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyCode(String codes){

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,codes);
        signInWithPhone(credential);

    }

    private void signInWithPhone(PhoneAuthCredential credential) {

        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(OtpSendingLCus.this, CustomerFoodPanel_BottomNavigation_Activity.class));
                    finish();
                }
                else {
                    Toast.makeText(OtpSendingLCus.this,"ERROR LINKING CREDENTIALS", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

}