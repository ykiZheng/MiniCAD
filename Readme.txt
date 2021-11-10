文件内容如下：
jar：		包含jar文件
META_INF：	包含MANIFEST.MF文件
src：		包含所有源代码
doc：		实验报告
demoFile：	内涵两张可以打开的样例图片(.png& .miniCAD)
*.bat：		bat命令，方便运行


若要重新编译出jar文件，在当前目录运行make.bat；
若要运行，在保证jar文件存在情况下，运行run..bat；
上述命令会产生.class文件，若想清除，运行clean.bat；
若要删除全部.class文件与jar文件，只留下源代码，运行cleanall.bat。