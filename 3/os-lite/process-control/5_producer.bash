#!/usr/bin/bash

MY_PIPE="$1"
echo $$ > .producer_pid

while true
do
read -r LINE
echo "$LINE" > "$MY_PIPE"
done

