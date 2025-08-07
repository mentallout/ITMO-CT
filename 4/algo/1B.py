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


n, m, a, b = map(int, input().split())
field = []
for i in range(n):
    field.append(input().strip())
blank = []
for i in range(n):
    for j in range(m):
        if field[i][j] == '*':
            blank.append((i, j))
if not blank:
    print(0)
elif 2 * b <= a:
    print(b * len(blank))
else:
    global adjacency_list, left, right
    black = []
    white = []
    for i, j in blank:
        black.append((i, j)) if (i + j) % 2 == 0 else white.append((i, j))
    black_cells = {cell: i + 1 for i, cell in enumerate(black)}
    white_cells = {cell: i + 1 for i, cell in enumerate(white)}
    adjacency_list = [[] for _ in range(len(black) + 1)]
    for cell in black:
        for di, dj in [(0, 1), (0, -1), (1, 0), (-1, 0)]:
            white_cell = (cell[0] + di, cell[1] + dj)
            if (cell[0] + di, cell[1] + dj) in white_cells:
                adjacency_list[black_cells[cell]].append(white_cells[(cell[0] + di, cell[1] + dj)])
    left = [0] * (len(black) + 1)
    right = [0] * (len(white) + 1)
    max_matching = 0
    for i in range(1, len(black) + 1):
        visited = [False] * (len(black) + 1)
        if dfs(i, visited):
            max_matching += 1
    print(a * max_matching + b * (len(blank) - 2 * max_matching))
