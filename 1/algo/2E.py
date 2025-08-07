n, k = map(int, input().split())
a = [int(i) for i in input().split()]
b = [int(i) for i in input().split()]
for i in range(k):
    if b[i] not in a:
        print(0)
    else:
        l, r = 0, n
        while l < r:
            mid = (l + r) // 2
            if a[mid] < b[i]:
                l = mid + 1
            else:
                r = mid
        print(l + 1, end=" ")
        l, r = 0, n
        while l < r:
            mid = (l + r) // 2
            if a[mid] > b[i]:
                r = mid
            else:
                l = mid + 1
        print(l)
