n = int(input())
N = [int(x) for x in input().split()]
pref = [0] * n
for i in range(1, n):
    if N[i]:
        j = N[i] - 1
        while j > -1 and not pref[i + j]:
            pref[i + j] = j + 1
            j -= 1
print(*pref)
