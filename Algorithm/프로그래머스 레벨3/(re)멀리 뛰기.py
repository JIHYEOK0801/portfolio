
def solution(n):
    answer = 0

    li = [1, 2]
    for i in range(n-2):
        li.append(li[-1] + li[-2])
        #print(li)

    answer = li[n-1]
    return answer%1234567

print(solution(2000))