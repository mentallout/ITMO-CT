n, m = map(int, input().split())
data = [list(map(int, input().split())) for _ in range(n)]
summa = [[0] * m for _ in range(n)]
summa[0][0] = data[0][0]
for i in range(1, n):
    summa[i][0] = summa[i - 1][0] + data[i][0]
for j in range(1, m):
    summa[0][j] = summa[0][j - 1] + data[0][j]
for i in range(1, n):
    for j in range(1, m):
        summa[i][j] = data[i][j] + max(summa[i - 1][j], summa[i][j - 1])
print(summa[-1][-1])

i, j = n - 1, m - 1
path = []
while i > 0 or j > 0:
    if i > 0 and (j == 0 or summa[i - 1][j] >= summa[i][j - 1]):
        path.append('D')
        i -= 1
    else:
        path.append('R')
        j -= 1

print(*path[::-1])
