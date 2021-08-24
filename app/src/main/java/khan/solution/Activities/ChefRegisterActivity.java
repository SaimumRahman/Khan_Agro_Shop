package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.HashMap;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import khan.solution.databinding.ChefLoginPhoneActivityBinding;
import khan.solution.databinding.ChefRegisterActivityBinding;

public class ChefRegisterActivity extends AppCompatActivity {

    private ChefRegisterActivityBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private final String role= "chef";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ChefRegisterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.signUpBtnCR.setOnClickListener(v ->{
            if (isValidations()) {
                auth.createUserWithEmailAndPassword(binding.emailEdtCR.getText().toString(),binding.PasswordEdtCR.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){

                                String userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                reference=FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("Role",role);
                                reference.updateChildren(hashMap).addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()){

                                        final HashMap<String,Object> hash=new HashMap<>();
                                        hash.put("First_Name",binding.firstNameEdtCR.getText().toString());
                                        hash.put("Last_Name",binding.lastNameEdtCR.getText().toString());
                                        hash.put("Email",binding.emailEdtCR.getText().toString());
                                        try {
                                            hash.put("Password",encryptPass());
                                        } catch (GeneralSecurityException e) {
                                            e.printStackTrace();
                                        }
                                        hash.put("Phone",binding.countryCodePickerCR.getSelectedCountryCodeWithPlus().toString()+binding.phoneEdtCR.getText().toString());
                                        hash.put("Address",binding.addressEdtCR.getText().toString());
                                        hash.put("Area_Code",binding.AreaCodeEdtCR.getText().toString());
                                        hash.put("Pin_Code",binding.pinCodeEdtCR.getText().toString());
                                        hash.put("State",binding.stateEdtCR.getText().toString());
                                        hash.put("City",binding.cityEdtCR.getText().toString());

                                        database.getReference("Chef").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .updateChildren(hash).addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()){
                                                Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task3 -> {

                                                    if (task3.isSuccessful()){
                                                        AlertDialog.Builder builder=new AlertDialog.Builder(ChefRegisterActivity.this);
                                                        builder.setMessage("Registration is Successfull. Please Verify your Email");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                                            dialogInterface.dismiss();

                                                            String phoneNumber=binding.countryCodePickerCR.getSelectedCountryCodeWithPlus()+binding.phoneEdtCR.getText().toString();
                                                            Intent intent=new Intent(ChefRegisterActivity.this,ChefPhoneVerifyActivity.class);
                                                            intent.putExtra("phoneNumber",phoneNumber);
                                                            startActivity(intent);
                                                            finish();

                                                        });
                                                        AlertDialog alertDialog=builder.create();
                                                        alertDialog.show();
                                                    }
                                                    else {
                                                        Toast.makeText(ChefRegisterActivity.this,"WRONG TASK3",Toast.LENGTH_SHORT).show();
                                                    }


                                                });
                                            }
                                            else {
                                                Toast.makeText(ChefRegisterActivity.this,"WRONG TASK2",Toast.LENGTH_SHORT).show();
                                            }

                                        });
                                    }
                                    else {Toast.makeText(ChefRegisterActivity.this,"WRONG TASK1",Toast.LENGTH_SHORT).show();}

                                });

                            }
                            else {Toast.makeText(ChefRegisterActivity.this,"WRONG TASK",Toast.LENGTH_SHORT).show();}
                        });
            }
            else {Toast.makeText(ChefRegisterActivity.this,"WRONG",Toast.LENGTH_SHORT).show();}
        });

        binding.signUpWithMailCR.setOnClickListener(v ->{
            startActivity(new Intent  (ChefRegisterActivity.this,ChefLoginEmailActivity.class));
            finish();
        });

        binding.signUpWithPhoneCR.setOnClickListener(v->{
            startActivity(new Intent(ChefRegisterActivity.this,ChefLoginPhoneActivity.class));
            finish();
        });



    }

    private boolean isValidations(){
        boolean isValid=true;
        if (TextUtils.isEmpty(binding.firstNameEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter FIrst Name",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.lastNameEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.emailEdtCR.getText().toString()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailEdtCR.getText().toString()).matches()){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Email ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.PasswordEdtCR.getText().toString())
            && binding.PasswordEdtCR.getText().toString().length()>8){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Password ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.confirmtPasswordEdtCR.getText().toString()) &&
                !binding.confirmtPasswordEdtCR.equals(binding.PasswordEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Password Doesn't Match",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.addressEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Address ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.stateEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter State ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.cityEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter City ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.AreaCodeEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Area Code",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.pinCodeEdtCR.getText().toString())){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Pin COde ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.phoneEdtCR.getText().toString())
        && binding.phoneEdtCR.getText().toString().length()>=11){
            Toast.makeText(ChefRegisterActivity.this,"Please Enter Pin COde ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }

    return isValid;
    }

    private String encryptPass() throws GeneralSecurityException {

        String encryptedMsg = AESCrypt.encrypt(binding.PasswordEdtCR.getText().toString(), "KhanAgroShop");
        return encryptedMsg;
    }
}