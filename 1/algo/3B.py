n = int(input())
a = [int(i) for i in input().split()]
b = [-1] * n
stack = []
for i in range(n):
    while stack and a[i] < a[stack[-1]]:
        b[stack.pop()] = i
    stack.append(i)
print(*b)
