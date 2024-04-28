@echo off
setlocal enabledelayedexpansion

REM 현재 디렉토리에서 JPG 파일의 확장자 제거
for %%i in (*.jpg) do (
    set "filename=%%~ni"
    ren "%%i" "!filename!"
)

REM 현재 디렉토리에서 PNG 파일의 확장자 제거
for %%i in (*.png) do (
    set "filename=%%~ni"
    ren "%%i" "!filename!"
)

endlocal
