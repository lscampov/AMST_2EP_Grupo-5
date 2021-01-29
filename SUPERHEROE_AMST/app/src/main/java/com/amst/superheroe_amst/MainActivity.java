package com.amst.superheroe_amst;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    EditText nombreSuperheroe;
    ArrayList<Heroe> nombres=new ArrayList<>();
    RequestQueue queue = null;
    String token = "3811291872251089";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreSuperheroe= findViewById(R.id.editPersonName);
        queue= Volley.newRequestQueue(getApplicationContext());
        context=this;
    }

    public void buscarHeroe(View view) {

        if (nombreSuperheroe.getText().toString().length()>=3){
            String url="https://www.superheroapi.com/api.php/"+token+"/search/"+nombreSuperheroe.getText().toString();
            JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray myJsonArray = response.getJSONArray("results");
                        for (int i=0;i<myJsonArray.length();i++){
                            JSONObject myObject=myJsonArray.getJSONObject(i);
                            String id=myObject.getString("id");
                            String alterego=myObject.getJSONObject("biography").getString("full-name");
                            String nombre=myObject.getString("name");
                            HashMap habilidades=new HashMap();
                            habilidades.put("Inteligencia",myObject.getJSONObject("powerstats").getString("intelligence"));
                            habilidades.put("Fuerza",myObject.getJSONObject("powerstats").getString("strength"));
                            habilidades.put("Velocidad",myObject.getJSONObject("powerstats").getString("speed"));
                            habilidades.put("Durabilidad",myObject.getJSONObject("powerstats").getString("durability"));
                            habilidades.put("Poder",myObject.getJSONObject("powerstats").getString("power"));
                            habilidades.put("Combate",myObject.getJSONObject("powerstats").getString("combat"));
                            Heroe heroes=new Heroe(id,alterego,nombre,habilidades);
                            nombres.add(heroes);
                            System.out.println(heroes);
                        }
                        Intent intent=new Intent(context,InfoNombres.class);
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("Superheroes",nombres);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        System.out.println("Error "+e);
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Error: "+error);
                }
            });
            queue.add(request);
        }
        else{
            Toast.makeText(this,"Ingrese minimo 3 letras.",Toast.LENGTH_SHORT).show();
            nombreSuperheroe.setText("");
        }
    }
}
