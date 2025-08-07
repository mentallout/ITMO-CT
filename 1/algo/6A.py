n = int(input())
a = [int(i) for i in input().split()]
d, prev = [0] * n, [0] * n
ans = []
for i in range(n):
    d[i], prev[i] = 1, -1
    for j in range(i):
        if d[i] < d[j] + 1 and a[i] > a[j]:
            d[i] = d[j] + 1
            prev[i] = j
pos, l = 0, d[0]
for i in range(n):
    if d[i] > l:
        pos = i
        l = d[i]
print(l)
while pos != -1:
    ans.append(a[pos])
    pos = prev[pos]
print(*ans[::-1])
