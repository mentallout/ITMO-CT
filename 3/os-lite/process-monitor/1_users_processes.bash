#!/bin/bash

ps -u $USER | wc -l > users_processes
ps -u $USER | sed '1d' | awk '{print $1 ":" $4}' >> users_processes

