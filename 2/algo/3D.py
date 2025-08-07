from collections import deque

n = int(input())
a = list(map(int, input().split()))
b = list(map(int, input().split()))
maximum, minimum = deque(), deque()
j = res = 0
for i, (x, y) in enumerate(zip(a, b)):
    while maximum and a[maximum[-1]] <= x:
        maximum.pop()
    maximum.append(i)
    while minimum and b[minimum[-1]] >= y:
        minimum.pop()
    minimum.append(i)
    while j <= i and a[maximum[0]] > b[minimum[0]]:
        j += 1
        while maximum and maximum[0] < j:
            maximum.popleft()
        while minimum and minimum[0] < j:
            minimum.popleft()
    if maximum and minimum and a[maximum[0]] == b[minimum[0]]:
        res += min(maximum[0], minimum[0]) - j + 1
print(res)
