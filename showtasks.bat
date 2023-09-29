call ./runcrud.bat
if "%ERRORLEVEL%" == "0" goto runbrowser
echo RUNCRUD build error
goto fail

:runbrowser
start chrome
if "%ERRORLEVEL%" == "0" goto crudpage
echo Cannot open web browser
goto fail

:crudpage
start http://localhost:8080/crud/v1/tasks
goto end

:fail
echo.
echo Build error

:end
echo.
echo Done.


