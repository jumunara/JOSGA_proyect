package com.example.josga;

import static android.os.Build.VERSION_CODES.M;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class MainLoginActivity extends AppCompatActivity {

        private EditText mEditTextNombre;
        private EditText mEditTextEmail;
        private EditText mEditTextPassword;
        private Button mButtonRegistrar;

        //VARIABLES DE DATOS A REGISTRAR
        private String nombre = "";
        private String email = "";
        private String password = "";

        FirebaseAuth mAuth;
        DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mEditTextNombre = (EditText) findViewById(R.id.editTextName);
        mEditTextEmail = (EditText) findViewById(R.id.editTextEmail);
        mEditTextPassword = (EditText) findViewById(R.id.editTextPassword);
        mButtonRegistrar = (Button) findViewById(R.id.btnregister);

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
        });

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
                                startActivity(new Intent(MainLoginActivity.this, ProfileActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(MainLoginActivity.this, "no se pudieron crear los datos corre", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(MainLoginActivity.this, "No se pduio registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
