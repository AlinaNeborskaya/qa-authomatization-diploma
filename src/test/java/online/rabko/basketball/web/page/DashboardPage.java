package online.rabko.basketball.web.page;

import io.qameta.allure.Step;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Page Object для страницы Dashboard.
 * <p>
 * Отвечает за взаимодействие с главной страницей приложения после авторизации.
 * Содержит методы для проверки загрузки страницы и перехода к созданию проекта.
 * </p>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Страница не может быть открыта напрямую (только после логина)</li>
 *     <li>Проверка загрузки выполняется по заголовку страницы</li>
 *     <li>Используется {@link WebDriverWait} для ожиданий</li>
 * </ul>
 *
 * @author YourName
 */
public class DashboardPage extends BasePage<DashboardPage> {

    /**
     * Таймаут ожидания элементов на странице.
     */
    private static final Duration TIMEOUT = Duration.ofSeconds(15);

    /**
     * Экземпляр ожидания для синхронизации с DOM.
     */
    private final WebDriverWait wait;

    /**
     * Локатор кнопки добавления проекта.
     */
    private final By addProjectButtonLocator = By.cssSelector("[data-testid='sidebarProjectsAddButton']");

    /**
     * Локатор заголовка страницы Dashboard.
     */
    private final By dashboardHeaderLocator = By.cssSelector("[data-testid='testCaseContentHeaderTitle']");

    /**
     * Конструктор страницы Dashboard.
     *
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        super(driver);
        this.driver = driver;
        this.wait = new WebDriverWait(driver, TIMEOUT);
    }

    /**
     * Метод загрузки страницы.
     *
     * @throws UnsupportedOperationException так как страница не открывается напрямую
     */
    @Override
    protected void load() {
        throw new UnsupportedOperationException("Dashboard page cannot be opened directly");
    }

    /**
     * Проверяет, что страница успешно загружена.
     * <p>
     * Ожидает появления заголовка и сравнивает его текст с ожидаемым значением.
     * </p>
     *
     * @throws IllegalStateException если страница не загрузилась или заголовок не совпадает
     */
    @Override
    @Step("Проверка, что страница Dashboard загружена")
    protected void isLoaded() throws Error {
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
    }

    /**
     * Проверяет, открыта ли страница Dashboard.
     *
     * @return true если страница отображается и заголовок совпадает, иначе false
     */
    @Step("Проверить, что страница Dashboard открыта")
    public boolean isPageOpened() {
        WebDriverWait wait = new WebDriverWait(driver, TIMEOUT);
        try {
            WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardHeaderLocator));
            return header.getText().trim().equals("Dashboard");
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Получает текст заголовка страницы Dashboard.
     * <p>
     * Ожидает появления элемента заголовка на странице и возвращает его текстовое содержимое
     * без ведущих и завершающих пробелов.
     * </p>
     *
     * <p><b>Особенности:</b></p>
     * <ul>
     *     <li>Использует явное ожидание ({@link WebDriverWait})</li>
     *     <li>Гарантирует, что элемент видим перед получением текста</li>
     *     <li>Возвращает очищенную строку ({@code trim()})</li>
     * </ul>
     *
     * @return текст заголовка страницы Dashboard
     * @throws org.openqa.selenium.TimeoutException если элемент не появился за отведённое время
     */
    @Step("Получить текст заголовка Dashboard")
    public String getHeaderText() {
        return wait.until(ExpectedConditions
                        .visibilityOfElementLocated(dashboardHeaderLocator))
                .getText()
                .trim();
    }

    /**
     * Переходит на страницу создания/просмотра проекта.
     * <p>
     * Кликает по кнопке добавления проекта и ожидает загрузки страницы проекта.
     * </p>
     *
     * @return объект {@link ProjectPage}
     */
    @Step("Перейти на страницу с проектами=")
    public ProjectPage goToProject() {
        WebElement projectLink = wait.until(ExpectedConditions.elementToBeClickable(addProjectButtonLocator));
        projectLink.click();
        ProjectPage projectPage = new ProjectPage(driver);
        projectPage.get();
        return projectPage;
    }
}
