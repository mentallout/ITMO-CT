#!/bin/bash

input_file="cpu_burst.txt"
output_file="cpu_burst_with_avg.txt"
> "$output_file"

current_ppid=""
total_art=0
child_count=0

while read -r line
do
ppid=$(echo "$line" | awk -F ' : ' '{print $2}' | cut -d '=' -f2)
art=$(echo "$line" | awk -F ' : ' '{print $3}' | cut -d '=' -f2)
if [[ "$current_ppid" != "$ppid" && "$current_ppid" != "" ]]
then
avg=$(echo "scale=3; $total_art / $child_count" | bc)
printf "Average_Running_Children_of_ParentID=%d is %.3f\n" "$current_ppid" "$avg" >> "$output_file"
total_art=0
child_count=0
fi
echo "$line" >> "$output_file"
current_ppid="$ppid"
total_art=$(echo "$total_art + $art" | bc)
((child_count++))
done < "$input_file"

if [[ "$current_ppid" != "" ]]
then
avg=$(echo "scale=3; $total_art / $child_count" | bc)
printf "Average_Running_Children_of_ParentID=%d is %.3f\n" "$current_ppid" "$avg" >> "$output_file"
fi

