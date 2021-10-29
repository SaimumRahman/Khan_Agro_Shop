package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;

import khan.solution.Model.Cart;
import khan.solution.R;
import khan.solution.databinding.ActivityRazorPayBinding;

public class RazorPayActivity extends AppCompatActivity implements PaymentResultListener {

    private static final String TAG = RazorPayActivity.class.getSimpleName();
    private ActivityRazorPayBinding binding;
    private FirebaseAuth mAuth;
    private String user;
    private int AMount;
    private String Order_ID,Name,Email,Phone,Address,Total_Bill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityRazorPayBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Order_ID=getIntent().getStringExtra("Order_ID");
        Name=getIntent().getStringExtra("Name");
        Email=getIntent().getStringExtra("Email");
        Phone=getIntent().getStringExtra("Phone");
        Address=getIntent().getStringExtra("Address");
        Total_Bill=getIntent().getStringExtra("Total_Bill");

        AMount=Integer.parseInt(Total_Bill);

        mAuth=FirebaseAuth.getInstance();
        user=mAuth.getCurrentUser().getUid();

        Checkout.preload(getApplicationContext());


            startPayment();



    }

    public void startPayment() {

        Checkout checkout= new Checkout();

        checkout.setKeyID("rzp_live_m0UGshkgsH7igw");

        checkout.setImage(R.drawable.logo);
        checkout.setFullScreenDisable(true);

        /**
         * Reference to current activity
         */
       final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "Freshly");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("user_id", user);//from response of step 3.
            options.put("theme.color", "#F44811");
            options.put("currency", "INR");
            options.put("amount", AMount*100);//pass amount in currency subunits
            options.put("email", Email);
            options.put("prefill.contact",Phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        UpdateOrder();
        Toast.makeText(RazorPayActivity.this, "Payment is Successful and Order is being processed to admin", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {

    }

    private void UpdateOrder() {

            final FirebaseDatabase da=FirebaseDatabase.getInstance();
            final DatabaseReference databaseReference2=da.getReference("Orders").child(user);
            final DatabaseReference ref=da.getReference("Cart").child(user);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        Cart car=snapshot1.getValue(Cart.class);

                        final HashMap<String,Object> hashMaps=new HashMap<>();
                        hashMaps.put("Customer_ID",user);
                        hashMaps.put("Order_ID",Order_ID);
                        hashMaps.put("Name",Name);
                        hashMaps.put("Email",Email);
                        hashMaps.put("Phone",Phone);
                        hashMaps.put("Address",Address);
                        hashMaps.put("Total_Bill",Total_Bill);
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

    private void emptyCart() {

        final FirebaseDatabase database=FirebaseDatabase.getInstance();
        final DatabaseReference reference=database.getReference("Cart").child(user);

        reference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RazorPayActivity.this,
                        "Please Pay the Bill", Toast.LENGTH_SHORT).show();
            }
        });

    }
}