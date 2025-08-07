n = int(input())
graph = []
for _ in range(n):
    graph.append(list(map(int, input().split())))
dist = [0] * n
p = [-1] * n
x = -1
for i in range(n):
    x = -1
    for u in range(n):
        for v in range(n):
            if graph[u][v] != 100000:
                if dist[v] > dist[u] + graph[u][v]:
                    dist[v] = dist[u] + graph[u][v]
                    p[v] = u
                    x = v
if x == -1:
    print('NO')
else:
    for _ in range(n):
        x = p[x]
    cycle = []
    current = x
    while True:
        cycle.append(current)
        current = p[current]
        if current == x and len(cycle) > 1:
            break
    print('YES', len(cycle), sep="\n")
    print(*[x + 1 for x in cycle[::-1]])
