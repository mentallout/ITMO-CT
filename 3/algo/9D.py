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
max_len = 0
for i in range(1, len(s)):
    if N[i] + i == len(s) and max_len + i >= len(s):
        print(s[:len(s) - i])
        exit(0)
    max_len = max(max_len, N[i])
print('Just a legend')
