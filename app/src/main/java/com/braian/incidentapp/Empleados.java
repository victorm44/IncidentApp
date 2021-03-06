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
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Empleados extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button btn_eliminar;
    Button btn_editar;
    private String cedula, nombre, apellido;
    ArrayList<String> listadatos = new ArrayList<>();
    ListView lvEmpleados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        lvEmpleados = findViewById(R.id.lvEmpleados);
        btn_eliminar = findViewById(R.id.btn_eliminarEm);
        llenarArray();
        btn_eliminar.setOnClickListener(view -> iniciarDelete());
        cedula();
        //obtenerString();

    }

    public void iniciarDelete(){
        Intent i = new Intent(this, EliminarUsuarios.class);
        startActivity(i);
    }


    private void mostrar(){

        mDatabase.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( final DataSnapshot snapshot1 : snapshot.getChildren()){
                    mDatabase.child("users").child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //Reportes reportes = snapshot1.getValue(Reportes.class);
                            String str = snapshot1.getValue().toString();
                            str= str.substring(1, str.length()-1);
                            str = str.replace(",", "\n");
                            listadatos.add(str);
                            Log.e("Datos", ""+str);
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
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listadatos);
        lvEmpleados.setAdapter(adapter);
    }

    public String prueba(String p) {
        String sub2= prueba2(p);
        System.out.println("prueb:"+ sub2);
        sub2= prueba2(p).substring(7);
        boolean bol= false;
        if(p.contains("cedula=")) {
            String sub = prueba2(p).substring(7);
            for(int x=0; x<sub.length(); x++) {
                char[ ] y=sub.toCharArray();
                if(y[x]=='\n' && bol == false){
                    sub2 = sub.substring(1, x);
                    bol= true;
                    System.out.println("entra");
                }
            }
        }
        return sub2;

    }

    public String prueba2(String p) {
        boolean bol = false;
        int contador = 0;
        String sub="";
        if(p.contains("cedula=")) {
            for(int x=0; x<p.length(); x++) {
                char[ ] y=p.toCharArray();
                if(y[x]=='\n') {

                    contador++;
                }
                if(contador >= 2 && bol == false){
                    sub = p.substring(x+1);
                    bol = true;
                }
            }
        }
        return sub;
    }

    private void obtenerString(){
        final String[] p = {new String()};
        lvEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p[0] =  lvEmpleados.getItemAtPosition(i)+"";
                Log.e("d", ""+prueba(p[0]));
                Query query = mDatabase.child("users").orderByChild("cedula").equalTo(prueba(p[0]));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            for(DataSnapshot data : snapshot.getChildren()){
                                String key = data.getKey();
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference ref = database.getReference("users").child(key);
                                ref.removeValue();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

    public void cedula(){
        final String[] p = {new String()};
        lvEmpleados.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p[0] =  lvEmpleados.getItemAtPosition(i)+"";
                Log.e("d", ""+prueba(p[0]));
                iniciar(prueba(p[0])+"");
            }
        });

    }

    public void iniciar(String j){
        Intent i = new Intent(this, EditarEmpleado.class);
        i.putExtra("cedula", j);
        startActivity(i);
    }

}