import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.Random;
import java.sql.*;

public class TestFeedbackApp {
    public static void main(String[] args) throws ClassNotFoundException {
        resetData();
        System.setProperty("webdriver.chrome.driver", "D:\\coding\\libs\\chromedriver.exe");
        WebDriver intChromeDriver = new ChromeDriver();
        intChromeDriver.get("http://127.0.0.1:5000");
        intChromeDriver.manage().window().maximize();
        intChromeDriver.manage().window().minimize();
        intChromeDriver.manage().window().fullscreen();
        intChromeDriver.manage().window().maximize();
        WebElement testing4EveryoneIcon = intChromeDriver.findElement(By.className("logo"));
        Boolean checkFeedbackPageDisplayed = testing4EveryoneIcon.isDisplayed();
        if (checkFeedbackPageDisplayed){
            System.out.println("We are in Feedback App");
        }
        intChromeDriver.findElement(By.name("customer")).sendKeys("Testing4Everyone");
        Select consultDropdown = new Select(intChromeDriver.findElement(By.name("consultant")));
        consultDropdown.selectByIndex(1);
        consultDropdown.selectByValue("Mayra Riggs");
        consultDropdown.selectByVisibleText("Ali Petty");
        List<WebElement> listRating = intChromeDriver.findElements(By.xpath("//input[@name='rating']"));
//        System.out.println(String.valueOf(generateRandomValue(0,listRating.size())));
        listRating.get(generateRandomValue(0,listRating.size())).click();
        intChromeDriver.findElement(By.name("comments")).sendKeys("Here is my comments by automation test script");
        intChromeDriver.findElement(By.className("btn")).click();
        String message = intChromeDriver.findElement(By.xpath("//h2")).getText();

        if (message == ("Thank you for rating your experience. We hope to see you soon!")){
            System.out.println("We input the feedback successfully");
        }
        ResultSet resultSet = checkDatabase();
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("customer"));
                System.out.println(resultSet.getString("consultant"));
            }
        }catch (Exception e){

        }
        intChromeDriver.close();
    }

    private static int generateRandomValue(int min, int max){
        Random newRandom = new Random();
        return newRandom.nextInt(max - min) + min ;
    }

    private static void resetData() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String url = "jdbc:postgresql://192.168.1.101:5432/feedbackdb";
        final String username = "postgres";
        final String password="Password@12345";
        String sqlStatement = "Delete from public.feedback where customer = 'Testing4Everyone';";
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            boolean resultSet = statement.execute(sqlStatement);
            connection.close();
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
    }

    private static ResultSet checkDatabase() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String url = "jdbc:postgresql://192.168.1.101:5432/feedbackdb";
        final String username = "postgres";
        final String password="Password@12345";
        String sqlStatement = "select customer, consultant from public.feedback where customer = 'Testing4Everyone';";
        ResultSet resultSet = null;
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlStatement);
            return resultSet;
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return resultSet;
    }
}