package com.braian.incidentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.braian.incidentapp.clases.Empleado;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Empleados extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    ArrayList<Empleado> dato = new ArrayList<>();
    ArrayList<String> listadatos = new ArrayList<>();
    ArrayAdapter<Empleado> adapter;
    ListView lvEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lvEmpleados = findViewById(R.id.lvEmpleados);

    }

    private ArrayList<String> lista(){
        ArrayList<String> lista = new ArrayList<>();
        Empleado e = new Empleado();



        return lista;
    }

    public void poblar_lista()
    {
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, listadatos);
        lvEmpleados.setAdapter(adapter);
    }

}