n = int(input())
a, b, c = [], [], []
for i in range(n):
    temp = [int(i) for i in input().split()]
    a.append(temp[0])
    b.append(temp[1])
    c.append(temp[2])
p = [0] * (n + 1)
p[1] = a[0]
if n > 1:
    p[2] = min(a[0] + a[1], b[0])
for i in range(3, n + 1):
    p[i] = min(p[i - 1] + a[i - 1], p[i - 2] + b[i - 2], p[i - 3] + c[i - 3])
print(p[-1])
