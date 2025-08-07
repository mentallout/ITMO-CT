n = int(input())
matrix = [list(map(int, input().split())) for _ in range(n)]
graph = [[matrix[i][j] for j in range(n)] for i in range(n)]
d = [100000] * n
for x in range(n):
    d[x] = 0
    for _ in range(n - 1):
        for u in range(n):
            for v in range(n):
                if d[u] != 100000 and graph[u][v] != 100000:
                    d[v] = min(d[v], d[u] + graph[u][v])
    for u in range(n):
        for v in range(n):
            if d[u] != 100000 and graph[u][v] != 100000 and d[u] + graph[u][v] < d[v]:
                print('YES')
                exit(0)
print('NO')
