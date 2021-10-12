package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity  {


    private FirebaseAuth mAuth;
    private Button button;
    private EditText correo, passw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        button = findViewById(R.id.btn_ingresar);
        correo = findViewById(R.id.txt_correo);
        passw = findViewById(R.id.txt_pass);

        button.setOnClickListener(v -> {
           log(correo.getText().toString(),passw.getText().toString());
        });
    }

    private void log(String mail, String pass){

        if (mail.isEmpty() || pass.isEmpty()){
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(this, task -> {
                if (task.isSuccessful()){
                    Intent i = new Intent(this, Principal.class);
                    startActivity(i);
                }else {
                    Toast.makeText(login.this, "Datos incorrectos", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}