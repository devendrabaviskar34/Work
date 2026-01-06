package Assignment;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Assignment_01 {

    static String APiData;
    WebDriver driver;
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @Test(priority = 1)
    public void ApiData() {

        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .header("x-api-key", "reqres_4908ad6b88794dbd8e4ca6155e4758f9")
                .queryParam("page", 1)
                .when()
                .get("https://reqres.in/api/users?page=1");

        response.then()
                .statusCode(200);

        APiData = response.jsonPath().getString("data[0].first_name");
        System.out.println("API Data fetched: " + APiData);


    }

    @Test(priority = 2)
    public void PDF() {

        // Navigate to Website
        driver.get("https://smallpdf.com/edit-pdf");

        // Uplaod the file
        WebElement UploadFile = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//label[@Class='sc-16z3mvs-2 hdkHlv']//input[@type='file']")));

        // Send the file path
        UploadFile.sendKeys("D:\\dummy_pdf.pdf");

        // Click on the Text option
        WebElement ClickOnAddTextbtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='fx7l8p-0 jFCdPQ']//button)[4]")));
        ClickOnAddTextbtn.click();

        // Click on the Document page
        WebElement ClickOntheTextPage = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id='pageWidgetContainer1']")));
        ClickOntheTextPage.click();

        // Add the Api data on document page
        Actions act = new Actions(driver);
        act.sendKeys("Api Data add :" + APiData).perform();


        // Add one sign annotation
        // Add sign annotation
        WebElement ClickOnAddSignBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class='fx7l8p-0 jFCdPQ']//button)[8]")));
        ClickOnAddSignBtn.click();

        // Click on a new sign option
        WebElement ClickOnNewSignature = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='New signature']")));
        ClickOnNewSignature.click();

        // Click on the sign type
        WebElement ClickOnType = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//div[@class='ng9kw0-1 dpiSLm']//button[2])")));
        ClickOnType.click();


        // Click on Text type
        WebElement ClickOnTextFl = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='signature-text-input']")));
        ClickOnTextFl.sendKeys("Devendra");

        // Click on create button
        WebElement ClickOnCreate = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Create']")));
        ClickOnCreate.click();

        // Click on document page to add sign
        ClickOntheTextPage.click();

        // Click on download
        WebElement DowloadPdf = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Download']")));
        DowloadPdf.click();

    }

    @AfterClass
    public void quiteBroser() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
}
