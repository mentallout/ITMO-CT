k = int(input())
a = [int(x) for x in input().split()]
c = [int(x) for x in input().split()]

P = [0] * k
Q = [1] + [-i for i in c]
for n in range(k):
    v = 0
    for i in range(min(n + 1, len(Q))):
        v += Q[i] * a[n - i]
    P[n] = v
while len(P) > 1 and P[-1] == 0:
    P.pop()
while len(Q) > 1 and Q[-1] == 0:
    Q.pop()

for x in [P, Q]:
    print(len(x) - 1)
    print(*x)
