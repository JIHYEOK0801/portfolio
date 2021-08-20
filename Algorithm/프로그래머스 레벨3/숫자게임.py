"""
처음 생각했던 방법 ::

A를 정렬시키고 B도 정렬시킨 다음에 그냥 비교해서 점수를 산출하면 도출 될 줄 알았다.

하지만 같은 숫자 때문에 답을 도출 못했음.

ex))  A = [1,3,3,5,7] B = [1,1,1,3,3]
그냥 비교하면 0점이 되어버렸다.


최종 생각했던 방법 ::

배열 B에 index 변수를 하나 둬서
A보다 작지 않게 유지하면서 답을 세어 나가는 방법.
"""
def solution(A, B):
    answer = 0

    A.sort()
    B.sort()

    j = 0

    for i in range(len(B)):
        if (B[i] > A[j]):
            answer += 1
            j += 1
        
    return answer

print(solution([5,1,3,7], [2,2,6,8]))
## print(solution([2,2,2,2], [1,1,1,1]))