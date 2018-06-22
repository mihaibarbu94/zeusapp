package ic.zeus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import ic.zeus.MainActivity;
import ic.zeus.R;
import ic.zeus.sockets.Sender;

public class HomeFragment extends Fragment {
    private Button sendData;
    private View view;
    Button updateIp;
    EditText ip_editor;
    private static final String DEBUG = "Home Fragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;

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
        ip_editor.setText(Sender.IP_ADDRESS);
        updateIp.setOnClickListener(
                new View.OnClickListener()
                {
                    public void onClick(View view)
                    {
                        ip_editor.setText(ip_editor.getText().toString());
                        Sender.IP_ADDRESS = ip_editor.getText().toString();
                        Log.v("EditText", ip_editor.getText().toString());
                    }
                });
    }
}


