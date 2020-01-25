#!/bin/bash

CLASSPATH=.:.lift/*

cat input6.txt | java -ea -cp "$CLASSPATH"  BruteCollinearPoints 4
#cat grid4x4.txt | java -ea -cp "$CLASSPATH"  BruteCollinearPoints 55