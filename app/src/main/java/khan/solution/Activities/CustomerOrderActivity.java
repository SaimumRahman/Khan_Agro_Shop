package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import io.paperdb.Paper;
import khan.solution.Model.Cart;
import khan.solution.Model.Prevelent;
import khan.solution.MyFirebaseInstanceIDService;
import khan.solution.databinding.ActivityCustomerOrderBinding;

public class CustomerOrderActivity extends AppCompatActivity {

    private ActivityCustomerOrderBinding binding;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference,databaseReference2;
    private String user,Order_ID;
    private DatabaseReference databaseReference3;

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
        databaseReference3=database.getReference("Orders");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                int total = 0;
                
             for(DataSnapshot snapshot1:snapshot.getChildren()){

                     Cart cart=snapshot1.getValue(Cart.class);

                     int prices=Integer.parseInt(cart.getPrice());
                      total=total+prices;
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
            startActivity(new Intent(CustomerOrderActivity.this,CustomerNavigationActivity.class));
            finish();
        });

        binding.razorpaybtn.setOnClickListener(v ->{
            if (!TextUtils.isEmpty(binding.NameEdtOrder.getText()) &&
                    !TextUtils.isEmpty(binding.emailEdtOrder.getText()) &&
                    Patterns.EMAIL_ADDRESS.matcher(binding.emailEdtOrder.getText()).matches() &&
                    !TextUtils.isEmpty(binding.phoneEdtOrder.getText()) &&
                    !TextUtils.isEmpty(binding.edtAddressOrder.getText())
            ) {
                Intent intent = new Intent(CustomerOrderActivity.this, RazorPayActivity.class);
                intent.putExtra("Order_ID", Order_ID);
                intent.putExtra("Name", binding.NameEdtOrder.getText().toString());
                intent.putExtra("Email", binding.emailEdtOrder.getText().toString());
                intent.putExtra("Phone", binding.phoneEdtOrder.getText().toString());
                intent.putExtra("Address", binding.edtAddressOrder.getText().toString());
                intent.putExtra("Total_Bill", binding.totalpricecustomer2.getText().toString());
                startActivity(intent);
           }
        });


    }

    private void UpdateOrder() {

        if (!TextUtils.isEmpty(binding.NameEdtOrder.getText()) &&
            !TextUtils.isEmpty(binding.emailEdtOrder.getText()) &&
                Patterns.EMAIL_ADDRESS.matcher(binding.emailEdtOrder.getText()).matches() &&
             !TextUtils.isEmpty(binding.phoneEdtOrder.getText()) &&
                !TextUtils.isEmpty(binding.edtAddressOrder.getText())
        ){

            final FirebaseDatabase da=FirebaseDatabase.getInstance();
            final DatabaseReference ref=da.getReference("Cart").child(user);
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        Cart car=snapshot1.getValue(Cart.class);

                        final HashMap<String,Object> hashMaps=new HashMap<>();
                        hashMaps.put("Customer_ID",user);
                        hashMaps.put("Order_ID",Order_ID);
                        hashMaps.put("Name",binding.NameEdtOrder.getText().toString());
                        hashMaps.put("Email",binding.emailEdtOrder.getText().toString());
                        hashMaps.put("Phone",binding.phoneEdtOrder.getText().toString());
                        hashMaps.put("Address",binding.edtAddressOrder.getText().toString());
                        hashMaps.put("Total_Bill",binding.totalpricecustomer2.getText().toString());
                        databaseReference2.child(Order_ID).updateChildren(hashMaps)
                                .addOnCompleteListener(task -> {

                                    final DatabaseReference databaseReference22=da.getReference("Products").child(user);

                                    final HashMap<String,Object> neh=new HashMap<>();
                                    neh.put("cart_id",car.getCart_id().toString());
                                    neh.put("price",car.getPrice().toString());
                                    neh.put("details",car.getDetails().toString());
                                    neh.put("imageuri",car.getImage_Uri().toString());
                                    neh.put("quantity",car.getQuantity().toString());

                                    databaseReference22.child(car.getCart_id()).updateChildren(neh).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            emptyCart();
                                        }
                                    });

                                });

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
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