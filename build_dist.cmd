@echo off
call mvn clean install
xcopy target\vobuzzer.jar dist /Y