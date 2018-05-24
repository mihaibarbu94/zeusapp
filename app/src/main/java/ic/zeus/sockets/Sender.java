package ic.zeus.sockets;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ic.zeus.BatteryReader;
import ic.zeus.DeviceNameReader;

public class Sender {

    private static final String DEBUG = "Sender";
    public static String IP_ADRESS = "192.168.1.137";

    public void sendData() {
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

        // imperial 146.169.183.138
        Log.d(DEBUG, dataToSend);
        String[] payload = new String[]{IP_ADRESS, "8084", dataToSend};
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
