package khan.solution.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Objects;

import khan.solution.databinding.CustomerLoginPhoneActivityBinding;
import khan.solution.databinding.CustomerRegisterActivityBinding;

public class CustomerRegisterActivity extends AppCompatActivity {

    private CustomerRegisterActivityBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private final String role= "customer";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= CustomerRegisterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        binding.signUpBtnCSR.setOnClickListener(v ->{
            if (isValidations()) {
                auth.createUserWithEmailAndPassword(binding.emailEdtCSR.getText().toString(),binding.PasswordEdtCSR.getText().toString())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()){

                                String userId= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid();
                                reference=FirebaseDatabase.getInstance().getReference("User").child(userId);
                                final HashMap<String,Object> hashMap=new HashMap<>();
                                hashMap.put("Customer",role);
                                reference.updateChildren(hashMap).addOnCompleteListener(task1 -> {

                                    if (task1.isSuccessful()){

                                        final HashMap<String,Object> hash=new HashMap<>();
                                        hash.put("First_Name",binding.firstNameEdtCSR.getText().toString());
                                        hash.put("Last_Name",binding.lastNameEdtCSR.getText().toString());
                                        hash.put("Email",binding.emailEdtCSR.getText().toString());
                                        try {
                                            hash.put("Password",encryptPass());
                                        } catch (GeneralSecurityException e) {
                                            e.printStackTrace();
                                        }
                                        hash.put("Phone",binding.countryCodePickerCSR.getSelectedCountryCodeWithPlus().toString()+binding.phoneEdtCSR.getText().toString());
                                        hash.put("Address",binding.addressEdtCSR.getText().toString());
                                        hash.put("Area_Code",binding.AreaCodeEdtCSR.getText().toString());
                                        hash.put("Pin_Code",binding.pinCodeEdtCSR.getText().toString());
                                        hash.put("State",binding.stateEdtCSR.getText().toString());
                                        hash.put("City",binding.cityEdtCSR.getText().toString());

                                        database.getReference("Customer").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .updateChildren(hash).addOnCompleteListener(task2 -> {
                                            if (task2.isSuccessful()){
                                                Objects.requireNonNull(auth.getCurrentUser()).sendEmailVerification().addOnCompleteListener(task3 -> {

                                                    if (task3.isSuccessful()){
                                                        AlertDialog.Builder builder=new AlertDialog.Builder(CustomerRegisterActivity.this);
                                                        builder.setMessage("Registration is Successfull. Please Verify your Email");
                                                        builder.setCancelable(false);
                                                        builder.setPositiveButton("OK", (dialogInterface, i) -> {
                                                            dialogInterface.dismiss();

                                                            String phoneNumber=binding.countryCodePickerCSR.getSelectedCountryCodeWithPlus()+binding.phoneEdtCSR.getText().toString();
                                                            Intent intent=new Intent(CustomerRegisterActivity.this,VerifyPhoneCus.class);
                                                            intent.putExtra("phoneNumber",phoneNumber);
                                                            startActivity(intent);
                                                            finish();

                                                        });
                                                        AlertDialog alertDialog=builder.create();
                                                        alertDialog.show();
                                                    }
                                                    else {
                                                        Toast.makeText(CustomerRegisterActivity.this,"WRONG TASK3",Toast.LENGTH_SHORT).show();
                                                    }


                                                });
                                            }
                                            else {
                                                Toast.makeText(CustomerRegisterActivity.this,"WRONG TASK2",Toast.LENGTH_SHORT).show();
                                            }

                                        });
                                    }
                                    else {Toast.makeText(CustomerRegisterActivity.this,"WRONG TASK1",Toast.LENGTH_SHORT).show();}

                                });

                            }
                            else {Toast.makeText(CustomerRegisterActivity.this,"WRONG TASK",Toast.LENGTH_SHORT).show();}
                        });
            }
            else {Toast.makeText(CustomerRegisterActivity.this,"WRONG",Toast.LENGTH_SHORT).show();}
        });

        binding.signUpWithMailCSR.setOnClickListener(v ->{
            startActivity(new Intent  (CustomerRegisterActivity.this,CustomerLoginEmailActivity.class));
            finish();
        });

        binding.signUpWithPhoneCSR.setOnClickListener(v->{
            startActivity(new Intent(CustomerRegisterActivity.this,CustomerLoginPhoneActivity.class));
            finish();
        });



    }

    private boolean isValidations(){
        boolean isValid=true;
        if (TextUtils.isEmpty(binding.firstNameEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter FIrst Name",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.lastNameEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Last Name",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.emailEdtCSR.getText().toString()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailEdtCSR.getText().toString()).matches()){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Email ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.PasswordEdtCSR.getText().toString())
                && binding.PasswordEdtCSR.getText().toString().length()>8){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Password ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.confirmtPasswordEdtCSR.getText().toString()) &&
                !binding.confirmtPasswordEdtCSR.equals(binding.PasswordEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Password Doesn't Match",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.addressEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Address ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.stateEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter State ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.cityEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter City ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.AreaCodeEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Area Code",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.pinCodeEdtCSR.getText().toString())){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Pin COde ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }
        if (TextUtils.isEmpty(binding.phoneEdtCSR.getText().toString())
                && binding.phoneEdtCSR.getText().toString().length()>=11){
            Toast.makeText(CustomerRegisterActivity.this,"Please Enter Pin COde ",Toast.LENGTH_SHORT).show();
            isValid=false;
        }

        return isValid;
    }

    private String encryptPass() throws GeneralSecurityException {

        String encryptedMsg = AESCrypt.encrypt(binding.PasswordEdtCSR.getText().toString(), "KhanAgroShop");
        return encryptedMsg;
    }
}