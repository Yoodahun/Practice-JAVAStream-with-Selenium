import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;

public class StreamOnSeleniumTest {

    WebDriver driver;


    @BeforeTest
    void setupTest() {
        WebDriverManager.chromedriver().setup();
    }



    @BeforeMethod
    public void beforeStartTests(Method method) {
        driver = new ChromeDriver();
        System.out.println("Start " + method.getName() + " -------------------" );
    }

    @AfterTest
    void teardown() {
        driver.close();
        driver.quit();
    }

    @Test
    public void checkSortedItem() {

        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

        Select pageSize = returnSelectObject(By.id("page-menu"));

        pageSize.selectByValue("20");

        List<String> originalTextList = driver.findElements(By.xpath("//tr/td[1]"))
                .stream().map(WebElement::getText)
                .sorted()
                .collect(Collectors.toList());

        driver.findElement(By.xpath("//tr/th[1]")).click();

        List<String> sortedTextList = driver.findElements(By.xpath("//tr/td[1]"))
                .stream().map(WebElement::getText)
                .collect(Collectors.toList());

        Assert.assertEquals(originalTextList, sortedTextList);
    }

    @Test
    public void checkItemPrice() {
        driver.get("https://rahulshettyacademy.com/seleniumPractise/#/offers");

        // scan the name column with getText, Hit item, print item price.
        returnSelectObject(By.id("page-menu")).selectByValue("20");

        //using Custom method
        List<String> originalTextList = driver.findElements(By.xpath("//tr/td[1]"))
                .stream().filter(s -> s.getText().contains("Beans"))
                .map(this::getPriceVeggie)
                .collect(Collectors.toList());

        originalTextList.forEach(System.out::println);

    }

    private String getPriceVeggie(WebElement s) {

        System.out.println(s.getText());

        return s.findElement(By.xpath("following-sibling::td[1]")).getText();

    }

    private Select returnSelectObject(By selectId) {
        return new Select(driver.findElement(selectId));
    }
}
