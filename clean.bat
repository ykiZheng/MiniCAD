@echo off
@title 批处理删除class所在文件夹

if exist bin (rmdir /s /q bin) else (echo no class file need to be cleanned )