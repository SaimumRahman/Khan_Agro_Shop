package khan.solution.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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

import khan.solution.Activities.CustomerOrderActivity;
import khan.solution.Adapter.AdapterCustomerCart;
import khan.solution.Adapter.AdapterCustomerHome;
import khan.solution.Model.Cart;
import khan.solution.Model.DishPost;
import khan.solution.databinding.FragmentCustomerCartBinding;
import khan.solution.databinding.FragmentCustomerHomeBinding;

public class CustomerCartFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private FragmentCustomerCartBinding binding;
    private List<Cart> cartList;
    private AdapterCustomerCart adapterCustomerCart;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private FirebaseAuth auth;
    private String user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding=FragmentCustomerCartBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Home");

        firebaseDatabase=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        user=auth.getCurrentUser().getUid();
        databaseReference=firebaseDatabase.getReference("Cart").child(user);

        binding.customercartrecycler.setHasFixedSize(true);
        binding.customercartrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        cartList=new ArrayList<>();
        binding.customerCartswipe.setOnRefreshListener(this);

        binding.customerOrderss.setOnClickListener(v ->{
            startActivity(new Intent(getContext(),CustomerOrderActivity.class));
        });

        binding.customerCartswipe.post(new Runnable() {
            @Override
            public void run() {
                binding.customerCartswipe.setRefreshing(false);
                customermenu();
            }
        });

        return binding.getRoot();
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//
//        binding.customerOrderss.setOnClickListener(v ->{
//            startActivity(new Intent(context, CustomerOrderActivity.class));
//        });
//
//    }

    @Nullable
    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {

        binding.customerCartswipe.setRefreshing(false);
        cartList.clear();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot snapshot1:snapshot.getChildren()){

                            Cart cart=snapshot1.getValue(Cart.class);
                            cartList.add(cart);

//                        Cart cart=snapshot1.getValue(Cart.class);
//                        cartList.add(cart);
                    }
                    adapterCustomerCart = new AdapterCustomerCart(getContext(), cartList);
                    binding.customercartrecycler.setAdapter(adapterCustomerCart);
                    adapterCustomerCart.notifyDataSetChanged();
                    binding.customerCartswipe.setRefreshing(false);

                }
                else {
                  //  Toast.makeText(co,"No Data Exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.customerCartswipe.setRefreshing(false);
            }
        });

    }
}
