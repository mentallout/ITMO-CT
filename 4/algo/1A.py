def dfs(v, visited):
    if visited[v]:
        return False
    visited[v] = True
    for to in adjacency_list[v]:
        if right[to] == 0 or dfs(right[to], visited):
            right[to] = v
            left[v] = to
            return True
    return False


n, m = map(int, input().split())
edges = set()
for i in range(1, n + 1):
    for j in input().split()[:-1]:
        edges.add((i, int(j)))
adjacency_list = [[] for _ in range(n + 1)]
for u, v in edges:
    adjacency_list[u].append(v)
left = [0] * (n + 1)
right = [0] * (m + 1)
max_matching = 0
for i in range(1, n + 1):
    visited = [False] * (n + 1)
    if dfs(i, visited):
        max_matching += 1
print(max_matching)
for i in range(1, n + 1):
    if left[i] != 0:
        print(i, left[i])
