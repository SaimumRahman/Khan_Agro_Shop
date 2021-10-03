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

import khan.solution.Activities.LoginActivity;
import khan.solution.Adapter.AdapterAdminHome;
import khan.solution.Model.DishPost;
import khan.solution.SplashActivity;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentLogoutBinding;

public class CustomerLogout extends Fragment {

    private FragmentLogoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentLogoutBinding.inflate(getLayoutInflater());
        getActivity().setTitle("LogOut");

     //  final FirebaseAuth auth=FirebaseAuth.getInstance();

        binding.logoutbtn.setOnClickListener(v ->{



        });

        return binding.getRoot();
    }

}


