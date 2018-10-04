package pe.leidy_cs.appclient.interfaces;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import pe.leidy_cs.appclient.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btn_buscarPersona(View view){
        Intent intent = new Intent(MainActivity.this, BuscarPersona.class);
        startActivity(intent);
    }

    public void btn_formularioPersona(View view){
        Intent intent = new Intent(MainActivity.this, PersonaForm.class);
        intent.putExtra("operacion", "insertar");
        startActivity(intent);
    }
}
