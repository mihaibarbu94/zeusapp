package ic.zeus.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ic.zeus.BatteryReader;
import ic.zeus.DeviceNameReader;
import ic.zeus.MainActivity;
import ic.zeus.R;
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
        String deviceName = DeviceNameReader.getDeviceName();
        String dataToSend = "";
//        try {
//             dataToSend= prepareDataToSend1(deviceName, batteryReader.getReading()).toString();
//        } catch (JSONException e) {
//            Log.e("Home Fragment", "ERROR", e);
//        }
        dataToSend= prepareDataToSend2(deviceName, batteryReader.getReading()).toString();
//        new MailSender().execute(dataToSend);
//        Log.d(DEBUG, "MAIL SENT!!!!!!!!!!!!!!!!!!!!!!!");

        String[] payload = new String[]{"192.168.1.137", "8084", dataToSend};
        new ServerCommunicator().execute(payload);
        Log.d(DEBUG, "Payload sent to server!!!!!!!!!!!!!!!!!!!!!!!");
    }

    private String prepareDataToSend(String deviceName, ArrayList<Long> data){
        String output = "[";
        output += "\"" + deviceName + "\",";
        for(int i = 0; i < data.size(); i++){
            output += "\"" + data.get(i);
            if (i != data.size() - 1){
                output += "\",";
            } else {
                output += "\"";
            }
        }

        output += "]";

        return output;
    }

    private JSONObject prepareDataToSend1(String deviceName, ArrayList<Long> data) throws JSONException {
        JSONObject object = new JSONObject();
        object.put(deviceName, new JSONArray(data));

        return object;
    }

    private JSONArray prepareDataToSend2(String deviceName, ArrayList<Long> data){
        ArrayList<String> dataToString = new ArrayList<>();
        dataToString.add(deviceName);
        for(Long l: data){
            dataToString.add(String.valueOf(l));
        }
        return new JSONArray(dataToString);
    }
}


