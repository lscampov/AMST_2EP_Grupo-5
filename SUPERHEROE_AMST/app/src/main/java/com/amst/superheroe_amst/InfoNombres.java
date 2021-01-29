package com.amst.superheroe_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class InfoNombres extends AppCompatActivity {

    ListView listView;
    ArrayList<Heroe> heroes = new ArrayList<>();
    ArrayList<String> datos =new ArrayList<>();
    Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_nombres);
        listView=(ListView)findViewById(R.id.ListView);

        ArrayAdapter adaptador= new ArrayAdapter(this,android.R.layout.simple_list_item_1, datos);
        listView.setAdapter(adaptador);
        obtenerInfo();
        context=this;
        TextView tvResultado=findViewById(R.id.tv_resultado);
        tvResultado.setText(tvResultado.getText().toString()+" "+ datos.size());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(context,InfoHeroe.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("Superheroes", heroes.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
            }
        });
    }

    public void regresar(View v){
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
    private void obtenerInfo(){
        Bundle objetoEnviado= getIntent().getExtras();
        if(objetoEnviado!=null){
            heroes = (ArrayList<Heroe>) objetoEnviado.getSerializable("Superheroes");
        }
        obtenerDatos();
    }
    private void obtenerDatos(){
        for(Heroe heroe: heroes){
            datos.add(heroe.nombre);
        }
    }

}