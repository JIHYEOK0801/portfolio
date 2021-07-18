# absolutes 와 signs 를 zip으로 묶고 순회하면서 answer 에 +

def solution(absolutes, signs):
    answer = 0

    for a,b in zip(absolutes, signs):
        if b == True:
            answer += a
        else:
            answer += a*(-1)
    
    return answer

solution([4,7,12], [True,False,True])