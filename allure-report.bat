@echo off
set RESULTS_DIR=/allure-results


echo Запуск Allure Serve для папки "%RESULTS_DIR%"...
allure serve "%RESULTS_DIR%"

IF %ERRORLEVEL% EQU 0 (
    echo Отчет успешно открыт в браузере.
) ELSE (
    echo Ошибка при запуске Allure Serve.
    exit /b 1
)
pause
