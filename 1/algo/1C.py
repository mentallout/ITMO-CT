n, m, k = map(int, input().split())
arr = [input() for i in range(n)]

for j in range(m - 1, m - k - 1, -1):
    res = [0] * n
    count = [0] * 26
    for i in range(n):
        count[ord(arr[i][j]) - ord('a')] += 1
    for i in range(1, 26):
        count[i] += count[i - 1]
    i = n - 1
    while i >= 0:
        res[count[ord(arr[i][j]) - ord('a')] - 1] = arr[i]
        count[ord(arr[i][j]) - ord('a')] -= 1
        i -= 1
    for i in range(n):
        arr[i] = res[i]

for i in range(n):
    print(arr[i])
