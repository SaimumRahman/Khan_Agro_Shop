package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import khan.solution.databinding.PhoneRegisterActivityBinding;

public class PhoneRegisterActivity extends AppCompatActivity {

    private PhoneRegisterActivityBinding binding;

    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private String verificationId;

    private static final String TAG="MAIN_TAG";

    private FirebaseAuth auth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=PhoneRegisterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Customer");

        binding.countryCodePicker.setVisibility(View.VISIBLE);
        binding.phoneNumberEdt.setVisibility(View.VISIBLE);
        binding.submitBtn.setVisibility(View.VISIBLE);

        binding.verifyCodeEdt.setVisibility(View.INVISIBLE);
        binding.verifyBtn.setVisibility(View.INVISIBLE);
        binding.resendCodeTv.setVisibility(View.INVISIBLE);
        auth=FirebaseAuth.getInstance();

        dialog=new ProgressDialog(PhoneRegisterActivity.this);
        dialog.setTitle("Please waiting.....");
        dialog.setCanceledOnTouchOutside(false);

        callbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInnWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                dialog.dismiss();
                Toast.makeText(PhoneRegisterActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken tokens) {
                super.onCodeSent(s, forceResendingToken);
                verificationId=s;
                forceResendingToken=tokens;
                dialog.dismiss();

                binding.countryCodePicker.setVisibility(View.INVISIBLE);
                binding.phoneNumberEdt.setVisibility(View.INVISIBLE);
                binding.submitBtn.setVisibility(View.INVISIBLE);

                binding.verifyCodeEdt.setVisibility(View.VISIBLE);
                binding.verifyBtn.setVisibility(View.VISIBLE);
                binding.resendCodeTv.setVisibility(View.VISIBLE);

            }
        };

        binding.submitBtn.setOnClickListener(v ->{
            String code=binding.countryCodePicker.getSelectedCountryCodeWithPlus().trim();
            String number=(code+binding.phoneNumberEdt.getText().toString());


            if (!TextUtils.isEmpty(number) && number.length()>=10){

                  startPhoneNumberVerification(number);

            }
            else {
                Toast.makeText(PhoneRegisterActivity.this,"FILL UP THE FORM",Toast.LENGTH_SHORT).show();
            }

        });

        binding.verifyBtn.setOnClickListener(v ->{

          String vCode=binding.verifyCodeEdt.getText().toString();

            if (!TextUtils.isEmpty(vCode)){

                verifyPhoneWithCode(verificationId,vCode);

            }
            else {
                Toast.makeText(PhoneRegisterActivity.this,"FILL UP THE FORM",Toast.LENGTH_SHORT).show();
            }

        });

        binding.resendCodeTv.setOnClickListener(v ->{
            String code=binding.countryCodePicker.getSelectedCountryCodeWithPlus().trim();
            String number=(code+binding.phoneNumberEdt.getText().toString());

            if (!TextUtils.isEmpty(number) && number.length()>=10){

                resendVerificationCode(number,forceResendingToken);

            }
            else {
                Toast.makeText(PhoneRegisterActivity.this,"FILL UP THE FORM",Toast.LENGTH_SHORT).show();
            }

        });


    }

    private void startPhoneNumberVerification(String number) {

        dialog.setMessage("Verifying Phone Number");
        dialog.show();

        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(PhoneRegisterActivity.this)
                .setCallbacks(callbacks)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void resendVerificationCode(String number,PhoneAuthProvider.ForceResendingToken token) {
        dialog.setMessage("Resending Code");
        dialog.show();

        PhoneAuthOptions options= PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(number)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(PhoneRegisterActivity.this)
                .setCallbacks(callbacks)
                .setForceResendingToken(token)
                .build();

        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    private void verifyPhoneWithCode(String verificationId, String code) {

        dialog.setMessage("Verifying Code");
        dialog.show();

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInnWithPhoneAuthCredential(credential);

    }

    private void signInnWithPhoneAuthCredential(PhoneAuthCredential credential) {

        dialog.setMessage("Logging In");
        dialog.show();

        auth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                dialog.dismiss();
                String phoneNumber=auth.getCurrentUser().getPhoneNumber();
                String uid=auth.getCurrentUser().getUid();

                HashMap<String,Object>user_Phone_Hash=new HashMap<>();
                user_Phone_Hash.put("user_details",phoneNumber);
                user_Phone_Hash.put("user_id",uid);

                databaseReference.child(uid).updateChildren(user_Phone_Hash).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                      if (task.isSuccessful()){
                          Toast.makeText(PhoneRegisterActivity.this,"Registered",Toast.LENGTH_SHORT).show();
                      }
                      else {
                          Toast.makeText(PhoneRegisterActivity.this,"Not Registered",Toast.LENGTH_SHORT).show();
                      }
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PhoneRegisterActivity.this,"Not Registered",Toast.LENGTH_SHORT).show();
            }
        });

    }
}