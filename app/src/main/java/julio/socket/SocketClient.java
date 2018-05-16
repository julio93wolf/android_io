package julio.socket;

import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import java.net.URI;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class SocketClient {

    private Socket socket;
    private SocketActivity objSocketActivity;

    public SocketClient(SocketActivity paramSocketActivity) {

        objSocketActivity = paramSocketActivity;

        try {
            String uri = "https://juliosocketapp.herokuapp.com:443";
            socket = IO.socket(URI.create(uri));
            socket.on("server_checked", onServerChecked);
            socket.connect();
            Toast.makeText(
                    objSocketActivity.getApplicationContext(),
                    "Connected: " + uri,
                    Toast.LENGTH_LONG
            ).show();
            Log.d("SocketInit","Connected!");
        } catch (Exception e) {
            Log.d("ErrorSocketInit", e.toString());
        }
    }

    private Emitter.Listener onServerChecked = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            objSocketActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        JSONObject obj = (JSONObject)args[0];
                        objSocketActivity.attrSwitch1.setChecked(obj.getBoolean("checked_1"));
                        objSocketActivity.attrSwitch2.setChecked(obj.getBoolean("checked_2"));
                        Log.d("ServerChecked", obj.toString());
                    } catch (Exception e) {
                        Log.d("ErrorServerChecked", e.toString());
                    }
                }
            });
        }
    };

    public void emmitClientChecked () {
        try {
            JSONObject obj = new JSONObject();
            obj.put("checked_1", objSocketActivity.attrSwitch1.isChecked());
            obj.put("checked_2", objSocketActivity.attrSwitch2.isChecked());
            socket.emit("client_checked",obj);
            Log.d("EmitSwitch1", obj.toString());
        } catch (Exception e) {
            Log.d("ErrorSwitch1", e.toString());
        }
    }
}
