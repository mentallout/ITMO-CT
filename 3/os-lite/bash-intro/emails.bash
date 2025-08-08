#!/bin/bash

grep -E -Roh "[[:alpha:].+-]+@[[:alpha:]-]+\.[[:alpha:].-]+" /etc | sort -u | sed 's/$/,/' | tr -d '\n' > etc_emails.lst

