n, m = map(int, input().split())
edges = [[] for _ in range(n)]
for i in range(m):
    u, v = map(int, input().split())
    edges[u - 1].append(v - 1)
    edges[v - 1].append(u - 1)
visited = [False] * n
components = []
for i in range(n):
    if not visited[i]:
        components.append([])
        stack = [i]
        components[-1].append(i)
        visited[i] = True
        while stack:
            u = stack.pop()
            for v in edges[u]:
                if not visited[v]:
                    visited[v] = True
                    components[-1].append(v)
                    stack.append(v)
print(len(components))
for x in components:
    print(len(x))
    print(*[i + 1 for i in x])
