n = int(input())
ladder = [0] * (n + 1)
ladder[0] = 0
ladder[1] = 1
for i in range(2, n + 1):
    ladder[i] = i % 10 + min(ladder[i - 1], ladder[i - 2])
print(ladder[n])
