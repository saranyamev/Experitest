package Seetest;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.testng.annotations.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.logging.Level;

public class SampleiOSTest {
    private String reportDirectory = "reports";
    private String reportFormat = "xml";
    private String testName = "iOS Test";
    private String accessKey = "eyJ4cC51IjoxNDUxNDUsInhwLnAiOjE0NTE0NCwieHAubSI6Ik1UVXpNRFl4TXpZd01ESTJOQSIsImFsZyI6IkhTMjU2In0.eyJleHAiOjE4NDU5NzM2MDAsImlzcyI6ImNvbS5leHBlcml0ZXN0In0.KTQcweVJFYfTyQpITDV9kLv2-oQ7COnK5NB0Y9LK1Rs";
    protected IOSDriver<IOSElement> driver = null;

    DesiredCapabilities dc = new DesiredCapabilities();
    
    @BeforeMethod
    public void setUp() throws MalformedURLException {
        dc.setCapability("reportDirectory", reportDirectory);
        dc.setCapability("reportFormat", reportFormat);
        dc.setCapability("testName", testName);
        dc.setCapability("accessKey", accessKey);
        dc.setCapability("deviceQuery", "@os='ios'");
        //dc.setCapability(MobileCapabilityType.UDID, "da396575c118503aada6b45c6f8f1652d14d9722");
        dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, "com.experitest.ExperiBank");
        dc.setCapability("instrumentApp", true);
        driver = new IOSDriver<>(new URL("https://demo.experitest.com:443/wd/hub"), dc);
        driver.setLogLevel(Level.INFO);
    }

    @Test
    public void testUntitled() {
        driver.findElement(By.xpath("//*[@placeholder='Username']")).click();
        driver.getKeyboard().sendKeys("company");
        driver.findElement(By.xpath("//*[@placeholder='Password']")).click();
        driver.findElement(By.xpath("//*[@placeholder='Password']")).sendKeys("company");
        new WebDriverWait(driver, 30).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='loginButton']")));
        driver.findElement(By.xpath("//*[@text='loginButton']")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@text='logoutButton']")));
        driver.findElement(By.xpath("//*[@text='logoutButton']")).click();
        driver.executeScript("seetest:client.deviceAction(\"Home\")");
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}