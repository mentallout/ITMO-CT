#!/usr/bin/bash

./6_handler.bash & HANDLER=$!
./6_producer.bash "$HANDLER"

