package pe.leidy_cs.appclient.clases;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Persona {
    String cedulaPer;
    String apellidoPer;
    String nombrePer;

    public String getCedulaPer() {
        return cedulaPer;
    }

    public void setCedulaPer(String cedulaPer) {
        this.cedulaPer = cedulaPer;
    }

    public String getApellidoPer() {
        return apellidoPer;
    }

    public void setApellidoPer(String apellidoPer) {
        this.apellidoPer = apellidoPer;
    }

    public String getNombrePer() {
        return nombrePer;
    }

    public void setNombrePer(String nombrePer) {
        this.nombrePer = nombrePer;
    }

    public static ArrayList<Persona> obtenerPersonas(String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Persona>>(){}.getType();
        return gson.fromJson(json, type);
    }
}
