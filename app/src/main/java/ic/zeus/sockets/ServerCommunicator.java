package ic.zeus.sockets;

import android.os.AsyncTask;
import android.util.Log;

public class ServerCommunicator extends AsyncTask<String, Void, Void> {

    private Exception exception;
    @Override
    protected Void doInBackground(String... urls) {
        try {
            Log.e("Deb", "ServerCommunicator");
            ClientThread clientThread = new ClientThread(urls[0], Integer.parseInt(urls[1]), urls[2]);
            clientThread.run();
            Log.e("deb", "Sent Mail!");
        } catch (Exception e) {
            this.exception = e;
            Log.e("deb", "Sent Mail!" + e);

            return null;
        }

        return null;
    }
}
