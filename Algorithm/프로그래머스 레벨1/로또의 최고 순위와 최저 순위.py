def solution(lottos, win_nums):
    answer = []
    count = 0
    zero_count = lottos.count(0)
    
    for n in lottos:
        if n == 0: continue
        if n in win_nums:
            count += 1

    max_count = zero_count + count
    min_count = count
    if min_count == 0 :
        min_count += 1
    if max_count == 0:
        max_count += 1

    answer.append(7-max_count)
    answer.append(7-min_count)
    

    return answer


print(solution([44, 1, 0, 0, 31, 25], [31, 10, 45, 1, 6, 19]))