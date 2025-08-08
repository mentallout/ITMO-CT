#!/bin/bash

a=$1
b=$2
if [ "$a" -gt 0 ] && [ "$a" -lt "$b" ]
then
summa=0
for ((i=a; i<=b; i++))
do
summa=$((summa + i))
echo $summa
done
fi

