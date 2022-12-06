package com.example.rajfilms;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SendCommentActivity extends AppCompatActivity {
    private EditText txtComentario;
    private Button btnSendComment;
    String tituloFilme, userNome;
    int idFilme;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_comment);

        this.txtComentario = findViewById(R.id.txtComentario);
        this.btnSendComment = findViewById(R.id.btnSendComment);
        db = FirebaseFirestore.getInstance();

        Intent i = getIntent();
        tituloFilme = i.getStringExtra("tituloFilme");
        userNome = i.getStringExtra("userNome");
        idFilme = i.getIntExtra("idFilme", 0);

        this.btnSendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comentario = txtComentario.getText().toString().trim();

                if(comentario.isEmpty()){
                    Util.toast(getBaseContext(), "Escreva um comentario.");
                }else{
                    enviarComentario(comentario);
                    onBackPressed();
                }
            }
        });
    }

    private void enviarComentario(String comentario){
        Date data = new Date();
        SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String dataComentario = formataData.format(data);

        CollectionReference dbComentarios = db.collection("Comentarios");

        Comentario c = new Comentario(idFilme, tituloFilme, comentario, userNome, dataComentario);

        dbComentarios.add(c).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Util.toast(getBaseContext(), "Comentario enviado com sucesso.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Util.toast(getBaseContext(), "Não foi possivel enviar o comentario, tente mais tarde.");
            }
        });
    }
}