from collections import deque

n, m, k = map(int, input().split())
good = [[] for _ in range(n)]
bad = [[] for _ in range(n)]
for _ in range(m):
    u, v = map(int, input().split())
    good[u - 1].append(v - 1)
    good[v - 1].append(u - 1)
for _ in range(k):
    u, v = map(int, input().split())
    bad[u - 1].append(v - 1)
    bad[v - 1].append(u - 1)
color = [-1] * n
for start in range(n):
    if color[start] == -1:
        queue = deque([start])
        color[start] = 0
        while queue:
            v = queue.popleft()
            for u in good[v]:
                if color[u] == -1:
                    color[u] = color[v]
                    queue.append(u)
                elif color[u] != color[v]:
                    print('No')
                    exit(0)
            for u in bad[v]:
                if color[u] == -1:
                    color[u] = 1 - color[v]
                    queue.append(u)
                elif color[u] == color[v]:
                    print('No')
                    exit(0)
bus1 = [i + 1 for i in range(n) if color[i] == 0]
bus2 = [i + 1 for i in range(n) if color[i] == 1]
print('Yes')
print(len(bus1), *bus1)
print(len(bus2), *bus2)
