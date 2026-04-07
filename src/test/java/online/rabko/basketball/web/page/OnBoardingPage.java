package online.rabko.basketball.web.page;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object для страницы OnBoarding.
 * <p>
 * Содержит методы проверки загрузки страницы и перехода на Dashboard.
 * </p>
 */
public class OnBoardingPage extends BasePage<OnBoardingPage> {

    private final WebDriverWait wait;
    private final By dashboardLinkLocator = By.cssSelector("[data-testid='onboardingSidebarDashboard']");

    public OnBoardingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException("Onboarding page cannot be opened directly");
    }

    /**
     * Проверяет, что страница OnBoarding успешно загружена.
     * <p>
     * Ожидает, что заголовок страницы содержит "Onboarding".
     * </p>
     *
     * @throws IllegalStateException если страница не загрузилась
     */
    @Override
    @Step("Проверка, что страница OnBoarding загружена")
    protected void isLoaded() {
        try {
            // Ждем, пока заголовок страницы содержит "Onboarding"
            boolean titleIsCorrect = wait.until(driver -> driver.getTitle().contains("Onboarding"));

            // Если условие не выполнено, кидаем исключение
            if (!titleIsCorrect) {
                throw new IllegalStateException("OnBoarding page not loaded (title mismatch)");
            }

        } catch (Exception e) {
            throw new IllegalStateException("OnBoarding page не загрузилась корректно", e);
        }
    }

    /**
     * Проверяет, открыта ли страница OnBoarding.
     *
     * @return true если заголовок содержит "Onboarding", иначе false
     */
    @Step("Проверить, что страница OnBoarding открыта")
    public boolean isPageOpened() {
        try {
            return wait.until(driver -> driver.getTitle().contains("Onboarding"));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Переходит на страницу Dashboard.
     *
     * @return объект {@link DashboardPage}
     */
    @Step("Перейти на страницу Dashboard")
    public DashboardPage goToDashboard() {
        WebElement dashboardLink = wait.until(ExpectedConditions.elementToBeClickable(dashboardLinkLocator));
        dashboardLink.click();
        DashboardPage dashboardPage = new DashboardPage(driver);
        dashboardPage.get();
        return dashboardPage;
    }
}
