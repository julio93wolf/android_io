package julio.socket;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

public class SocketActivity extends AppCompatActivity {

    public  Switch attrSwitch1, attrSwitch2;
    private SocketClient objSocketClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        attrSwitch1 = findViewById(R.id.switch1);
        attrSwitch2 = findViewById(R.id.switch2);

        objSocketClient = new SocketClient(this);

        attrSwitch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objSocketClient.emmitClientChecked();
            }
        });

        attrSwitch2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objSocketClient.emmitClientChecked();
            }
        });
    }
}
