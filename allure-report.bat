@echo off
REM Путь к JSON-файлам тестов
set RESULTS_DIR=C:\Users\Artyom\Desktop\diploma\web-ui-testing\allure-results

REM Полный путь к allure.bat
set ALLURE_BIN="C:\Program Files\allure-2.38.1\bin\allure.bat"

echo Запуск Allure Serve для папки "%RESULTS_DIR%"...
%ALLURE_BIN% serve "%RESULTS_DIR%"

IF %ERRORLEVEL% EQU 0 (
    echo Отчет успешно открыт в браузере.
) ELSE (
    echo Ошибка при запуске Allure Serve.
    exit /b 1
)
pause
