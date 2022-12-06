package com.example.rajfilms;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecyclerViewInterface{
    private ArrayList<Filme> filmes;
    private RecyclerView rvFilms;
    private FilmeAdapter adapter;
    private FirebaseFirestore db;
    private EditText txtPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtPesquisa = findViewById(R.id.txtPesquisa);
        this.rvFilms = findViewById(R.id.listFilms);
        rvFilms.setHasFixedSize(true);
        rvFilms.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        filmes = new ArrayList<Filme>();
        adapter = new FilmeAdapter(this, filmes, this);

        rvFilms.setAdapter(adapter);
        EventChangeListener();

        txtPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                ArrayList<Filme> filmesSearch = new ArrayList<>();
                for(Filme f : filmes){
                    if(f.getTitulo().toLowerCase().contains(charSequence.toString().toLowerCase())){
                        filmesSearch.add(f);
                        adapter = new FilmeAdapter(MainActivity.this, filmesSearch, MainActivity.this);
                        rvFilms.setAdapter(adapter);
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void EventChangeListener(){
        db.collection("Filmes").orderBy("titulo", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for(DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                filmes.add(dc.getDocument().toObject(Filme.class));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent i;

        switch (item.getItemId()) {
            case R.id.itemSobre:
                i = new Intent(MainActivity.this, InformationActivity.class);
                startActivity(i);
                break;

            case R.id.itemPerfil:
                i = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(i);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(MainActivity.this, DetailActivity.class);
        Filme f = filmes.get(position);

        i.putExtra("title", f.getTitulo());
        i.putExtra("description", filmes.get(position).getDescricao());
        i.putExtra("genre", filmes.get(position).getGenero());
        i.putExtra("image", filmes.get(position).getPoster());
        i.putExtra("id", filmes.get(position).getId());

        startActivity(i);
    }


}