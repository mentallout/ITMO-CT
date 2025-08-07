h = [int(i) for i in input().split()]
n = h[0]
h = [-1] + h[1:] + [0]
stack = []
res = 0
for i in range(n + 2):
    while stack and h[i] < h[stack[-1]]:
        x = stack.pop()
        height = h[x]
        width = i - stack[-1] - 1 if stack else i
        res = max(res, height * width)
    stack.append(i)
print(res)
