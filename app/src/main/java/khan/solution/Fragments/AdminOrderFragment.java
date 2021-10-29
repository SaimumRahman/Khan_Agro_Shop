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
import khan.solution.Adapter.AdapterPayBill;
import khan.solution.Model.DishPost;
import khan.solution.Model.PayBill;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentOrderAdminBinding;

public class AdminOrderFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private FragmentOrderAdminBinding binding;
    private List<PayBill> payBillListArray;
    private AdapterPayBill adapterPayBill;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding= FragmentOrderAdminBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Chicken");

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Orders");

        binding.adminorderrecycler.setHasFixedSize(true);
        binding.adminorderrecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        payBillListArray=new ArrayList<>();
        binding.adminorderswipe.setOnRefreshListener(this);


        binding.adminorderswipe.post(() -> {
            binding.adminorderswipe.setRefreshing(false);
            customermenu();
        });

        return binding.getRoot();
    }

    @Override
    public void onRefresh() {
        customermenu();
    }

    private void customermenu() {

        binding.adminorderswipe.setRefreshing(false);
        payBillListArray.clear();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot snapshot1:snapshot.getChildren()){
                        for (DataSnapshot snapshot2:snapshot1.getChildren()){
                            PayBill payBill=snapshot2.getValue(PayBill.class);
                            payBillListArray.add(payBill);
                        }
                    }
                    adapterPayBill = new AdapterPayBill(getContext(), payBillListArray);
                    binding.adminorderrecycler.setAdapter(adapterPayBill);

                    binding.adminorderswipe.setRefreshing(false);

                }
                else {
                  //  Toast.makeText(getActivity(),"No Data Exists",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.adminorderswipe.setRefreshing(false);
            }
        });

    }
}
