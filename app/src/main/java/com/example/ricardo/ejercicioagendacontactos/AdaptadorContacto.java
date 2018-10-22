package com.example.ricardo.ejercicioagendacontactos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdaptadorContacto  extends ArrayAdapter<Contacto> {
    private List<Contacto> data;
    private int layout;
    private LayoutInflater inflator;
    private Contacto contact;

    public AdaptadorContacto(@NonNull Context context, List<Contacto> data) {
        this(context,R.layout.item_contacto, data);
    }

    public AdaptadorContacto(@NonNull Context context, int layout, List<Contacto> data) {
        super(context, layout, data);
        this.layout = layout;
        this.data = data;
        inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        // inflator = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        // Cuando se crea el convertView se instancia el ViewHolder
        ViewHolder vh = new ViewHolder();

        if (convertView == null) {
            convertView = inflator.inflate(layout,null);

            vh.tvNombre = convertView.findViewById(R.id.nombre);
            vh.tvTelefono = convertView.findViewById(R.id.telefono);

            // El ViewHolder hay que guardarlo para reutilizarlo, porque mantiene sus referencias
            convertView.setTag(vh);
        }

        contact = data.get(position);

        vh = (ViewHolder) convertView.getTag();

        vh.tvNombre.setText(contact.getNombre());
        vh.tvTelefono.setText(String.valueOf(contact.getNumero()));

        return convertView;
    }


    // La idea del ViewHolder es que sea un almacén de datos.
    class ViewHolder{
        public TextView tvNombre, tvTelefono;
    }
}
