package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.braian.incidentapp.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class EditarEmpleado extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText nombre, apellido, cedula, correo, contra;
    RadioButton user, admin;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_empleado);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        conectar();
        validar();
        registrar.setOnClickListener(v -> registro());
    }

    public void validar(){
        Bundle extras = getIntent().getExtras();
        String d1 = extras.getString("cedula");
        if(d1 != null){
            cedula.setText(d1);
        }
    }

    private void registro(){
        String mail = correo.getText().toString();
        String pas = contra.getText().toString();
        mAuth.createUserWithEmailAndPassword(mail, pas)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid();
                            obtenerString(cedula.getText().toString());
                            crearRegistro(id,nombre.getText().toString(), apellido.getText().toString(), cedula.getText().toString());
                            retornar();
                        } else {
                            tostada();
                        }
                    }
                });
    }

    private void retornar(){
        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, Principal.class);
        startActivity(i);
    }

    private void tostada(){
        Toast.makeText(this, "Tu contraseña tiene que ser mas larga", Toast.LENGTH_SHORT).show();
    }

    private void crearRegistro(String id, String nombre, String apellido, String cedula){
        String rol = "";
        if (admin.isChecked()){
            rol = "admin";
        }else if (user.isChecked()){
            rol = "user";
        }
        User user = new User(nombre,apellido, rol, cedula);
        mDatabase.child(id).setValue(user);
    }

    private void obtenerString(String ced){
        Query query = mDatabase.child("users").orderByChild("cedula").equalTo(ced);
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

    private void conectar(){
        nombre = findViewById(R.id.txt_nombreE);
        apellido = findViewById(R.id.txt_apellidoE);
        cedula = findViewById(R.id.txt_cedulaE);
        correo = findViewById(R.id.txt_mailE);
        contra = findViewById(R.id.txt_contraseñaE);
        registrar = findViewById(R.id.btn_validar_registroE);
        user = findViewById(R.id.rb_usuE);
        admin = findViewById(R.id.rb_adminE);
    }
}