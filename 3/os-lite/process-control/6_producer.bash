#!/usr/bin/bash

HANDLER="$1"

while true
do
read -r LINE
case $LINE in
"+")
kill -USR1 "$HANDLER"
;;
"*")
kill -USR2 "$HANDLER"
;;
"TERM")
echo "Quitting"
kill -SIGTERM "$HANDLER"
exit
;;
*)
;;
esac
done

