n = int(input())
a = [int(i) for i in input().split()]
m = int(input())
b = [int(i) for i in input().split()]
dp = [[0] * (m + 1) for _ in range(n + 1)]
for i in range(1, n + 1):
    for j in range(1, m + 1):
        if a[i - 1] == b[j - 1]:
            dp[i][j] = dp[i - 1][j - 1] + 1
        else:
            dp[i][j] = max(dp[i][j - 1], dp[i - 1][j])
print(dp[n][m])

ans = []
i, j = n, m
while i > 0 and j > 0:
    if a[i - 1] == b[j - 1]:
        ans.append(a[i - 1])
        i -= 1
        j -= 1
    elif dp[i][j - 1] > dp[i - 1][j]:
        j -= 1
    else:
        i -= 1
print(*ans[::-1])
