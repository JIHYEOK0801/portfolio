# BFS 활용
# 한칸 전진시 1인 칸들을 큐에 저장, 저장하면 원래 위치는 0으로 만들고 삭제
from collections import deque

def solution(maps):
    answer = 0

    m = len(maps)
    n = len(maps[0])

    ## newmap 만들기
    newmaps = [[0] * (len(maps[0]) + 2)]
    for mapp in maps:
        temp = [0] + mapp + [0]
        newmaps.append(temp)
    newmaps.append([0] * (len(maps[0]) + 2))

    if newmaps[1][1] == 0 or newmaps[m][n] == 0:
        return -1
    endpoint = [m,n]
    positions = deque([[1,1]])
    updownleftright = [[-1,0],[1,0],[0,-1],[0,1]] ## 위, 아래, 왼쪽, 오른쪽
    
    while(True):
        if not positions: ## positions에 노드가 없을 때
            return -1
        if positions[0] == endpoint: ## 해당 노드가 끝 점일때
            answer = newmaps[positions[0][0]][positions[0][1]]
            return answer
        else:  
            newposi = [] 
            posi = positions.popleft()
            
            for node in updownleftright:
                if newmaps[posi[0]+node[0]][posi[1]+node[1]] == 1:
                    newmaps[posi[0]+node[0]][posi[1]+node[1]] += newmaps[posi[0]][posi[1]] # 새로운 노드 value += 현 위치 노드 value
                    newposi.append([posi[0]+node[0],posi[1]+node[1]])
            
            newmaps[posi[0]][posi[1]] = 0 # 원래 있던 위치는 0으로 만들어준다
            
            for n in newposi:
                positions.append(n)

            
            

#print(solution([[1,0,1,1,1],[1,0,1,0,1],[1,0,1,1,1],[1,1,1,0,1],[0,0,0,0,1]]))
#print(solution([[1,1,0],[0,1,1]]))