"""
1. a[0] a[-1]은 항상 됨
2. 사이의 것들은 양쪽 모두 본인보다 작은수가 있을 시 남을 수 없음

1,2 번을 깨달으면 금방 풀 수 있는 문제이다.
"""
def solution(a):
    a_reversed = list(reversed(a))
    count_list = [0] * len(a)
    a_reversed_count_list = [0]*len(a_reversed)
    answer = 0
    
    a_smallest_in_left = float('inf')
    a_reversed_smallest_in_left = float('inf')
    
    for i in range(len(a)):
        if i>0:
            if a_smallest_in_left < a[i]:
                count_list[i] += 1
            else:
                a_smallest_in_left = a[i]


            
            if a_reversed_smallest_in_left < a_reversed[i]:
                a_reversed_count_list[i] += 1
            else:
                a_reversed_smallest_in_left = a_reversed[i]
        
        else:
            a_smallest_in_left = a[i]
            a_reversed_smallest_in_left = a_reversed[i]

    a_reversed_count_list = list(reversed(a_reversed_count_list))

    for i,j in zip(count_list,a_reversed_count_list):
        if i+j < 2:
            answer += 1
        
    return answer

#print(solution([9,-1,-5]))
print(solution([-16,27,65,-2,58,-92,-71,-68,-61,-33]))
#print(solution([1,3,2,4,5,7,6,9]))

