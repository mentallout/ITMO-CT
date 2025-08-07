n = int(input())
if n <= 2:
    print(n)
else:
    if n % 2:
        result = n * (n - 1) * (n - 2)
    else:
        if n % 3:
            result = n * (n - 1) * (n - 3)
        else:
            result = (n - 1) * (n - 2) * (n - 3)
    print(result)
