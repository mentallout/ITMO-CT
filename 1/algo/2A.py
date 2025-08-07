n, k = map(int, input().split())
arr = [int(i) for i in input().split()]
l, r = 0, arr[-1] - arr[0] + 1
while r - l > 0:
    mid = (l + r) // 2
    last = arr[0]
    cnt = 1
    for i in range(1, n):
        if arr[i] - last > mid:
            cnt += 1
            last = arr[i]
    if cnt >= k:
        l = mid + 1
    else:
        r = mid
print(l)
