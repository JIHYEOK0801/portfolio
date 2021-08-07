"""
1. 첫번 째 방법 - permutations 이용하기 (시간초과 남)
from itertools import permutations
def solution(n, k):
    li = [ i + 1 for i in range(n)]
    answer = []

    p = list(permutations(li, n))
    print(p)
    for i in range(len(p)):
        print(i+1 ,' : ' , p[i])
        
    answer = list(p[k-1])

    return answer

print(solution(4,15))
"""


"""
2. 두번째 방법
몫과 나머지를 활용한 방법
나열하는 숫자의 각 자리마다 고정된 숫자가 몇번 반복되는지를 이용

ex) n = 3, k = 5
원활한 계산위해 k = k - 1

1, 2, 3
1, 3, 2
2, 1, 3
2, 3, 1
3, 1, 2 (target)
3, 2, 1

1이 제일 앞에 고정된 채로 (n-1)! 번 반복된다(1.,2.번)
2이 제일 앞에 고정된 채로 (n-1)! 번 반복된다(3.,4.번)
이런 식으로 유추해 가면 해결
"""


def factorial(n):
    i = 1
    while n > 1:
        i *= n
        n -= 1
    return i


def solution(n, k):
    answer = []
    k = k - 1
    n_li = [i + 1 for i in range(n)]

    while n_li:
        f = factorial(n - 1)
        mok = k // f
        namuji = k % f
        answer.append(n_li[mok])
        del n_li[mok]
        k = namuji
        n = n - 1
        # print(answer)
    return answer


print(solution(3, 5))
