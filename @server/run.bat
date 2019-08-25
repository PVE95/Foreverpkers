@echo off
title RetroScape
color 7c
"C:\Program Files\Java\jdk1.8.0_171\bin\java.exe" -Xms256m -Xmx512m -cp bin;deps/JavaBridge.jar;deps/php-script.jar;deps/php-servlet.jar;deps/gson-2.2.4.jar;deps/poi.jar;deps/mysql.jar;deps/mina.jar;deps/slf4j.jar;deps/slf4j-nop.jar;deps/jython.jar;log4j-1.2.15.jar;deps/everythingrs-api.jar; server.Server
pause