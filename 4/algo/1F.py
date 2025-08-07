def kuhn(v, visited, right, adjacency_list):
    if visited[v]:
        return False
    visited[v] = True
    for girl in adjacency_list[v]:
        if right[girl] == -1 or kuhn(right[girl], visited, right, adjacency_list):
            right[girl] = v
            return True
    return False


def dfs(v, boys, right, girls, adjacency_list):
    if boys[v]:
        return
    boys[v] = True
    for girl in adjacency_list[v]:
        if right[girl] != -1:
            girls[girl] = True
            dfs(right[girl], boys, right, girls, adjacency_list)


k = int(input())
for case in range(k):
    if case > 0:
        print()
    m, n = map(int, input().split())
    adjacency_list = []
    for i in range(m):
        known_girls = set([x - 1 for x in list(map(int, input().split()))[:-1]])
        adjacency_list.append([j for j in range(n) if j not in known_girls])
    left = []
    right = [-1] * n
    for v in range(m):
        visited = [False] * m
        kuhn(v, visited, right, adjacency_list)
    for i in range(m):
        flag = False
        for j in range(n):
            if right[j] == i:
                flag = True
                break
        if not flag:
            left.append(i)
    boys = [False] * m
    girls = [False] * n
    for v in left:
        dfs(v, boys, right, girls, adjacency_list)
    boys = [i + 1 for i in range(m) if boys[i]]
    girls = [i + 1 for i in range(n) if not girls[i]]
    print(len(boys) + len(girls))
    print(len(boys), len(girls))
    if boys:
        print(' '.join(str(x) for x in sorted(boys)))
    else:
        print()
    if girls:
        print(' '.join(str(x) for x in sorted(girls)))
    else:
        print()
