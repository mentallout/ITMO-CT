n = int(input())
k = int(input())
danger = [int(i) for i in input().split()]
ladder = [0] * (n + 1)
ladder[0] = 1
for i in range(1, n + 1):
    if i in danger:
        ladder[i] = 0
    else:
        ladder[i] = ladder[i - 1] + ladder[i - 2]
print(ladder[n])
