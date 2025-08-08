#!/bin/bash

width=$1
height=$2
if [ "$width" -gt 0 ] && [ "$height" -gt 0 ]
then
x=$((width / 2))
y=$((height / 2))
while true;
do
echo "x=$x;y=$y"
read -n 1 key
[[ "$key" == "q" ]] && break
case "$key" in
w|W)
((y++))
;;
s|S)
((y--))
;;
a|A)
((x--))
;;
d|D)
((x++))
;;
*)
continue
;;
esac
if [ "$x" -lt 0 ] || [ "$x" -gt "$width"] || [ "$y" -lt 0 ] || [ "$y" -gt "$height" ]
then
break
fi
done
fi

