#!/bin/bash
mkdir dist
javac -classpath . @files.txt -d ./dist
