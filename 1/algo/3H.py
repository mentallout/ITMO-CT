a = [int(i) for i in input().split()]
b = [int(i) for i in input().split()]
cnt = 0
while len(a) > 0 and len(b) > 0:
    cnt += 1
    f, s = a.pop(0), b.pop(0)
    if [f, s] == [0, 9] or f > s and [s, f] != [0, 9]:
        a = a + [f, s]
    else:
        b = b + [f, s]
    if cnt == 10 ** 6:
        print('botva')
        break
if len(b) == 0:
    print('first', cnt)
elif len(a) == 0:
    print('second', cnt)
