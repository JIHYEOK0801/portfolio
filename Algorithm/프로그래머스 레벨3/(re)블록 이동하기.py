def bfs(board):
    def check_move(route):
        def move_up(position):
            if position[0] == 0 or position[2] == 0:
                return []
            elif board[position[0] - 1][position[1]] == 1 or board[position[2] - 1][position[3]] == 1:
                return []
            else:
                return[position[0] - 1, position[1], position[2] - 1, position[3]]

        def move_right(position):
            if position[1] == len(board)-1 or position[3] == len(board)-1:
                return []
            elif board[position[0]][position[1] + 1] == 1 or board[position[2]][position[3] + 1] == 1:
                return []
            else:
                return[position[0], position[1] + 1, position[2], position[3] + 1]

        def move_down(position):
            if position[0] == len(board)-1 or position[2] == len(board)-1:
                return []
            elif board[position[0]+1][position[1]] == 1 or board[position[2]+1][position[3]] == 1:
                return []
            else:
                return[position[0]+1, position[1], position[2]+1, position[3]]

        def move_left(position):
            if position[1] == 0 or position[3] == 0:
                return []
            elif board[position[0]][position[1] - 1] == 1 or board[position[2]][position[3] - 1] == 1:
                return []
            else:
                return[position[0], position[1]-1, position[2], position[3]-1]
            
        up = move_up(route)
        right = move_right(route)
        down = move_down(route)
        left = move_left(route)
        return[up,right,down,left]
        
    def check_rotate(route):    
        def rotate_right_up(position):
            if position[0] == 0 or position[2] == 0:
                return []
            elif board[position[0] - 1][position[1]] == 1 or board[position[2] - 1][position[3]] == 1:
                return []
            else:
                return[position[0]-1, position[1]+1, position[2], position[3]]

        def rotate_right_down(position):
            if position[0] == len(board)-1 or position[2] == len(board)-1:
                return []
            elif board[position[0]+1][position[1]] == 1 or board[position[2]+1][position[3]] == 1:
                return []
            else:
                return[position[0]+1, position[1]+1, position[2], position[3]]

        def rotate_left_up(position):
            if position[0] == 0 or position[2] == 0:
                return []
            elif board[position[0] - 1][position[1]] == 1 or board[position[2] - 1][position[3]] == 1:
                return []
            else:
                return[position[0], position[1], position[2]-1, position[3]-1]

        def rotate_left_down(position):
            if position[0] == len(board)-1 or position[2] == len(board)-1:
                return []
            elif board[position[0]+1][position[1]] == 1 or board[position[2]+1][position[3]] == 1:
                return []
            else:
                return[position[0], position[1], position[2]+1, position[3]+1]
        
        if route[0] == route[2]:
            rru = rotate_right_up(route)
            rrd = rotate_right_down(route)
            rlu = rotate_left_up(route)
            rld = rotate_left_down(route)
            return[rru,rrd,rlu,rld]
        else:
            
        

    ## 0 : 왼쪽다리 row, 1 : 왼쪽다리 column
    ## 2 : 오른쪽다리 row, 3 : 오른쪽다리 column

    current_routes = [[0,0,0,1]]
    visited = []
    end_point = [len(board),len(board[0])]
    time = 0
    while(current_routes):
        for route in current_routes:
            if route[:2] == end_point or route[3:] == end_point:
                break
        direction_list = []
        togo = []
        for route in current_routes:
            move = check_move(route)
            rotate = check_rotate(route)
            direction_list.append(move + rotate)
        for direction in direction_list:
            if direction and direction not in visited and direction not in togo:
                togo.append(direction)
        current_routes = togo
        time += 1

            






def solution(board):
    answer = bfs(board)

    return answer


print(solution([[0, 0, 0, 1, 1],[0, 0, 0, 1, 0],[0, 1, 0, 1, 1],[1, 1, 0, 0, 1],[0, 0, 0, 0, 0]]))