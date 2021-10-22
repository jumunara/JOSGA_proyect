package com.example.josga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class Sala extends Fragment {

    private TextView Codigo;
    private Button Refresh;
    Button button;

    String nombre = "";
    String RoomName = "";
    String role = "";
    String mensage = "";

    FirebaseDatabase database;
    DatabaseReference mensageRef;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sala);

        button = (Button) findViewById(R.id.button);
        button.setEnabled(false);
        Refresh = (Button) findViewById(R.id.Refreshcode);
        Codigo = (TextView) findViewById(R.id.CodigoDeSala);

        database = FirebaseDatabase.getInstance();

        SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        nombre = preferences.getString("PlayerName", "");

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            RoomName = extras.getString("nombre de la sala");
            if (RoomName.equals(nombre)){
                role = "host";
            } else{
                role = "quest";
            }
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Enviar mensaje
                button.setEnabled(false);
                mensage = role + ":Empacado!";
                mensageRef.setValue(mensage);
            }
        });

        //Mensaje entrante
        mensageRef = database.getReference("salas/" + RoomName + "/mensage");
        mensage = role + "Empacado!";
        mensageRef.setValue(mensage);
        addRoomEventListener();

        Refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random ramdom = new Random();
                int NUMERO = ramdom.nextInt(999999);
                    Codigo.setText(Integer.toString(NUMERO));
            }
        });


        image = (ImageView) findViewById(R.id.ruleta);
        image.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View Evento, MotionEvent Accion) {
                switch (Accion.getAction()){
                    case MotionEvent.ACTION_MOVE:
                        GirarRuleta(image);
                        break;
                }
                return true;
            }
        });

    }

    private void addRoomEventListener (){
        mensageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                //Mensaje resivido
                if(role.equals("host")){
                    if(datasnapshot.getValue(String.class).contains("guest:")){
                        button.setEnabled(true);
                        Toast.makeText(Sala.this, "" +
                                datasnapshot.getValue(String.class).replace("guest:", ""), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    if(datasnapshot.getValue(String.class).contains("host:")){
                        button.setEnabled(true);
                        Toast.makeText(Sala.this, "" +
                                datasnapshot.getValue(String.class).replace("host:", ""), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError dataerror) {
                //ERROR - Reintentar
                mensageRef.setValue(mensage);
            }
        });
    }
    private void GirarRuleta(ImageView image) {
        RotateAnimation Animacion = new RotateAnimation(0,570,RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        Animacion.setDuration(2000);
        image.startAnimation(Animacion);

    }



}