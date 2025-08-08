#!/usr/bin/bash

K=$1

for (( i=0;i<K;i++ ))
do
./newmem.bash 9000000 &
sleep 1
done

