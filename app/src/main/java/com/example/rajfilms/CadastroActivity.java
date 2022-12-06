package com.example.rajfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CadastroActivity extends AppCompatActivity {
    private EditText txtNome, txtEmail, txtPassword;
    private Button btnConfirmar, btnCancelar;
    private FirebaseAuth auth;
    private String usuarioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        this.txtNome = findViewById(R.id.txtNome);
        this.txtEmail = findViewById(R.id.txtEmail);
        this.txtPassword = findViewById(R.id.txtPassWord);
        this.btnConfirmar = findViewById(R.id.btnConfirmar);
        this.btnCancelar = findViewById(R.id.btnCancelar);
        auth = FirebaseAuth.getInstance();

        this.btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.btnConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nome = txtNome.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if(nome.isEmpty() || email.isEmpty() || password.isEmpty()){
                    Util.toast(getBaseContext(), "Preencha todos os campos");
                }else{
                    cadastrarUsuario(email, password);
                }
            }
        });
    }

    private void cadastrarUsuario(String email, String password){
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    salvarUsuario();
                    Util.toast(getBaseContext(), "Cadastro efetuado com sucesso");
                    finish();
                    Intent i = new Intent(CadastroActivity.this, MainActivity.class);
                    startActivity(i);
                }
                else{
                    String resp = task.getException().toString();
                    Util.opcoesErro(getBaseContext(), resp);
                }
            }
        });
    }

    private void salvarUsuario(){
        String nome = txtNome.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, Object> usuarios = new HashMap<>();
        usuarios.put("nome", nome);

        usuarioId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Usuarios").document(usuarioId);
        documentReference.set(usuarios).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("db", "Sucesso ao salvar os dados");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("db", "Erro ao salvar os dados" + e.toString());
            }
        });
    }
}