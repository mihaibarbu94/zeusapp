package ic.zeus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ic.zeus.BatteryReader;
import ic.zeus.MainActivity;
import ic.zeus.R;
import ic.zeus.mail_manager.MailSender;
import ic.zeus.sockets.ServerCommunicator;

public class HomeFragment extends Fragment {
    private Button sendData;
    private View view;
    private static final String DEBUG = "File Parsing";

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

        sendData = (Button) view.findViewById(R.id.send_data);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendData();
            }
        });
    }

    private void sendData() {
        BatteryReader batteryReader = new BatteryReader();
        String reading = "" + batteryReader.getReading();
        new MailSender().execute(reading);
        Log.d(DEBUG, "MAIL SENT!!!!!!!!!!!!!!!!!!!!!!!");

        String[] payload = new String[]{"192.168.1.137", "8084", reading};
        new ServerCommunicator().execute(payload);
        Log.d(DEBUG, "Payload sent to server!!!!!!!!!!!!!!!!!!!!!!!");
    }
}


