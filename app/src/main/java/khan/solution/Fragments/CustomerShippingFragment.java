package khan.solution.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import khan.solution.Activities.LoginActivity;
import khan.solution.Adapter.AdapterAdminHome;
import khan.solution.Adapter.AdapterDetailsOrder;
import khan.solution.Model.DishPost;
import khan.solution.Model.Prevelent;
import khan.solution.Model.Products;
import khan.solution.SplashActivity;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentLogoutBinding;
import khan.solution.databinding.FragmentShippingCustomerBinding;

public class CustomerShippingFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private FragmentShippingCustomerBinding binding;
    private List<Products> productsArrayList;
    private AdapterDetailsOrder adapterDetailsOrder;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentShippingCustomerBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Chicken");

        auth=FirebaseAuth.getInstance();
        user= auth.getCurrentUser().getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Shipped").child(user);

        binding.customershippingrecycler.setHasFixedSize(true);
        binding.customershippingrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        productsArrayList = new ArrayList<>();
        binding.customershippingswipe.setOnRefreshListener(this);


        binding.customershippingswipe.post(new Runnable() {
            @Override
            public void run() {
                binding.customershippingswipe.setRefreshing(false);
                customermenu();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {

        binding.customershippingswipe.setRefreshing(false);
        productsArrayList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        Products products = snapshot1.getValue(Products.class);
                        productsArrayList.add(products);


                    }
                    adapterDetailsOrder = new AdapterDetailsOrder(getContext(), productsArrayList);
                    binding.customershippingrecycler.setAdapter(adapterDetailsOrder);

                    binding.customershippingswipe.setRefreshing(false);

                } else {
                    Toast.makeText(getContext(), "No Data Exists", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.customershippingswipe.setRefreshing(false);
            }
        });

    }
}