@echo off
@title make jar files
cd src
javac -encoding utf-8 -d ../bin Main.java
cd ..
md bin\icon
md jar
copy src\icon\*.png bin\icon
cd bin
jar cvfm ../jar/test.jar ../META-INF/Manifest.MF shapes utils view icon *.class
cd ..
echo make success.
pause