#!/bin/bash

grep -E -h '\(WW\)' /var/log/Xorg.0.log | sed 's/(WW)/Warning:/g' > X_info_warn.log
grep -E -h '\(II\)' /var/log/Xorg.0.log | sed 's/(II)/Information:/g' >> X_info_warn.log
cat X_info_warn.log

