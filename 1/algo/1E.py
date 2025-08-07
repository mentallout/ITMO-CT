from math import *

n = int(input())
arr = [int(i) for i in range(1, n + 1)]
for i in range(2, n):
    arr[i], arr[floor(i / 2)] = arr[floor(i / 2)], arr[i]
print(*arr)
