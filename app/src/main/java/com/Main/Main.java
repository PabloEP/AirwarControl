package com.Main;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.*;


public class Main extends AppCompatActivity implements SensorEventListener {

    private static int portnumber = 1101;
    private static String hostname = "192.168.0.113"; //<---------------------------------------------------------------PONER LA NUEVA IP
    private String horizontal, vertical, accion;
    private TextView PuntosText, MemoriaText, NivelText, HorText, VerText, ActText;
    private Sensor mySensor;
    private SensorManager SM;
    private int disparo = 0;
    private Button btnConectar;
    private static final String debugString = "debug";
    Nodo nodo = new Nodo();
    Gson gson = new Gson();
    JSONObject jsonObject = new JSONObject();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Creador del Manejador del Sensor
        SM = (SensorManager)getSystemService(SENSOR_SERVICE);

        //Creador del sensor acelerometro
        mySensor = SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        //Registrar Sensor Listener
        SM.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);

        //Asignar text
        PuntosText = (TextView)findViewById(R.id.txtPuntos);
        MemoriaText = (TextView)findViewById(R.id.txtMemoria);
        NivelText = (TextView)findViewById(R.id.txtNivel);
        HorText = (TextView)findViewById(R.id.txtHorizontal);
        VerText = (TextView)findViewById(R.id.txtVertical);
        ActText = (TextView)findViewById(R.id.txtAccion);
        System.out.println("========================================================================================================================================================================");

        //Accion del boton
        btnConectar = (Button) findViewById(R.id.btnShoot);
        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hilo para cargar la coneccion al MeshManager
                new Thread() {
                    @Override
                    public void run() {
                        disparo += 1;
                        Socket socket;

                        try {
                            Log.i(debugString, "Llenando el Json");
                            jsonObject.put("Horizontal", null);
                            jsonObject.put("Vertical", null);
                            jsonObject.put("Accion", "Disparando");


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
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
    @Override
    public void onSensorChanged(SensorEvent event){
        if( event.values[0] > 2){
            horizontal = "IZQUIERDA";
            new Thread() {
                @Override
                public void run() {
                    Socket socket;

                    try {
                        Log.i(debugString, "Llenando el Json");
                        jsonObject.put("Horizontal", horizontal);
                        jsonObject.put("Vertical", vertical);
                        jsonObject.put("Accion", null);


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
        }else if(event.values[0] < -2){
            horizontal = "DERECHA" ;
            new Thread() {
                @Override
                public void run() {
                    Socket socket;

                    try {
                        Log.i(debugString, "Llenando el Json");
                        jsonObject.put("Horizontal", horizontal);
                        jsonObject.put("Vertical", vertical);
                        jsonObject.put("Accion", null);


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
        if(event.values[1] > 2){
            vertical = "ABAJO";
            new Thread() {
                @Override
                public void run() {
                    Socket socket;

                    try {
                        Log.i(debugString, "Llenando el Json");
                        jsonObject.put("Horizontal", horizontal);
                        jsonObject.put("Vertical", vertical);
                        jsonObject.put("Accion", null);


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
        }else if(event.values[1] < -2 ){
            vertical = "ARRIBA";
            new Thread() {
                @Override
                public void run() {
                    Socket socket;

                    try {
                        Log.i(debugString, "Llenando el Json");
                        jsonObject.put("Horizontal", horizontal);
                        jsonObject.put("Vertical", vertical);
                        jsonObject.put("Accion", null);


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


        //FUERA DEL THREAD
        ActText.setText("Accion: Disparo " + disparo);
        HorText.setText("Horizontal: " + horizontal);
        VerText.setText("Vertical: " + vertical);
        if( -2 < event.values[0] & event.values[0] < 2 ){
            horizontal = null;
        }
        if(-2 < event.values[1] & event.values[1] < 2 ){
            vertical = null;
        }
    }


}
