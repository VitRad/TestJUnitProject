import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SmsCodePage {
    WebDriver driver;
    By SMSCode = By.xpath("//input[@id='confirmPassword']");
    By cont = By.xpath("//button[text()='Продолжить']");
    By newLogin = By.xpath("//input[@id='login']");
    By smsEr = By.xpath("//div//p[contains(text(),'СМС')]");
    By so = By.xpath("//button[text()='Начать заново']");
    public SmsCodePage(WebDriver driver) {
        this.driver = driver;
    }
    public void setSMSToField(String s){
        WebElement enterSMS = driver.findElement(SMSCode);
        enterSMS.sendKeys(s);
    }
    public void clickContinue(){
        WebElement con = driver.findElement(cont);
        con.click();
    }
    public WebElement findElement(By by){
        return driver.findElement(by);
    }
    public String getErSMS(){
        WebElement smsError = driver.findElement(smsEr);
        return smsError.getText();
    }
    public CardNumberPage pressStartOver(){
        WebElement we = driver.findElement(so);
        we.click();
        return new CardNumberPage(driver);
    }
    public String getSmsCodeFromField() {
        WebElement textField = driver.findElement(SMSCode);
        return textField.getAttribute("value").replaceAll(" ", "");
    }
}
