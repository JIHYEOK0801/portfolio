## heap을 이용한 문제풀이

import heapq
def solution(n, works):
    answer = 0
    heap = []
    for i in works:
        heapq.heappush(heap, (-i,i))
    
    while(n > 0):
        n -= 1
        max_n = heapq.heappop(heap)[1]
        if max_n < 1:
            return 0
        insert_n = max_n - 1
        heapq.heappush(heap,(-insert_n, insert_n))
        #print(heap)
    
    for i in heap:
        answer += i[1] ** 2

    return answer

print(solution( 4, [4, 3, 3]))