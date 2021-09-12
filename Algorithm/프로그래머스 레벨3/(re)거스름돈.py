def solution(n, money):
    answer = 0
    dic = {}
    money.sort(reverse=True)

    for m in money:
        dic[m] = n//m

    for key in money:
        
    
    


    
    return answer

print(solution(5,[1,2,5]))