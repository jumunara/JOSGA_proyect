package com.example.josga.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.josga.R;
import com.example.josga.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;

    }

    public View onViewCreated(LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        //Inflación de Layout en este fragmento, para tener acciones de botones,etc, o llamadas a pantallas
        //Logica de inicializacion
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

        //Logica de la cración de los componentes
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonCrearSala = view.findViewById(R.id.buttonCrearSala);

        buttonCrearSala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Llamado del primer fragmento de navegacion
                Navigation.findNavController(v).navigate(R.id.Sala);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}