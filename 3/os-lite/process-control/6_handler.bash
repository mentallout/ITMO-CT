#!/usr/bin/bash

VALUE=1
trap 'VALUE=$((VALUE + 2)); echo "Current value: $VALUE"' USR1
trap 'VALUE=$((VALUE * 2)); echo "Current value: $VALUE"' USR2
trap 'echo "Quitting because of signal from other process"; exit' SIGTERM

while true
do
sleep 1
done

