package ic.zeus;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

import java.util.ArrayList;

public class BatteryReader {

    public ArrayList<Long> getReading(){
        ArrayList<Long> result = new ArrayList<>();
        BatteryManager mBatteryManager;
        mBatteryManager = (BatteryManager) Zeus.getAppContext().getSystemService(Context.BATTERY_SERVICE);
        Long energy = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER);
        Long current = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);
        Long current_now = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_AVERAGE);
        Long capacity = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        Long charge_counter = mBatteryManager.getLongProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER);


        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = Zeus.getAppContext().registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);


        float batteryPct = (level / (float)scale) * 100;

        //result.add(energy);
        result.add(current);
        //result.add(current_now);
        //result.add(capacity);
        //result.add(charge_counter);
        result.add((long) batteryPct);
        result.add((long) voltage);
        return result;
    }
}
