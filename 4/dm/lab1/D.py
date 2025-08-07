from fractions import Fraction
from math import comb

r, k = map(int, input().split())
pk = [int(x) for x in input().split()]

a = []
for n in range(k + 1):
    term = Fraction(0)
    for i in range(len(pk)):
        if n - i >= 0:
            term += Fraction(pk[i]) * comb(n + k - i, k) * (r ** (n - i))
    a.append(term)
t = list(range(k + 1))
f = [x / (r ** n) for n, x in enumerate(a)]
n = len(t)
result = [Fraction(0) for _ in range(n)]
for i in range(n):
    basis = [Fraction(1)]
    denom = Fraction(1)
    for j in range(n):
        if i != j:
            denom *= (t[i] - t[j])
            temp = [Fraction(0)] * (len(basis) + len([-t[j], 1]) - 1)
            for l in range(len(basis)):
                for m in range(len([-t[j], 1])):
                    temp[l + m] += basis[l] * [-t[j], 1][m]
            basis = temp
    basis = [coef * f[i] / denom for coef in basis]
    temp = [Fraction(0)] * max(len(result), len(basis))
    for l in range(len(result)):
        temp[l] += result[l]
    for l in range(len(basis)):
        temp[l] += basis[l]
    result = temp

for coef in result:
    print(f'{coef.numerator}/{coef.denominator}', end=' ')
