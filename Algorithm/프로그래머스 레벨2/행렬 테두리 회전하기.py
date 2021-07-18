## (x1, y1)의 원소를 저장해 두고 네 방향씩 각각 밀어줌. 한바퀴 돌고 돌아왔을 때 저장해둔 원소를 넣어줌.
def solution(rows, columns, queries):
    answer = []
    maps = []

    for i in range(0,rows):
        temp = []
        for j in range(0,columns):
            temp.append(columns*i + j+1)
        maps.append(temp)

    for q in queries:
        x1,y1,x2,y2 = q[0]-1, q[1]-1, q[2]-1, q[3]-1
        temp = maps[x1][y1]
        foranswerlist = []

        for i in range(x1, x2):
            foranswerlist.append(maps[i][y1])
            maps[i][y1] = maps[i+1][y1]
            
        for i in range(y1, y2):
            foranswerlist.append(maps[x2][i])
            maps[x2][i] = maps[x2][i+1]
            
        
        for i in range(x2, x1, -1):
            foranswerlist.append(maps[i][y2])
            maps[i][y2] = maps[i-1][y2]
            

        for i in range(y2, y1, -1):
            foranswerlist.append(maps[x1][i])
            maps[x1][i] = maps[x1][i-1]
            
        
        answer.append(min(foranswerlist))
        maps[x1][y1+1] = temp

        for m in maps:
            print(m)
        print('\n')

    return answer


print(solution(6,6,[[2,2,5,4],[3,3,6,6],[5,1,6,3]]))