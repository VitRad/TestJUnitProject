import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import sun.awt.SunHints;

public class CardNumberPage {
    WebDriver driver;
    By buttomContinue = By.xpath("//button[text()='Продолжить']");
    By cardNumberField = By.xpath("//input[@id='cardNumber']");

    public CardNumberPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getErrorCard(){
        WebElement cardError = driver.findElement(By.xpath("//p[text()='Вы неправильно указали']"));
        return cardError.getText();
    }
    public WebElement findElement(By by){
        return driver.findElement(by);
    }

    public void setCardNumberToField(String s){
        WebElement textField = driver.findElement(cardNumberField);
        textField.sendKeys(s);
    }
    public String getCardNumberFromField(){
        WebElement textField = driver.findElement(cardNumberField);
        return textField.getAttribute("value").replaceAll(" ", "");
    }
    public SmsCodePage clickContinue(){
        WebElement butCont = driver.findElement(buttomContinue);
        butCont.click();
        return new SmsCodePage(driver);
    }

}