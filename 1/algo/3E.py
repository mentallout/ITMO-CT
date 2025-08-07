com = input()
stack = []
while True:
    if com == "pop":
        print(stack.pop(-1))
    elif com == "back":
        print(stack[-1])
    elif com == "size":
        print(len(stack))
    elif com == "clear":
        stack = []
        print('ok')
    elif com == "exit":
        print('bye')
        break
    else:
        stack.append(int(com.split()[-1]))
        print('ok')
    com = input()
