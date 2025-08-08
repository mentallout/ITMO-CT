#!/usr/bin/bash

N=100000

while true
do
echo "Testing N=$N"
for (( i=0; i<30; i++ ))
do
./newmem.bash $N &
APP_PID=$!
sleep 1
done
wait
sleep 5
if sudo dmesg | grep -q -i "Out of memory: Killed process $APP_PID"
then
break
else
N=$(( N+100000 ))
fi
done

echo "Maximum N: $((N--))"

