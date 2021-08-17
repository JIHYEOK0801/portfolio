"""
동적계획법

숫자 5 1개로 만들 수 있는 수 : 1번 set
5

숫자 5 2개로 만들 수 있는 수 :
5 + 5 , 5 - 5 , 5 * 5, 5 // 5 , 55 : 2번 set

숫자 2개로 만들 수 있는 수들은 2 = 1 + 1 --> 1번 세트와 1번 세트를 사칙연산한 수들과 55

숫자 3개로 만들 수 있는 수들은 3 = 1 + 2, 2 + 1 --> 1번 세트와 2번 세트를 사칙연산한 수들, 2번세트와 1번 세트를 사칙연산한 수들과 555

숫자 4개로 만들 수 있는 수들은 4 = 1 + 3, 2 + 2, 3 + 1 --> ..... 

이런 방식으로 8까지 찾으면 된다



처음에 나는 위와 같은 접근은 중복되는 수들이 많을 까봐 이미 배제해 두었던 것이 실수였다.
"""


def solution(N, number):
    answer = 0

    n_list = [set([0])]

    for i in range(1, 9):
        temp = set()
        temp.add(int(str(N) * i))

        for j in range(1, i):
            n_list_i = list(n_list[j])
            n_list_j = list(n_list[i - j])

            for item_i in n_list_i:
                for item_j in n_list_j:
                    temp.add(item_i + item_j)
                    temp.add(item_i - item_j)
                    if item_j is not 0:
                        temp.add(item_i // item_j)
                    temp.add(item_i * item_j)

        if number in temp:
            answer = i
            return answer

        n_list.append(temp)
    else:
        answer = -1
        return answer

    return answer


print(solution(2, 11))
