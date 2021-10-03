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
import khan.solution.Model.DishPost;
import khan.solution.databinding.FragmentAdminHomeBinding;

public class AdminChickenFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private FragmentAdminHomeBinding binding;
    private List<DishPost>dishPostArrayList;
    private AdapterAdminHome adapterAdminHome;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentAdminHomeBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Chicken");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Dish_Post").child("Chicken");

        binding.adminHomeRecycler.setHasFixedSize(true);
        binding.adminHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        dishPostArrayList=new ArrayList<>();
        binding.adminhomeswipe.setOnRefreshListener(this);


        binding.adminhomeswipe.post(new Runnable() {
            @Override
            public void run() {
                binding.adminhomeswipe.setRefreshing(false);
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

        binding.adminhomeswipe.setRefreshing(false);
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
                    adapterAdminHome = new AdapterAdminHome(getContext(), dishPostArrayList);
                    binding.adminHomeRecycler.setAdapter(adapterAdminHome);

                    binding.adminhomeswipe.setRefreshing(false);

                }
                else {
                    Toast.makeText(getContext(),"No Data Exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.adminhomeswipe.setRefreshing(false);
            }
        });

    }
}
