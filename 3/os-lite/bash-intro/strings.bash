#!/bin/bash

while true;
do
read -r string
if [[ "$string" == "q" ]]; 
then
break
fi
echo ${#string}
case "$string" in
*[![:alpha:]]*)
echo "false"
;;
*)
echo "true"
;;
esac
done

