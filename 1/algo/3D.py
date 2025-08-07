n, k = int(input()), int(input())
a = []
for _ in range(n):
    a.append(int(input()))
mins = []
stack = []
for i in range(len(a)):
    val = a[i]
    while stack and stack[0] < i - k + 1:
        stack.pop(0)
    while stack and a[stack[-1]] > val:
        stack.pop()
    stack.append(i)
    if i >= k - 1:
        mins.append(a[stack[0]])
print(*mins, sep="\n")
