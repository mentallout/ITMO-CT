#!/bin/bash

tail -n +2 catalogue.csv | sort -t ';' -k5 -n | head -n 5 | sort -t ';' -k3 -n | cut -d ';' -f2 > best.lst


