package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.braian.incidentapp.clases.Reportes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MostrarReportes extends AppCompatActivity {

    ListView mostrarReportes;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    ArrayList<String> report = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_reportes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        conectar();
        llenarArray();

    }

    private void mostrar(){

        mDatabase.child("reportes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( final DataSnapshot snapshot1 : snapshot.getChildren()){
                    mDatabase.child("reportes").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Reportes reportes = snapshot1.getValue(Reportes.class);
                            report.add(snapshot1.getValue().toString());
                            Log.e("Datos", ""+snapshot1.getValue());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("error", "hubo un problema");
            }
        });
    }

    private void llenarArray(){
        mostrar();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, report);
        mostrarReportes.setAdapter(adapter);
    }

    private void conectar(){
        mostrarReportes = findViewById(R.id.lvReportes);
    }
}