#!/usr/bin/bash

RESTORE_DIR="$HOME/restore"
LATEST_BACKUP=$(find "$HOME" -maxdepth 1 -type d -name 'Backup-*' | sort | tail -n 1)

if [ "$LATEST_BACKUP" = "" ]
then
echo "Couldn't find any backups"
exit 1
fi

if [ -d "$RESTORE_DIR" ]
then
rm -rf "$RESTORE_DIR" || { echo "Failed to remove $RESTORE_DIR"; exit 1; }
fi
mkdir -p "$RESTORE_DIR"

for file in "$LATEST_BACKUP"/*
do
[ -f "$file" ] || continue
filename=$(basename "$file")
if ! echo "$filename" | grep -qE '\.[0-9]{4}-[0-9]{2}-[0-9]{2}$'
then
cp "$file" "$RESTORE_DIR/"
fi
done

