from collections import deque


def bfs(graph, parent):
    queue = deque([0])
    visited[0] = True
    while queue:
        u = queue.popleft()
        for v in range(len(graph)):
            if not visited[v] and graph[u][v] > 0:
                visited[v] = True
                parent[v] = u
                if v == n - 1:
                    return True
                queue.append(v)
    return False


n, m = map(int, input().split())
graph = [[0] * n for _ in range(n)]
for _ in range(m):
    a, b, c = map(int, input().split())
    graph[a - 1][b - 1] = c
max_flow = 0
inv_graph = [x[:] for x in graph]
parent = [-1] * len(graph)
visited = [False] * len(graph)
while bfs(inv_graph, parent):
    path_flow = float('inf')
    s = n - 1
    while s != 0:
        path_flow = min(path_flow, inv_graph[parent[s]][s])
        s = parent[s]
    max_flow += path_flow
    v = n - 1
    while v != 0:
        inv_graph[parent[v]][v] -= path_flow
        inv_graph[v][parent[v]] += path_flow
        v = parent[v]
    visited = [False] * len(graph)
print(max_flow)
