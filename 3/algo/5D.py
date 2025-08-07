from collections import deque

num1 = int(input())
num2 = int(input())
queue = deque([(num1, [num1])])
visited = set()
while queue:
    current, path = queue.popleft()
    if current == num2:
        for x in path:
            print(x)
        break
    if current in visited:
        continue
    visited.add(current)
    num = str(current)
    neighbors = []
    if num[0] != '9':
        neighbors.append(int(str(int(num[0]) + 1) + num[1:]))
    if num[-1] != '1':
        neighbors.append(int(num[:-1] + str(int(num[-1]) - 1)))
    neighbors.append(int(num[-1] + num[:-1]))
    neighbors.append(int(num[1:] + num[0]))
    for neighbor in neighbors:
        if neighbor not in visited:
            queue.append((neighbor, path + [neighbor]))
