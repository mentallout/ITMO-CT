n, k = map(int, input().split())
l = [0] * n
for i in range(n):
    l[i] = int(input())
left, right = 1, max(l)
res = 0
while left <= right:
    mid = (left + right) // 2
    cnt = 0
    for i in range(n):
        cnt += l[i] // mid
    if cnt >= k:
        res = mid
        left = mid + 1
    else:
        right = mid - 1
if res < 0:
    res = 0
print(res)
