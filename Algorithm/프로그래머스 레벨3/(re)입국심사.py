## 이분탐색
## '정해진 시간'에 얼마나 사람을 수용할 수 있는지 를 탐색해야한다!!

def solution(n, times):
    answer = 0

    max_time = max(times) * n

    left_t = 0
    right_t = max_time
    
    while(right_t - left_t > 1):
        mid = left_t + (right_t - left_t)//2 
        max_person = 0
        print(left_t, mid, right_t)
        for t in times:
            max_person += mid // t
            if max_person >= n:
                right_t = mid
                break
        else:
            left_t = mid
        
    answer = right_t

    return answer

print(solution(6, [7,10]))