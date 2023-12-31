#!/bin/bash

javac -d classes -cp classes -sourcepath src  \
  src/com/tarek/Main.java \
  src/com/tarek/GUI/*.java \
  src/com/tarek/GUI/annotations/*.java \
  src/com/tarek/GUI/player/*.java \
  src/com/tarek/GUI/recorder/*.java \
  src/com/tarek/player/*.java \
  && java -cp classes com.tarek.Main
