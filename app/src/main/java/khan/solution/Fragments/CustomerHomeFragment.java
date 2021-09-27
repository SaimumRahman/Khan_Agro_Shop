package khan.solution.Fragments;

import android.annotation.SuppressLint;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import khan.solution.Adapter.AdapterAdminHome;
import khan.solution.Adapter.AdapterCustomerHome;
import khan.solution.Model.DishPost;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentCustomerHomeBinding;

public class CustomerHomeFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{


    private FragmentCustomerHomeBinding binding;
    private List<DishPost> dishPostArrayList;
    private AdapterCustomerHome customerHomeAdapter;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentCustomerHomeBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Home");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Dish_Post");

        binding.customerHomeRecycler.setHasFixedSize(true);
        binding.customerHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dishPostArrayList=new ArrayList<>();
        binding.customerhomeswipe.setOnRefreshListener(this);


        binding.customerhomeswipe.post(new Runnable() {
            @Override
            public void run() {
                binding.customerhomeswipe.setRefreshing(false);
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

        binding.customerhomeswipe.setRefreshing(false);
        dishPostArrayList.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        DishPost post=snapshot1.getValue(DishPost.class);
                        dishPostArrayList.add(post);
                    }
                    customerHomeAdapter = new AdapterCustomerHome(getContext(), dishPostArrayList);
                    binding.customerHomeRecycler.setAdapter(customerHomeAdapter);

                    binding.customerhomeswipe.setRefreshing(false);

                }
                else {
                    Toast.makeText(getContext(),"No Data Exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.customerhomeswipe.setRefreshing(false);
            }
        });

    }
}
