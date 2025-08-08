#!/usr/bin/bash

MY_PIPE="my_pipe"
mkfifo "$MY_PIPE"
./5_producer.bash "$MY_PIPE" & ./5_handler.bash "$MY_PIPE"
rm .producer_pid
rm "$MY_PIPE"

