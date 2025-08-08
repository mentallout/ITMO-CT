#!/usr/bin/bash

FILE="$1"

if [ ! -e "$FILE" ]
then
echo "File '$FILE' does not exist"
exit 1
fi

HOME_DIR="$HOME"
TRASH_DIR="$HOME_DIR/.trash"
LOG_FILE="$HOME_DIR/.trash.log"

mkdir -p "$TRASH_DIR"
touch "$LOG_FILE"

LINK_NAME=$(ls "$TRASH_DIR" | grep -E '^[0-9]+$' | sort -n | tail -n 1)
if [ -z "$LINK_NAME" ]
then
LINK_NAME=1
else
LINK_NAME=$((LINK_NAME + 1))
fi

LINK_PATH="$TRASH_DIR/$LINK_NAME"
ln "$FILE" "$LINK_PATH"
rm "$FILE"

FULL_PATH=$(realpath "$FILE")
echo "$FULL_PATH $LINK_NAME" >> "$LOG_FILE"

