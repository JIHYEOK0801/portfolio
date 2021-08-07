## heap을 이용한 문제풀이

## 1. 남아있는 값들의 제곱값의 합을 최소화 시키는 것이 목표
## 2. 이를 위해서는 최대값의 크기가 최대한 작아져야 한다
## 3. n이 0보다 큰 동안 힙에서 최대값을 1씩 뺀후 다시 힙에 넣으면 된다

import heapq


def solution(n, works):
    answer = 0
    heap = []
    for i in works:
        heapq.heappush(heap, (-i, i))

    while n > 0:
        n -= 1
        max_n = heapq.heappop(heap)[1]
        if max_n < 1:
            return 0
        insert_n = max_n - 1
        heapq.heappush(heap, (-insert_n, insert_n))
        # print(heap)

    for i in heap:
        answer += i[1] ** 2

    return answer


print(solution(4, [4, 3, 3]))
