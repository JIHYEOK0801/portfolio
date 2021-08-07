"""def solution(stones, k):
    answer = 0
    max_number = float("inf")
    for i in range(0, len(stones) - k + 1):
        li_max_number = 0
        for j in range(i, i + k):
            if stones[j] > li_max_number:
                li_max_number = stones[j]

        if li_max_number <= max_number:
            max_number = li_max_number

    answer = max_number
    return answer"""
# 위는 시간초과 코드

"""
정답을 구할 수 있는 범위가 제한된 문제는 '이분탐색' 꼭 생각할 것!!!!!

통과 할 수 있는 사람 수를 기준으로 이분탐색을 실시 한다.


"""


def solution(stones, k):
    answer = 0
    L = min(stones)
    R = max(stones)

    # 이분탐색 시작
    while L <= R:

        midnumber = (L + R) // 2

        # 건널 수 없는 징검다리를 세는데 사용
        count = 0

        for i in stones:
            if i - (midnumber - 1) <= 0:
                count += 1
            else:
                count = 0

            if count >= k:
                R = midnumber - 1
                break
        else:
            answer = midnumber
            L = midnumber + 1

    return answer


print(solution([2, 4, 5, 3, 2, 1, 4, 2, 5, 1], 3))
# print(solution([2, 4, 5, 2, 2, 1, 4, 2, 5, 1], 3))
