MOD = 998244353
n, m = map(int, input().split())
p = [int(x) for x in input().split()] + [0] * (m - n - 1)
unop = [(p[0] + 1) % MOD] + p[1:]

squared = [1]
for i in range(1, m):
    s = unop[i]
    for j in range(1, i):
        s = (s - squared[j] * squared[i - j]) % MOD
    squared.append((s * pow(2 * squared[0], MOD - 2, MOD)) % MOD)
print(*squared[:m])

exponential = [1]
for i in range(1, m):
    s = 0
    for j in range(1, i + 1):
        if j < len(p):
            s = (s + p[j] * exponential[i - j] * j) % MOD
    exponential.append((s * pow(i, MOD - 2, MOD)) % MOD)
print(*exponential[:m])

derivated = [(i * unop[i]) % MOD for i in range(1, len(unop))]
inverted = [pow(unop[0], MOD - 2, MOD)]
for i in range(1, m):
    s = 0
    for j in range(1, i + 1):
        if j < len(unop):
            s = (s + unop[j] * inverted[i - j]) % MOD
    inverted.append((-s * inverted[0]) % MOD)
multiplicated = [0] * m
for i in range(len(derivated)):
    for j in range(len(inverted)):
        if i + j < m:
            multiplicated[i + j] = (multiplicated[i + j] + derivated[i] * inverted[j]) % MOD
integrated = [0]
for i in range(m):
    integrated.append(multiplicated[i] * pow(i + 1, MOD - 2, MOD) % MOD)
print(*integrated[:m])
