## 1. number가 짝수이면 number+1 을 저장하면 됨 --> 끝자리가 0으로 끝나기 때문
## 2. number가 홀수이면 
    ## 2-1. number(2진수)의 뒤에서 부터 01 인 부분을 찾고
    ## 2-2. 둘이 교환해주면 된다.
    ## 2-3. 0이 없는 경우에는 ex) 11111(2)
    ## 2-4. number[0] = 0, number = '1' + number ex) 11111(2) --> 101111(2)

def solution(numbers):
    answer = []

    for number in numbers:
        if number % 2 == 0: answer.append(number + 1)
        else:
            bin_str = list(bin(number)[2:])
            
            for i in range(len(bin_str)-1, 0, -1):
                if bin_str[i] == '1':
                    if bin_str[i-1] == '0':
                        bin_str[i] ='0'
                        bin_str[i-1] ='1'
                        break
            else:
                bin_str[0] ='0'
                
                bin_str = ['1'] + bin_str
            
            
            answer.append((int('0b'+''.join(bin_str), 2)))
        
            


    return answer

print(solution([2,7]))