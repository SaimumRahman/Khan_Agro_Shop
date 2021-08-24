package khan.solution.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import khan.solution.databinding.SignOptionActivityBinding;

public class SignOptionActivity extends AppCompatActivity {

    private SignOptionActivityBinding signOptionActivityBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signOptionActivityBinding=SignOptionActivityBinding.inflate(getLayoutInflater());
        setContentView(signOptionActivityBinding.getRoot());

        signOptionActivityBinding.signInWIthEmailBtn.setOnClickListener(view -> {

            Intent signInEmailIntent = new Intent(SignOptionActivity.this,ChooseUserTypeActivity.class);
            signInEmailIntent.putExtra("Home", "Email");
            startActivity(signInEmailIntent);
            finish();

        });

        signOptionActivityBinding.signInWIthPhoneBtn.setOnClickListener(view -> {

            Intent signInWithPhoneIntent = new Intent(SignOptionActivity.this,ChooseUserTypeActivity.class);
            signInWithPhoneIntent.putExtra("Home", "Phone");
            startActivity(signInWithPhoneIntent);
            finish();

        });

        signOptionActivityBinding.signInUpeBtn.setOnClickListener(view -> {

            Intent signInUpIntent = new Intent(SignOptionActivity.this,ChooseUserTypeActivity.class);
            signInUpIntent.putExtra("Home", "SignUp");
            startActivity(signInUpIntent);
            finish();

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
    }
}