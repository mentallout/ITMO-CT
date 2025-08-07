n = int(input()) - 2
first, second = 1, 1
while n > 0:
    first, second = second, first + second
    n -= 1
print(second)
