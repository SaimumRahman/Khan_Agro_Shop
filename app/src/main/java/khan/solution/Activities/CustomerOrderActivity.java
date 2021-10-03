package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.UUID;

import khan.solution.Model.Cart;
import khan.solution.MyFirebaseInstanceIDService;
import khan.solution.databinding.ActivityCustomerOrderBinding;

public class CustomerOrderActivity extends AppCompatActivity {

    private ActivityCustomerOrderBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,databaseReference2;
    private String user,Order_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCustomerOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser().getUid();

         Order_ID = UUID.randomUUID().toString();

        database=FirebaseDatabase.getInstance();

        databaseReference=database.getReference("Cart").child(user);
        databaseReference2=database.getReference("Orders").child(user);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             for(DataSnapshot snapshot1:snapshot.getChildren()){

                     Cart cart=snapshot1.getValue(Cart.class);

                     int prices=Integer.parseInt(cart.getPrice());
                     int total=prices+prices;
                     binding.totalpricecustomer2.setText(String.valueOf(total));


                 //Toast.makeText(CustomerOrderActivity.this, , Toast.LENGTH_SHORT).show();
             }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.paybillbtn.setOnClickListener(v ->{
            UpdateOrder();
        });


    }

    private void UpdateOrder() {

        if (!TextUtils.isEmpty(binding.NameEdtOrder.getText()) &&
            !TextUtils.isEmpty(binding.emailEdtOrder.getText()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailEdtOrder.getText()).matches() &&
             !TextUtils.isEmpty(binding.phoneEdtOrder.getText()) &&
                !TextUtils.isEmpty(binding.edtAddressOrder.getText())
        ){


            final HashMap<String,Object> hashMaps=new HashMap<>();
            hashMaps.put("Customer_ID",user);
            hashMaps.put("Order_ID",Order_ID);
            hashMaps.put("Total_Bill",binding.totalpricecustomer2.getText().toString());
            hashMaps.put("Name",binding.NameEdtOrder.getText().toString());
            hashMaps.put("Email",binding.emailEdtOrder.getText().toString());
            hashMaps.put("Phone",binding.phoneEdtOrder.getText().toString());
            hashMaps.put("Address",binding.edtAddressOrder.getText().toString());

            databaseReference2.child(Order_ID).updateChildren(hashMaps)
                    .addOnCompleteListener(task -> {

//                        MyFirebaseInstanceIDService myFirebaseInstanceIDService=new MyFirebaseInstanceIDService(
//                                binding.NameEdtOrder.getText().toString(),
//                                binding.emailEdtOrder.getText().toString(),
//                                binding.phoneEdtOrder.getText().toString(),
//                                binding.edtAddressOrder.getText().toString(),
//                                binding.totalpricecustomer2.getText().toString(),
//                                CustomerOrderActivity.this
//                        );

                        emptyCart();

                    });
        }
    }

    private void emptyCart() {

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("Cart").child(user);

        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CustomerOrderActivity.this,
                        "Please Pay the Bill", Toast.LENGTH_SHORT).show();
            }
        });

    }
}