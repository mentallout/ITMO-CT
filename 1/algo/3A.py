n = int(input())
a = [int(i) for i in input().split()]
left, right = 0, 0
summa = 0
end = -1
res = a[0]
for r in range(n):
    summa += a[r]
    if summa > res:
        res = summa
        left = end + 1
        right = r
    if summa < 0:
        summa = 0
        end = r
print(left + 1, right + 1, res)
