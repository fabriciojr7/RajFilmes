package com.example.rajfilms;

import android.content.Context;
import android.widget.Toast;

public class Util {
    public static void opcoesErro(Context context, String resposta){
        if(resposta.contains("least 6 characters")){
            toast(context, "Digite uma senha maior que 5 caracteres");
        }else if(resposta.contains("address is badly")){
            toast(context, "Formato de e-mail inválido");
        }else if(resposta.contains("password is invalid")){
            toast(context, "Senha inválida");
        }else if(resposta.contains("There is no user")){
            toast(context, "Este e-mail não esta cadastrado");
        }else if(resposta.contains("address is already")){
            toast(context, "E-mail já esta sendo utilizado");
        }else if(resposta.contains("interrupted connection")){
            toast(context, "Sem conexão com a internet");
        }else{
            toast(context, resposta);
        }
    }

    public static void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
