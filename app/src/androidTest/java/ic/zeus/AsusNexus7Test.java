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
import android.support.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @@Test comment here@@
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AsusNexus7Test {
    private static final String TAG = "@@MyClassNameHere@@";
    private static final long DEFAULT_TIMEOUT = 1000;
    private UiDevice mDevice;

    @Before
    public void setUp() {
        // Initialize UiDevice instance
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(instrumentation);
    }

    @Test
    public void culebraGeneratedTest() {
        mDevice.click(596, 1877);
        mDevice.pressHome();
        // should add sleep here
        mDevice.findObject(By.desc("Chrome")
                .clazz("android.widget.TextView")
                .text("Chrome")
                .pkg("com.android.launcher"))
                .clickAndWait(Until.newWindow(), DEFAULT_TIMEOUT);
        mDevice.swipe(611, 937, 611, 937, 500);
        mDevice.pressHome();
        // should add sleep here

    }

}
