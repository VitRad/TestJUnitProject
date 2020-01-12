import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;

public class RegistrTests {
    private WebDriver driver;
    String cardNumber = "4276134032156792";
    String invalidCard = "АбAb[/?.%&#@!$:";
    int num = 99999 + new Random().nextInt(99999 - 1);
    int num1 = 999 + new Random().nextInt(999 - 1);
    String smsCode = "" + num;
    String smsCodeShort = "" + num1;

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
    public void TS_001_CheckActiveContinuePozitive(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        WebElement cont = cardNumberPage.findElement(cardNumberPage.buttomContinue);
        assertTrue(cont.isEnabled());
    }
    @Test
    public void TS_002_CheckActiveContinueNegative_1(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber+1);
        WebElement cont = cardNumberPage.findElement(cardNumberPage.buttomContinue);
        assertFalse(cont.isEnabled());
    }
    @Test
    public void TS_003_CheckActiveContinueNegative_2(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        WebElement cont = cardNumberPage.findElement(cardNumberPage.buttomContinue);
        assertFalse(cont.isEnabled());
    }
    @Test
    public void TS_004_CheckCardNumberFieldNegative_1(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(invalidCard);
        WebElement we = cardNumberPage.findElement(cardNumberPage.cardNumberField);
        we.sendKeys(Keys.BACK_SPACE);
        String s = cardNumberPage.getErrorCard1();
        String text = cardNumberPage.getCardNumberFromField();
        assertEquals("Ошибка ввода в поле", "", text);
        WebElement cont = cardNumberPage.findElement(cardNumberPage.buttomContinue);
        assertFalse(cont.isEnabled());
        assertEquals("Некорректное сообщение", "Необходимо ввести номер вашей карты Сбербанка",s);

    }
    @Test
    public void TS_005_CheckCardNumberFieldLength(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber + cardNumber);
        String text = cardNumberPage.getCardNumberFromField();
        assertEquals("Ошибка ввода в поле", 18, text.length());
    }
    @Test
    public void TS_006_InvalidCardNumber(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber + cardNumber);
        WebElement we = cardNumberPage.findElement(cardNumberPage.cardNumberField);
        we.sendKeys(Keys.BACK_SPACE);
        String s = cardNumberPage.getErrorCard2();
        assertEquals("Некорректное сообщение",
                "Вы неправильно указали\n" +
                        "номер карты. Пожалуйста,\n" +
                        "проверьте количество цифр\n" +
                        "введённого номера карты\n" +
                        "и их последовательность.",s);
    }
    @Test
    public void TS_007_NotSberCardNumber(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField("1");
        String s = cardNumberPage.getErrorCard3();
        assertEquals("Некорректное сообщение",
                "Введена карта другого банка. " +
                        "Пожалуйста, используйте карту " +
                        "Сбербанка или проверьте корректность введённого номера.",s);
    }
    @Test
    public void TS_008_CheckContinueButtom(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        WebElement smsField = smsCodePage.findElement(smsCodePage.SMSCode);
        assertTrue(smsField.isEnabled());
    }
    @Test
    public void TS_009_ValidCardNumberValidSmsCode(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        WebElement newLogin = smsCodePage.findElement(smsCodePage.newLogin);
        assertTrue(newLogin.isEnabled());
    }
    @Test
    public void TS_010_ValidCardNumberInvalidSmsCode_Er1(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", s);
    }
    @Test
    public void TS_011_ValidCardNumberInvalidSmsCode_Er2(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", s);
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s2 = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталась 1 попытка", s2);
    }
    @Test
    public void TS_012_ValidCardNumberInvalidSmsCode_Er3(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", s);
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s2 = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталась 1 попытка", s2);
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s3 = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Попытки ввода СМС-кода исчерпаны", s3);
        WebElement sms = driver.findElement(smsCodePage.SMSCode);
        assertFalse(sms.isEnabled());
    }

    @Test
    public void TS_013_ValidCardNumberInvalidSmsCodeStartOver(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталось 2 попытки", s);
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s2 = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Неверный СМС-код.\n" +
                "У вас осталась 1 попытка", s2);
        smsCodePage.setSMSToField(smsCode);
        smsCodePage.clickContinue();
        String s3 = smsCodePage.getErSMS();
        assertEquals("Неверный текст ошибки", "Попытки ввода СМС-кода исчерпаны", s3);
        WebElement sms = smsCodePage.findElement(smsCodePage.SMSCode);
        assertFalse(sms.isEnabled());
        CardNumberPage cardNumberPage2 = smsCodePage.pressStartOver();
        WebElement cnf = cardNumberPage2.findElement(cardNumberPage2.cardNumberField);
        assertTrue(cnf.isEnabled());
    }
    @Test
    public void TS_014_ValidCardNumberInvalidSmsCode_Short(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(smsCodeShort);
        smsCodePage.clickContinue();
        WebElement enterSMS = smsCodePage.findElement(smsCodePage.SMSCode);
        assertTrue(enterSMS.isEnabled());
    }
    @Test
    public void TS_015_ValidCardNumberNullSmsCode(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.clickContinue();
        WebElement enterSMS = smsCodePage.findElement(smsCodePage.SMSCode);
        assertTrue(enterSMS.isEnabled());
    }
    @Test
    public void TS_016_ValidCardNumberStringSmsCode(){
        AuthPage authPage = new AuthPage(driver);
        CardNumberPage cardNumberPage = authPage.setRegButtom();
        cardNumberPage.setCardNumberToField(cardNumber);
        SmsCodePage smsCodePage = cardNumberPage.clickContinue();
        smsCodePage.setSMSToField(invalidCard);
        String text = smsCodePage.getSmsCodeFromField();
        assertEquals("Ошибка ввода в поле", "", text);
        smsCodePage.clickContinue();
        WebElement enterSMS = smsCodePage.findElement(smsCodePage.SMSCode);
        assertTrue(enterSMS.isEnabled());
    }
}

