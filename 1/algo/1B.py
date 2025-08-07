from random import randint


def quicksort(a):
    if len(a) < 2:
        return a
    low, same, high = [], [], []

    pivot = a[randint(0, len(a) - 1)]

    for item in a:
        if item < pivot:
            low.append(item)
        elif item == pivot:
            same.append(item)
        elif item > pivot:
            high.append(item)
    return quicksort(low) + same + quicksort(high)


n = int(input())
array = [int(i) for i in input().split()]
print(*quicksort(array))
