def gcd(a, b):
    if b == 0:
        return a, 1, 0
    f, x1, y1 = gcd(b, a % b)
    x = y1
    y = x1 - (a // b) * y1
    return f, x, y


a, b, c = map(int, input().split())
f, x0, y0 = gcd(a, b)
if c % f != 0:
    print('Impossible')
    exit(0)
x0 *= c // f
y0 *= c // f
f1 = b // f
f2 = a // f
k = (-x0) // f1
if x0 + k * f1 < 0:
    k += 1
x = x0 + k * f1
y = y0 - k * f2
if x < 0:
    print('Impossible')
else:
    print(x, y)
