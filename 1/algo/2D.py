n, k = map(int, input().split())
a = [int(i) for i in input().split()]
b = [int(i) for i in input().split()]
a = sorted(a)
for i in range(k):
    l, r = 0, n - 1
    flag = 'NO'
    while l <= r:
        mid = (l + r) // 2
        last = a[mid]
        if last == b[i]:
            flag = 'YES'
            break
        elif last < b[i]:
            l = mid + 1
        else:
            r = mid - 1
    print(flag)
