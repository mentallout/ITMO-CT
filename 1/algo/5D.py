n, k = map(int, input().split())
ladder = [0] * (n + 1)
ladder[0] = 1
ladder[1] = 1
for i in range(1, n + 1):
    for j in range(1, k + 1):
        if i - j > 0:
            ladder[i] += ladder[i - j]
print(ladder[n])
