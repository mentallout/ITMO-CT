n, m = map(int, input().split())
d = [[30000] * n for _ in range(n)]
for i in range(n):
    d[i][i] = 0
for i in range(m):
    u, v, w = map(int, input().split())
    d[u - 1][v - 1] = min(d[u - 1][v - 1], w)
for k in range(n):
    for i in range(n):
        for j in range(n):
            if d[i][k] < 30000 and d[k][j] < 30000:
                d[i][j] = min(d[i][j], d[i][k] + d[k][j])
for row in d:
    print(*row)
