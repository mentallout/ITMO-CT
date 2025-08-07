p = int(input())
half = p // 2
inv = [0] * (half + 1)
inv[1] = 1
for i in range(2, half + 1):
    inv[i] = (p - p // i) * inv[p % i] % p
current_sum = 0
for i in range(1, p):
    if i <= half:
        inverse = inv[i]
    else:
        inverse = p - inv[p - i]
    current_sum += inverse
    if current_sum >= p:
        current_sum -= p
    if i % 100 == 0:
        print(current_sum)
        current_sum = 0
if (p - 1) % 100 != 0:
    print(current_sum)
