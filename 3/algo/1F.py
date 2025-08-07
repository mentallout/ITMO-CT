n, m = map(int, input().split())
edges = [[] for _ in range(n)]
for i in range(m):
    u, v = map(int, input().split())
    edges[u - 1].append(v - 1)
permutation = map(int, input().split())
position = [0] * n
for i, v in enumerate(permutation):
    position[v - 1] = i
for u in range(n):
    for v in edges[u]:
        if position[u] > position[v]:
            print('NO')
            exit(0)
print('YES')
