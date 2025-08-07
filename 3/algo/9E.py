s = input() + '$'
suf = [s[i:] for i in range(len(s))]
suf = [len(s) - len(x) for x in sorted(suf)]
pos = [0] * len(s)
lcp = [0] * len(s)
for i in range(len(s)):
    pos[suf[i]] = i
k = 0
for i in range(len(s)):
    if k > 0:
        k -= 1
    if pos[i] == len(s) - 1:
        lcp[len(s) - 1] = -1
        k = 0
        continue
    else:
        j = suf[pos[i] + 1]
        while max(i + k, j + k) < len(s) and s[i + k] == s[j + k]:
            k += 1
        lcp[pos[i]] = k
print((len(s) - 1) * len(s) // 2 - (sum(lcp) + 1))
