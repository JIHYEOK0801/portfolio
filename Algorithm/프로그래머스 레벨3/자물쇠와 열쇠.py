from copy import copy, deepcopy

def rotate_90(new_lock):
    copy_lock = deepcopy(new_lock)

    for i in range(0, len(copy_lock)):
        for j in range(0, len(copy_lock)):
            copy_lock[i][j] = new_lock[len(new_lock) -1 -j][i]
    
    return copy_lock
    

def check_open(key, new_lock):
    for i in range(len(key) - 1, len(new_lock) - len(key) + 1):
        for j in range(len(key) - 1, len(new_lock) - len(key) + 1):
            if new_lock[i][j] != 1:
                return 0
    return 1
    
def check_by_move(key, new_lock):
    is_open = 0

    for i in range(0, len(new_lock) - len(key) + 1):
        for j in range(0, len(new_lock) - len(key) + 1):
            copy_lock = deepcopy(new_lock)

            for keyi in range(0, len(key)):
                for keyj in range(0, len(key)):
                    copy_lock[i+keyi][j+keyj] += key[keyi][keyj]
            is_open = check_open(key, copy_lock)

            if is_open == 1:
                return 1
            

    return is_open


def solution(key, lock):

    answer = False

    key_len = len(key)
    lock_len = len(lock)
    new_lock = []

    ## new_lock map 만들기(패딩 추가)
    for i in range(key_len - 1):
        new_lock.append([0] * (key_len - 1) + [0] * lock_len + [0] * (key_len -1))  # 첫줄
    for line in lock:
        new_lock.append([0] * (key_len - 1) + line + [0] * (key_len - 1))
    for i in range(key_len - 1):
        new_lock.append([0] * (key_len - 1) + [0] * lock_len + [0] * (key_len -1))  # 마지막줄
    
    
    
    for i in range(4):
        is_open = check_by_move(key, new_lock)
        if is_open == 1:
            return True
        key = rotate_90(key)
    
    return answer

print(solution([[0, 0, 0], [1, 0, 0], [0, 1, 1]], [[1, 1, 1], [1, 1, 0], [1, 0, 1]]))
#print(solution([[0, 0, 1, 0], [0, 1, 0, 0], [1, 0, 1, 0], [0, 0, 0, 0]], [[1, 1, 1, 1, 1, 1], [1, 1, 1, 0, 1, 1], [1, 1, 0, 1, 1, 1],[1, 0, 1, 0, 1, 1], [1, 1, 1, 1, 1, 1], [1, 1, 1, 1, 1, 1]]))