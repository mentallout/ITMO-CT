from collections import deque

n, m = map(int, input().split())
pairs = [[] for _ in range(n)]
for _ in range(m):
    u, v = map(int, input().split())
    pairs[u - 1].append(v - 1)
    pairs[v - 1].append(u - 1)
color = [-1] * n
group_0 = []
group_1 = []
for start in range(n):
    if color[start] == -1:
        queue = deque([start])
        color[start] = 0
        temp_group_0 = [start]
        temp_group_1 = []
        while queue:
            v = queue.popleft()
            for u in pairs[v]:
                if color[u] == -1:
                    color[u] = 1 - color[v]
                    if color[u] == 0:
                        temp_group_0.append(u)
                    else:
                        temp_group_1.append(u)
                    queue.append(u)
                elif color[u] == color[v]:
                    print('NO')
                    exit(0)
        group_0.extend(temp_group_0)
        group_1.extend(temp_group_1)
print("YES")
print(len(group_0))
print(*[x + 1 for x in group_0])
