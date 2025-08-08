#!/usr/bin/bash

./multiply.bash &
pid1=$!
./multiply.bash &
pid2=$!
./multiply.bash &
pid3=$!

cpulimit -p $pid1 -l 10 &
kill $pid3

