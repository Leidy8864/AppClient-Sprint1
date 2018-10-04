package pe.leidy_cs.appclient.interfaces;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import pe.leidy_cs.appclient.clases.HttpRequest;

import pe.leidy_cs.appclient.R;
import pe.leidy_cs.appclient.clases.Persona;
import pe.leidy_cs.appclient.clases.PersonaAdapter;

public class BuscarPersona extends AppCompatActivity {

    Spinner spinnerParametro;
    EditText dato;
    ListView listViewPersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_persona);
        inicializar();
    }

    public void inicializar(){
        this.spinnerParametro = (Spinner)findViewById(R.id.spinnerPersonParametros);
        this.dato = (EditText) findViewById(R.id.editTextDato);
        this.listViewPersona = (ListView) findViewById(R.id.listViewPersonas);

        new getPersonas().execute("http://10.200.174.163:8000/rest/personas/");
    }

    private class getPersonas extends AsyncTask<String, Void, String>{
        public String doInBackground(String... params){
            try {
                return HttpRequest.get(params[0]).accept("application/json").body();
            }catch (Exception e){
                return "";
            }
        }
        public void onPostExecute(String result){
            if(result.isEmpty()){
                Toast.makeText(BuscarPersona.this, "No se generaron resultados", Toast.LENGTH_LONG).show();
            }else{
                ArrayList<Persona> personas = Persona.obtenerPersonas(result);
                ArrayList<Persona> personas_aux = new ArrayList<>();

                if (spinnerParametro.getSelectedItem().toString().equals("Listar Todo")){
                    personas_aux = personas;
                }else{
                    for (int i=0; i< personas.size(); i++){
                        switch (spinnerParametro.getSelectedItem().toString()){
                            case "Cedula":
                                if (personas.get(i).getCedulaPer().equals(dato.getText().toString().trim())){
                                    personas_aux.add(personas.get(i));
                                }
                                break;
                            case "Nombres":
                                if (personas.get(i).getNombrePer().equals(dato.getText().toString().trim())){
                                    personas_aux.add(personas.get(i));
                                }
                                break;
                            case "Apellidos":
                                if (personas.get(i).getApellidoPer().equals(dato.getText().toString().trim())){
                                    personas_aux.add(personas.get(i));
                                }
                                break;
                        }
                    }
                }
                if (personas_aux.size() != 0){
                    PersonaAdapter adapter = new PersonaAdapter(BuscarPersona.this, personas_aux);
                    listViewPersona.setAdapter(adapter);
                    listViewPersona.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(BuscarPersona.this, PersonaForm.class);
                            intent.putExtra("operacion", "actualizar");
                            intent.putExtra("id_persona", ((Persona) parent.getAdapter().getItem(position)).getCedulaPer());
                            startActivity(intent);
                        }
                    });
                }
            }
        }
    }
}
