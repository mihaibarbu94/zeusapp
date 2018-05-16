package ic.zeus;

import android.app.Application;
import android.content.Context;

public class Zeus extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Zeus.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Zeus.context;
    }
}
