package Seetest;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.android.AndroidKeyCode;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.logging.Level;

public class SampleAndroidTest {
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "Sample Android Test";
    private String accessKey = "eyJleHAiOjE4NDU5NzM2MDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.KTQcweVJFYfTyQpITDV9kLv2-oQ7COnK5NB0Y9LK1Rs";
    protected AndroidDriver<AndroidElement> driver = null;

    DesiredCapabilities dc = new DesiredCapabilities();
    
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@os='android'");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.example.circleci.circlecidemo2");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".MainActivity");
        dc.setCapability("instrumentApp", true);
        driver = new AndroidDriver<>(new URL("https://demo.experitest.com:443/wd/hub"), dc);
        driver.setLogLevel(Level.INFO);
    }

    @Test
    public void testUntitled() {
        driver.findElement(By.xpath("//*[@id='icon' and ./parent::*[@id='navigation_dashboard']]")).click();
        driver.findElement(By.xpath("//*[@id='icon' and ./parent::*[@id='navigation_notifications']]")).click();
        driver.findElement(By.xpath("//*[@id='icon' and ./parent::*[@id='navigation_home']]")).click();
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}