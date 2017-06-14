package com.tomaylla.juanca.chatjuanca.ActividadDeUsuarios.Usuarios;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import com.tomaylla.juanca.chatjuanca.R;

/**
 * Created by user on 26/05/2017.
 */

public class UsuariosBuscadorAdapter extends RecyclerView.Adapter<HolderUsuariosBuscador> {

    private List<UsuarioBuscadorAtributos> atributosList;
    private Context context;

    public UsuariosBuscadorAdapter(List<UsuarioBuscadorAtributos> atributosList, Context context) {
        this.atributosList = atributosList;
        this.context = context;
    }

    @Override
    public HolderUsuariosBuscador onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_buscar_usuarios,parent,false);
        return new HolderUsuariosBuscador(v);
    }

    @Override
    public void onBindViewHolder(HolderUsuariosBuscador holder, int position) {
        holder.getFotoPerfil().setImageResource(atributosList.get(position).getFotoPerfil());
        holder.getNameUsuario().setText(atributosList.get(position).getNombre());
        holder.getEstadoUsuario().setText(atributosList.get(position).getEstadoUsuario());
    }

    @Override
    public int getItemCount() {
        return atributosList.size();
    }
}
