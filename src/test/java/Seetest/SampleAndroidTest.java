package Seetest;
//package <set your test package>;
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
    private String testName = "Android Test1";
    private String accessKey = "eyJ4cC51IjoxNDUxNDUsInhwLnAiOjE0NTE0NCwieHAubSI6Ik1UVXpNRFl4TXpZd01ESTJOQSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NDU5NzM2MDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.KTQcweVJFYfTyQpITDV9kLv2-oQ7COnK5NB0Y9LK1Rs";
    protected AndroidDriver<AndroidElement> driver = null;

    DesiredCapabilities dc = new DesiredCapabilities();
    
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("accessKey", accessKey);
        dc.setCapability(MobileCapabilityType.UDID, "0117114300883901");
        dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, "com.experitest.ExperiBank");
        dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ".LoginActivity");
        dc.setCapability("instrumentApp", true);
        driver = new AndroidDriver<>(new URL("https://demo.experitest.com:443/wd/hub"), dc);
        driver.setLogLevel(Level.INFO);
    }

    @Test
    public void testUntitled() {
        driver.findElement(By.xpath("//*[@hint='Username']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@hint='Password']")).sendKeys("company");
        driver.findElement(By.xpath("//*[@text='Login']")).click();
        driver.findElement(By.xpath("//*[@text='Logout']")).click();
        driver.pressKeyCode(AndroidKeyCode.HOME);
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}