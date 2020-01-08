import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AuthPage {
    WebDriver driver;

    public AuthPage(WebDriver driver){
        this.driver = driver;
    }

    private By regButtom = By.xpath("//span[contains(text(),'Регистрация')]");

    public RegistrPage setRegButtom(){
        driver.findElement(regButtom).click();
        return new RegistrPage(driver);
    }
}
