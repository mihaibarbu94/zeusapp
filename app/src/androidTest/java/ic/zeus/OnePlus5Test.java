package ic.zeus;
/*************************************************************************
 * CulebraTester (C) 2015-2016 Diego Torres Milano
 *
 * <p/>
 ************************************************************************/

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Pattern;

import ic.zeus.sockets.Sender;

/**
 * @@Test comment here@@
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class OnePlus5Test {
    private static final String TAG = "@@MyClassNameHere@@";
    private static final long DEFAULT_TIMEOUT = 1000;
    private UiDevice mDevice;
    private long currentTime;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(instrumentation);
    }

    /**
     * @@Test comment here@@
     *
     * @throws Exception
     */
    private void culebraGeneratedTest() throws Exception {
        mDevice.pressHome();
        mDevice.click(559, 1708);
        mDevice.findObject(By.res("net.oneplus.launcher:id/all_apps_handle")
                .clazz("android.widget.ImageView")
                .text(Pattern.compile(""))
                .pkg("net.oneplus.launcher"))
                .clickAndWait(Until.newWindow(), DEFAULT_TIMEOUT);

        (new UiScrollable(new UiSelector().className("android.support.v7.widget.RecyclerView")
                .resourceId("net.oneplus.launcher:id/apps_list_view")
                .index(2)
                .packageName("net.oneplus.launcher")))
                .getChildByDescription(new UiSelector().description(
                        "Chrome"), "Chrome", true).click();
    }

    private boolean timeIsOut(int minutesUntilTestIsFinished) {
        long remainingTime = minutesUntilTestIsFinished * 60 - (System.currentTimeMillis() - currentTime) / 1000;
        Log.e("TIME", "Remaining time:" + remainingTime);
        return remainingTime <= 0;
    }

    private void swipeAndWait(int times){
        while(times != 0) {
            mDevice.swipe(500,1500,500,300,50);
            mDevice.waitForIdle(1000);
            times--;
        }
    }

    @Test
    public void test() throws Exception {
        currentTime = System.currentTimeMillis();
        culebraGeneratedTest();

        int times = 70;
        while(times != 0 && !timeIsOut(30)) {
            swipeAndWait(60);
            times--;
        }

        mDevice.waitForIdle(1000);

        Sender sender = new Sender();
        sender.sendData();
    }

}
