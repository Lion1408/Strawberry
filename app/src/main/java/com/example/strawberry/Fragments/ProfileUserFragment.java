package com.example.strawberry.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.strawberry.R;
import com.example.strawberry.databinding.FragmentProfileUserBinding;

import org.w3c.dom.Text;

public class ProfileUserFragment extends Fragment {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile_user, container, false);
        TextView test = view.findViewById(R.id.test);
        test.setOnClickListener(v -> {
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
        });
        return view;
    }

}