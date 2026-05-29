@echo off
REM ============================================================
REM  start.bat — Inicia el proyecto Parqueadero en Windows
REM ============================================================
REM  Este script:
REM    1. Lee las variables del archivo .env de la raiz
REM    2. Las exporta como variables de entorno
REM    3. Entra a la carpeta backend/ y arranca con Maven
REM
REM  Para usar: doble clic en este archivo, o ejecutar en terminal
REM ============================================================

echo.
echo  Leyendo configuracion desde .env...

REM Leer cada linea que no sea comentario (#) ni vacia
for /f "usebackq tokens=1,* delims==" %%a in (`findstr /v "^#" .env ^| findstr /v "^$"`) do (
    set "%%a=%%b"
)

echo  Configuracion cargada.
echo.
echo  Iniciando backend...
echo  Espera hasta ver: SISTEMA INICIADO CORRECTAMENTE
echo.

cd backend
mvn spring-boot:run
