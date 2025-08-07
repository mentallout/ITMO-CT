temp = input().split()
n, a = int(temp[0]), float(temp[1])
h = [a] + [0.00] * (n - 1)
l, r = 0, a
while r - l > 0.000001:
    mid = (l + r) / 2
    h[1] = mid
    for i in range(2, n):
        h[i] = 2 + 2 * h[i - 1] - h[i - 2]
        if h[i] < 0:
            l = mid
            break
    if l != mid:
        r = mid
print(round(h[n - 1], 2))
