## 1. 각 원소마다 딕셔너리를 만드록 소인수 분해를 한 후 {소인수 : 개수} 형태로 저장. 각 소인수의 개수 + 1 을 다 곱해주면 약수의 개수

def solution(left, right):
    answer = 0
    

    for i in range(left, right+1):
        dic = {}
        n = i
        k = 2
        
        while(n > 1):
            c = 0

            while(n % k == 0):
                n = n//k
                c += 1

            if c > 0:
                dic[k] = c
            
            k += 1


        count = 1
        for key in dic.keys():
            count *= (dic[key] + 1)
        
        if count % 2 == 0:
            answer += i
        else:
            answer -= i
        
    return answer


print(solution(13,17))
print(solution(24,27))