package com.example.josga;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class MainLoginActivity extends AppCompatActivity {

    private EditText mEditTextNombre;
    private EditText mEditTextEmail;
    private EditText mEditTextPassword;
    private Button mButtonRegistrar;
    private Button mButtonLogin;

    //VARIABLES DE DATOS A REGISTRAR
    String nombre = "";
    private String email = "";
    private String password = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    FirebaseDatabase database;
    DatabaseReference PlayerRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //database = FirebaseDatabase.getInstance();

        mEditTextNombre = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonRegistrar = (Button) findViewById(R.id.btnregister);
        mButtonLogin = (Button) findViewById(R.id.btnlogin);


        //Verificacion si el jugador existe
        /*SharedPreferences preferences = getSharedPreferences("PREPS", 0);
        nombre = preferences.getString("playerName", "");*/

        /*if (!nombre.equals("")){
            PlayerRef = database.getReference("players/" + nombre);
            addEventListener();
            PlayerRef.setValue("");
        }*/


        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre= mEditTextNombre.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();

                if (!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty() ){

                    if(password.length() >= 6 ){
                        loginUser();
                    }
                    else{
                        Toast.makeText(MainLoginActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(MainLoginActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }

            }

            private void loginUser(){
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainLoginActivity.this, GameMenu.class));
                            finish();
                        }
                        else{
                            Toast.makeText(MainLoginActivity.this, "El usuario no esta registrado", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        mButtonRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nombre = mEditTextNombre.getText().toString();
                email = mEditTextEmail.getText().toString();
                password = mEditTextPassword.getText().toString();


                if(!nombre.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                    if(password.length() >= 6){
                        registerUsuer();
                    }
                    else{
                        Toast.makeText(MainLoginActivity.this, "El password debe tener 6 caracteres",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(MainLoginActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();
                }
            }


        private void registerUsuer(){
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Map<String, Object> map = new HashMap<>();
                        map.put("nombre", nombre);
                        map.put("email", email);
                        map.put("password", password);

                        String id = mAuth.getCurrentUser().getUid();

                        mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task2) {
                                if (task2.isSuccessful()){
                                    Toast.makeText(MainLoginActivity.this,"Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(MainLoginActivity.this, "Los datos no se crearón correctamente", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(MainLoginActivity.this, "Ya existe un usuario con este correo", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
        });
    }
    /*private void addEventListener(){
       //Lectura de la base de datos
       PlayerRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               if (!nombre.equals("")){
                    SharedPreferences preferences = getSharedPreferences("PREPS", 0);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("nombre", nombre);
                    editor.apply();

                    startActivity(new Intent(getApplicationContext(), MainLoginActivity.class));
                    finish();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError error) {

           }
       });
    }*/
}

