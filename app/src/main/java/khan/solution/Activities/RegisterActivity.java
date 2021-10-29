package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import khan.solution.R;
import khan.solution.SplashActivity;
import khan.solution.databinding.RegisterActivityBinding;

public class RegisterActivity extends AppCompatActivity {

    private RegisterActivityBinding binding;
    private static final int RC_SIGN_IN=100;
    private GoogleSignInClient googleSignInClient;
    private FirebaseAuth auth;
    private static final String TAG="GOOGLE_SIGN_IN_TAG";
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=RegisterActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();


        googleSignInClient = GoogleSignIn.getClient(RegisterActivity.this, gso);

        auth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Customer");

        binding.googleRegBtn.setOnClickListener(v ->{

            Intent signInIntent = googleSignInClient.getSignInIntent();
            startActivityForResult(signInIntent, RC_SIGN_IN);
            Toast.makeText(RegisterActivity.this,"CLICKED",Toast.LENGTH_SHORT).show();

        });

        binding.phoneRegBtn.setOnClickListener(v ->{
            startActivity(new Intent(RegisterActivity.this, PhoneRegisterActivity.class));
            finish();
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String account) {

        AuthCredential credential= GoogleAuthProvider.getCredential(account,null);
        auth.signInWithCredential(credential).addOnSuccessListener(authResult -> {

            FirebaseUser firebaseUser=auth.getCurrentUser();
            String uid=firebaseUser.getUid();
            String email=firebaseUser.getEmail();

            if (authResult.getAdditionalUserInfo().isNewUser()){

                HashMap<String,Object>user_has=new HashMap();
                user_has.put("user_id",uid);
                user_has.put("user_details",email);

                databaseReference.child(uid).updateChildren(user_has).addOnCompleteListener(task -> {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this,"Registered Successfully",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                        finish();
                    }
                    else {
                        Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_SHORT).show();
                    }
                });
                Toast.makeText(RegisterActivity.this,"ELOGGED IN",Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(RegisterActivity.this,"Email Already Exists Logging In",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
            }



        }).addOnFailureListener(e -> Toast.makeText(RegisterActivity.this,"Email Already Exists",Toast.LENGTH_SHORT).show());

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();

        if (user!=null){
            //Toast.makeText(RegisterActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(RegisterActivity.this,CustomerNavigationActivity.class));
        }
        else {
            Toast.makeText(RegisterActivity.this, "Please register", Toast.LENGTH_SHORT).show();
        }
    }
}