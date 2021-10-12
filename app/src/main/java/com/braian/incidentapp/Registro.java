package com.braian.incidentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.braian.incidentapp.clases.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registro extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    EditText nombre, apellido, cedula, correo, contra;
    RadioButton user, admin;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        conectar();

        registrar.setOnClickListener(v -> registro());
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
        Toast.makeText(this, "Hay problemas revisa tu conexión", Toast.LENGTH_SHORT).show();
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

    private void conectar(){
        nombre = findViewById(R.id.txt_nombre);
        apellido = findViewById(R.id.txt_apellido);
        cedula = findViewById(R.id.txt_cedula);
        correo = findViewById(R.id.txt_mail);
        contra = findViewById(R.id.txt_contraseña);
        registrar = findViewById(R.id.btn_validar_registro);
        user = findViewById(R.id.rb_usu);
        admin = findViewById(R.id.rb_admin);
    }
}