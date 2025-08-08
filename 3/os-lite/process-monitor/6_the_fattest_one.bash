#!/bin/bash

grep -s 'VmRSS' /proc/[0-9]*/status | sort -k2 -n | tail -1

# for top: top and there M
# got same PIDs

