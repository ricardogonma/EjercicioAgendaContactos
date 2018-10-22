package com.example.ricardo.ejercicioagendacontactos;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LeerContactos {

    public static ArrayList<String> leerContactosMemInterna(Context context){
        ArrayList<String> texto = new ArrayList<>();
        try{
            InputStreamReader isr = new InputStreamReader(context.openFileInput(MetodosEstaticos.ARCHIVO));
            BufferedReader fin = new BufferedReader(isr);
            String linea = null;
            while((linea = fin.readLine()) != null){
                texto.add(linea);
            }

            fin.close();
        }
        catch (Exception ex){

        }
        return texto;
    }

    public static ArrayList<Contacto> leerContactos(Context context){
        ArrayList<String> contactosMemInt = leerContactosMemInterna(context);
        ArrayList<String> contactosMemExt = leerContactosMemExterna();
        ArrayList<String> contactosTxt = null;
        ArrayList<Contacto> contactos = new ArrayList<>();

        Log.v("MITAAAG","externos" +contactosMemExt);
        Log.v("MITAAAG","externos" +contactosMemInt);

        if(contactosMemInt != null || contactosMemExt != null){
            for(int i = 0; i < contactosMemInt.size() ; i++){
                if(contactosMemExt == null){
                    break;
                }
                for(int j = 0; j < contactosMemExt.size() ; j++){
                    if(contactosMemExt.get(j) == contactosMemInt.get(i)){
                        contactosMemExt.remove(i);
                    }
                }
            }
            if(contactosMemExt != null){
                contactosMemInt.addAll(contactosMemExt);
            }
            contactosTxt = contactosMemInt;
        }
        if(contactosTxt != null) {
            for (String c : contactosTxt) {
                String nombre = "";
                int numero = 0;
                boolean empiezaNombre = true;
                int ultimoValorLeido = 0;
                for(int i = 0 ; i < c.length() ; i++){
                    if(c.substring(i,(i+8)).equals("Numero: ")){
                        empiezaNombre = false;
                        ultimoValorLeido = i+8;
                        break;
                    }
                    if(empiezaNombre){
                        nombre += c.substring(i+8, i+9);
                    }
                }
                nombre = nombre.substring(0,nombre.length()-9);
                numero = Integer.parseInt(c.substring(ultimoValorLeido, c.length()));

                /*c = c.replace("Nombre: ", "");
                c = c.replace("Numero: ", "");
                c = c.replace(",", "");
                Log.v("MITAAAG",c.toString());
                String cSeparado[] = c.split(" ");*/
                //Log.v("MITAAAG",cSeparado.toString());
                contactos.add(new Contacto(nombre, numero));
            }
        }
        return contactos;
    }

    public static ArrayList<String> leerContactosMemExterna(){
        ArrayList<String> texto = new ArrayList<>();
        try
        {
            File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), MetodosEstaticos.ARCHIVO);
            InputStreamReader isr = new InputStreamReader(new FileInputStream(f));
            BufferedReader fin = new BufferedReader(isr);
            String linea = null;
            while((linea = fin.readLine()) != null){
                texto.add(linea);
            }
            fin.close();
        }
        catch (Exception ex){
            texto = null;
        }
        return texto;
    }
}
