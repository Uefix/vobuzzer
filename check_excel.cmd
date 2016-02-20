@echo off
java -jar dist\vobuzzer.jar -checkexcel daten\Fragenkatalog.xlsx
if "%1"=="" pause