def gcd(a, b):
    if a == 0:
        return b, 0, 1
    g, x1, y1 = gcd(b % a, a)
    x = y1 - (b // a) * x1
    y = x1
    return g, x, y


t = int(input())
for i in range(t):
    a, b, n, m = map(int, input().split())
    tn, tm = n, m
    while tm:
        tn, tm = tm, tn % tm
    d = tn
    if (a % d) != (b % d):
        print('NO')
        continue
    lcm = (n * m) // d
    n1 = n // d
    m1 = m // d
    g, u, _ = gcd(n1, m1)
    if g != 1:
        print('NO')
        continue
    t0 = (((b - a) // d) * u) % m1
    x0 = (a + n * t0) % lcm
    print('YES', x0, lcm)
