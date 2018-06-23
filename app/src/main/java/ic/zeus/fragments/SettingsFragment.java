package ic.zeus.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.OutputStreamWriter;

import ic.zeus.MainActivity;
import ic.zeus.R;
import ic.zeus.Zeus;
import ic.zeus.sockets.Sender;

public class SettingsFragment extends Fragment {
    private Button sendData;
    private View view;
    Button updateIp;
    Button updatePort;
    EditText ip_editor;
    EditText port_editor;
    private static final String DEBUG = "Home Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;

    }

    private void writeToFile(String data, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        if (((MainActivity) getActivity()).getSupportActionBar() != null) {
            ((MainActivity) getActivity()).getSupportActionBar().show();
        }

        view = getView();

        sendData = view.findViewById(R.id.send_data);
        final Sender sender = new Sender();
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sender.sendTestInfo("Test browser", "10");
            }
        });

        updateIp = view.findViewById(R.id.update_ip);
        ip_editor = view.findViewById(R.id.edit_ip);
        String serverIp = sender.readFromFile(Zeus.getAppContext());
        ip_editor.setText(serverIp);
        updateIp.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ip_editor.setText(ip_editor.getText().toString());
                        writeToFile(ip_editor.getText().toString(), Zeus.getAppContext());
                        Log.v("EditText", ip_editor.getText().toString());
                    }
                });

        updatePort = view.findViewById(R.id.update_port);
        port_editor = view.findViewById(R.id.edit_port);
        port_editor.setText(Sender.serverPort);
    }
}


