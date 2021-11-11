package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.braian.incidentapp.clases.Reportes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class EditarReportes extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private EditText fecha, titulo, descripcion;
    private RadioGroup prioridad;
    private RadioButton baja, media ,alta;
    private Button btnEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_reportes);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        conectar();

        btnEnviar.setOnClickListener(v -> registro());
    }

    public String validar(){
        String d1="";
        Bundle extras = getIntent().getExtras();
        d1 = extras.getString("descripcion");
        return d1;
    }

    private void registro(){
        //String id = mAuth.getUid();
        registrarIncidente(fecha.getText().toString(), titulo.getText().toString(), descripcion.getText().toString());
        obtenerString(validar());
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

    private void obtenerString(String ced){
        Query query = mDatabase.child("reportes").orderByChild("descripcion").equalTo(ced);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot data : snapshot.getChildren()){
                        String key = data.getKey();
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference("reportes").child(key);
                        ref.removeValue();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    private void conectar(){
        btnEnviar = findViewById(R.id.btnEnviarR);
        fecha = findViewById(R.id.txtFechaR);
        titulo =findViewById(R.id.txtTituloR);
        descripcion = findViewById(R.id.txtDescripcionR);
        prioridad = findViewById(R.id.rbPrioridadR);
        baja = findViewById(R.id.rbBajaR);
        media = findViewById(R.id.rbMediaR);
        alta = findViewById(R.id.rbAltaR);
    }




}