package com.tomaylla.juanca.chatjuanca.ActividadDeUsuarios;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.tomaylla.juanca.chatjuanca.Login;
import com.tomaylla.juanca.chatjuanca.Preferences;
import com.tomaylla.juanca.chatjuanca.R;

/**
 * Created by user on 14/05/2017.
 */

public class ActivityUsuarios extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Mensajeria");
        setContentView(R.layout.activity_usuarios);
        tabLayout = (TabLayout) findViewById(R.id.tabLayoutUsuarios);
        viewPager = (ViewPager) findViewById(R.id.viewPagerUsuarios);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new AdapterUsuarios(getSupportFragmentManager()));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0){
                    //setTitle("Chat");
                }else if(position==1){
                    //setTitle("Registro");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_amigos,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id==R.id.NoCerrarSesionMenu){
            Preferences.savePreferenceBoolean(ActivityUsuarios.this,false,Preferences.PREFERENCE_ESTADO_BUTTON_SESION);
            Intent i = new Intent(ActivityUsuarios.this,Login.class);
            startActivity(i);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
