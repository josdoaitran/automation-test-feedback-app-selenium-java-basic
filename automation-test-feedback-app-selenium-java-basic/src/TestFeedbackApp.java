import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestFeedbackApp {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\coding\\libs\\chromedriver.exe");
        WebDriver intChromeDriver = new ChromeDriver();
        intChromeDriver.get("https://testing4everyonefeedback.herokuapp.com/");
        intChromeDriver.manage().window().maximize();

        intChromeDriver.findElement(By.name("customer")).sendKeys("Testing4Everyone");

//        intChromeDriver.close();
    }
}
