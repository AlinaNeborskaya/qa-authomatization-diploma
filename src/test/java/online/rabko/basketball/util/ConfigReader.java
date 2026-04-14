package online.rabko.basketball.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Properties;

/**
 * Утилитный класс для работы с конфигурационными данными тестового приложения.
 * <p>
 * Все настройки загружаются из файла {@code application.properties}, расположенного в {@code src/test/resources}.
 * Класс предоставляет статические методы для получения email, пароля, имени пользователя и хоста API.
 * Пароли хранятся в Base64 и автоматически декодируются при чтении.
 * </p>
 *
 * <p><b>Особенности:</b></p>
 * <ul>
 *     <li>Файл конфигурации загружается один раз при первом обращении к классу</li>
 *     <li>Методы безопасно возвращают данные, не раскрывая внутренние детали загрузки</li>
 *     <li>Пароли декодируются автоматически из Base64</li>
 *     <li>Поддерживаются отдельные настройки для email/пароля пользователя и для API пользователя</li>
 * </ul>
 *
 * <p><b>Пример использования:</b></p>
 * <pre>{@code
 * // Получение настроек пользователя
 * String email = ConfigReader.getEmail();
 * String password = ConfigReader.getPassword();
 *
 * // Получение настроек API
 * String apiHost = ConfigReader.getApiHost();
 * String apiUsername = ConfigReader.getApiUsername();
 * String apiPassword = ConfigReader.getApiUserPassword();
 * }</pre>
 */
public class ConfigReader {

    /**
     * Объект {@link Properties} для хранения значений из файла конфигурации.
     */
    private static final Properties properties = new Properties();

    /**
     * Статический блок инициализации.
     * <p>
     * Загружает свойства из {@code application.properties} при первом обращении к классу.
     * В случае ошибки чтения или отсутствия файла выбрасывается {@link RuntimeException}.
     * </p>
     */
    static {
        try (InputStream input = ConfigReader.class.getClassLoader()
            .getResourceAsStream("application.properties")) {

            if (input == null) {
                throw new RuntimeException("Не найден файл application.properties в resources");
            }

            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке application.properties", e);
        }
    }

    /**
     * Возвращает email тестового пользователя.
     *
     * @return email пользователя
     */
    public static String getEmail() {
        return properties.getProperty("app.user.email");
    }

    /**
     * Возвращает пароль тестового пользователя.
     * <p>
     * Пароль хранится в Base64 и автоматически декодируется.
     * </p>
     *
     * @return декодированный пароль пользователя
     */
    public static String getPassword() {
        String base64Password = properties.getProperty("app.user.password");
        return new String(Base64.getDecoder().decode(base64Password));
    }

    /**
     * Возвращает адрес хоста API.
     *
     * @return URL API
     */
    public static String getApiHost() {
        return properties.getProperty("api.host");
    }

    /**
     * Возвращает имя пользователя для API.
     *
     * @return имя пользователя API
     */
    public static String getApiUsername() {
        return properties.getProperty("api.user.name");
    }

    /**
     * Возвращает пароль для API пользователя.
     * <p>
     * Пароль хранится в Base64 и декодируется автоматически.
     * </p>
     *
     * @return декодированный пароль API пользователя
     */
    public static String getApiUserPassword() {
        String base64Password = properties.getProperty("api.user.password");
        return new String(Base64.getDecoder().decode(base64Password));
    }
}
