#!/bin/bash

output_file="cpu_burst.txt"
> "$output_file"

for pid in /proc/[0-9]*
do
if [[ -d "$pid" ]]
then
pid_num=${pid#/proc/}
status_file="$pid/status"
sched_file="$pid/sched"
if [[ -f "$status_file" && -f "$sched_file" ]]
then
ppid=$(awk '/^PPid:/ {print $2}' "$status_file")
sum_exec_runtime=$(awk '/se\.sum_exec_runtime/ {print $3}' "$sched_file")
nr_switches=$(awk '/nr_switches/ {print $3}' "$sched_file")
if [[ "$nr_switches" -gt 0 ]]
then
art=$(echo "scale=3; $sum_exec_runtime / $nr_switches" | bc)
else
art=0
fi
art=$(printf "%.3f" "$art")
echo "ProcessID=$pid_num : Parent_ProcessID=$ppid : Average_Running_Time=$art" >> "$output_file"
fi
fi
done

sort -t '=' -k3n "$output_file" -o "$output_file"

