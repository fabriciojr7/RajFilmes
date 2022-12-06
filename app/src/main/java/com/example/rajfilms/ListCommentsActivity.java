package com.example.rajfilms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ListCommentsActivity extends AppCompatActivity implements RecyclerViewInterface{
    ArrayList<Comentario> comentarios = new ArrayList<>();
    private TextView lblTitle;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_comments);

        this.lblTitle = findViewById(R.id.lblTitle);
        RecyclerView rvComments = findViewById(R.id.listComments);

        Intent i = getIntent();
        comentarios = (ArrayList<Comentario>) i.getSerializableExtra("comentarios");
        title = i.getStringExtra("title");

        lblTitle.setText(title);

        ComentarioAdapter adapter = new ComentarioAdapter(this, comentarios, this);
        rvComments.setAdapter(adapter);
        rvComments.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onItemClick(int position) {
        Intent i = new Intent(ListCommentsActivity.this, CommentDetailActivity.class);

        i.putExtra("title", comentarios.get(position).getTituloFilme());
        i.putExtra("comment", comentarios.get(position).getComentario());
        i.putExtra("nameUser", comentarios.get(position).getUserNome());
        i.putExtra("dataHora", comentarios.get(position).getDataComentario());



        startActivity(i);
    }
}