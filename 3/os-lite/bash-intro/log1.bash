#!/bin/bash

grep -E 'systemd(\[[[:digit:]]+\]|-[[:alpha:]]+\[[[:digit:]]+\])' /var/log/syslog > system.log

