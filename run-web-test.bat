@echo off
echo ==============================
echo Running selected JUnit tests via Gradle
echo ==============================
gradlew.bat test  --tests "online.rabko.basketball.web.test.DashboardTest" ^
                  --tests "online.rabko.basketball.web.test.LoginTest" ^
                  --tests "online.rabko.basketball.web.test.OnBoardingTest" ^
                  --tests "online.rabko.basketball.web.test.ProjectTest"
IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo Some tests failed! Check the output above for details.
    exit /b %ERRORLEVEL%
) ELSE (
    echo.
    echo All selected tests passed successfully!
)
pause
