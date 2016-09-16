package com.Main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class Main extends AppCompatActivity {

    private static  int portnumber = 1101;
    private static  String hostname = "192.168.0.114";
    private Button btnConectar;
    private static final String debugString = "debug";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Accion del boton
        btnConectar = (Button) findViewById(R.id.btnConectar);
        btnConectar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                new Thread() {
                    @Override
                    public void run() {
                        EditText txtIPManager = (EditText) findViewById(R.id.txtIPManager);
                        EditText txtPuertoManager = (EditText) findViewById(R.id.txtPuertoManager);
                        EditText txtIPNodo = (EditText) findViewById(R.id.txtPuertoNodo);
                        EditText txtBytes = (EditText) findViewById(R.id.txtBytes);
                        EditText txtID = (EditText) findViewById(R.id.txtID);
                        EditText txtTel = (EditText) findViewById(R.id.txtTel);
                        TextView lblConec = (TextView) findViewById(R.id.lblConeccion);
                        portnumber = Integer.parseInt(txtPuertoManager.getText().toString());
                        hostname = txtIPManager.getText().toString();
                        ;

                        JSONObject jsonObject = new JSONObject();
                        Socket socket = null;
                        try {
                            jsonObject.put("NodoTel", Integer.parseInt(txtTel.getText().toString()));
                            jsonObject.put("NodoID", txtID.getText().toString());
                            jsonObject.put("Bytes", Integer.parseInt(txtBytes.getText().toString()));
                            jsonObject.put("IPNodo", txtIPNodo.getText().toString());

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
                            Log.i(debugString, "llega al reader");
                            Log.i(debugString, br.readLine());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }.start();
            }
        });

    }
}
