s = input()
pref = [0] * len(s)
for i in range(1, len(s)):
    k = pref[i - 1]
    while k > 0 and s[i] != s[k]:
        k = pref[k - 1]
    if s[i] == s[k]:
        k += 1
    pref[i] = k
print(*pref)
