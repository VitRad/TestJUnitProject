import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class Test_id_001 {
    WebDriver driver;
    String cardNumber = "4276134032156792";
    String smsCode = "" + (int) (Math.random()*100000);
    @Before
    public void openPage(){
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://online.sberbank.ru/");
    }
    @After
    public void closeBrowser(){
        driver.close();
    }
    @Test
    public void test1() {
        //Поиск элемента "Регистрация"
        WebElement registrButtom = driver.findElement(By.xpath("//span[contains(text(),'Регистрация')]"));
        registrButtom.click();
        //Поиск блока "Регистрация в Сбербанк Онлайн"
        WebElement blockRegistrInSBOL = driver.findElement(By.xpath("//span[contains(text(),'Регистрация')]"));
        //Проверка, что блок существует и у него ожидаемое название
        assertEquals("Отсутствует блок 'Регистрация в Сбербанк Онлайн'", "Регистрация в Сбербанк Онлайн", blockRegistrInSBOL.getText());
        //Поиск блока для ввода номера карты
        WebElement blockEnterNumberCard = driver.findElement(By.xpath("//h1[contains(text(),'Введите номер карты')]"));
        //Проверка, что блок для ввода номера карты существует и у него ожидаемое название
        assertEquals("Отсутствует блок для ввода номера карты", "Введите номер карты", blockEnterNumberCard.getText());
        WebElement textField = driver.findElement(By.xpath("//input[@id='cardNumber']"));
        textField.sendKeys(cardNumber);
        //Проверка, что доступна кнопка "Продолжить"
        WebElement bottumContinue = driver.findElement(By.xpath("//button[text()='Продолжить']"));
        //Проверка, что у кнопки валидное название
        assertEquals("Невалидное название кнопки Продолжить", "Продолжить", bottumContinue.getText());
        bottumContinue.click();
        //Поиск поля для ввода СМС-кода

        WebElement enterSMS = driver.findElement(By.xpath("//input[@id='confirmPassword']"));
        //Ввести СМС-код
        enterSMS.sendKeys(smsCode);
        //Найти кнопку "Продолжить"
        WebElement continue2 = driver.findElement(By.xpath("//button[text()='Продолжить']"));
        //и нажать на нее
        continue2.click();
        WebElement smsError = driver.findElement(By.xpath("//div//p[contains(text(),'Неверный СМС')]"));
        assertEquals("Сообщение не соответствует ожидаемому", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", smsError.getText());
//        try{
//            WebElement createLoginAndPassword = driver.findElement(By.xpath("//h1[text()='Создайте логин и пароль']"));
//        } catch (Exception e){
//            System.out.println("Не перешли на страницу для создания логина и пароля");
//        }
    }
}
