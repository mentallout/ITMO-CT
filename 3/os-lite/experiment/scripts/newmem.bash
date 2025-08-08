#!/usr/bin/bash

arr=()
count=0
N=$1

while true
do
arr+=(1 2 3 4 5)
((count++))
if (( ${#arr[@]} > N ))
then
break
fi
done

