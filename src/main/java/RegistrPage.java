import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RegistrPage {
    WebDriver driver;

    public RegistrPage(WebDriver driver) {
        this.driver = driver;
    }
    public String getErrorCard(){
        WebElement cardError = driver.findElement(By.xpath("//p[text()='Вы неправильно указали']"));
        return cardError.getText();
    }
    public String getErrorSMS(){
        WebElement smsError = driver.findElement(By.xpath("//div//p[contains(text(),'Неверный СМС')]"));
    return smsError.getText();
}
    public void setCardNumberToField(String s){
        WebElement textField = driver.findElement(By.xpath("//input[@id='cardNumber']"));
        textField.sendKeys(s);
        WebElement bottumContinue = driver.findElement(By.xpath("//button[text()='Продолжить']"));
        bottumContinue.click();
    }
    public void getSMSToField(String s){
        WebElement enterSMS = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        enterSMS.sendKeys(s);
        WebElement cont = driver.findElement(By.xpath("//button[text()='Продолжить']"));
        cont.click();
    }
}