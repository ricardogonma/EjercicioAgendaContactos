package com.example.ricardo.ejercicioagendacontactos;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class MetodosEstaticos {

    public static final int PERMISOS_LECTURA = 0;
    public static final int PERMISOS_MEMORIA_EXTERNA = 10;
    public static final String ARCHIVO = "contactos.cvs";
    
    public static void comprobarPermisosLectura(Activity context){
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
                mostrarDialogo(context, Manifest.permission.READ_CONTACTS, PERMISOS_LECTURA, context.getResources().getString(R.string.dialog_msg_contacts));

            } else {
                solicitarPermisos(context, Manifest.permission.READ_CONTACTS, PERMISOS_LECTURA);
            }
        }
        if(ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_CONTACTS)) {
                mostrarDialogo(context, Manifest.permission.READ_EXTERNAL_STORAGE,PERMISOS_MEMORIA_EXTERNA,context.getResources().getString(R.string.dialog_msg_mem));

            } else {
                solicitarPermisos(context, Manifest.permission.READ_EXTERNAL_STORAGE, PERMISOS_MEMORIA_EXTERNA);
            }
        }
    }

    public static void mostrarDialogo(final Activity context, final String permiso, final int resultado, String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(mensaje);
        builder.setPositiveButton(context.getResources().getString(R.string.dialog_aceptar), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                solicitarPermisos(context,permiso, resultado);
            }
        });
        builder.create();
        builder.show();
    }

    public static void solicitarPermisos(Activity context, String permiso, int resultado){
        ActivityCompat.requestPermissions(context,
                new String[]{permiso}, resultado);
    }


}
