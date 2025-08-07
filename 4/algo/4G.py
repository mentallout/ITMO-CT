a, b = map(int, input().split())
primes = [False, False] + [True] * (b - 1)
for i in range(2, int(b ** 0.5) + 1):
    if primes[i]:
        for j in range(i * i, b + 1, i):
            primes[j] = False
print(sum(primes[a:b + 1]))
