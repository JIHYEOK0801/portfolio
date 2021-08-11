"""
:: 처음에 내가 생각했던 방법

1. logs를 오름차순으로 배열한다.
2. 각 log의 시작시간 + 공익광고 길이 안의 총 playtime을 구한다.
3. 제일 높은 playtime인 구간을 구한다.

문제점

1. logs를 초로 환산하고 오름차순으로 배열하는데 걸리는 시간
2. 각 log의 시작시간을 기준으로 계산한다고 해서 제일 높은 playtime이 도출 되는지


:: 풀이법
누적합을 이용한 방식이다

1. logs를 순회하며 log 시작시간에는 +1, 마감시간에는 -1을 해준다. (들어오고 나가고를 체크)
2. i = 1 ~ i = ( len(time_map) - 1 ) 까지 time_map[i] += time_map[i-1] --> 그 시각의 재생 숫자가 생성됨
3. 2.를 다시 반복 --> 누적 재생 숫자가 생성됨


"""


def to_sec(time):
    h = time[:2]
    m = time[3:5]
    s = time[6:]

    sec = int(s) + int(m) * 60 + int(h) * 3600

    return sec


def sec_to_str(sec):
    h = sec // 3600
    h = "{0:02d}".format(h)
    sec %= 3600

    m = sec // 60
    m = "{0:02d}".format(m)
    sec %= 60

    s = sec
    s = "{0:02d}".format(s)

    return h + ":" + m + ":" + s


def solution(play_time, adv_time, logs):
    answer = ""
    play_time = to_sec(play_time)
    adv_time = to_sec(adv_time)
    time_map = [0] * (play_time + 1)

    for log in logs:
        log_start = to_sec(log[:8])
        log_end = to_sec(log[9:])
        time_map[log_start] += 1
        time_map[log_end] -= 1

    for i in range(1, len(time_map)):
        time_map[i] += time_map[i - 1]

    for i in range(1, len(time_map)):
        time_map[i] += time_map[i - 1]

    max_time = 0
    index = 0
    for i in range(adv_time - 1, len(time_map)):
        if i >= adv_time:
            if time_map[i] - time_map[i - adv_time] > max_time:
                max_time = time_map[i] - time_map[i - adv_time]
                index = i - adv_time + 1
        else:
            if time_map[i] > max_time:
                max_time = time_map[i]
                index = 0

    answer = sec_to_str(index)
    return answer


print(
    solution(
        "02:03:55",
        "00:14:15",
        [
            "01:20:15-01:45:14",
            "00:40:31-01:00:00",
            "00:25:50-00:48:29",
            "01:30:59-01:53:29",
            "01:37:44-02:02:30",
        ],
    )
)
