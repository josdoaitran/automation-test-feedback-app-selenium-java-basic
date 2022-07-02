# Preparation:
- Java 8
- App example: https://testing4everyonefeedback.herokuapp.com/
or install it in your local here: https://github.com/josdoaitran/mini-python-web-app
Videoclip: https://www.youtube.com/watch?v=kuDSZmOYxVQ
- Download Java Selenium Library from: https://www.selenium.dev/downloads/
  + => Save as .Zip file in your local and extract it as a folder.
  + => Add Selenium java library into your Java project. (In IntelliJ: Project Structure > Module > Dependencies)
  
- Download Driver for your browser.
In order to control your browser and do the actions in your browser. We need to have Selenium WebDriver to control our browser. In each browser type, we need to download correct Driver.
  + Chrome Driver Link: https://chromedriver.chromium.org
  + Firefox - Geckodriver link: https://firefox-source-docs.mozilla.org/testing/geckodriver/Support.html

If you're using Macbook/MacOSX, please download driver for Mac and extract it into `/usr/local/bin`

## Create a Java project
Class: `TestFeedbackApp`

### The first step to open browser and access to Feedbackapp

```aidl
public class TestFeedbackApp {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", "D:\\coding\\libs\\chromedriver.exe");
        WebDriver intChromeDriver = new ChromeDriver();
        intChromeDriver.get("https://testing4everyonefeedback.herokuapp.com/");
    }
}
```

### To close browser:
`
intChromeDriver.close();
`

### To maximize or minimize browsers in test:
`intChromeDriver.manage().window().maximize();
intChromeDriver.manage().window().minimize();
`
To switch to Full Screen mode:
`intChromeDriver.manage().window().fullscreen();`


## Connect to Postgres DB
https://jdbc.postgresql.org/download.html