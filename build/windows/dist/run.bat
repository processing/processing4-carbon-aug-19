@echo off

REM --- if you need more ram, change the 64m (which means 
REM --- 64 megabytes) to something higher. 

set SAVEDCP=%CLASSPATH%
set CLASSPATH=%CLASSPATH%;java\lib\rt.jar;lib;lib\build;lib\pde.jar;lib\kjc.jar;lib\oro.jar;lib\comm.jar;c:\winnt\system32\qtjava.zip;c:\windows\system32\qtjava.zip

java -ms64m -mx64m PdeBase

set CLASSPATH=%SAVEDCP%
