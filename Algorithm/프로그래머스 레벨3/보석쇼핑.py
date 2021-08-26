"""
l,r포인터를 활용해서 시간복잡도를 최소로 가져가는 방법

같은 길이의 구간으로 반복해서 검사한다는 생각까지만 했어서 처음에는 포인터 생각을 하지 못했다.


"""
from copy import deepcopy
def solution(gems):
    answer = [1, len(gems)]
    setted_gems = set(deepcopy(gems))
    target_count = len(setted_gems)

    dic_gems = {}
    l = 0
    r = 0
    min_count = float('inf')
    for r in range(0, len(gems)):
        if gems[r] in dic_gems:
            dic_gems[gems[r]] += 1
        else:
            dic_gems[gems[r]] = 1

        while len(dic_gems) >= target_count:
            count = r - l + 1
            if count < min_count:
                answer = [l + 1, r + 1]
                min_count = count
            if dic_gems[gems[l]] == 1:
                dic_gems.pop(gems[l])
            else:
                dic_gems[gems[l]] -= 1
            l += 1
    return answer

#print(solution(["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]))
#print(solution(["AA", "AB", "AC", "AA", "AC"]))
#print(solution(["ZZZ", "YYY", "NNNN", "YYY", "BBB"]))
print(solution(['a','b','b','a','b','c']))