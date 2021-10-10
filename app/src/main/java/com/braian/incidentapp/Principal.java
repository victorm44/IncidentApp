package com.braian.incidentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.braian.incidentapp.clases.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Principal extends AppCompatActivity {

    Button btn_reporte;
    Button btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btn_reporte = findViewById(R.id.btn_reportar);
        btn_registro = findViewById(R.id.btn_registrar);

        btn_registro.setOnClickListener(v -> registro());
        btn_reporte.setOnClickListener(v -> reporte());
    }

    private void reporte(){
        Intent i = new Intent(this, Reportar.class);
        startActivity(i);
    }

    private void registro(){
        Intent i = new Intent(this, Registro.class);
        startActivity(i);
    }
}