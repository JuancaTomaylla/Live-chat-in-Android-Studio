package com.tomaylla.juanca.chatjuanca.ActividadDeUsuarios.Usuarios;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tomaylla.juanca.chatjuanca.R;

/**
 * Created by user on 26/05/2017.
 */

public class HolderUsuariosBuscador extends RecyclerView.ViewHolder {

    private ImageView fotoPerfil;
    private TextView nameUsuario;
    private TextView estadoUsuario;
    private Button enviarSolicitud;

    public HolderUsuariosBuscador(View itemView) {
        super(itemView);
        fotoPerfil = (ImageView) itemView.findViewById(R.id.fotoDePerfilSolicitud);
        nameUsuario = (TextView) itemView.findViewById(R.id.nombreUsuario);
        estadoUsuario = (TextView) itemView.findViewById(R.id.estadoUsuario);
        enviarSolicitud = (Button) itemView.findViewById(R.id.enviarSolicitud);
    }

    public ImageView getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(ImageView fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getNameUsuario() {
        return nameUsuario;
    }

    public void setNameUsuario(TextView nameUsuario) {
        this.nameUsuario = nameUsuario;
    }

    public TextView getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(TextView estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }

    public Button getEnviarSolicitud() {
        return enviarSolicitud;
    }

    public void setEnviarSolicitud(Button enviarSolicitud) {
        this.enviarSolicitud = enviarSolicitud;
    }
}
