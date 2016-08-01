#!/bin/sh

cd resource-watchStore

export TERM=${TERM:-dumb}

gradle -Dorg.gradle.native=false build

ls build/libs
cp build/libs/watchStore.jar ../resource-jar
cp Dockerfile ../resource-jar

