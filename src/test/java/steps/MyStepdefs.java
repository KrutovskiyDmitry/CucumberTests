package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MyStepdefs extends MyHandlers {
    public WebDriver driver;
    public WebDriverWait wait;
    private static int total;


    @Before
        public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5000, TimeUnit.MILLISECONDS);
        wait = new WebDriverWait(driver, 5000);
        driver.get("http://invitro.ru/");

    }


    @Given ("^Открыта страница (.*) и закрыты всплывающие окна$")
    public void navigateToPage(String url) {
        driver.navigate().to(url);
        try {
            driver.findElement(By.xpath("//div[@class='close-block']")).click();
            driver.findElement(By.cssSelector(".attention-close-button")).click();
            driver.findElement(By.cssSelector(".btn--narrow.city__confirm-btn")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @When("^Прокликать все пункты и подпункты меню$")
    public void getArrayOfMenues() {
        driver.navigate().to("http://invitro.ru/radiology/");
        List<WebElement> elementList  =driver
                .findElements(By.xpath("//ul[@class='side-bar-second__list']/descendant::a"));
        int numberOfListElements = elementList.size();
//перебор списка элементов меню и обновление списка, т.к. перестраивается DOM после каждого обносления страницы
        for (int i = 0; i < numberOfListElements ; i++){
            elementList.get(i).click();
            elementList = driver.findElements(By.xpath("//ul[@class='side-bar-second__list']/descendant::a"));
            }
        }


    @When("^Нажать кнопку \"Ваш город\"$")
    public void pressYourCity() {
        driver.findElement(By.xpath("//span[@class='city__name city__btn city__name--label']")).click();
    }

    @When("^Нажать кнопку \"Выбрать другой\"$")
    public  void pressChangeCity() {
        driver.findElement(By.xpath("//a[@class='btn btn--narrow btn--empty city__change-btn']")).click();
    }
    @When("^Выбрать (.*)$")
    public void selectCity (String text) {
        try {
            driver.findElement(By.linkText(text)).click();
        } catch (Exception e) {
            System.out.println("Элемент с названием " + text + " некликабелен");
            System.out.println("Возможно, эта страница уже загружена");
        }
    }

    @Then("^На главной странице выбран (.*)$")
    public void checkCity (String currentCity) {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//span[@class='city__name city__btn city__name--label']")));
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='city__name city__btn city__name--label']"))
                .getText().equalsIgnoreCase(currentCity));
    }

    @When("^Нажать кнопку \"Получить результаты анализов\"$")
    public void pressGetAnalyzesButton() {
        driver.findElement(By.xpath("//div[@class='close-block']")).click();
        driver.findElement(By.xpath("//button[@data-mfp-src='#popupResult']")).click();
    }

    @Then("^Проверить доступность всплывающего окна$")
    public void checkPopUp() {
        Assert.assertTrue(driver.findElement(By.id("popupResult")).isDisplayed());
    }

    @When("^Нажать кнопку \"Найти результаты\"$")
    public void pressSubmit() {
        driver.findElement(By.xpath("//button[@name='findSubmit']")).click();
    }

    @Then("^Проверить наличие сообщения (.*)$")
    public void checkMessage(String message) {
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath("//div[@class='attention__content']/div")));
        Assert.assertTrue((driver.findElement(By.xpath("//div[@class='attention__content']/div"))
                .getText().equalsIgnoreCase(message)));
    }

    @When("^Ввести в поле (.*) значение (.*)$")
    public void fillFields(String id, String string){
        driver.findElement(By.id(id)).click();
        driver.findElement(By.id(id)).sendKeys(string);

    }

    @When("^Перейти на страницу выбора анализов$")
    public void navigateToAnalyzes () {
        driver.navigate().to("https://www.invitro.ru/analizes/for-doctors/");
    }

    @When("^Запомнить стоимость услуги$")
    public int getPriceOfService () {
        // суммирование основной услуги и дополнительной
        int subtotal = refineAndParse(driver.findElement(By
                .xpath("//div[@class='info-block__section info-block__section--price']/span[@class='info-block__price']")));
        total = subtotal + refineAndParse(driver
                .findElement(By.xpath("//div[@class='info-overprice']/ul[@class='info-overprice__list']")));
        return(total);
    }
    @When("^Добавить услугу в корзину$")
    public void buyService() {
        try {
            driver.findElement(By.xpath("//div[@id='monitoring_close']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver.findElement(By.xpath("//div[@class='btn-icon btn-icon--fill ripple']")).click();
    }

    @When("^Перейти в корзину$")
    public void goToBasket() {
            //driver.findElement(By.xpath("//a[@href='/personal/basket/']")).click();
        driver.navigate().to("https://www.invitro.ru/personal/basket/");
        }


    @When("^Сравнить стоимость услуги и стоимость заказа в корзине$")
    public void compareTotalAndBusket() {
        //refineAndParse убирает лишние символы из цены услуги и приводит к типу int
        int basket = refineAndParse(driver.findElement(By.xpath("//div[@class='total-price-price']")));
        Assert.assertEquals(total, basket);
    }

    @When("^Выполнить поиск ([0-9]*)$")
    public void searchServiceById (String id) {
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys(id, Keys.ENTER);
    }


    @Then ("^Проверить что текущая страница (.*)$")
    public void selectMenu(String menuName) {
        Assert.assertEquals(driver.getCurrentUrl(), menuName);
    }


    @After
    public void finishFeature() {
        driver.quit();
    }

    @And("^Найти (.*)")
    public void searchForService(String service) {
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys(service, Keys.ENTER);
    }
}
