
from collections import deque

def solution(places):
    answer = []

    for place in places: ## places 순회
        
        p_position = [] # p의 위치를 모은 리스트
        
        new_place = ['X' * (len(place[0]) + 2)] # place마다 새로운 map 만들기, 패딩처리

        for line in place:
            new_place.append('X' + line + 'X')
        new_place.append('X' * (len(place[0]) + 2))

        ## p 위치 리스트 만들기
        for i in range(len(new_place)):
            for j in range(len(new_place[i])):
                if new_place[i][j] == 'P':
                    p_position.append((i,j))


        ## p < 2 이면 거리두기 가능
        if len(p_position) < 2:
            answer.append(1)
            continue

        ## p > 13 이면 거리두기 불가능
        elif len(p_position) > 13:
            answer.append(0)
            continue
        
        ## 2 <= p <= 13 일때

        else:
            ## 각각의 p가 서로 거리두기를 지키고 있는지 확인하기 위한 for문
            ## bfs 사용
            direction = [(-1, 0), (0, -1), (1, 0), (0, 1)] # 위, 왼쪽, 아래, 오른쪽
            check_p = False

            for p in p_position:
                # 각 p마다 p_neighbor라는 리스트로 bfs

                p_neighbor = deque([p])
                distance = 0
                visited = [] # 방문한 노드
                
                while(distance < 3): # 각 p 마다 거리가 3 미만인 곳 까지만 체크
                    temp = deque() # 인근노드 저장을 위한 큐

                    while(p_neighbor): # 더 나아갈 노드가 있는동안
                        
                        posi_now = p_neighbor.popleft()
                        x,y = posi_now[0], posi_now[1]
                        visited.append((x,y))

                        if distance != 0: # distance > 0 일때
                            if new_place[x][y] == 'P': # 0 < distance < 3 에 p가 있다는 의미이므로 break
                                check_p = True
                                break
                        
                        for d in direction: # 위, 왼쪽, 아래, 오른쪽 으로 나아간다.
                            d1, d2 = d[0], d[1]
                            if (x + d1, y + d2) not in visited:
                                if new_place[x + d1][y + d2] != 'X':
                                    temp.append((x + d1, y + d2))
                            
                    ## 모았던 temp들을 p_neighbor에 추가
                    ## 1. p_neighbor는 하나도 없을 때 까지 leftpop을 했었으므로 p_neighbor = temp가 된다.
                    ## 2. temp 가 [] 이면 인근 노드 모두 'X' 였다는 의미
                    
                    p_neighbor = p_neighbor + temp
                    
                    distance += 1
                
                if check_p == True:
                    answer.append(0)
                    break
            else:
                answer.append(1)
            
                

    return answer

##print(solution([["POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"], ["POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"], ["PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"], ["OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"], ["PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"]]))
print(solution([["OOOOO", "POOOO", "OOOOO", "POOOO", "OOOOO"]]))