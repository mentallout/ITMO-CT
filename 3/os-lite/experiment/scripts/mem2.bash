#!/usr/bin/bash

LOG_FILE="../logs/report2.log"
> "$LOG_FILE"

arr=()
count=0

while true
do
arr+=(1 2 3 4 5)
((count++))
if (( count % 100000 == 0 ))
then
echo "Step: $count, Array size: ${#arr[@]}" >> "$LOG_FILE"
fi
done

