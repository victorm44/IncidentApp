package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.braian.incidentapp.clases.Reportes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Reportar extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText fecha, titulo, descripcion;
    private RadioGroup prioridad;
    private RadioButton baja, media ,alta;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        conectar();

        btnEnviar.setOnClickListener(v -> registro());

    }

    private void registro(){
        //String id = mAuth.getUid();
        registrarIncidente(fecha.getText().toString(), titulo.getText().toString(), descripcion.getText().toString());
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Principal.class);
        startActivity(i);
    }


    private void registrarIncidente(String fecha, String titulo, String descripcion){
        String tPrioridad = "";
        if (baja.isChecked()){
            tPrioridad = "baja";
        }else if (media.isChecked()){
            tPrioridad = "media";
        }else if(alta.isChecked()){
            tPrioridad = "alta";
        }
        Reportes reportes = new Reportes(fecha, titulo, descripcion, tPrioridad);
        mDatabase.child("reportes").push().setValue(reportes);

    }
    private void conectar(){
        btnEnviar = findViewById(R.id.btnEnviar);
        fecha = findViewById(R.id.txtFecha);
        titulo =findViewById(R.id.txtTitulo);
        descripcion = findViewById(R.id.txtDescripcion);
        prioridad = findViewById(R.id.rbPrioridad);
        baja = findViewById(R.id.rbBaja);
        media = findViewById(R.id.rbMedia);
        alta = findViewById(R.id.rbAlta);
    }

}