#!/usr/bin/bash

TRASH_DIR="$HOME/.trash"
TRASH_LOG="$HOME/.trash.log"

if [[ $# -lt 1 ]]
then
echo "Usage: $0 <filename> [-i|--ignore|-u|--unique|-o|--overwrite]"
exit 1
fi

POLICY="--ignore"
FILENAME=""

while [[ $# -gt 0 ]]
do
case "$1" in
-i|--ignore) POLICY="--ignore" ;;
-u|--unique) POLICY="--unique" ;;
-o|--overwrite) POLICY="--overwrite" ;;
*) FILENAME="$1" ;;
esac
shift
done

if [[ -z "$FILENAME" ]]
then
echo "No filename"
exit 1
fi

FOUND=false

while IFS= read -r line
do
original_path=$(echo "$line" | awk '{print $1}')
pos=$(echo "$line" | awk '{print $2}')
trash_path="$TRASH_DIR/$pos"

if [[ "$(basename "$original_path")" == "$FILENAME" ]]
then
FOUND=true
echo "Found: $original_path"
while true
do
echo "Restore $original_path? (y/n)"
read -r response < /dev/tty
case "$response" in
y)
restore_dir=$(dirname "$original_path")
[[ ! -d "$restore_dir" ]] && restore_dir="$HOME"
restore_path="$restore_dir/$(basename "$original_path")"
case "$POLICY" in
--ignore)
if [[ -e "$restore_path" ]]
then
echo "File $restore_path already exists. Skipping."
break
fi
;;
--unique)
if [[ -e "$restore_path" ]]
then
i=1
while [[ -e "${restore_path}($i)" ]]
do
((i++))
done
restore_path="${restore_path}($i)"
fi
;;
--overwrite)
[[ -e "$restore_path" ]] && rm -f "$restore_path"
;;
esac
mv "$trash_path" "$restore_path"
if [[ $? -eq 0 ]]
then
echo "File restored to $restore_path"
sed -i "\#$original_path $pos#d" "$TRASH_LOG"
else
echo "Failed to restore $original_path"
fi
break
;;
n|skip) break ;;
*) echo "Invalid response" ;;
esac
done
fi
done < "$TRASH_LOG"

if ! $FOUND
then
echo "No matching file found in trash log"
fi

