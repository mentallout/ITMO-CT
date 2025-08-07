s = input()
N = [len(s)] + [0] * (len(s) - 1)
l, r = 0, 0
for i in range(1, len(s)):
    N[i] = max(min(r - i, N[i - l]), 0)
    while N[i] + i < len(s) and s[N[i]] == s[i + N[i]]:
        N[i] += 1
    if i + N[i] > r:
        l = i
        r = N[i] + i
print(*N)
