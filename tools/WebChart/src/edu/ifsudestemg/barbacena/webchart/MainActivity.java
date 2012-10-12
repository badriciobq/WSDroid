package edu.ifsudestemg.barbacena.webchart;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends Activity {
    private SharedPreferences prefs;
    private ImageView graph;
    final int WIDTH = 300;
    final int HEIGHT = 320;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		graph = (ImageView) findViewById(R.id.testy_img);
		prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());			
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		graph.setImageBitmap(plotALL());
		
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	
    	getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId())
    	{
    	case R.id.menu_settings:
    		startActivity(new Intent(this, Prefs.class));
    		
    		break;
    	case R.id.menu_exit:
    		finish();
    	
    		break;
    	case R.id.menu_about:
    		
    		break;
    	}
    	// TODO Auto-generated method stub
    	return super.onOptionsItemSelected(item);
    }

    public void updateALL(View view){
    	graph.setImageBitmap(plotALL());
    }
    
    public void updateHD(View view){
    	graph.setImageBitmap(plotHD());
    }
    
    public void updateMEM(View view){
    	graph.setImageBitmap(plotMEM());
    }
    
    public void updateCPU(View view){
    	graph.setImageBitmap(plotCPU());
    }
    
    
    private Bitmap plotALL(){

    	MakeGraph m = new MakeGraph();
    	Bitmap img = m.graph(WIDTH, HEIGHT);
    	
    	try {

    		HttpQuery query = new HttpQuery(getUsuario(), getSenha());
    		String resultado = query.queryURL(getUrl());

    		JSONObject jsonObj = new JSONObject(resultado);

    		int status = jsonObj.getInt("status");

    		if(status == 200){

    			ArrayList<Float> dados = new ArrayList<Float>();
    			ArrayList<String> label = new ArrayList<String>();
    			
    			JSONObject jsonHD = (JSONObject) jsonObj.get("hardDisk");

    			Iterator<String> it = jsonHD.keys();
    			while(it.hasNext()){
    				String t = it.next();
    				label.add(t);
    				dados.add(Float.valueOf(((JSONObject) jsonHD.get(t)).getString("percent")));
    				
    				Log.d(HttpQuery.MY_APP_TAG, "Label: " + t + " data: " + Float.valueOf(((JSONObject) jsonHD.get(t)).getString("percent")));
    			}

    			JSONObject jsonMem = (JSONObject) jsonObj.get("memory");
    			label.add("Memória");
    			dados.add(Float.parseFloat(jsonMem.getString("percent")));

    			JSONObject jsonPc = (JSONObject) jsonObj.get("cpu"); 
    			label.add("Processador");
    			dados.add(Float.parseFloat(jsonPc.getString("percent")));
    				
    			m.drawBarData(img, WIDTH, HEIGHT, dados, label);
    		
    		}else if(status == 401)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    		else if (status == 404)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();

    	} catch (NullPointerException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL Não encontrada");

    	} catch (IllegalArgumentException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL mal formatada");
    	}

    	return img;
    }

    
    
    private Bitmap plotHD(){

    	MakeGraph m = new MakeGraph();
    	Bitmap img = m.graph(WIDTH, HEIGHT);
    	
    	try {

    		HttpQuery query = new HttpQuery(getUsuario(), getSenha());
    		String resultado = query.queryURL(getUrl());

    		JSONObject jsonObj = new JSONObject(resultado);

    		int status = jsonObj.getInt("status");

    		if(status == 200){

    			ArrayList<Float> dados = new ArrayList<Float>();
    			ArrayList<String> label = new ArrayList<String>();
    			
    			JSONObject jsonHD = (JSONObject) jsonObj.get("hardDisk");

    			Iterator<String> it = jsonHD.keys();
    			while(it.hasNext()){
    				String t = it.next();
    				label.add(t);
    				dados.add(Float.valueOf(((JSONObject) jsonHD.get(t)).getString("percent")));
    				
    				Log.d(HttpQuery.MY_APP_TAG, "Label: " + t + " data: " + Float.valueOf(((JSONObject) jsonHD.get(t)).getString("percent")));
    			}

    			m.drawBarData(img, WIDTH, HEIGHT, dados, label);
    			
    			/*
    			JSONObject jsonMem = (JSONObject) jsonObj.get("memory");
    			Log.i(HttpQuery.MY_APP_TAG, "Memória: " + jsonMem.get("percent"));

    			JSONObject jsonPc = (JSONObject) jsonObj.get("cpu"); 
    			Log.i(HttpQuery.MY_APP_TAG, "Processador: " + jsonPc.get("percent"));
    			*/


    		}else if(status == 401)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    		else if (status == 404)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();

    	} catch (NullPointerException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL Não encontrada");

    	} catch (IllegalArgumentException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL mal formatada");
    	}

    	return img;
    }

    
    private Bitmap plotMEM(){

    	MakeGraph m = new MakeGraph();
    	Bitmap img = m.graph(WIDTH, HEIGHT);
    	
    	try {

    		HttpQuery query = new HttpQuery(getUsuario(), getSenha());
    		String resultado = query.queryURL(getUrl());

    		JSONObject jsonObj = new JSONObject(resultado);

    		int status = jsonObj.getInt("status");

    		if(status == 200){

    			ArrayList<Float> dados = new ArrayList<Float>();
    			ArrayList<String> label = new ArrayList<String>();
    			
    			JSONObject jsonMem = (JSONObject) jsonObj.get("memory");
    			Log.i(HttpQuery.MY_APP_TAG, "Memória: " + jsonMem.get("percent"));
    			label.add("Memória");
    			dados.add(Float.parseFloat(jsonMem.getString("percent")));
    			
    			m.drawBarData(img, WIDTH, HEIGHT, dados, label);

    		}else if(status == 401)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    		else if (status == 404)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();

    	} catch (NullPointerException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL Não encontrada");

    	} catch (IllegalArgumentException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL mal formatada");
    	}

    	return img;
    }
    

    private Bitmap plotCPU(){

    	MakeGraph m = new MakeGraph();
    	Bitmap img = m.graph(WIDTH, HEIGHT);
    	
    	try {

    		HttpQuery query = new HttpQuery(getUsuario(), getSenha());
    		String resultado = query.queryURL(getUrl());

    		JSONObject jsonObj = new JSONObject(resultado);

    		int status = jsonObj.getInt("status");

    		if(status == 200){

    			ArrayList<Float> dados = new ArrayList<Float>();
    			ArrayList<String> label = new ArrayList<String>();
    			

    			JSONObject jsonPc = (JSONObject) jsonObj.get("cpu"); 
    			Log.i(HttpQuery.MY_APP_TAG, "Processador: " + jsonPc.get("percent"));
    			label.add("Processador");
    			dados.add(Float.parseFloat(jsonPc.getString("percent")));

    			m.drawBarData(img, WIDTH, HEIGHT, dados, label);

    		}else if(status == 401)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    		else if (status == 404)
    			Log.i(HttpQuery.MY_APP_TAG, "Mensagem: " + jsonObj.get("message"));

    	} catch (JSONException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();

    	} catch (NullPointerException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL Não encontrada");

    	} catch (IllegalArgumentException e) {
    		Log.e(HttpQuery.MY_APP_TAG, "URL mal formatada");
    	}

    	return img;
    }
    
    
    private String getUrl(){
		String url = prefs.getString("url", "");
		String porta = prefs.getString("porta", "8001");
		return url + ":" + porta;
    }
    
    private String getUsuario(){
		String usuario = prefs.getString("usuario", "");
		return usuario;
    }
    
    private String getSenha(){
		String senha = prefs.getString("senha", "");
		return senha;
    }
}
