package khan.solution.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

import khan.solution.Activities.AdminLoginActivity;
import khan.solution.SplashActivity;
import khan.solution.databinding.FragmentLogoutBinding;

public class AdminLogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding=FragmentLogoutBinding.inflate(getLayoutInflater());
        getActivity().setTitle("LogOut");

        final FirebaseAuth auth=FirebaseAuth.getInstance();

        binding.logoutbtn.setOnClickListener(v ->{

            auth.signOut();

            startActivity(new Intent(getContext(), AdminLoginActivity.class));
            getActivity().finish();

        });

        return binding.getRoot();
    }

}



