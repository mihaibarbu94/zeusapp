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
public class ExampleInstrumentedTest {
    private static final String TAG = "@@MyClassNameHere@@";
    private static final long DEFAULT_TIMEOUT = 1000;
    private UiDevice mDevice;

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
    private void culebraGeneratedTest(int times) throws Exception {
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

        assert(times > 5);
        while(times != 0) {
            mDevice.swipe(500,1300,500,400,50);
            mDevice.waitForIdle(1000);
            times--;
        }

        mDevice.waitForIdle(1000);

        Sender sender = new Sender();
        sender.sendData();
    }

    @Test
    public void test() throws Exception {
        culebraGeneratedTest(5);
    }

}
