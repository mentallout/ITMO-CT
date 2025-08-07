def merge_sort(arr, start, end):
    if (end - start) > 1:
        mid = (start + end) // 2
        merge_sort(arr, start, mid)
        merge_sort(arr, mid, end)
        merging(arr, start, mid, end)


def merging(arr, start, mid, end):
    left = arr[start:mid]
    right = arr[mid:end]
    k = start
    i, j = 0, 0
    while (start + i < mid) and (mid + j < end):
        if left[i] <= right[j]:
            arr[k] = left[i]
            i += 1
        else:
            arr[k] = right[j]
            j += 1
        k += 1
    if start + i < mid:
        while k < end:
            arr[k] = left[i]
            i += 1
            k += 1
    else:
        while k < end:
            arr[k] = right[j]
            j += 1
            k += 1


n = int(input())
array = [int(i) for i in input().split()]
merge_sort(array, 0, len(array))
print(*array)
