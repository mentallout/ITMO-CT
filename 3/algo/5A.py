from collections import deque

n, m = map(int, input().split())
graph = [[] for _ in range(n)]
for i in range(m):
    u, v = map(int, input().split())
    graph[u - 1].append(v - 1)
s, t = map(int, input().split())
s -= 1
t -= 1
queue = deque([s])
distances = [-1] * n
parents = [-1] * n
distances[s] = 0
while queue:
    current = queue.popleft()
    for neighbor in graph[current]:
        if distances[neighbor] == -1:
            distances[neighbor] = distances[current] + 1
            parents[neighbor] = current
            queue.append(neighbor)
            if neighbor == t:
                break
if distances[t] == -1:
    print(-1)
    exit(0)
path = []
current = t
while current != -1:
    path.append(current)
    current = parents[current]
else:
    print(distances[t])
    print(*[x + 1 for x in path[::-1]])
