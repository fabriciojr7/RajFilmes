package com.example.rajfilms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ComentarioAdapter extends RecyclerView.Adapter<ComentarioAdapter.MyViewHolder>{
    private final RecyclerViewInterface rvInterface;
    Context context;
    ArrayList<Comentario> comentarios;

    public ComentarioAdapter(Context contex, ArrayList<Comentario> comentarios, RecyclerViewInterface rvInterface){
        this.context = contex;
        this.comentarios = comentarios;
        this.rvInterface = rvInterface;
    }

    @NonNull
    @Override
    public ComentarioAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.comment_item, parent,false);
        return new ComentarioAdapter.MyViewHolder(view, rvInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentarioAdapter.MyViewHolder holder, int position) {
        holder.lblCommentItem.setText(comentarios.get(position).getComentario());
        holder.lblDataCommentItem.setText(comentarios.get(position).getDataComentario());
        holder.lblNomeUserComment.setText(comentarios.get(position).getUserNome());
    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }

    public static class MyViewHolder extends  RecyclerView.ViewHolder{
        TextView lblCommentItem, lblDataCommentItem, lblNomeUserComment;

        public MyViewHolder(@NonNull View itemView, RecyclerViewInterface rvInterface){
            super(itemView);
            lblCommentItem = itemView.findViewById(R.id.lblCommentItem);
            lblDataCommentItem = itemView.findViewById(R.id.lblDataCommentItem);
            lblNomeUserComment = itemView.findViewById(R.id.lblNomeUserComment);

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
