#!/bin/sh

cd src/com/github/mithunder/parser/
java -cp \
/usr/share/java/antlr3.jar:/usr/share/java/antlr3-runtime.jar:/usr/share/java/stringtemplate.jar \
org.antlr.Tool \
GuardCommand.g 
