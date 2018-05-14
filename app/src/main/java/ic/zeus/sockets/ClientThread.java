package ic.zeus.sockets;

import android.util.Log;

import java.io.DataOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread implements Runnable {

    private final String TAG = "ClientActivity";
    private String serverIpAddress;
    private int serverPort;
    private String data;
    public static boolean connected = false;
    private String receivedMessage;
    public static Socket clientSocket;
    public static InetAddress serverAddr;


    public ClientThread(String serverIpAddress, int serverPort, String data) {
        this.serverIpAddress = serverIpAddress;
        this.serverPort      = serverPort;
        this.data            = data;
    }

    public void run() {
        try {
            serverAddr = InetAddress.getByName(serverIpAddress);
            Log.d(TAG, "C: Connecting...");
            Log.d(TAG, "C: Connecting..." + serverPort);
            Log.d(TAG, "C: Connecting..." + serverIpAddress);
            clientSocket = new Socket(serverAddr, serverPort);
           // clientSocket.setReuseAddress(true);
            Log.d(TAG, "Connecting to" + serverIpAddress + "PORT:" + serverPort);
//            Socket socket = new Socket();
//            InetSocketAddress socketAddress = new InetSocketAddress(serverIpAddress, ServerThread.SERVERPORT);
//            socket.connect(socketAddress);
            Log.d(TAG, "C: Connecting..." + clientSocket.getInetAddress());
            Log.d(TAG, "C: Connecting..." + clientSocket.getLocalPort());

            connected = true;

            try {
                Log.d(TAG, "C: Reading command.");

//                    DataInputStream DIS = new DataInputStream(clientSocket.getInputStream());
//                    receivedMessage     = DIS.readUTF();
//                    Log.d(TAG, "RECEIVED MESSAGE: " + receivedMessage);

                DataOutputStream DOS = new DataOutputStream(clientSocket.getOutputStream());
                DOS.writeUTF(data);
                Log.d(TAG, "SENT MESSAGE: " + data);

            } catch (Exception e) {
                Log.e(TAG, "S: Error connection", e);
            }

            clientSocket.close();
            Log.d("ClientActivity", "C: Closed.");
        } catch (Exception e) {
            Log.e("ClientActivity", "C: Error creating socket", e);
            e.printStackTrace();
            connected = false;
        }
    }
}
