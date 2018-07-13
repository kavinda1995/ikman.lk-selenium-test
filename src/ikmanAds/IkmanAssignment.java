package ikmanAds;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import java.util.List;
import org.testng.annotations.*;

public class IkmanAssignment {

    private String baseURL = "https://ikman.lk";
    private String driverPath = "C:\\SeleniumWebDriver\\chromedriver.exe";
    private WebDriver driver;
    private String numberOfAds;
    private Boolean validation = true;

    @BeforeTest
    public void setUpBrowser() {
        System.setProperty("webdriver.chrome.driver", driverPath);
        driver = new ChromeDriver();

        //Opening the Browser
        driver.get(baseURL);
        System.out.println("Opened page ikman.lk");
    }

    @Test(priority = 0)
    public void openPropertyPage() {
        //Clicking the Property Button
        String pathToProperty = "/html[1]/body[1]/div[2]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/a[1]";
        WebElement propertyBtn = driver.findElement(By.xpath(pathToProperty));
        propertyBtn.click();
        System.out.println("Opened Property Page");
    }

    @Test(priority = 1)
    public void openHousesPage() {
        //Clicking on Houses Filter link
        String housesFilterLink = "//a[@href='/en/ads/sri-lanka/houses?categoryType=ads&categoryName=Property']";
        WebElement houseLink = driver.findElement(By.xpath(housesFilterLink));
        houseLink.click();
        System.out.println("Opened Houses Page");
    }

    @Test(priority = 2)
    public void selectColombo() {
        //Clicking on Colombo region filter
        String colomboFilterLink = "//a[@href='/en/ads/colombo/houses?categoryType=ads&categoryName=Houses&type=for_sale']";
        WebElement colomboLink = driver.findElement(By.xpath(colomboFilterLink));
        colomboLink.click();
        System.out.println("Opened Colombo Page");
    }

    @Test(priority = 3)
    public void selectPrice() {
        //Click on Price filter
        String priceFilter = "//div[@class='ui-accordion-item filter-price']//a[@class='ui-accordion-title t-small']";
        WebElement priceFilterBtn = ((ChromeDriver) driver).findElementByXPath(priceFilter);
        priceFilterBtn.click();

        //Set the Minimum Price value to 5,000,000

        //Getting the MinPrice Element
        String minPriceInput = "//div[@class='ui-accordion-content is-price']//label[@class='ui-label'][contains(text(),'Min.')]";
        WebElement minPrice = ((ChromeDriver) driver).findElementByXPath(minPriceInput);

        //Using Actions to set focus on the input box
        Actions focusMin = new Actions(driver);
        focusMin.moveToElement(minPrice);
        focusMin.click();
        focusMin.sendKeys("5000000");
        focusMin.build().perform();
        System.out.println("Setted the Min-Price to 5000000");

        //Set the Minimum Price value to 7,500,000

        //Getting the MinPrice Element
        String maxPriceInput = "//div[@class='ui-accordion-content is-price']//label[@class='ui-label'][contains(text(),'Max.')]";
        WebElement maxPrice = ((ChromeDriver) driver).findElementByXPath(maxPriceInput);

        //Using Actions to set focus on the input box
        Actions focusMax = new Actions(driver);
        focusMax.moveToElement(maxPrice);
        focusMax.click();
        focusMax.sendKeys("7500000");
        focusMax.build().perform();
        System.out.println("Setted the Max-Price to 7500000");

        //Clicking the Apply Filters Button
        String applyFilter = "//div[@class='ui-accordion-content is-price']//button[@class='ui-btn is-standard btn-apply'][contains(text(),'Apply filters')]";
        WebElement applyFilterBtn = ((ChromeDriver) driver).findElementByXPath(applyFilter);
        applyFilterBtn.click();
        System.out.println("Applied Price Filter");
    }

    @Test(priority = 4)
    public void selectBeds() {
        //Click on Beds Filter
        String bedsFilterLink = "//div[@class='ui-accordion-item filter-enum filter-bedrooms']//a[@class='ui-accordion-title t-small']";
        WebElement bedsFilter = ((ChromeDriver) driver).findElementByXPath(bedsFilterLink);
        bedsFilter.click();

        //Select 3 beds
        String bedsRadioBtn = "//input[@id='filters2values-3']";
        WebElement bedsBtn = ((ChromeDriver) driver).findElementByXPath(bedsRadioBtn);
        bedsBtn.click();
        System.out.println("Selected 3 beds filter");

        //Get the number of items in the sale page
        String numberOfItemsPath = "//span[@class='t-small summary-count']";
        String numberOfItems = ((ChromeDriver) driver).findElementByXPath(numberOfItemsPath).getText();
        String[] expression = numberOfItems.split(" ");
        numberOfAds = expression[3];
        System.out.println("No of Ads Found : " + numberOfItems);
    }

    @Test(priority = 5)
    public void listAds() {
        Integer nuOfAds = Integer.parseInt(numberOfAds);
        int adCounter = 1;

        //Iterate through all pages
        for (int i = 0; i <= (nuOfAds / 27); i++) {

            //Get ads on one page to a list
            int count = 0;
            List<WebElement> adds = ((ChromeDriver) driver).findElementsByClassName("ui-item");

            //Iterate through ads
            for (WebElement add : adds) {
                //Remove first two ads (Sponsored)
                if (count < 2) {
                    count++;
                } else {
                    //Getting Price of the ad
                    String price = add.findElement(By.className("item-info")).getText();

                    //Printing the ad as per the need
                    System.out.println("Ad Number " + (adCounter) + " Price is : " + price);

                    //Sanitizing the Price to integer
                    String[] priceArr = price.split("Rs| |,");
                    String priceMerge = priceArr[2] + priceArr[3] + priceArr[4];
                    Integer priceVal = Integer.parseInt(priceMerge);

                    //Getting no. of beds//a[@class='col-6 lg-3 pag-next']
                    String beds = add.findElement(By.xpath("//p[@class='item-meta']//span[1]")).getText();
                    String[] bedsArr = beds.split(" ");
                    Integer bedsCount = Integer.parseInt(bedsArr[1]);

                    //Validating the price and no.of Beds
                    if (5000000 <= priceVal && priceVal <= 7500000 && bedsCount == 3) {
                        count++;
                        adCounter++;
                    } else {
                        System.out.println("Ad Number " + (adCounter) + "'s Validation failed");
                        validation = false;
                        count++;
                        adCounter++;
                    }

                }
            }

            try {
                String nextBtnLink = "//a[@class='col-6 lg-3 pag-next']";
                WebElement nextBtn = ((ChromeDriver) driver).findElementByXPath(nextBtnLink);
                nextBtn.click();
            } catch (Exception e) {
                System.out.println("End of ads");
            }
        }
    }

    @AfterTest
    public void closeBrowser() {
        if (!validation) {
            System.out.println("Some validations are failed");
        } else {
            System.out.println("All validations succeeded");
        }
        driver.close();
    }

}
