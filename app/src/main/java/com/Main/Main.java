package com.Main;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.*;


public class Main extends AppCompatActivity {

    private static int portnumber;
    private static String hostname, IPNodo;
    private Button btnConectar;
    private static final String debugString = "debug";
    Nodo nodo = new Nodo();
    Gson gson = new Gson();
    JsonObject jsonRecibido = new JsonObject();
    JSONObject jsonObject = new JSONObject();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        //Accion del boton
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hilo para cargar la coneccion al MeshManager
                new Thread() {
                    @Override
                    public void run() {
                        //Base 64
                        /*
                        Base
                        64
                        String testValue = "32515";
                        byte[] encodeValue = Base64.encode(testValue.getBytes(), Base64.DEFAULT);
                        byte[] decodeValue = Base64.decode(encodeValue, Base64.DEFAULT);
                        String encode, decode;
                        encode = new String(encodeValue);
                        decode = new String(decodeValue);

                        Log.i(debugString, "defaultValue = " + testValue);
                        Log.i(debugString, "encodeValue = " + encode);
                        Log.i(debugString, "decodeValue = " + decode);
                        */

                        EditText txtIPManager = (EditText) findViewById(R.id.txtIPManager);
                        EditText txtPuertoManager = (EditText) findViewById(R.id.txtPuertoManager);
                        EditText txtPuertoNodo = (EditText) findViewById(R.id.txtPuertoNodo);
                        EditText txtBytes = (EditText) findViewById(R.id.txtBytes);
                        EditText txtID = (EditText) findViewById(R.id.txtID);
                        EditText txtTel = (EditText) findViewById(R.id.txtTel);
                        IPNodo = obtenerIP();
                        portnumber = Integer.parseInt(txtPuertoManager.getText().toString());
                        hostname = txtIPManager.getText().toString();


                        /*
                        //Llenando datos del nodo
                        nodo.setNodoBytes(Integer.parseInt(txtBytes.getText().toString()));
                        nodo.setNodoID(txtID.getText().toString());
                        nodo.setNodoIP(IPNodo);
                        nodo.setNodoPort(Integer.parseInt(txtPuertoNodo.getText().toString()));
                        nodo.setNodoTel(Integer.parseInt(txtTel.getText().toString()));


                        String nodoJSON = gson.toJson(nodo);
                        Log.i(debugString, "El JSON ES:        !!!!!!!!!");
                        Log.i(debugString, nodoJSON);
                        */

                        Socket socket;
                        try {
                            Log.i(debugString, "Llenando el Json");
                            jsonObject.put("NodoTel", Integer.parseInt(txtTel.getText().toString()));
                            jsonObject.put("NodoID", txtID.getText().toString());
                            jsonObject.put("Bytes", Integer.parseInt(txtBytes.getText().toString()));
                            jsonObject.put("NodoPort", Integer.parseInt(txtPuertoNodo.getText().toString()));
                            jsonObject.put("NodoIP", IPNodo);
                            jsonObject.put("TipoAccion", 1);


                            Log.i(debugString, jsonObject.toString());


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            Log.i(debugString, "Tratando de conectar");

                            socket = new Socket(hostname, portnumber);
                            Log.i(debugString, "Coneccion establecida");


                            //Enviar json
                            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                            bw.write(jsonObject.toString());
                            bw.newLine();
                            bw.flush();

                            //
                            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                            Log.i(debugString, "Llega del Server:");
                            String recibido = br.readLine();
                            Log.i(debugString, recibido);



                        } catch (IOException e) {
                            Log.i(debugString, "Se cayó la conexion");
                        }
                    }

                }.start();

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private String obtenerIP() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> enumNetworkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            while (enumNetworkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = enumNetworkInterfaces
                        .nextElement();
                Enumeration<InetAddress> enumInetAddress = networkInterface
                        .getInetAddresses();
                while (enumInetAddress.hasMoreElements()) {
                    InetAddress inetAddress = enumInetAddress.nextElement();

                    if (inetAddress.isSiteLocalAddress()) {
                        ip = inetAddress.getHostAddress();
                    }

                }

            }

        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            ip += "Problemas de IP " + e.toString() + "\n";
        }

        return ip;
    }
    /*
    public static String cifrarBase64(String a) {
        Base64. encoder = Base64.getEncoder();
        String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
        return b;
    }

    public static String descifrarBase64(String a) {
        Base64 decoder = Base64.getDecoder();
        byte[] decodedByteArray = decoder.decode(a);

        String b = new String(decodedByteArray);
        return b;
    }
    */

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.Main/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.Main/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
