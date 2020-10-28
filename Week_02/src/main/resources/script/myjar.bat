@echo off

set "t=%time: =0%"
set timeString=%t::=.%
set printGC=-XX:+PrintGCDetails

set gcs=%~1
set ram=%~2
set class=%~3

if %~1==sgc (
set gc=-XX:+UseSerialGC
) ^
else if %~1==pgc (
set gc=-XX:+UseParallelGC
) ^
else if %~1==cms (
set gc=-XX:+UseConcMarkSweepGC
) ^
else if %~1==g1 (
set gc=-XX:+UseG1GC
set printGC=-XX:+PrintGC -XX:MaxGCPauseMillis=10
)
set fileName=%timeString%_%gc:~5%_%ram%

java %gc% -Xms%ram% -Xmx%ram% -Xloggc:%fileName%.log %printGC% -XX:+PrintGCDateStamps %class% %fileName%
