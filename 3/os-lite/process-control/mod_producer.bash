#!/usr/bin/bash

PIPE="chess"

if [[ ! -p $PIPE ]]
then
mkfifo $PIPE
fi

while true
do
read -p x y x2 y2
echo "$x $y $x2 $y2" > $PIPE
done

