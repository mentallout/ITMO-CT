from itertools import groupby

n = int(input())
factors = []
while n % 2 == 0:
    factors.append(2)
    n //= 2
p = 3
while p * p <= n:
    while n % p == 0:
        factors.append(p)
        n //= p
    p += 2
if n > 1:
    factors.append(n)
result = []
for prime, group in groupby(factors):
    count = sum(1 for _ in group)
    if count > 1:
        result.append(f'{prime}^{count}')
    else:
        result.append(str(prime))
print('*'.join(result))
