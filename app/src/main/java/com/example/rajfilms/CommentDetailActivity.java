package com.example.rajfilms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class CommentDetailActivity extends AppCompatActivity {
    private TextView lblTitleFilmComment, lblComment, lblNomeComment, lblDataHoraComment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_detail);
        this.lblTitleFilmComment = findViewById(R.id.lblTitleFilmComment);
        this.lblComment = findViewById(R.id.lblComment);
        this.lblNomeComment = findViewById(R.id.lblNomeComment);
        this.lblDataHoraComment = findViewById(R.id.lblDataHoraComment);

        Intent i = getIntent();
        String title = i.getStringExtra("title");
        String comment = i.getStringExtra("comment");
        String nameUser = i.getStringExtra("nameUser");
        String dataHora = i.getStringExtra("dataHora");

        lblTitleFilmComment.setText(title);
        lblComment.setText(comment);
        lblNomeComment.setText(nameUser);
        lblDataHoraComment.setText(dataHora);
    }
}