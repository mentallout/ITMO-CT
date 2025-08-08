#!/bin/bash

man bash | tr -cs '[:alpha:]' '\n' | awk 'length($0) >= 4' | sort | uniq -c | sort -nr | head -n 3 | awk '{print $2}'

