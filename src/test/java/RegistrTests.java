import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;

public class RegistrTests {
    private WebDriver driver;
    String cardNumber = "4276134032156792";
    String smsCode = "" + (int) (Math.random()*100000);
    @Before
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
        driver.get("https://online.sberbank.ru/");
    }
    @After
    public void closeBrowser(){
        driver.close();
    }
    @Test
    public void Reg_wrongSMS() {
        AuthPage authPage = new AuthPage(driver);
        RegistrPage registrPage = authPage.setRegButtom();
        registrPage.setCardNumberToField(cardNumber);
        registrPage.getSMSToField(smsCode);
        assertEquals("Сообщение не соответствует ожидаемому", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", registrPage.getErrorSMS());
    }
    @Test
    public void Reg_invalidCardNumber() {
        AuthPage authPage = new AuthPage(driver);
        RegistrPage registrPage = authPage.setRegButtom();
        registrPage.setCardNumberToField(cardNumber + 1);
        assertEquals("Ошибка в номере карты", "Вы неправильно указали\n" +
                "номер карты. Пожалуйста,\n" +
                "проверьте количество цифр\n" +
                "введённого номера карты\n" +
                "и их последовательность.", registrPage.getErrorCard());
    }
}

