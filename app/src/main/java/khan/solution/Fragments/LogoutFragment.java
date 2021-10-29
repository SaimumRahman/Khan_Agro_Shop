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

import io.paperdb.Paper;
import khan.solution.Activities.ChooseActivity;
import khan.solution.Activities.LoginActivity;
import khan.solution.Model.Prevelent;
import khan.solution.databinding.FragmentAdminHomeBinding;
import khan.solution.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(getLayoutInflater());
        getActivity().setTitle("Chicken");

        final FirebaseAuth a= FirebaseAuth.getInstance();

        binding.logoutbtn.setOnClickListener(v ->{

            a.signOut();

            startActivity(new Intent(getContext(), ChooseActivity.class));
            getActivity().finish();

        });

        return binding.getRoot();

    }
}
