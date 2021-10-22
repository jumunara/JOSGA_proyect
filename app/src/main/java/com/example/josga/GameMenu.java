package com.example.josga;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.josga.databinding.ActivityGameMenuBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameMenu extends AppCompatActivity {

    private ActivityGameMenuBinding binding;

    android.widget.ListView listView;
    Button button;

    String nombre = "";
    String RoomName = "";

    FirebaseDatabase database;
    DatabaseReference RoomRef;
    DatabaseReference RoomsRef;

    List<String> RoomsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        nombre = preferences.getString("PlayerName", "");
        RoomName = nombre;

        listView =  findViewById(R.id.listView);
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
        });*/

        //Mostrar si la sala esta disponible
        /*addRoomEventListener();*/

        binding = ActivityGameMenuBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_game_menu);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    /*private void addRoomEventListener(){
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
                Toast.makeText(GameMenu.this, "ERROR", Toast.LENGTH_SHORT).show();
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

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(GameMenu.this,
                            android.R.layout.simple_list_item_1, RoomsList);
                    listView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError dataerror) {
                //ERROR- NADA
            }
        });
    }*/
}