## 1. 문자를 쌓아가며 dic에 일치하는 key가 있으면 숫자 추가

def solution(s):
    answer = ''
    dic = {'zero' : '0', 'one' : '1', 'two' : '2', 'three' : '3', 'four' : '4', 'five': '5', 'six' : '6', 'seven' : '7', 'eight' : '8', 'nine' : '9'}
    
    temp = ''
    for string in s:
        if string >= '0' and string <= '9':
            answer += string
        
        else:
            temp += string
            if temp in dic:
                answer += dic[temp]
                temp = ''
            



    return int(answer)

print(solution("one4seveneight"))