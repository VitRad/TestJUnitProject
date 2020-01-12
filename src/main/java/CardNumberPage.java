import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CardNumberPage {
    WebDriver driver;
    By buttomContinue = By.xpath("//button[text()='Продолжить']");
    By cardNumberField = By.xpath("//input[@id='cardNumber']");
    By cardError1 = By.xpath("//p[contains(text(), 'Необходимо')]");
    By cardError2 = By.xpath("//p[contains(text(),'Вы неправильно')]");
    By cardError3 = By.xpath("//p[contains(text(), 'Введена')]");

    public CardNumberPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getErrorCard1(){
        WebElement cardEr = driver.findElement(cardError1);
        return cardEr.getText();
    }
    public String getErrorCard2(){
        WebElement cardEr = driver.findElement(cardError2);
        return cardEr.getText();
    }
    public String getErrorCard3(){
        WebElement cardEr = driver.findElement(cardError3);
        return cardEr.getText();
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