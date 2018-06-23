package ic.zeus.sockets;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import ic.zeus.BatteryReader;
import ic.zeus.DeviceNameReader;
import ic.zeus.Zeus;

public class Sender {

    private static final String DEBUG = "Sender";
    //public static String IP_ADDRESS = "192.168.1.20";
    private static String serverIp;
    public static String serverPort = "8084";

    public String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Log.i("IP ADDRESSSSS", ret);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    public void sendData() {
        serverIp = readFromFile(Zeus.getAppContext());
        BatteryReader batteryReader = new BatteryReader();
        String deviceName = DeviceNameReader.getDeviceName();

        String dataToSend= prepareDataToSend2(deviceName, batteryReader.getReading()).toString();
        Log.d(DEBUG, dataToSend);
        String[] payload = new String[]{serverIp, serverPort, dataToSend};
        new ServerCommunicator().execute(payload);
        Log.d(DEBUG, "Payload sent to server!!!!!!!!!!!!!!!!!!!!!!!");
    }

    public void sendTestInfo(String browser, String time) {
        serverIp = readFromFile(Zeus.getAppContext());
        BatteryReader batteryReader = new BatteryReader();
        String deviceName = DeviceNameReader.getDeviceName();
        String dataToSend = "";
//        try {
//             dataToSend= prepareDataToSend1(deviceName, batteryReader.getReading()).toString();
//        } catch (JSONException e) {
//            Log.e("Home Fragment", "ERROR", e);
//        }
        dataToSend= prepareDataToSend3(deviceName, browser, time, batteryReader.getReading()).toString();
//        new MailSender().execute(dataToSend);
//        Log.d(DEBUG, "MAIL SENT!!!!!!!!!!!!!!!!!!!!!!!");

        // imperial 146.169.183.138
        Log.d(DEBUG, dataToSend);
        String[] payload = new String[]{serverIp, serverPort, dataToSend};
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

    private JSONArray prepareDataToSend3(String deviceName, String browser, String time,  ArrayList<Long> data){
        ArrayList<String> dataToString = new ArrayList<>();
        dataToString.add(deviceName);
        dataToString.add(browser);
        dataToString.add(time);
        for(Long l: data){
            dataToString.add(String.valueOf(l));
        }
        return new JSONArray(dataToString);
    }
}
