package pe.leidy_cs.appclient.clases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import pe.leidy_cs.appclient.R;

public class PersonaAdapter extends BaseAdapter{
    Context context;
    ArrayList<Persona> personaArrayList;

    public PersonaAdapter(Context context, ArrayList<Persona> personaArrayList){
        this.context = context;
        this.personaArrayList = personaArrayList;
    }

    @Override
    public int getCount() {
        return this.personaArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.personaArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Creacion de la vista
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.layout_persona, parent, false);

        //Objetto del formulario
        TextView cedula = (TextView) view.findViewById(R.id.textView5);
        TextView nombre = (TextView) view.findViewById(R.id.textView6);
        TextView apellido = (TextView) view.findViewById(R.id.textView7);

        Persona persona = this.personaArrayList.get(position);
        if(persona != null){
            cedula.setText("Cedula: "+persona.getCedulaPer());
            nombre.setText("Nombre: "+persona.getNombrePer());
            apellido.setText("Apellido: "+persona.getApellidoPer());
        }
        return view;
    }
}
