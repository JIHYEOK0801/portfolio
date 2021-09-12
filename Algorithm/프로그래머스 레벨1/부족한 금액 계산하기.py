def solution(price, money, count):
    answer = -1
    target = 0
    for i in range(1, count+1):
        target += price * i
    
    if money > target :
        return 0
    answer = target - money
    return answer

print(solution(3, 20, 4))