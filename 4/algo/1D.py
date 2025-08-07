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
adjacency_list = [[] for _ in range(n + 1)]
for _ in range(m):
    u, v = map(int, input().split())
    adjacency_list[u].append(v)
left = [0] * (n + 1)
right = [0] * (n + 1)
max_matching = 0
for i in range(1, n + 1):
    visited = [False] * (n + 1)
    if dfs(i, visited):
        max_matching += 1
print(n - max_matching)
