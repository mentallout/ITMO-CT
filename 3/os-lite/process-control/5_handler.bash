#!/usr/bin/bash

MY_PIPE="$1"
PRODUCER=$(cat .producer_pid)
MODE="SUM"
RESULT=1

while true
do
read -r LINE < "$MY_PIPE"
case "$LINE" in
"+")
MODE="SUM"
;;
"*")
MODE="MULT"
;;
"QUIT")
echo "Quitting"
kill "$PRODUCER"
exit 0
;;
*)
if [[ "$LINE" =~ ^-?[[:digit:]]+$ ]]
then
if [ "$MODE" = "+" ]
then
RESULT=$((RESULT + LINE))
elif [ "$MODE" = "MULT" ]
then
RESULT=$((RESULT * LINE))
fi
echo "$RESULT"
else
echo "Invalid input"
kill "$PRODUCER"
exit 1
fi
;;
esac
done

