package edu.ifsudestemg.barbacena.webchart;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Log;


public class HttpQuery {
	final static String MY_APP_TAG = "edu.ifsudestemg.barbacena";
	private String usuario;
	private String senha;
	
	public HttpQuery() {
	}
	
	public HttpQuery(String usuario, String senha) {
		super();
		this.usuario = usuario;
		this.senha = senha;
	}

	public String queryURL(String url){
		HttpClient client;
	
		client = new DefaultHttpClient();
		Credentials creds = new UsernamePasswordCredentials(getUsuario(), getSenha());
		((AbstractHttpClient) client).getCredentialsProvider().setCredentials( new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);

		HttpGet httpget = new HttpGet(url);

		Log.i(MY_APP_TAG, "requisição: " + httpget.getURI());
		HttpResponse response;

		try {
			response = client.execute(httpget);

			Log.i(MY_APP_TAG, "------------------------------------");
			Log.i(MY_APP_TAG, String.valueOf(response.getStatusLine()));
			Log.i(MY_APP_TAG, "-------------------------------------");

			HttpEntity entity = response.getEntity();

			if(entity != null){
				Log.i(MY_APP_TAG, "Lendo Resposta...");
				InputStream in = entity.getContent();
				String texto = readString(in);
				return texto;
			}

		} catch (ClientProtocolException e) {
			Log.e(MY_APP_TAG, "Erro de protocolo");
			
		} catch (IOException e) {
			Log.e(MY_APP_TAG, "Erro no meio de comunicação");
		}
		
		return null;
	}

	private String readString(InputStream in) throws IOException{
		byte[] bytes = readBytes(in);
		String texto = new String(bytes);

		return texto;
	}

	private byte[] readBytes(InputStream in) throws IOException{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try{
			byte[] buf = new byte[1024];
			int len;

			while((len = in.read(buf)) > 0){
				bos.write(buf, 0 , len);
			}
			byte[] bytes = bos.toByteArray();
			return bytes;

		}finally{
			bos.close();
		}
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
