"""
내가 생각했던 풀이법:::

1. 시작지점에서 경주로를 만들면서 지금 짓고 있는 경주로의 루트가 최소 금액 루트인지 알 수 있는가??
2. 1번이 불가능 하다고 생각하여 종료지점에 도착하는 경우의 수를 모두 구해야 한다고 생각
3. 구한 경우의 수마다 금액을 산출해서 최소금액이 정답


"""
"""from copy import deepcopy

min_money = float("inf")
min_count = float("inf")
start_point = [1, 1]
end_point = []


def check_money(route_history):
    money = 0
    strate = 100
    corner = 500
    for i in range(len(route_history)):

        if i >= 2:
            i_x = route_history[i][0]
            i_y = route_history[i][1]
            i_2_x = route_history[i - 2][0]
            i_2_y = route_history[i - 2][1]

            if (i_x != i_2_x) & (i_y != i_2_y):
                money += corner
                money += strate
            else:
                money += strate

        elif i == 1:
            money += strate
        elif i == 0:
            continue
    return money


def find_route(board, point_now, count, route_history):
    global min_count
    global min_money
    global min_count
    x = point_now[0]
    y = point_now[1]

    ## 방문표시, history 추가
    copyboard = deepcopy(board)
    copyroute_history = deepcopy(route_history)
    copyboard[x][y] = 1
    copyroute_history.append([x, y])

    ## end_point 에 도착했을 때
    if x == end_point[0] & y == end_point[1]:
        if count <= min_count:
            min_count = count
            money = check_money(copyroute_history)
            if money < min_money:
                min_money = money
        return

    ## 시계방향 순으로 나아갈 방향 검사
    if copyboard[x - 1][y] == 0:
        new_point = [x - 1, y]
        find_route(copyboard, new_point, count + 1, copyroute_history)

    if copyboard[x][y + 1] == 0:
        new_point = [x, y + 1]
        find_route(copyboard, new_point, count + 1, copyroute_history)

    if copyboard[x + 1][y] == 0:
        new_point = [x + 1, y]
        find_route(copyboard, new_point, count + 1, copyroute_history)

    if copyboard[x][y - 1] == 0:
        new_point = [x, y - 1]
        find_route(copyboard, new_point, count + 1, copyroute_history)


def solution(board):
    answer = 0
    global end_point
    global start_point
    global min_money
    end_point = [len(board), len(board)]

    for i in range(len(board)):
        board[i] = [1] + board[i] + [1]
    board = [[1] * (len(board) + 2)] + board + [[1] * (len(board) + 2)]

    find_route(board, [1, 1], 0, [])

    answer = min_money

    return answer
"""
from collections import deque


def solution(board):
    answer = 0
    min_money = float("inf")
    end_point = [len(board), len(board)]

    ## padding 1로 추가
    for i in range(len(board)):
        board[i] = [1] + board[i] + [1]
    board = [[1] * (len(board) + 2)] + board + [[1] * (len(board) + 2)]

    ## 행, 열, 코너개수, 방향, 진행 칸 수
    to_go_list = deque([[1, 1, 0, "start", 0]])

    while to_go_list:

        temp_list = to_go_list.popleft()

        i = temp_list[0]
        j = temp_list[1]
        corner_count = temp_list[2]
        direction = temp_list[3]
        count = temp_list[4]

        if corner_count > end_point[0] * end_point[1]:
            break
        ## 도착했을 때
        if i == end_point[0] and j == end_point[1]:

            money = count * 100 + corner_count * 500
            if money < min_money:
                min_money = money
            else:
                continue

        ## 방문표시
        board[i][j] = 1

        ## 시계방향 순으로 나아갈 방향 검사
        if board[i - 1][j] == 0:
            if direction == "up" or direction == "start":
                to_go_list.append([i - 1, j, corner_count, "up", count + 1])
            elif direction == "right" or direction == "left" or direction == "down":
                to_go_list.append([i - 1, j, corner_count + 1, "up", count + 1])

        if board[i][j + 1] == 0:
            if direction == "right" or direction == "start":
                to_go_list.append([i, j + 1, corner_count, "right", count + 1])
            elif direction == "up" or direction == "down" or direction == "left":
                to_go_list.append([i, j + 1, corner_count + 1, "right", count + 1])

        if board[i + 1][j] == 0:
            if direction == "down" or direction == "start":
                to_go_list.append([i + 1, j, corner_count, "down", count + 1])
            elif direction == "up" or direction == "right" or direction == "left":
                to_go_list.append([i + 1, j, corner_count + 1, "down", count + 1])

        if board[i][j - 1] == 0:
            if direction == "left" or direction == "start":
                to_go_list.append([i, j - 1, corner_count, "left", count + 1])
            elif direction == "up" or direction == "right" or direction == "down":
                to_go_list.append([i, j - 1, corner_count + 1, "left", count + 1])

    answer = min_money
    return answer


# print(solution([[0, 0, 0], [0, 0, 0], [0, 0, 0]]))
# print(solution([[0, 0, 1], [0, 0, 1], [0, 0, 0]]))
"""print(
    solution(
        [
            [0, 0, 0, 0, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 1, 0, 0],
            [0, 0, 0, 0, 1, 0, 0, 0],
            [0, 0, 0, 1, 0, 0, 0, 1],
            [0, 0, 1, 0, 0, 0, 1, 0],
            [0, 1, 0, 0, 0, 1, 0, 0],
            [1, 0, 0, 0, 0, 0, 0, 0],
        ]
    )
)"""
print(
    solution(
        [
            [0, 0, 0, 0, 0, 0],
            [0, 1, 1, 1, 1, 0],
            [0, 0, 1, 0, 0, 0],
            [1, 0, 0, 1, 0, 1],
            [0, 1, 0, 0, 0, 1],
            [0, 0, 0, 0, 0, 0],
        ]
    )
)
