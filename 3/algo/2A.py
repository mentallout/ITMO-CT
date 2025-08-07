from collections import defaultdict


def dfs1(v):
    visited[v] = True
    for neighbor in graph[v]:
        if not visited[neighbor]:
            dfs1(neighbor)
    order.append(v)


def dfs2(v, component):
    visited[v] = True
    component.append(v)
    for neighbor in reverse_graph[v]:
        if not visited[neighbor]:
            dfs2(neighbor, component)


n, m = map(int, input().split())
graph, reverse_graph = defaultdict(list), defaultdict(list)
for i in range(m):
    u, v = map(int, input().split())
    graph[u - 1].append(v - 1)
    reverse_graph[v - 1].append(u - 1)
order = []
visited = [False] * n
for i in range(n):
    if not visited[i]:
        stack = [i]
        while stack:
            v = stack.pop()
            if v < 0:
                order.append(~v)
                continue
            if not visited[v]:
                visited[v] = True
                stack.append(~v)
                for neighbor in graph[v]:
                    if not visited[neighbor]:
                        stack.append(neighbor)
components = []
visited = [False] * n
while order:
    x = order.pop()
    if not visited[x]:
        stack = [x]
        component = []
        while stack:
            v = stack.pop()
            if not visited[v]:
                visited[v] = True
                component.append(v)
                for neighbor in reverse_graph[v]:
                    if not visited[neighbor]:
                        stack.append(neighbor)
        components.append(component)
print(len(components))
component_id = {v: i + 1 for i, component in enumerate(components) for v in component}
print(' '.join(str(component_id.get(i, 0)) for i in range(n)))
