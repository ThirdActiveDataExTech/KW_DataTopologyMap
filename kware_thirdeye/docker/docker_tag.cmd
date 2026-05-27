@echo off
setlocal

REM Check VERSION variable
if "%~1"=="" (
  echo Error: VERSION not provided
  echo Usage: %~nx0 ^<VERSION^>
  exit /b 1
)

set VERSION=%~1
set REPO=rnd.kware.co.kr
set IMAGE_NAME=kware-thirdeye

REM Docker login
docker login %REPO%

REM Docker tag setup
docker tag %IMAGE_NAME%:latest %REPO%/%IMAGE_NAME%:latest
docker tag %IMAGE_NAME%:latest %REPO%/%IMAGE_NAME%:%VERSION%

REM Check tag result
if %ERRORLEVEL% equ 0 (
  echo Docker tags applied successfully for %IMAGE_NAME% version %VERSION% and latest
) else (
  echo Error: Docker tag failed
  exit /b 1
)

endlocal
