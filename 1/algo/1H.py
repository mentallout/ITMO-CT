n, k = map(int, input().split())
arr = [int(i) for i in input().split()]
arr = sorted(arr)
print(arr[k - 1])
