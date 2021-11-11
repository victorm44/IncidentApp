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
import java.util.List;

public class MostrarReportes extends AppCompatActivity {

    ListView mostrarReportes;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    Button btn_eliminar;
    Button btn_editar;
    ArrayList<String> reportesB = new ArrayList<>();

    ArrayList<String> report = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_reportes);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        btn_eliminar = findViewById(R.id.btn_eliminar);
        conectar();
        llenarArray();
        btn_eliminar.setOnClickListener(view -> iniciarDelete() );
        descripcion();
        //obtenerString();
    }

    public void iniciarDelete(){
        Intent i = new Intent(this, EliminarReportes.class);
        startActivity(i);
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
                            String str = snapshot1.getValue().toString();
                            str= str.substring(1, str.length()-1);
                            str = str.replace(",", "\n");
                            report.add(str);
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


    public void descripcion(){
        final String[] p = {new String()};
        mostrarReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p[0] =  mostrarReportes.getItemAtPosition(i)+"";
                Log.e("d", ""+prueba(p[0]));
                iniciar(prueba(p[0])+"");
            }
        });

    }

    public void iniciar(String j){
        Intent i = new Intent(this, EditarReportes.class);
        i.putExtra("descripcion", j);
        startActivity(i);
    }


    private void llenarArray(){
        mostrar();
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, report);
        mostrarReportes.setAdapter(adapter);
    }

    public String prueba(String p) {
        String sub2 = p.substring(11);
        System.out.println(p);
        boolean bol =false;
        if(p.contains("descripcion=")) {
            String sub = p.substring(11);
            for(int x=0; x<sub.length(); x++) {
                char[] y=sub.toCharArray();
                if(y[x] == '\n' && bol == false) {
                    sub2 = sub.substring(1, x);
                    bol=true;
                }
            }
        }
        return  sub2;
    }

    private void obtenerString(){
        final String[] p = {new String()};
        mostrarReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                p[0] =  mostrarReportes.getItemAtPosition(i)+"";
                Log.e("d", ""+prueba(p[0]));
                Query query = mDatabase.child("reportes").orderByChild("descripcion").equalTo(prueba(p[0]));
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
        });

    }

    private void conectar(){
        mostrarReportes = findViewById(R.id.lvReportes);
    }
}