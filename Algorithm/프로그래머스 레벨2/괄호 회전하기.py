## 스택에 '{' '[' '('을 넣고 '}' ']' ')' 가 나올 때 스택 제일 위에 알맞은 괄호가 있는지 확인
def solution(s):
    if len(s) % 2 == 1:
            return 0
    
    new_s = s + s
    answer = 0
    
    for i in range(len(s)):
        
        strings = new_s[i:i+len(s)]
        stack = []
        
        for string in strings:
            if string == '{' or string == '[' or string == '(':
                stack.append(string)
            else:
                if not stack:
                    break
                
                if string == '}':
                    if stack[-1] == '{':
                        stack.pop()
                    else:
                        break
                if string == ')':
                    if stack[-1] == '(':
                        stack.pop()
                    else:
                        break
                if string == ']':
                    if stack[-1] == '[':
                        stack.pop()
                    else:
                        break
        else:
            answer += 1
        
            
    return answer


#print(solution("[](){}"))
print(solution("[)(]"))