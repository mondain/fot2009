SETLOCAL

SET FLEX_HOME=C:\dev\flex
SET PATH=C:\dev\flex\bin;%PATH%

java -cp %FLEX_HOME%;%FLEX_HOME%\frameworks -jar %FLEX_HOME%\lib\mxmlc.jar appserver261.mxml


ENDLOCAL

pause

