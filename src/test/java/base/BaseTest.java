package base;

import com.thoughtworks.gauge.AfterScenario;
import com.thoughtworks.gauge.BeforeScenario;
import helper.ElementHelper;
import helper.StoreHelper;
import model.ElementInfo;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class BaseTest {
    public static WebDriver driver;
    double difference;

    @BeforeScenario
    public void setup(){
        System.setProperty("webdriver.chrome.driver","drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @AfterScenario
    public void tearDown() throws Exception { driver.quit(); }



    public void waitSec(int sec){
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public WebElement findElement(String key){
        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 30);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.presenceOfElementLocated(infoParam));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center', inline: 'center'})",
                webElement);
        return webElement;
    }

    public void clickElement(WebElement element){
        element.click();
    }

    public void hoverElement(String key) {
        Actions action = new Actions(driver);
        action.moveToElement(findElement(key)).build().perform();
    }


    public void getValues(String key1, String key2) {
        String oldPrice = findElement(key1).getText();
        String newPrice = findElement(key2).getText();
        System.out.println("Eski fiyat: "+ oldPrice);
        System.out.println("Yeni fiyat: "+ newPrice);

        double priceOld = Double.parseDouble(oldPrice);
        double priceNew = Double.parseDouble(newPrice);
        difference = priceOld - priceNew;
    }

    public void getDifference(){
        File file = new File("difference.txt");
        try(BufferedWriter br = new BufferedWriter(new FileWriter(file))){
            br.write("Old Price - New Price = : "+ difference);
        } catch (IOException e) {
            System.out.println("Unable to read file " +file.toString());
        }
    }

}
