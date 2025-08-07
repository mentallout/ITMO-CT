MOD = 998244353
n, m = map(int, input().split())
p = [int(x) for x in input().split()]
q = [int(x) for x in input().split()]
addition = [0] * max(len(p), len(q))
multiplication = [0] * (len(p) + len(q) - 1)
division = [0] * 1000

for i in range(len(addition)):
    addition[i] = ((p[i] if i < len(p) else 0) + (q[i] if i < len(q) else 0)) % MOD

for i in range(len(p)):
    for j in range(len(q)):
        multiplication[i + j] = (multiplication[i + j] + p[i] * q[j]) % MOD

for x in ([addition, multiplication]):
    while len(x) > 1 and x[-1] == 0:
        x.pop()
    print(len(x) - 1)
    print(*x)

onedivq = [pow(q[0], MOD - 2, MOD)] + [0] * 999
for i in range(1, 1000):
    a = 0
    for j in range(1, min(len(q), i + 1)):
        a += q[j] * onedivq[i - j] % MOD
        a %= MOD
    onedivq[i] = (-a) % MOD
for i in range(1000):
    for j in range(min(len(p), i + 1)):
        division[i] = (division[i] + p[j] * onedivq[i - j]) % MOD

print(*division[:1000])
