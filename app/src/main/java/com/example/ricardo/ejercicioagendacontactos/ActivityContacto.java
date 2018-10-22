package com.example.ricardo.ejercicioagendacontactos;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ActivityContacto extends AppCompatActivity {

    TextInputLayout txtNombre, txtNumero;
    Button btnEnviar;
    RadioGroup radios;
    ArrayList<Contacto> contactos = new ArrayList<>();
    Contacto contacto = null;
    ArrayList<Contacto> contactosNuevo = new ArrayList<>();
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);
        toolbarInit();
        componentes();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearUsuario();
            }
        });
        if(contacto != null){
            rellenarContacto();
            for(Contacto c: contactos){
                if(c.getNombre() != contacto.getNombre()){
                    contactosNuevo.add(c);
                }
            }
        }else{
            contactosNuevo = contactos;
        }
    }

    private void componentes(){
        txtNombre = findViewById(R.id.txtNombre);
        txtNumero = findViewById(R.id.txtNumero);
        btnEnviar = findViewById(R.id.button);
        radios = findViewById(R.id.radios);
        iniciarIntent();
    }

    private void iniciarIntent(){
        intent = getIntent();
        if(intent.getExtras() != null) {
            if(intent.getExtras().containsKey("contactos")) {
                contactos = intent.getParcelableArrayListExtra("contactos");
            }
            if(intent.getExtras().containsKey("contactos")) {
                contacto = intent.getParcelableExtra("contacto");
            }
        }
    }

    private void rellenarContacto(){
        txtNombre.getEditText().setText(contacto.getNombre());
        txtNumero.getEditText().setText(String.valueOf(contacto.getNumero()));
    }

    private void toolbarInit(){
        Toolbar toolbar = findViewById(R.id.toolbarContacto);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private boolean gestionErrores(){
        txtNombre.setError(null);
        txtNumero.setError(null);
        String etNombre = txtNombre.getEditText().getText().toString();
        String etNumero = txtNumero.getEditText().getText().toString();

        for(Contacto cn: contactosNuevo){
            if(etNombre.toLowerCase().equals(cn.getNombre().toLowerCase())){
                txtNombre.setError(getResources().getString(R.string.repetido));
                return false;
            }
        }

        if(etNombre.isEmpty() || etNumero.isEmpty()) {
            if (etNombre.isEmpty()) {
                txtNombre.setError(getResources().getString(R.string.errorVacio));
            }
            if (etNumero.isEmpty()) {
                txtNumero.setError(getResources().getString(R.string.errorVacio));
            }
            return false;
        }
        return true;
    }

    private void crearUsuario(){
        if(gestionErrores()){
            String nombre = txtNombre.getEditText().getText().toString();
            int numero = Integer.parseInt(txtNumero.getEditText().getText().toString());
            Contacto contacto = new Contacto(nombre, numero);
            contactosNuevo.add(contacto);
            File f = null;
            int radiosId = radios.getCheckedRadioButtonId();
            switch (radiosId){
                case R.id.memExtPriv:
                    f = getExternaPrivada(MetodosEstaticos.ARCHIVO);
                case R.id.memIntPriv:
                    f = getInternaPrivada(MetodosEstaticos.ARCHIVO);
            }

            Log.v("MITAAAG","CONTACTOS + "+contactosNuevo);

            if(f != null){
                write(f, contactosNuevo);
                Intent i = new Intent();
                intent.putExtra("contactos",contactosNuevo);
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }

    private boolean write(File f, ArrayList<Contacto> contactos){
        Boolean escrito = true;
        FileWriter fw = null;
        try{
            fw = new FileWriter(f);
            fw.write("");
            for(Contacto c: contactos){
                fw.append(c.toString() + "\n");
            }
            fw.flush();
            fw.close();
        }catch(IOException e){
            escrito = false;
        }
        return escrito;
    }

    private File getInternaPrivada(String archivo){
        return new File(getFilesDir(),archivo);
    }

    private File getExternaPrivada(String archivo){
        return new File(getExternalFilesDir(null),archivo);
    }
}
