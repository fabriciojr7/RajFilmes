package com.example.rajfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private TextView txtEmailLogin, txtPassWordLogin;
    private Button btnLogin;
    private TextView lblCliqueAqui;
    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_LoginRajFilms);
        setContentView(R.layout.activity_login);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user != null){
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(i);
        }


        this.txtEmailLogin = findViewById(R.id.txtEmailLogin);
        this.txtPassWordLogin = findViewById(R.id.txtPassWordLogin);
        this.btnLogin = findViewById(R.id.btnSendComment);
        this.lblCliqueAqui = findViewById(R.id.lblCliqueAqui);

        this.lblCliqueAqui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(LoginActivity.this, CadastroActivity.class);
                startActivity(i);
            }
        });

        this.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtEmailLogin.getText().toString().trim();
                String password = txtPassWordLogin.getText().toString().trim();

                if(email.isEmpty() || password.isEmpty()){
                    Util.toast(getBaseContext(), "Preencha todos os campos");
                }else{
                    auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Util.toast(getBaseContext(), "usuario logado com sucesso");
                                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                String resp = task.getException().toString();
                                Util.opcoesErro(getBaseContext(), resp);
                            }
                        }
                    });
                }
            }
        });
    }
}