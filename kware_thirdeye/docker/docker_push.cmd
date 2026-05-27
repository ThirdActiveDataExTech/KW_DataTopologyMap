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

REM Docker push setup
docker push %REPO%/%IMAGE_NAME%:latest
docker push %REPO%/%IMAGE_NAME%:%VERSION%

REM Check push result
if %ERRORLEVEL% equ 0 (
  echo Docker push applied successfully for %IMAGE_NAME% version %VERSION% and latest
) else (
  echo Error: Docker push failed
  exit /b 1
)

endlocal
