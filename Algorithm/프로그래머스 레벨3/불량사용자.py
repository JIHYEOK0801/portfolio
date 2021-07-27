from itertools import combinations
from collections import deque
from copy import deepcopy
## 두 문자열이 같을 수 있는지 비교하는 함수
def check_same(u_id, b_id): 
    check_same_bit = 0
    if len(u_id) == len(b_id):
        for i in range(len(u_id)):
            if b_id[i] != '*':
                if u_id[i] != b_id[i]:
                    break
        else:
            check_same_bit = 1
    
    return check_same_bit


def solution(user_id, banned_id):
    answer = 1
    dic = dict()

    for index in range(len(banned_id)):
        dic[index] = []
    
    ## banned_id를 순회하면서 각 id 마다 부합하는 user_id를 딕셔너리 형태로 생성

    ## 1. lenghth 비교, 알파벳 비교
    for index in range(len(banned_id)):
        for u_id in user_id:
            if check_same(u_id, banned_id[index]):
                dic[index].append(u_id)
    
    ## select_lists에는 각 ban_id 마다 가능한 list가 들어있음
    select_lists = []
    for i in dic:
        if len(dic[i]) <= 0:
            return 0
        else:
            select_lists.append(dic[i])
    

    answers = deque()


    for select in select_lists:
        ## 첫번째 원소를 고를때
        if not answers:
            for u_id in select:
                answers.append([u_id])
        
        ## 첫번째가 아닐 때
        else:
            new_answers = deque()
            while(answers):
                a = answers.popleft()

                for u_id in select:
                    if u_id not in a:
                        temp = deepcopy(a)
                        temp.append(u_id)
                        new_answers.append(temp)
            answers = deepcopy(new_answers)
    
    for i in range(len(answers)):
        answers[i] = set(answers[i])

    to_count_answer = []

    for a in answers:
        if a not in to_count_answer:
            to_count_answer.append(a)

    answer = len(to_count_answer)
    return answer

#print(solution(["frodo", "fradi", "crodo", "abc123", "frodoc"],["fr*d*", "abc1**"]))
print(solution(["frodo", "fradi", "crodo", "abc123", "frodoc"],["*rodo", "*rodo", "******"]	))
