n, m = map(int, input().split())
edges = []
graph = [[] for _ in range(n)]
for i in range(m):
    u, v = map(int, input().split())
    edges.append((u - 1, v - 1))
    graph[u - 1].append((v - 1, i))
    graph[v - 1].append((u - 1, i))
visited = [False] * n
spanning_tree = []
stack = [(0, -1)]
while stack:
    u, i = stack.pop()
    if not visited[u]:
        visited[u] = True
        if i != -1:
            spanning_tree.append(i)
        for neighbor, x in graph[u]:
            if not visited[neighbor]:
                stack.append((neighbor, x))
for x in spanning_tree:
    print(edges[x][0] + 1, edges[x][1] + 1)
