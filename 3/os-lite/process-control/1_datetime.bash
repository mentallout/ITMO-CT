#!/usr/bin/bash

BASE_DIR="$HOME/test"
ARCHIVE_DIR="$BASE_DIR/archived"
CURRENT_DATE=$(date '+%Y-%m-%d_%H-%M-%S')
CURRENT_DAY=$(date '+%Y-%m-%d')
LOG_FILE="$HOME/report"

mkdir -p "$BASE_DIR"
mkdir -p "$ARCHIVE_DIR"

find "$BASE_DIR" -maxdepth 1 -type f -not -name "archived" -not -name "$CURRENT_DATE" | while read FILE
do
BASENAME=$(basename "$FILE")
FILE_DAY=$(echo "$BASENAME" | cut -d'_' -f1)
if [[ "$FILE_DAY" != "$CURRENT_DAY" ]] 
then
tar -czf "$ARCHIVE_DIR/$FILE_DAY.tar.gz" "$FILE" && rm -f "$FILE"
fi
done

NEW_FILE="$BASE_DIR/${CURRENT_DATE}"
touch "$NEW_FILE"

echo "$(date '+%Y-%m-%d %H:%M:%S') test was created successfully" >> "$LOG_FILE"

