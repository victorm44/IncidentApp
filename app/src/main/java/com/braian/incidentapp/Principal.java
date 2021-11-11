package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.braian.incidentapp.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class Principal extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button btn_reporte;
    Button btn_registro;
    Button btn_mostrarEmpleado;
    Button btn_mostrarReportes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        conectar();
        checkRol();


        btn_mostrarReportes.setOnClickListener(v -> mostrarReportes());
        btn_registro.setOnClickListener(v -> registro());
        btn_reporte.setOnClickListener(v -> reporte());
        btn_mostrarEmpleado.setOnClickListener(view -> mostrarEmpleado());
    }

    private void conectar(){
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_reporte = findViewById(R.id.btn_reportar);
        btn_registro = findViewById(R.id.btn_registrar);
        btn_mostrarEmpleado = findViewById(R.id.btn_mostrarEmpleado);
        btn_mostrarReportes =findViewById(R.id.btn_mostrarReportes);
    }

    private void checkRol(){

        String id = "";
        if (mAuth.getCurrentUser() != null){
            id = mAuth.getUid();
        }
        mDatabase.child("users").child(id).child("rol").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    if (task.getResult().getValue() != null){
                        unable(task.getResult().getValue().toString().trim());
                    }
                }
            }
        });
    }

    private void reporte(){
        Intent i = new Intent(this, Reportar.class);
        startActivity(i);
    }


    private void unable(String s){
        if (!s.equals("admin")){
            btn_registro.setVisibility(View.INVISIBLE);
            btn_registro.setEnabled(false);
        }
    }

    private void registro(){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }

    private void mostrarEmpleado(){
        Intent i = new Intent(this, Empleados.class);
        startActivity(i);
    }

    private void mostrarReportes(){
        Intent i = new Intent(this, MostrarReportes.class);
        startActivity(i);
    }
}