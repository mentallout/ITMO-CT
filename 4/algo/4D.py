n = int(input())
results = []
for _ in range(n):
    num = int(input())
    if num < 2:
        print('NO')
        continue
    small_primes = (2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37)
    flag = 0
    for p in small_primes:
        if num % p == 0:
            print('NO' if num != p else 'YES')
            flag = 1
            break
    if flag == 1:
        continue
    d = num - 1
    s = 0
    while d % 2 == 0:
        s += 1
        d //= 2
    flag = 0
    for a in (2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37):
        if a >= num:
            continue
        x = pow(a, d, num)
        if x == 1 or x == num - 1:
            continue
        for _ in range(s - 1):
            x = (x * x) % num
            if x == num - 1:
                break
        else:
            flag = 1
            break
    print('YES' if flag == 0 else 'NO')
