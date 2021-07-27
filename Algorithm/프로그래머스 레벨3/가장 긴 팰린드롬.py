def is_palindrome(s):
    
    str1 = s[0 : len(s)//2]
    for i in range(len(str1)):
        if str1[i] != s[len(s) - i - 1]:
            return False
    else:
        return True


def solution(s):
    answer = 1
    length = len(s)

    while length != 1:
        i = 0
        while(i + length <= len(s)):
            temp_s = s[i : i + length]
            a = is_palindrome(temp_s)
            if a == True:
                return length
            i += 1

        length -= 1
    

    return answer

#print(solution("abcdcba"))
print(solution("a"))