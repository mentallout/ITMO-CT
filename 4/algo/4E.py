a, n, m = map(int, input().split())
result = 1
a %= m
while n:
    if n & 1:
        result = (result * a) % m
    a = (a * a) % m
    n //= 2
print(result)
