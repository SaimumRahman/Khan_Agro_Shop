package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import khan.solution.R;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 1000;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkingUser();
            }
        },3000);

    }

    private void checkingUser(){

        if (auth.getCurrentUser()!=null){

            if (auth.getCurrentUser().isEmailVerified()){

                reference=database.getReference("User").child(auth.getUid()+"/Role");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       if (snapshot.exists()){
                           String role=snapshot.getValue(String.class);


                           if (role.equals("chef")){
                               startActivity(new Intent(MainActivity.this,ChefFoodPanel_BottomNavigation_Activity.class));
                               finish();
                           }
                           if (role.equals("customer")) {
                               startActivity(new Intent(MainActivity.this, ChefFoodPanel_BottomNavigation_Activity.class));
                               finish();

                           }



                        }else  {
                            startActivity(new Intent(MainActivity.this,SignOptionActivity.class));
                            finish();
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)  {
                        Toast.makeText(MainActivity.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
            else {
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Email not Verified, please verify again");
                builder.setCancelable(false);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        Intent mainIntent = new Intent(MainActivity.this,SignOptionActivity.class);
                        MainActivity.this.startActivity(mainIntent);
                        MainActivity.this.finish();

                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
                auth.signOut();
          //      Toast.makeText(MainActivity.this,"", Toast.LENGTH_SHORT).show();
            }

        }else {

            Intent mainIntent = new Intent(MainActivity.this,SignOptionActivity.class);
            MainActivity.this.startActivity(mainIntent);
            MainActivity.this.finish();
        }

    }
}