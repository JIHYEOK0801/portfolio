"""
대표적인 재귀문제.

어떤 항목이 반복적으로 호출 되는지를 잘 파악해야한다.


<문제를 푸는 순서>

먼저 전체적으로 어떤 반복구성이 있는지 파악한다.

여기서의 반복구조는 다음과 같다.

1. from 에서 n-1개의 원판을 temp로 옮겨 놓는다.
2. from 에서 제일 밑의 원판을 to로 옮겨 놓는다.
3. temp에 남아있는 n-1개의 원판들을 to로 옮긴다.

ex 1) n = 4
    1-1. 3개의 원판을 from 에서 temp로 옮긴다.
    1-2. from에 남아있는 원판을 to 로 옮긴다.
    1-3. temp에 있는 3개의 원판을 to로 옮긴다.
    1-1 ~ 1-3의 과정은 각각 n = 3일 때 를 생각해 주면 된다.

"""

answer = []


def hanoi(n, start, to, temp):
    global answer

    if n == 1:
        answer.append([start, to])
        return

    hanoi(n - 1, start, temp, to)
    answer.append([start, to])
    hanoi(n - 1, temp, to, start)


def solution(n):
    global answer
    hanoi(n, 1, 3, 2)

    return answer


print(solution(4))
