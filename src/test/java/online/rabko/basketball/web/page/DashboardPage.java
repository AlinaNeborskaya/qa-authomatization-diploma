package online.rabko.basketball.web.page;

import io.qameta.allure.Allure;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы Dashboard.
 */
public class DashboardPage extends BasePage<DashboardPage> {

    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    private final WebDriverWait wait;

    private final By addProjectButtonLocator =
        By.cssSelector("[data-testid='sidebarProjectsAddButton']");

    private final By dashboardHeaderLocator =
        By.cssSelector("[data-testid='testCaseContentHeaderTitle']");

    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    @Override
    protected void load() {
        throw new UnsupportedOperationException(
            "Dashboard page cannot be opened directly"
        );
    }

    @Override
    protected void isLoaded() {
        Allure.step("Проверка, что страница Dashboard загружена", () -> {
            try {
                String headerText = getHeaderText();

                if (!"Dashboard".equals(headerText)) {
                    throw new IllegalStateException(
                        "Ожидался заголовок 'Dashboard', но был: " + headerText
                    );
                }

            } catch (Exception e) {
                throw new IllegalStateException("Dashboard page не загрузилась", e);
            }
        });
    }

    public boolean isPageOpened() {
        return Allure.step("Проверить, что страница Dashboard открыта", () -> {
            try {
                WebElement header = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(dashboardHeaderLocator)
                );

                return header.getText().trim().equals("Dashboard");

            } catch (Exception e) {
                return false;
            }
        });
    }

    public String getHeaderText() {
        return Allure.step("Получить текст заголовка Dashboard", () ->
            wait.until(ExpectedConditions
                    .visibilityOfElementLocated(dashboardHeaderLocator))
                .getText()
                .trim()
        );
    }

    public ProjectPage goToProject() {
        return Allure.step("Перейти на страницу проектов", () -> {
            WebElement projectLink =
                wait.until(ExpectedConditions.elementToBeClickable(addProjectButtonLocator));

            projectLink.click();

            ProjectPage projectPage = new ProjectPage(driver);
            projectPage.get();

            return projectPage;
        });
    }
}
