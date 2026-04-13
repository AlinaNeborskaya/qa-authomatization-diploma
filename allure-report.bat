@echo off

set RESULTS_DIR=allure-results
set REPORT_DIR=allure-report

echo Generating Allure report...

allure generate %RESULTS_DIR% --clean -o %REPORT_DIR%

IF %ERRORLEVEL% EQU 0 (
    echo Allure report generated successfully.
) ELSE (
    echo Error generating Allure report.
    exit /b 1
)
