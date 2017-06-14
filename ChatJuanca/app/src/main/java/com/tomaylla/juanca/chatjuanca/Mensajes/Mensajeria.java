package com.tomaylla.juanca.chatjuanca.Mensajes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.tomaylla.juanca.chatjuanca.Preferences;
import com.tomaylla.juanca.chatjuanca.R;
import com.tomaylla.juanca.chatjuanca.VolleyRP;

/**
 * Created by Yomaira on 03/01/2017.
 */

public class Mensajeria extends AppCompatActivity {

    public static final String MENSAJE = "MENSAJE";

    private BroadcastReceiver bR;

    private RecyclerView rv;
    private Button bTEnviarMensaje;
    private EditText eTEscribirMensaje;
    private List<MensajeDeTexto> mensajeDeTextos;
    private MensajeriaAdapter adapter;

    private String MENSAJE_ENVIAR = "";
    private String EMISOR = "";
    private String RECEPTOR;

    private static final String IP_MENSAJE = "http://juancaandroid.pe.hu/ArchivosPHP/Enviar_Mensajes.php";

    private VolleyRP volley;
    private RequestQueue mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        mensajeDeTextos = new ArrayList<>();

        EMISOR = Preferences.obtenerPreferenceString(this, Preferences.PREFERENCE_USUARIO_LOGIN);
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        if (bundle != null) {
            RECEPTOR = bundle.getString("key_receptor");
        }

        volley = VolleyRP.getInstance(this);
        mRequest = volley.getRequestQueue();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        bTEnviarMensaje = (Button) findViewById(R.id.bTenviarMensaje);
        eTEscribirMensaje = (EditText) findViewById(R.id.eTEsribirMensaje);

        rv = (RecyclerView) findViewById(R.id.rvMensajes);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);//Mensajeria
        rv.setLayoutManager(lm);

        adapter = new MensajeriaAdapter(mensajeDeTextos,this);
        rv.setAdapter(adapter);

        bTEnviarMensaje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                     //OnClick para enviar el mensaje a traves de un boton y que aparezca en el RecyclerView
                String mensaje = eTEscribirMensaje.getText().toString().trim();//"   hola  " => "hola"
                if(!mensaje.isEmpty() && !RECEPTOR.isEmpty()){
                    MENSAJE_ENVIAR = mensaje;
                    MandarMensaje();
                    CreateMensaje(mensaje,"00:00",1);
                    eTEscribirMensaje.setText("");
                }
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setScrollbarChat();

        bR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {            // Definimos los datos que tendra nuestro mensaje: "Mensaje" , "Hora" , "Emisor"
                String mensaje = intent.getStringExtra("key_mensaje");
                String hora = intent.getStringExtra("key_hora");
                String horaParametros[] = hora.split("\\,");
                String emisor = intent.getStringExtra("key_emisor_PHP");
                if(emisor.equals(RECEPTOR)){
                    CreateMensaje(mensaje,horaParametros[0],2);
                }
            }
        };

    }

    private void MandarMensaje(){    //Definimos un Hashmap para los 3 elementos de nuestra mensajeria
        HashMap<String,String> hashMapToken = new HashMap<>();
        hashMapToken.put("emisor",EMISOR);
        hashMapToken.put("receptor",RECEPTOR);
        hashMapToken.put("mensaje",MENSAJE_ENVIAR);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE,new JSONObject(hashMapToken), new Response.Listener<JSONObject>(){  //Solicitamos datos a traves de un JsonObjectRequest
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(Mensajeria.this,datos.getString("resultado"),Toast.LENGTH_SHORT).show();
                } catch (JSONException e){}
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this,"Ocurrio un error",Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,mRequest,this,volley);
    }

    public void CreateMensaje(String mensaje,String hora,int tipoDeMensaje){   //Creamos el mensaje con los 3 datos: tipo,hora y el mensaje
        MensajeDeTexto mensajeDeTextoAuxiliar = new MensajeDeTexto();
        mensajeDeTextoAuxiliar.setId("0");
        mensajeDeTextoAuxiliar.setMensaje(mensaje);
        mensajeDeTextoAuxiliar.setTipoMensaje(tipoDeMensaje);
        mensajeDeTextoAuxiliar.setHoraDelMensaje(hora);
        mensajeDeTextos.add(mensajeDeTextoAuxiliar);
        adapter.notifyDataSetChanged();
        setScrollbarChat();
    }

    @Override                        //Este metodo sirve para que la aplicacion se pausee y reanude cuando recibimos una llamada o algo parecido
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(bR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(bR,new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(adapter.getItemCount()-1);
    }

}
