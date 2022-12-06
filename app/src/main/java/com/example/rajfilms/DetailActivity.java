package com.example.rajfilms;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    private TextView lblTituloDetail, lblDescricaoDetail, lblGeneroDetail, lblVizualizarComentarios;
    private Button btnEnviarComentario;
    private ImageView imgPosterDetail;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayList<Comentario> comentarios = new ArrayList<Comentario>();
    private String title, description, genre, image, usuarioID, userNome;
    private int idFilme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initComponents();

        lblTituloDetail.setText(title);
        lblDescricaoDetail.setText(description);
        lblGeneroDetail.setText(genre);
        Picasso.get().load(image).into(imgPosterDetail);

        EventChangeListener(idFilme);

        this.btnEnviarComentario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), SendCommentActivity.class);
                i.putExtra("idFilme", idFilme);
                i.putExtra("tituloFilme", title);
                i.putExtra("userNome", userNome);
                startActivity(i);
            }
        });

        lblVizualizarComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), ListCommentsActivity.class);
                i.putExtra("comentarios", comentarios);
                i.putExtra("title", title);
                startActivity(i);
            }
        });
    }

    private void initComponents(){
        title = getIntent().getStringExtra("title");
        description = getIntent().getStringExtra("description");
        genre = getIntent().getStringExtra("genre");
        image = getIntent().getStringExtra("image");
        idFilme = getIntent().getIntExtra("id", 0);

        lblTituloDetail = findViewById(R.id.lblTituloDetail);
        lblDescricaoDetail = findViewById(R.id.lblDescricaoDetail);
        lblGeneroDetail = findViewById(R.id.lblGeneroDetail);
        lblVizualizarComentarios = findViewById(R.id.lblVizualizarComentarios);
        btnEnviarComentario = findViewById(R.id.btnSendComment);
        imgPosterDetail = findViewById(R.id.imgPosterDetail);
    }

    private void EventChangeListener(int idFilme){
        db.collection("Comentarios")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }

                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                if(dc.getDocument().toObject(Comentario.class).getIdFilme() == idFilme){

                                    comentarios.add(dc.getDocument().toObject(Comentario.class));
                                }
                            }
                        }
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Usuarios").document(usuarioID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if(documentSnapshot != null){
                    userNome = documentSnapshot.getString("nome");
                }
            }
        });
    }
}