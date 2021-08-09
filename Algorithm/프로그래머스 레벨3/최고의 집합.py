"""
n개의 자연수로 합은 S
곱은 최대로 만들어야 하는것이 문제

곱을 최대로 만드려면??
n개의 자연수 각각의 값들을 최대한 같게 만들어야한다
ex) n = 3 s = 9
3 3 3 ---->> 27 (target)
4 3 2 ---->> 24
5 3 1 ---->> 15
"""


def solution(n, s):
    answer = []

    mok = s // n
    namuji = s % n

    if mok == 0:
        return [-1]

    li = [mok] * n

    for i in range(1, namuji + 1):
        li[-i] += 1
    answer = li
    return answer


# print(solution(2, 9))
# print(solution(2, 1))
print(solution(5, 29))
