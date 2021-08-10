"""
내가 생각했던 풀이 방법:::

def check(weak, j, distance, n):
    inspect_possible_list = []

    new_weak = weak[j:] + weak[:j]
    for i in range(len(new_weak)):
        if new_weak[i] < new_weak[0]:
            new_weak[i] += n

    for i in range(len(new_weak)):
        if new_weak[i] <= new_weak[0] + distance:   
            number = new_weak[i]
            if number >= n:
                number -= n
            inspect_possible_list.append(number)
        else:
            break

    return inspect_possible_list


def solution(n, weak, dist):
    answer = 0
    dist.sort()

    for i in range(len(dist) - 1, -1, -1):
        # 취약지점이 없을 때
        if not weak:
            break
        # 취약지점이 남아있을 때
        else:
            inspect_possible_list = []
            for j in range(0, len(weak)):
                temp_list = check(weak, j, dist[i], n)
                if len(temp_list) >= len(inspect_possible_list):
                    inspect_possible_list = temp_list

            for w in inspect_possible_list:
                weak.remove(w)

            answer += 1
    if weak:
        answer = -1
    return answer

틀린 이유 : 

제일 넓은 범위를 점검할 수 있는 사람부터 최대로 점검 가능한 지점들을 저장해서 빼는 방식으로 시도했었음

점검하려는 친구마다 본인이 커버 가능한 최대의 점검 가능 구역을 세므로 문제의 취지와 맞지 않음

"""


"""
정답 풀이 방법 ::

1. 범위가 넓지 않기에 친구를 배치할 수 있는 경우의 수를 모두 만들어서 점검이 되는지 확인한다
2. 경우의 수를 만들 때는 사람 수를 오름차순으로 만든다
"""
from itertools import permutations
from collections import deque


def check(arrange, weak, n):
    weak_plusN = []
    for w in weak:
        weak_plusN.append(w + n)

    for i in range(len(weak)):
        new_weak = deque(weak[i:] + weak_plusN[:i])
        for distance in arrange:
            count = 0
            for j in range(len(new_weak)):
                if new_weak[j] <= new_weak[0] + distance:
                    count += 1
                else:
                    break
            for c in range(count):
                new_weak.popleft()
        if not new_weak:
            return len(arrange)

    return -1


def solution(n, weak, dist):
    answer = -1
    ## 가능한 배치의 방법 모두 만들기
    dist.sort()
    possible_arrange_list = []
    for i in range(len(dist)):
        possible_arrange_list += list(permutations(dist, i + 1))

    for arrange in possible_arrange_list:
        a = check(arrange, weak, n)
        if a > answer:
            return a
    return answer


print(solution(12, [1, 5, 6, 10], [1, 2, 3, 4]))
# print(solution(12, [1, 3, 4, 9, 10], [3, 5, 7]))
