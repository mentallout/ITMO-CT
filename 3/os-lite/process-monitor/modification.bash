#!/bin/bash

temp_start=$(mktemp)
temp_after=$(mktemp)

ps -e -o pid,uid,state,vsz,cmd --no-headers > "$temp_start"
sleep 300
ps -e -o pid,uid,state,vsz,cmd --no-headers > "$temp_after"

echo -e "PID   UID   CMD   VSZ DIFF"
while read -r after_process
do
pid=$(echo "$after_process" | awk '{print $1}')
uid=$(echo "$after_process" | awk '{print $2}')
state=$(echo "$after_process" | awk '{print $3}')
cmd=$(echo "$after_process" | awk '{for(i=5;i<=NF;i++) printf $i " ";}')
vsz=$(echo "$after_process" | awk '{print $4}')
before_process=$(grep "^$pid " "$temp_start")
if [ -n "$before_process" ]
then
before_state=$(echo "$before_process" | awk '{print $3}')
before_vsz=$(echo "$before_process" | awk '{print $4}')
if [ "$before_state" = "R" ] && [ "$before_vsz" = "S" ]
then
if [ "$before_vsz" -ne 0 ]
then
vsz_diff=$(echo "scale=2; (($vsz - $before_vsz) / $before_vsz) * 100" | bc)
else
vsz_diff=""
fi
echo -e "$pid   $uid   $cmd   $vsz_diff"
fi
fi
done < "$temp_after"

rm -f "$temp_start" "$temp_after"

