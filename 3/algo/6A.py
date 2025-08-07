n, m = map(int, input().split())
graph = []
for i in range(m):
    u, v, w = map(int, input().split())
    graph.append((u - 1, v - 1, w))
d = [0] + [30000] * (n - 1)
for _ in range(n - 1):
    for u, v, w in graph:
        if d[u] < 30000 and d[u] + w < d[v]:
            d[v] = d[u] + w
print(*[d if d < 30000 else 30000 for d in d])
