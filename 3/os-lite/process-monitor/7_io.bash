#!/bin/bash

temp_file=$(mktemp)
top1=""
top2=""
top3=""
max1=0
max2=0
max3=0

for pid in /proc/[0-9]*
do
if [ -r "$pid/io" ]
then
read_bytes=$(awk '/read_bytes:/ {print $2}' "$pid/io")
echo "${pid#/proc/}:$read_bytes" >> "$temp_file"
fi
done

sleep 60

while read -r line
do
pid=$(echo "$line" | cut -d':' -f1)
old_read_bytes=$(echo "$line" | cut -d':' -f2)
if [ -r "/proc/$pid/io" ]
then
read_bytes=$(awk '/read_bytes:/ {print $2}' "/proc/$pid/io")
diff=$((read_bytes - old_read_bytes))

if [ -r "/proc/$pid/cmdline" ]
then
cmdline=$(tr '\0' ' ' < "/proc/$pid/cmdline" | sed 's/ $//')
else
cmdline="[unknown]"
fi

if ((diff > max1))
then
max3=$max2; top3=$top2
max2=$max1; top2=$top1
max1=$diff; top1="$pid:$cmdline:$diff"
elif ((diff > max2))
then
max3=$max2; top3=$top2
max2=$diff; top2="$pid:$cmdline:$diff"
elif ((diff > max3))
then
max3=$diff; top3="$pid:$cmdline:$diff"
fi
fi
done < "$temp_file"

rm -f "$temp_file"

for data in "$top1" "$top2" "$top3"
do
echo "$data"
done

