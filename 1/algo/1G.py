def sort(arr, start=0, end=-1):
    if end == -1:
        end = len(arr) - 1
    if start < end:
        pivot = (start + end) // 2
        return sort(arr, start, pivot) + sort(arr, pivot + 1, end) + merging(arr, start, pivot, end)
    else:
        return 0


def merging(arr, start, pivot, end):
    a = []
    cnt = 0
    i, j = start, pivot + 1
    while i <= pivot and j <= end:
        if arr[i] <= arr[j]:
            a.append(arr[i])
            i += 1
        else:
            a.append(arr[j])
            cnt += pivot - i + 1
            j += 1
    a += arr[i:pivot + 1]
    a += arr[j:end + 1]
    arr[start:end + 1] = a
    return cnt


n = int(input())
arr = [int(i) for i in input().split()]
print(sort(arr))
