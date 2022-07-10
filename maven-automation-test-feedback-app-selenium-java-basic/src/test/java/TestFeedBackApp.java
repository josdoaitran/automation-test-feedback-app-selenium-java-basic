import java.sql.*;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;


public class TestFeedBackApp {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        resetData();
        System.setProperty("webdriver.chrome.driver", "D:\\coding\\libs\\chromedriver.exe");
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.get("http://127.0.0.1:5000/");
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().window().minimize();
        chromeDriver.manage().window().fullscreen();
        chromeDriver.manage().window().maximize();
        Boolean isCheckLogoElement = chromeDriver.findElement(By.className("logo")).isDisplayed();
        if (isCheckLogoElement){
            System.out.println("We are in Feedback app.");
        }
        chromeDriver.findElement(By.name("customer")).sendKeys("Test4");
        Select consultantSelect = new Select(chromeDriver.findElement(By.name("consultant")));
        consultantSelect.selectByIndex(3);
        consultantSelect.selectByValue("Mayra Riggs");
        List<WebElement> ratingRadio = chromeDriver.findElements(By.xpath("//input[@name='rating']"));
        System.out.println(ratingRadio.size());
        ratingRadio.get(8).click();
        ratingRadio.get(generateRandomIndex(0, ratingRadio.size())).click();
        chromeDriver.findElement(By.name("comments")).sendKeys("I like what you shared");
        chromeDriver.findElement(By.className("btn")).click();

        String textMessage = chromeDriver.findElement(By.xpath("//h2")).getText();
        if (textMessage.equalsIgnoreCase("Thank you for rating your experience. We hope to see you soon!")){
            System.out.println("You're already done");
        }

        ResultSet result = checkDatabase();
        try{
            while (result.next()){
                System.out.println(result.getString("customer"));
                System.out.println(result.getString("consultant"));
            }
        }catch (Exception e){
            System.out.println("Can not retrieve to result data");
        }

    }

    private static int generateRandomIndex(int min, int max){
        Random newRandom = new Random();
        return newRandom.nextInt(max - min) + min;
    }

    private static void resetData() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String url = "jdbc:postgresql://192.168.1.101:5432/feedbackdb";
        final String userName = "postgres";
        final String password = "Password@12345";

        String sqlStatement = "Delete from feedback where customer = 'Test4'";
        try(Connection connection = DriverManager.getConnection(url, userName, password))
        {
            Statement statement = connection.createStatement();
            statement.execute(sqlStatement);
            connection.close();
        }catch (SQLException e){
            System.out.println("Connection to db is failed");
        }
    }

    private static ResultSet checkDatabase() throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        final String url = "jdbc:postgresql://192.168.1.101:5432/feedbackdb";
        final String userName = "postgres";
        final String password = "Password@12345";
        String sqlStatement = "select customer, consultant from public.feedback where customer = 'Test4'";
        ResultSet result = null;
        try(Connection connection = DriverManager.getConnection(url, userName, password)){
            Statement st = connection.createStatement();
            result = st.executeQuery(sqlStatement);
            return  result;
        }catch (SQLException e){
            System.out.println("Connection to db is failed");
        }
        return result;
    }
}
