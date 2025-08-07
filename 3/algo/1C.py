n = int(input())
matrix = [list(map(int, input().split())) for _ in range(n)]
edges = [[] for _ in range(n)]
for i in range(n):
    for j in range(n):
        if matrix[i][j]:
            edges[i].append(j)
visited = [False] * n
parent = [-1] * n
stack = []
cycle = []
for start in range(n):
    if visited[start]:
        continue
    stack.append((start, -1))
    while stack:
        v, p = stack.pop()
        if visited[v]:
            cycle_start = v
            cycle.append(v)
            while p != -1 and p != cycle_start:
                cycle.append(p)
                p = parent[p]
            cycle.append(cycle_start)
            cycle.reverse()
            print("YES")
            print(len(cycle) - 1)
            print(*[x + 1 for x in cycle[1:]])
            exit(0)
        visited[v] = True
        parent[v] = p
        for u in edges[v]:
            if u != p:
                stack.append((u, v))
print("NO")
