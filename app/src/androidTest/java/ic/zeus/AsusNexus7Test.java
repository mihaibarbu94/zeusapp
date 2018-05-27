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
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.util.Log;
import android.util.Pair;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ic.zeus.sockets.Sender;

/**
 * @@Test comment here@@
 */
@RunWith(AndroidJUnit4.class)
@SdkSuppress(minSdkVersion = 18)
public class AsusNexus7Test {
    private static final long DEFAULT_TIMEOUT = 1000;
    private UiDevice mDevice;
    private UiScrollable uiScrollable;
    private long currentTime;
    private final Pair<Integer, Integer> chromeRefreshCoordinates = new Pair<>(250,200);
    private final Pair<Integer, Integer> firefoxRefreshCoordinates = new Pair<>(910,200);
    private final Pair<Integer, Integer> operaRefreshCoordinates = new Pair<>(980,190);
    private final Pair<Integer, Integer> redditNextCoordinates = new Pair<>(1100,1780);
    private final Pair<Integer, Integer> redditNextCoordinatesOpera = new Pair<>(800,1500);
    private final Pair<Integer, Integer> operaForward = new Pair<>(360,1770);
    private final Pair<Integer, Integer> operaBackwards = new Pair<>(125,1770);


    @Before
    public void setUp() {
        // Initialize UiDevice instance
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(instrumentation);
        uiScrollable =  new UiScrollable(new UiSelector().scrollable(true));
    }


    public void culebraGeneratedTest(String browserName) {
        mDevice.click(596, 1877);
        mDevice.pressHome();
        // should add sleep here
        mDevice.findObject(By.desc(browserName)
                .clazz("android.widget.TextView")
                .text(browserName)
                .pkg("com.android.launcher"))
                .clickAndWait(Until.newWindow(), DEFAULT_TIMEOUT);
    }


    private boolean timeIsOut(int minutesUntilTestIsFinished) {
        long remainingTime = minutesUntilTestIsFinished * 60 - (System.currentTimeMillis() - currentTime) / 1000;
        Log.e("TIME", "Remaining time:" + remainingTime);
        return remainingTime <= 0;
    }

    private void swipeAndWait(int times) {
        while(times != 0) {
            mDevice.swipe(500,1500,500,300,90);
            mDevice.waitForIdle();
            //swipe right -- this is to add a pause function to the scroll
            //and be consistent across all platforms
            mDevice.swipe(300,500,700,500,90);
            mDevice.waitForIdle();
            times--;
        }

    }

    private void testBrowserX(List<Pair> coordinates, String browserName, boolean swipeBefore) throws UiObjectNotFoundException {
        currentTime = System.currentTimeMillis();
        culebraGeneratedTest(browserName);


        int times = 70;
        while(times != 0 && !timeIsOut(30)) {
            uiScrollable.scrollToEnd(300, 60);
            clickOnCoordinates(coordinates, swipeBefore);
            mDevice.waitForIdle(5000);
            times--;
        }

        Sender sender = new Sender();
        sender.sendData();
    }

    private void testBrowser1(Pair coordinates, String browserName, boolean swipeBefore) throws UiObjectNotFoundException {
        currentTime = System.currentTimeMillis();
        culebraGeneratedTest(browserName);


        int times = 70;
        while(times != 0 && !timeIsOut(30)) {
            searchForText("NEXT");
            mDevice.waitForIdle(5000);
            times--;
        }

        Sender sender = new Sender();
        sender.sendData();
    }

    private void testBrowser(List<Pair> coordinates, String browserName, boolean swipeBefore) {
        currentTime = System.currentTimeMillis();
        culebraGeneratedTest(browserName);

        int times = 70;
        while(times != 0 && !timeIsOut(30)) {
            swipeAndWait(60);//should be 60 aprox 1 min
            clickOnCoordinates(coordinates, swipeBefore);
            times--;
        }

        Sender sender = new Sender();
        sender.sendData();
    }

    private void searchForText(String searchText)
            throws UiObjectNotFoundException {
        UiScrollable textScroll;
        UiObject text;
        if (searchText != null) {
            textScroll = new UiScrollable(new UiSelector().scrollable(true));
            textScroll.scrollIntoView(new UiSelector().text(searchText));
            text = new UiObject(new UiSelector().text(searchText));
            text.click();
        }
    }

    private void clickOnCoordinates(List<Pair> coordinates, boolean swipeBefore) {
        if (swipeBefore) {
            mDevice.waitForIdle();
            mDevice.swipe(500,300,500,1000,90);
            mDevice.waitForIdle();
        }

        mDevice.waitForIdle();
        for(Pair coordinate: coordinates){
            mDevice.click((int)coordinate.first, (int)coordinate.second);
            mDevice.swipe(300,500,700,500,90);
        }

        mDevice.waitForIdle();
    }

//    @Test
//    public void testChromeReddit() throws UiObjectNotFoundException {
//        testBrowser(redditNextCoordinates, "Chrome", false);
//    }
//
//    @Test
//    public void testFirefoxReddit() throws UiObjectNotFoundException {
//        testBrowser(firefoxRefreshCoordinates, "Firefox", true);
//    }



    @Test
    public void testChromeWithRefreshPage() {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(chromeRefreshCoordinates);
        testBrowser(coordinates, "Chrome", true);
    }

    @Test
    public void testFirefoxWithRefreshPage() {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(firefoxRefreshCoordinates);
        testBrowser(coordinates, "Firefox", true);
    }

    @Test
    public void testOperaWithRefreshPage() {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(operaForward);
        coordinates.add(operaBackwards);
        testBrowser(coordinates, "Opera Mini", false);
    }

}
