package com.example.josga.ui.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.josga.GameMenu;
import com.example.josga.MainLoginActivity;
import com.example.josga.R;
import com.example.josga.databinding.FragmentHomeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends AppCompatActivity {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    android.widget.ListView listView;
    Button button;

    String nombre = "";
    String RoomName = "";

    FirebaseDatabase database;
    DatabaseReference RoomRef;
    DatabaseReference RoomsRef;

    List<String> RoomsList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        nombre = preferences.getString("PlayerName", "");
        RoomName = nombre;

        listView = findViewById(R.id.listView);
        button = findViewById(R.id.buttonCrearSala);

        RoomsList = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Cracion de la sala y agregarse uno mismo como jugador
                button.setText("Crando Sala");
                button.setEnabled(false);
                RoomName = nombre;
                RoomRef = database.getReference("sala/" + RoomName + "/player1");
                addRoomEventListener();
                RoomRef.setValue(nombre);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Unirse a la sala y unirse como jugador 2
                RoomName = RoomsList.get(position);
                RoomRef = database.getReference("sala/" + RoomName + "/player2");
                addRoomEventListener();
                RoomRef.setValue(nombre);
            }
        });
        //Mostrar si la sala esta disponible
        addRoomEventListener();
    }

    /*public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
    }*/

        //Logica de la cración de los componentes
    /*@Override
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
    }*/

    private void addRoomEventListener(){
        RoomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                button.setText("Crear Sala");
                button.setEnabled(true);
                Intent intent = new Intent(getApplicationContext(), MainLoginActivity.class);
                intent.putExtra("Nombre de la sala", RoomName);
                startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError dataerror) {
                //ERROR
                button.setText("Crear Sala");
                button.setEnabled(true);
                Toast.makeText(HomeFragment.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addRoomsEventListener(){
        RoomsRef = database.getReference("Salasas");
        RoomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //Mostrar lista de las salas
                RoomsList.clear();
                Iterable<DataSnapshot> rooms = datasnapshot.getChildren();
                for (DataSnapshot snapshot : rooms){
                    RoomsList.add(snapshot.getKey());

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(HomeFragment.this,
                            android.R.layout.simple_list_item_1, RoomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError dataerror) {
                //ERROR- NADA
            }
        });
    }
    /*@Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }*/
}