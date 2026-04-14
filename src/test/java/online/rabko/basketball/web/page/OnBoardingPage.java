package online.rabko.basketball.web.page;

import io.qameta.allure.Allure;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * Page Object для страницы OnBoarding.
 */
public class OnBoardingPage extends BasePage<OnBoardingPage> {

    private final WebDriverWait wait;

    private final By dashboardLinkLocator =
        By.cssSelector("[data-testid='onboardingSidebarDashboard']");

    public OnBoardingPage(WebDriver driver) {
        super(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException(
            "Onboarding page cannot be opened directly"
        );
    }

    @Override
    protected void isLoaded() {
        Allure.step("Проверка, что страница OnBoarding загружена", () -> {
            try {
                boolean titleIsCorrect =
                    wait.until(driver -> driver.getTitle().contains("Onboarding"));

                if (!titleIsCorrect) {
                    throw new IllegalStateException(
                        "OnBoarding page not loaded (title mismatch)"
                    );
                }

            } catch (Exception e) {
                throw new IllegalStateException(
                    "OnBoarding page не загрузилась корректно", e
                );
            }
        });
    }

    public boolean isPageOpened() {
        return Allure.step("Проверить, что страница OnBoarding открыта", () -> {
            try {
                return wait.until(driver ->
                    driver.getTitle().contains("Onboarding")
                );
            } catch (Exception e) {
                return false;
            }
        });
    }

    public DashboardPage goToDashboard() {
        return Allure.step("Перейти на страницу Dashboard", () -> {
            WebElement dashboardLink =
                wait.until(ExpectedConditions.elementToBeClickable(dashboardLinkLocator));

            dashboardLink.click();

            DashboardPage dashboardPage = new DashboardPage(driver);
            dashboardPage.get();

            return dashboardPage;
        });
    }
}
