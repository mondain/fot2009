
SETLOCAL

set RED5_HOME=c:\dev\red5\java\server\trunk\dist
REM set JAVA_HOME=c:\dev\java5
REM set PATH=c:\dev\java5\bin;%PATH%

REM ant -Djava.target_version=1.5 -Djava.version.name=java5 -Dred5.root=%RED5_HOME% clean war upload-snapshot
ant -Dred5.root=%RED5_HOME% clean war

ENDLOCAL
