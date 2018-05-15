package julio.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

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
                socket_init();
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
                    Log.d("EmitSwitch1", obj.toString());
                } catch (Exception e) {
                    Log.d("ErrorSwitch1", e.toString());
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
                    Log.d("EmitSwitch2", obj.toString());
                } catch (Exception e) {
                    System.out.println(e.toString());
                    Log.d("ErrorSwitch2", e.toString());
                }
            }
        });
    }

    private void socket_init () {
        try {
            String uri = Host.getText() + ":" + Port.getText();
            socket = IO.socket(URI.create(uri));
            socket.on("server_checked", serverChecked);
            socket.connect();
            Toast.makeText(getApplicationContext(),"Connected: " + uri,Toast.LENGTH_LONG).show();
            Log.d("SocketInit","Connected!");
        } catch (Exception e) {
            Log.d("ErrorSocketInit", e.toString());
        }
    }

    private Emitter.Listener serverChecked = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject obj = (JSONObject)args[0];
                        Switch_1.setChecked(obj.getBoolean("checked_1"));
                        Switch_2.setChecked(obj.getBoolean("checked_2"));
                        Log.d("ServerChecked", obj.toString());
                    } catch (Exception e) {
                        Log.d("ErrorServerChecked", e.toString());
                    }
                }
            });
        }
    };
}
