#!/usr/bin/bash

PIPE="chess"

declare -A board
for i in {0..7}
do
for j in {0..7}
do
board[$i,$j]=" .  "
done
done

board[0,4]=" K1 "
board[0,1]=" H1 "
board[1,0]=" P1 "

board[7,4]=" K2 "
board[7,6]=" H2 "
board[6,7]=" P2 "

print_board() 
{
echo "    0      1       2       3       4     5     6     7"
for i in {0..7}
do
echo -n "$i |"
for j in {0..7}
do
echo -n " ${board[$i,$j]} |"
done
echo
done
echo
}

is_valid_move() 
{
local x=$1 y=$2 x2=$3 y2=$4 piece=$5
if [[ -z $piece || $piece == " .  " ]]
then
echo "No piece to move from ($x, $y)"
return 1
fi
if [[ $current_player -ne ${piece:1:1} ]]
then
echo "You can only move your pieces"
return 1
fi
case $piece in
P*)
if [[ $((x2 - x)) -ne 1 || $y -ne $y2 ]]
then
echo "Pawn can't go there"
return 1
fi
;;
N*)
dx=$((x2 - x))
dx=${dx#-}
dy=$((y2 - y))
dy=${dy#-}
if ! ([[ $dx -eq 2 && $dy -eq 1 ]] || [[ $dx -eq 1 && $dy -eq 2 ]])
then
echo "Horse can't go there"
return 1
fi
;;
K*)
dx=$((x2 - x))
dx=${dx#-}
dy=$((y2 - y))
dy=${dy#-}
if [[ $dx -gt 1 || $dy -gt 1 ]]
then
echo "Wrong can't go there"
return 1
fi
;;
*)
echo "Error"
return 1
;;
esac
if [[ ${board[$x2,$y2]} != " .  " ]]
then
echo "You can't go here"
return 1
fi
return 0
}

current_player=1
while true
do
print_board
echo "$current_player 's turn. Enter your move:"
read x y x2 y2 < $PIPE
piece=${board[$x,$y]}
if is_valid_move $x $y $x2 $y2 $piece
then
board[$x,$y]=" .  "
board[$x2,$y2]=$piece
if [[ ${board[$x2,$y2]} == "K2" ]]
then
echo "Player 1 won!"
print_board
break
elif [[ ${board[$x2,$y2]} == "K1" ]]
then
echo "Player 2 won!"
print_board
break
fi
current_player=$((3 - current_player))
else
echo "Try again"
fi
done

