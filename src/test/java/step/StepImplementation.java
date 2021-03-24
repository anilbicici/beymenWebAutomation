package step;

import base.BaseTest;
import com.thoughtworks.gauge.Step;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StepImplementation extends BaseTest{

    private final Logger logger = LoggerFactory.getLogger(getClass());


    @Step("<int> Saniye bekle")
    public void waitBySec(int sec) {
        waitSec(sec);
    }

    @Step("<url> sayfasina git")
    public void navigate(String url) {
        driver.get(url);
        logger.info(url + " adresine gidiliyor.");
    }

    @Step("Ana sayfanin acildigi kontrol edilir")
    public void checkPage() {
        String title = driver.getTitle();
        String expectedText = "Beymen.com – Lifestyle Destination";
        assertEquals(title,expectedText);
    }

    @Step("<key> urununu bul")
    public void checkElement(String key) {
        try {
            findElement(key);
        } catch (Exception e) {
            Assert.fail("Element: "+ key + " bulunamadı.");
        }
    }

    @Step("<key> menusunun uzerine gel")
    public void hoverE(String key) {
        hoverElement(key);
        logger.info(key + " elementine hover.");
    }

    @Step("<key> secenegine tikla")
    public void clickE(String key) {
        if (!key.equals("")) {
            WebElement element = findElement(key);
            clickElement(element);
            logger.info(key + " elementine click.");
        }
    }

    @Step("Acilan sayfa <url> iceriyor mu")
    public void urlControl(String url){
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains(url));
    }

    @Step("<key1> ve <key2> fiyat yazdir")
    public void writeValues(String key1,String key2){
        getValues(key1,key2);
    }

    @Step("2 Fiyat arasindaki farki yazdir")
    public void writeDifference(){
        getDifference();
    }



}
