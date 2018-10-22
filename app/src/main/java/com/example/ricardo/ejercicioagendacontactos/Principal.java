package com.example.ricardo.ejercicioagendacontactos;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    private ListView myListView;
    private ArrayList<Contacto> contactos;
    private static final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        contactos = LeerContactos.leerContactos(this);
        cargarToolbar();
        btnFlotante();
        myListView = findViewById(R.id.myListView);
        MetodosEstaticos.comprobarPermisosLectura(this);
        iniciarListView(contactos);
        escuchadorListView();
    }

    private void iniciarListView(ArrayList<Contacto> contactos){
        AdaptadorContacto aclv = new AdaptadorContacto(this, contactos);
        myListView.setAdapter(aclv);
    }

    private void escuchadorListView(){
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Se envía el contacto seleccionado para que sea editado
                Contacto c = (Contacto)parent.getItemAtPosition(position);
                Intent intent = new Intent(Principal.this, ActivityContacto.class);
                intent.putExtra("contactos",contactos);
                intent.putExtra("contacto",c);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    private void cargarToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void btnFlotante(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Principal.this, ActivityContacto.class);
                intent.putExtra("contactos",contactos);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MetodosEstaticos.PERMISOS_LECTURA: {
                if (grantResults.length <= 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    MetodosEstaticos.comprobarPermisosLectura(this);
                }
                return;
            }
            case MetodosEstaticos.PERMISOS_MEMORIA_EXTERNA: {
                if (grantResults.length <= 0 && grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    MetodosEstaticos.comprobarPermisosLectura(this);
                }else{

                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // resultCode = si o no; Si tiene resultado o no;
        // requestCode = código de respuesta de la actividad (Por qué camino vuelve)
        // data = Datos que devuelve

        switch (requestCode){
            case REQUEST_CODE:
                iniciarListView(data.<Contacto>getParcelableArrayListExtra("contactos"));
                break;
            default:
                break;
        }
    }

}
