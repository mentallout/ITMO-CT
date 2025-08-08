#!/bin/bash

ps -u | sed '1d' | sort -rk5 | head -n 1 | awk '{print $2}'

