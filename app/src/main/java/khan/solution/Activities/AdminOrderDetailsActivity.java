package khan.solution.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import khan.solution.Adapter.AdapterDetailsOrder;
import khan.solution.Adapter.AdapterPayBill;
import khan.solution.Model.PayBill;
import khan.solution.Model.Prevelent;
import khan.solution.Model.Products;
import khan.solution.databinding.ActivityAdminOrderDetailsBinding;

public class AdminOrderDetailsActivity extends AppCompatActivity {

    private ActivityAdminOrderDetailsBinding binding;
    private List<Products> productsArrayList;
    private AdapterDetailsOrder adapterDetailsOrder;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAdminOrderDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user=getIntent().getStringExtra("user");
        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Products").child(user);

        binding.adminorderdetailsreycler.setHasFixedSize(true);
        binding.adminorderdetailsreycler.setLayoutManager(new LinearLayoutManager(this));
        productsArrayList=new ArrayList<>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()){
                    Products products1=snapshot1.getValue(Products.class);
                    productsArrayList.add(products1);
                }
                adapterDetailsOrder = new AdapterDetailsOrder(AdminOrderDetailsActivity.this, productsArrayList);
                binding.adminorderdetailsreycler.setAdapter(adapterDetailsOrder);
                adapterDetailsOrder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}