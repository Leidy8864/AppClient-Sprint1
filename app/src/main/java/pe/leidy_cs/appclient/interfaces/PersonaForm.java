package pe.leidy_cs.appclient.interfaces;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;

import pe.leidy_cs.appclient.R;
import pe.leidy_cs.appclient.clases.Persona;

public class PersonaForm extends AppCompatActivity {
    EditText cedula, nombre, apellido;
    Persona persona;
    String id_persona;

    //Operacion insertar, actualizar
    String operacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persona_form);
        inicializar();

        Bundle bundle = getIntent().getExtras();
        this.operacion = bundle.getString("operacion");
        if (this.operacion.equals("actualizar")){
            this.id_persona = bundle.getString("id_persona");
            //obtener Persona
            new ObtenerPersona().execute();
        }else{
            this.id_persona = "";
        }
    }

    public void inicializar(){
        this.cedula = (EditText) findViewById(R.id.editTextCedula);
        this.nombre = (EditText) findViewById(R.id.editTextNombre);
        this.apellido = (EditText) findViewById(R.id.editTextApellido);
    }

    public void btn_clickGuardarPersona(View view) {
        persona = new Persona();
        persona.setCedulaPer(cedula.getText().toString().trim());
        persona.setNombrePer(nombre.getText().toString().trim());
        persona.setApellidoPer(apellido.getText().toString().trim());

        if (this.operacion.equals("actualizar"))
            new ActualizarPersona().execute();
        if (this.operacion.equals("insertar"))
            new InsertarPersona().execute();
    }

    public void btn_clickEliminarPersona(View view) {
        if (id_persona != ""){
            new EliminarPersona().execute();
        }else {
            Toast.makeText(PersonaForm.this, "Esta funcion esta \n disponile para personas registradas", Toast.LENGTH_LONG).show();
        }
    }

    //Eliminar Persona
    private class EliminarPersona extends AsyncTask<Void, Void, Boolean>{

        @Override
        public Boolean doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpDelete httpDelete = new HttpDelete("http://10.200.174.163:8000/rest/personas/"+id_persona+"/");
            httpDelete.setHeader("Content-Type", "application/json");

            try {
                httpClient.execute(httpDelete);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute(Boolean result) {
            if (result){
                Toast.makeText(PersonaForm.this, "Eliminado correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PersonaForm.this, "Problema al eliminar", Toast.LENGTH_LONG).show();
            }
        }
    }

    //Insertar Persona
    private class InsertarPersona extends AsyncTask<Void, Void, Boolean>{

        @Override
        public Boolean doInBackground(Void... voids) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://10.200.174.163:8000/rest/personas/");
            httpPost.setHeader("Content-Type", "application/json");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cedulaPer", persona.getCedulaPer());
                jsonObject.put("nombrePer", persona.getNombrePer());
                jsonObject.put("apellidoPer", persona.getApellidoPer());

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPost.setEntity(stringEntity);
                httpClient.execute(httpPost);

                return true;
            } catch (org.json.JSONException | java.io.IOException e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute(Boolean result) {
            if (result){
                Toast.makeText(PersonaForm.this, "Insertado correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(PersonaForm.this, "Problema al insertar", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class ActualizarPersona extends AsyncTask<Void, Void, Boolean>{
        @Override
        public Boolean doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut("http://10.200.174.163:8000/rest/personas/"+id_persona+"/");
            httpPut.setHeader("content-type", "application/json");

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("cedulaPer", persona.getCedulaPer());
                jsonObject.put("nombrePer", persona.getNombrePer());
                jsonObject.put("apellidoPer", persona.getApellidoPer());

                StringEntity stringEntity = new StringEntity(jsonObject.toString());
                httpPut.setEntity(stringEntity);
                httpClient.execute(httpPut);
                return true;

            } catch (org.json.JSONException | java.io.IOException e){
                e.printStackTrace();
                return false;
            }
        }

        @Override
        public void onPostExecute(Boolean result) {
            String msj;
            if (result){
                msj = "Actualizado correctamente";
            } else {
                msj = "Problemas al actualizar";
            }
            Toast.makeText(PersonaForm.this, msj, Toast.LENGTH_SHORT).show();
        }
    }

    //
    private class ObtenerPersona extends AsyncTask<Void, Void, Persona>{
        @Override
        public Persona doInBackground(Void... params) {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://10.200.174.163:8000/rest/personas/"+id_persona+"/");

            httpGet.setHeader("content-type", "application/json");
            persona = new Persona();
            try {
                HttpResponse response = httpClient.execute(httpGet);
                String responString = EntityUtils.toString(response.getEntity());

                JSONObject jsonObject = new JSONObject(responString);

                persona.setCedulaPer(jsonObject.getString("cedulaPer"));
                persona.setNombrePer(jsonObject.getString("nombrePer"));
                persona.setApellidoPer(jsonObject.getString("apellidoPer"));
                return persona;

            } catch (org.json.JSONException | java.io.IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public void onPostExecute(Persona persona) {
            super.onPostExecute(persona);
            if (persona != null){
                cedula.setText(persona.getCedulaPer());
                apellido.setText(persona.getApellidoPer());
                nombre.setText(persona.getNombrePer());
            } else {
                Toast.makeText(PersonaForm.this, "Problemas al obtener el objeto", Toast.LENGTH_LONG).show();
            }
        }
    }
}




