package newPackage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class clickElement {

    public static void main(String args[]) {

        System.setProperty("webdriver.chrome.driver", "C:\\SeleniumWebDriver\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String baseUrl = "https://facebook.com";

        //open the browser to the base url
        driver.get(baseUrl);

        //getting the input and password box and login btn
        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("pass"));
        WebElement loginBtn = driver.findElement(By.linkText("Log In"));

        System.out.println(loginBtn.getText());

        //filling the email and password boxes with values
        email.sendKeys("testSelenium@selenium.com");
        password.sendKeys("passwordForSelenium");

        //Clicking the Login Button
        loginBtn.click();


    }

}
