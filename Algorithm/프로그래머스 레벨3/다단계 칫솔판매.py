def solution(enroll, referral, seller, amount):
    answer = []
    
    dic = dict()
    for i in range(len(enroll)):
        dic[enroll[i]] = [referral[i],0,0,[]]
    
    for i in range(len(seller)):
        dic[seller[i]][1] += amount[i]*100
        dic[seller[i]][3].append(amount[i]*100)

    enroll_reversed = reversed(enroll)
    
    for name in enroll_reversed:
        ref = dic[name][0]
        mysell = dic[name][1]
        childsell = dic[name][2]
        mysell_list = dic[name][3]
        sell_percent_10 = 0
        for i in range(len(mysell_list)):
            sell_percent_10 += (mysell_list[i] // 10)

        mysell -= sell_percent_10
        dic[name][1] = mysell + childsell
        if ref == "-":
            continue
        dic[ref][2] += mysell//10 + childsell//10
    print(dic)
    
    return answer

print(solution(["john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"],["-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"],["young", "john", "tod", "emily", "mary"],[12, 4, 2, 5, 10]))
#print(solution(["john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"],["-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"],["sam", "emily", "jaimie", "edward"],[2, 3, 5, 4]))