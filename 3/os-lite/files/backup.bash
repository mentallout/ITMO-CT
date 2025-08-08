#!/usr/bin/bash

BACKUP_ROOT="$HOME"
SOURCE_DIR="${HOME}/source"
BACKUP_REPORT="${HOME}/backup-report"
CURRENT_DATE=$(date +%F)
BACKUP_DIR="${BACKUP_ROOT}/Backup-${CURRENT_DATE}"
ACTIVE_BACKUP=""

for dir in "${BACKUP_ROOT}"/Backup-*
do
if [ -d "$dir" ]
then
dir_date=$(basename "$dir" | cut -d'-' -f2-)
days_diff=$(( ($(date -d "$CURRENT_DATE" +%s) - $(date -d "$dir_date" +%s)) / (24*60*60) ))
if [ "$days_diff" -lt 7 ]
then
ACTIVE_BACKUP="$dir"
break
fi
fi
done

if [ -z "$ACTIVE_BACKUP" ]
then
mkdir -p "$BACKUP_DIR"
echo "New backup dir created: $BACKUP_DIR, date: $CURRENT_DATE" >> "$BACKUP_REPORT"
for file in "${SOURCE_DIR}"/*
do
if [ -f "$file" ]
then
cp "$file" "$BACKUP_DIR/"
echo "File copied: $(basename "$file")" >> "$BACKUP_REPORT"
fi
done
else
echo "Changes in backup dir: $ACTIVE_BACKUP, date: $CURRENT_DATE" >> "$BACKUP_REPORT"
for file in "${SOURCE_DIR}"/*
do
if [ -f "$file" ]
then
filename=$(basename "$file")
target_file="${ACTIVE_BACKUP}/${filename}"
if [ ! -f "$target_file" ]
then
cp "$file" "$target_file"
echo "File added: $filename" >> "$BACKUP_REPORT"
else
if ! cmp -s "$file" "$target_file"
then
versioned_name="${filename}.${CURRENT_DATE}"
mv "$target_file" "${ACTIVE_BACKUP}/${versioned_name}"
cp "$file" "$target_file"
echo "File changed: $filename $versioned_name" >> "$BACKUP_REPORT"
fi
fi
fi
done
fi

