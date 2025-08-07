from collections import deque


def dfs(v, to, visited, lvl):
    if v == n + 1:
        return to
    while visited[v] < len(graph[v]):
        e = graph[v][visited[v]]
        if e[2] > 0 and lvl[v] + 1 == lvl[e[0]]:
            d = dfs(e[0], min(to, e[2]), visited, lvl)
            if d:
                e[2] -= d
                graph[e[0]][e[1]][2] += d
                return d
        visited[v] += 1
    return 0


def bfs(lvl):
    for i in range(len(graph)):
        lvl[i] = -1
    lvl[n] = 0
    queue = deque([n])
    while queue:
        v = queue.popleft()
        for e in graph[v]:
            if e[2] > 0 and lvl[e[0]] == -1:
                lvl[e[0]] = lvl[v] + 1
                queue.append(e[0])
    return lvl[n + 1] != -1


n = int(input())
weights = list(map(int, input().split()))
dependencies = []
for i in range(n):
    line = input().split()
    dependencies.append([int(x) - 1 for x in line[1:]])
graph = [[] for _ in range(n + 2)]
ans = 0
for i in range(n):
    if weights[i] > 0:
        ans += weights[i]
        graph[n].append([i, len(graph[i]), weights[i]])
        graph[i].append([n, len(graph[n]) - 1, 0])
    elif weights[i] < 0:
        graph[i].append([n + 1, len(graph[n + 1]), -weights[i]])
        graph[n + 1].append([i, len(graph[i]) - 1, 0])
    for j in dependencies[i]:
        graph[i].append([j, len(graph[j]), float('inf')])
        graph[j].append([i, len(graph[i]) - 1, 0])
flow = 0
lvl = [-1] * len(graph)
while bfs(lvl):
    visited = [0] * len(graph)
    while True:
        f = dfs(n, float('inf'), visited, lvl)
        if f == 0:
            break
        flow += f
print(ans - flow)
