package com.tomaylla.juanca.chatjuanca.ActividadDeUsuarios.Amigos;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import com.tomaylla.juanca.chatjuanca.Preferences;
import com.tomaylla.juanca.chatjuanca.R;
import com.tomaylla.juanca.chatjuanca.VolleyRP;

/**
 * Created by user on 8/05/2017.
 */

public class FragmentAmigos extends Fragment {

    private RecyclerView rv;
    private List<AmigosAtributos> atributosList;
    private AmigosAdapter adapter;

    private VolleyRP volley;
    private RequestQueue mRequest;

    private static final String URL_GET_ALL_USUARIOS = "http://kevinandroidkap.pe.hu/ArchivosPHP/Usuarios_GETALL.php";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_amigos,container,false);

        volley = VolleyRP.getInstance(getContext());
        mRequest = volley.getRequestQueue();

        atributosList = new ArrayList<>();

        rv = (RecyclerView) v.findViewById(R.id.amigosRecyclerView);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new AmigosAdapter(atributosList,getContext());
        rv.setAdapter(adapter);
        SolicitudJSON();

        return v;
    }

    public void agregarAmigo(int fotoDePerfil, String nombre, String ultimoMensaje, String hora,String id){
        AmigosAtributos amigosAtributos = new AmigosAtributos();
        amigosAtributos.setFotoDePerfil(fotoDePerfil);
        amigosAtributos.setNombre(nombre);
        amigosAtributos.setUltimoMensaje(ultimoMensaje);
        amigosAtributos.setHora(hora);
        amigosAtributos.setId(id);
        atributosList.add(amigosAtributos);
        adapter.notifyDataSetChanged();
    }

    public void SolicitudJSON(){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL_GET_ALL_USUARIOS,null, new Response.Listener<JSONObject>(){
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String TodosLosDatos = datos.getString("resultado");
                    JSONArray jsonArray = new JSONArray(TodosLosDatos);
                    String NuestroUsuario = Preferences.obtenerPreferenceString(getContext(),Preferences.PREFERENCE_USUARIO_LOGIN);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        if(!js.getString("id").equals(NuestroUsuario)){
                            agregarAmigo(R.drawable.prueba,js.getString("nombre"),"mensaje "+i,"00:00",js.getString("id"));
                        }
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Ocurrio un error al descomponer el JSON",Toast.LENGTH_SHORT).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Ocurrio un error, por favor contactese con el administrador",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,getContext(),volley);
    }

}
