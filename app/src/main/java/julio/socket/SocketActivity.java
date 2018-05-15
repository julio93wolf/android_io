package julio.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import org.json.JSONObject;

import java.net.URI;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketActivity extends AppCompatActivity {

    public EditText Host,Port;
    public Switch Switch_1,Switch_2;
    public Button Connect;

    public Socket socket;

    public Boolean sw1_status,sw2_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        Host = (EditText) findViewById(R.id.host);
        Port = (EditText) findViewById(R.id.port);
        Switch_1 = (Switch) findViewById(R.id.switch1);
        Switch_2 = (Switch) findViewById(R.id.switch2);
        Connect = (Button) findViewById(R.id.conection);
        sw1_status = false;
        sw2_status = false;



        Connect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // socket_init();
                String uri = "http://"+Host.getText()+":"+Port.getText();
                Log.d("URI: ", uri);
                socket = IO.socket(URI.create(uri));

                socket.on("server_checked", new Emitter.Listener() {
                    @Override
                    public void call(Object... args) {
                        try {
                            JSONObject obj = (JSONObject)args[0];
                            Log.d("OnChecked: ", "" + obj.getBoolean("checked_1"));
                            Log.d("OnChecked: ", "" + obj.getBoolean("checked_2"));

                            sw1_status = obj.getBoolean("checked_1");
                            sw2_status = obj.getBoolean("checked_2");

                            Switch_1.setChecked(sw1_status);
                            Switch_2.setChecked(sw2_status);

                        } catch (Exception e) {
                            System.out.println(e.toString());
                            Log.d("Error: ", e.toString());
                        }
                    }
                });

                socket.connect();
                Log.d("Socket: ", socket.toString());
            }
        });

        Switch_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("checked_1", Switch_1.isChecked());
                    obj.put("checked_2", Switch_2.isChecked());
                    socket.emit("client_checked",obj);
                    Log.d("Emit: ", obj.toString());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    Log.d("Error: ", e.toString());
                }
            }
        });

        Switch_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JSONObject obj = new JSONObject();
                    obj.put("checked_1", Switch_1.isChecked());
                    obj.put("checked_2", Switch_2.isChecked());
                    socket.emit("client_checked",obj);
                    Log.d("Emit: ", obj.toString());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    Log.d("Error: ", e.toString());
                }
            }
        });
    }

    private void socket_init () {
        String uri = "http://"+Host.getText()+":"+Port.getText();
        Log.d("URI: ", uri);
        socket = IO.socket(URI.create(uri));

        socket.on("server_checked", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                try {
                    JSONObject obj = (JSONObject)args[0];
                    Log.d("OnChecked: ", "" + obj.getBoolean("checked_1"));
                    Log.d("OnChecked: ", "" + obj.getBoolean("checked_2"));
                    sw1_status = obj.getBoolean("checked_1");
                    sw2_status = obj.getBoolean("checked_2");

                    Switch_1.setChecked(sw1_status);
                    Switch_2.setChecked(sw2_status);

                } catch (Exception e) {
                    System.out.println(e.toString());
                    Log.d("Error: ", e.toString());
                }
            }
        });

        socket.connect();
        Log.d("Socket: ", socket.toString());
    }
}
