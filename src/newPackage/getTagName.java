package newPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class getTagName {

    public static void main(String args[]) {

        //Config the chrome driver
        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumWebDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "https://facebook.com";
        String tagName = "";

        //open the browser to the base url
        driver.get(baseUrl);

        //Getting the tagName of Email
        tagName = driver.findElement(By.id("email")).getTagName();

        //Print the tagName
        System.out.println("The Tag is : " + tagName);

        //Close the browser
        driver.close();

    }

}
