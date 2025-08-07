from collections import defaultdict

n, m = map(int, input().split())
x = [0] * m
y = [0] * m
z = [0] * m
s = [0] * (n + 1)
g = defaultdict(int)
a = [0] + list(map(int, input().split()))
for i in range(1, n + 1):
    g[a[i]] += 1
for i in range(m):
    x[i], y[i] = map(int, input().split())
for value, count in g.items():
    if value > count:
        continue
    for j in range(1, n + 1):
        if a[j] == value:
            s[j] = s[j - 1] + 1
        else:
            s[j] = s[j - 1]
    for j in range(m):
        if s[y[j]] - s[x[j] - 1] == value:
            z[j] += 1
for i in range(m):
    print(z[i])
