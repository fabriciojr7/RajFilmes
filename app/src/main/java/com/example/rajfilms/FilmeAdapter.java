package com.example.rajfilms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.MyViewHolder>{
    private final RecyclerViewInterface rvInterface;
    Context context;
    ArrayList<Filme> filmes;

    public FilmeAdapter(Context context, ArrayList<Filme> filmes, RecyclerViewInterface rvInterface){
        this.context = context;
        this.filmes = filmes;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public FilmeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.film_item, parent,false);
        return new FilmeAdapter.MyViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeAdapter.MyViewHolder holder, int position) {
        holder.txtTitulo.setText(filmes.get(position).getTitulo());
        holder.txtDescricao.setText(filmes.get(position).getDescricao());
        Picasso.get().load(filmes.get(position).getPoster()).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return filmes.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView txtTitulo, txtDescricao;
        ImageView imgPoster;
        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvInterface){
            super(itemView);
            txtTitulo = itemView.findViewById(R.id.lblTituloItemFilm);
            txtDescricao = itemView.findViewById(R.id.lblDescricaoItemFilm);
            imgPoster = itemView.findViewById(R.id.imgPosterItemFilm);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(rvInterface != null) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        rvInterface.onItemClick(pos);
                    }
                   }
                }
            });
        }
    }
}
