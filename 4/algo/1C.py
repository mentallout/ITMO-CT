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


m = int(input())
orders = []
for i in range(m):
    inp = input().split()
    a, b, c, d = map(int, inp[1:])
    hours, minutes = map(int, inp[0].split(':'))
    orders.append((hours * 60 + minutes, a, b, c, d))
adjacency_list = [[] for _ in range(m + 1)]
for i in range(m):
    for j in range(m):
        time1, a1, b1, c1, d1 = orders[i]
        time2, a2, b2, c2, d2 = orders[j]
        if i != j and (time1 + (abs(a1 - c1) + abs(b1 - d1)) + (abs(c1 - a2) + abs(d1 - b2)) <= time2 - 1):
            adjacency_list[i + 1].append(j + 1)
left = [0] * (m + 1)
right = [0] * (m + 1)
max_matching = 0
for i in range(1, m + 1):
    visited = [False] * (m + 1)
    if dfs(i, visited):
        max_matching += 1
print(m - max_matching)
