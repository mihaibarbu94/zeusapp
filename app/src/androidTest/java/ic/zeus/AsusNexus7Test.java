package ic.zeus;
/*************************************************************************
 * CulebraTester (C) 2015-2016 Diego Torres Milano
 *
 * <p/>
 ************************************************************************/

import android.app.Instrumentation;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.SdkSuppress;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
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
    private long testStartTime;
    private final Pair<Integer, Integer> chromeRefreshCoordinates = new Pair<>(250,200);
    private final Pair<Integer, Integer> firefoxRefreshCoordinates = new Pair<>(910,200);
    private final Pair<Integer, Integer> operaRefreshCoordinates = new Pair<>(980,190);
    private final Pair<Integer, Integer> redditNextCoordinates = new Pair<>(1100,1780);
    private final Pair<Integer, Integer> redditNextCoordinatesOpera = new Pair<>(800,1500);
    private final Pair<Integer, Integer> operaForward = new Pair<>(360,1770);
    private final Pair<Integer, Integer> operaHome = new Pair<>(610,1770);
    private final Pair<Integer, Integer> operaBackwards = new Pair<>(125,1770);
    private static final  int TIME_BETWEEN_SCROLLS_IN_MILL = 2000;
    private static final long LOADING_APP__TIME = 10000;
    private static final int TIME_TO_REFRESH_PAGE = 6000;
    private static final int TIME_TO_SCROLL_PAGE = 120; //120 seconds
    private final int TEST_TIME_IN_MINUTES = 30; // 30 minutes


    @Before
    public void setUp() {
        // Initialize UiDevice instance
        final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        mDevice = UiDevice.getInstance(instrumentation);
        uiScrollable =  new UiScrollable(new UiSelector().scrollable(true));
    }


    public void culebraGeneratedTest(String browserName) throws InterruptedException {
        mDevice.click(596, 1877);
        mDevice.pressHome();
        // should add sleep here
        mDevice.findObject(By.desc(browserName)
                .clazz("android.widget.TextView")
                .text(browserName)
                .pkg("com.android.launcher"))
                .clickAndWait(Until.newWindow(), DEFAULT_TIMEOUT);
        Thread.sleep(LOADING_APP__TIME);
    }

    private boolean timeIsOut(int timeOfTestInMinutes) {
        long remainingTimeInSeconds = remainingTimeInSeconds(timeOfTestInMinutes);
        Log.e("TIME", "Remaining time:" + remainingTimeInSeconds + " seconds.");
        return remainingTimeInSeconds <= 0;
    }

    private long remainingTimeInSeconds(int timeOfTestInMinutes){
        return timeOfTestInMinutes * 60 - calculateTimePassedSinceStart();
    }

    private long calculateTimePassedSinceStart(){
        return (System.currentTimeMillis() - testStartTime) / 1000;
    }

    /**
     * Swipes from down to up and left to right to have a pause in scrolling.
     * @param seconds the amount of time to to scroll
     */
    private void swipeAndWait(int seconds,
                              boolean preciseTimer,
                              int timeOfTestInMinutes)  throws InterruptedException{
        long startTime = System.currentTimeMillis();

        if (preciseTimer) {
            //Log.e("precise time", "true");
            while((System.currentTimeMillis() - startTime <= seconds * 1000) && !timeIsOut(timeOfTestInMinutes)) {
                //Log.e("precise time", String.valueOf(!timeIsOut(timeOfTestInMinutes)));
                swipeUpAndWait();
            }
        } else {
            //
            // Log.e("precise time", "false");
            while(System.currentTimeMillis() - startTime <= seconds * 1000) {
                swipeUpAndWait();
            }
        }
    }

    private void swipeUpAndWait () throws InterruptedException {
        mDevice.swipe(500,1500,500,300,90);
        mDevice.waitForIdle();
        Thread.sleep(TIME_BETWEEN_SCROLLS_IN_MILL);
    }

    /**
     * Sends the battery values to the server each TIME_TO_SCROLL_PAGE cycle.
     * This is to have a baseline reading how much battery the display and the OS
     * when idling is consuming.
     * @throws InterruptedException
     */
    private void baselineTest() throws InterruptedException {
        testStartTime = System.currentTimeMillis();
        mDevice.pressHome();
        Sender sender = new Sender();
        sender.sendTestInfo("baselineTest", String.valueOf(calculateTimePassedSinceStart()));

        while(true) {
            mDevice.pressHome();
            Thread.sleep(132 * 1000);
            sender.sendTestInfo("baselineTest", String.valueOf(calculateTimePassedSinceStart()));
        }
    }

    private void testBrowser(List<Pair> coordinates,
                             String browserName,
                             boolean swipeBefore,
                             int timeOfTestInMinutes) throws InterruptedException {
        culebraGeneratedTest(browserName);
        testStartTime = System.currentTimeMillis();
        Sender sender = new Sender();
        sender.sendTestInfo(browserName, String.valueOf(calculateTimePassedSinceStart()));
        boolean preciseTimer = false;

        while(!timeIsOut(timeOfTestInMinutes)) {
            if (remainingTimeInSeconds(timeOfTestInMinutes) <= TIME_TO_SCROLL_PAGE){
                preciseTimer = true;
            }

            swipeAndWait(TIME_TO_SCROLL_PAGE, preciseTimer, timeOfTestInMinutes); //120 -> 2 mins

            if (!preciseTimer) clickOnCoordinates(coordinates, swipeBefore); // if last run don't refresh

            sender.sendTestInfo(browserName, String.valueOf(calculateTimePassedSinceStart()));
        }
    }

    private void clickOnCoordinates(List<Pair> coordinates, boolean swipeBefore) throws InterruptedException {
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
        Thread.sleep(TIME_TO_REFRESH_PAGE);
    }

    private void closeAndReopenApp() throws RemoteException, InterruptedException {
        Thread.sleep(5000);
        mDevice.pressRecentApps();
        Thread.sleep(1000);
        mDevice.swipe(380,700,1200,700,60);
        Thread.sleep(2000);
    }

    @Test
    public void testChromeWithRefreshPage() throws InterruptedException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(chromeRefreshCoordinates);
        testBrowser(coordinates, "Chrome", true, TEST_TIME_IN_MINUTES);
    }

    @Test
    public void testFirefoxWithRefreshPage() throws InterruptedException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(firefoxRefreshCoordinates);
        testBrowser(coordinates, "Firefox", true, TEST_TIME_IN_MINUTES);
    }

    @Test
    public void testOperaWithRefreshPage() throws InterruptedException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(operaForward);
        coordinates.add(operaBackwards);
        testBrowser(coordinates, "Opera Mini", false, TEST_TIME_IN_MINUTES);
    }




    @Test
    public void testChromeWithRefreshPageUntilBatteryDead() throws InterruptedException, RemoteException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(chromeRefreshCoordinates);
        long startOfTest = System.currentTimeMillis();
        Log.i("START TIME", "0");
        while(true){
            testBrowser(coordinates, "Chrome", true, 20);
            closeAndReopenApp();
            double currentTimeInSeconds = (System.currentTimeMillis() - startOfTest) / 1000.0;
            String toPrint = currentTimeInSeconds + "s , " + currentTimeInSeconds / 60.0 + "min, " + currentTimeInSeconds / 3600.0 + "h";
            Log.i("TIME", toPrint);
        }
    }

    @Test
    public void testFirefoxWithRefreshPageUntilBatteryDead() throws InterruptedException, RemoteException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(firefoxRefreshCoordinates);
        long startOfTest = System.currentTimeMillis();
        Log.i("START TIME", "0");
        while(true){
            testBrowser(coordinates, "Firefox", true, 20);
            closeAndReopenApp();
            double currentTimeInSeconds = (System.currentTimeMillis() - startOfTest) / 1000.0;
            String toPrint = currentTimeInSeconds + "s , " + currentTimeInSeconds / 60.0 + "min, " + currentTimeInSeconds / 3600.0 + "h";
            Log.i("TIME", toPrint);
        }
    }



    @Test
    public void testOperaWithRefreshPageUntilBatteryDead() throws InterruptedException, RemoteException {
        List<Pair> coordinates = new ArrayList<>();
        coordinates.add(operaHome);
        coordinates.add(operaBackwards);
        long startOfTest = System.currentTimeMillis();
        Log.i("START TIME", "0");
        while(true) {
            testBrowser(coordinates, "Opera Mini", false, 20);
            closeAndReopenApp();
            double currentTimeInSeconds = (System.currentTimeMillis() - startOfTest) / 1000.0;
            String toPrint = currentTimeInSeconds + "s , " + currentTimeInSeconds / 60.0 + "min, " + currentTimeInSeconds / 3600.0 + "h";
            Log.i("TIME", toPrint);
        }
    }

    @Test
    public void testWithJustTheScreenOn() throws InterruptedException {
        baselineTest();
    }
}
