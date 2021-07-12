print('------------------------------------------------------------------------------')
# -*- coding: utf-8 -*-
from datetime import datetime
from datetime import timedelta

import schedule
import time
import crawling
import isHoliday
import use_ecos
import fb
import kospidetail
import model
from calendar import monthrange

print('<-----All File import complete------------------------------->')
print(datetime.today().strftime('%Y-%m-%d'))
##########################################일별 데이터 ##########################################

def kospidaebiupdown_update(): ## 대비, 등락률
    return crawling.get_kospi_daebiupdown()

def kospidetail_update(): ## 시가, 고가, 저가, 종가, 거래량
    return kospidetail.get_kospi_detail()

def kospitrading_update(): ## 기관, 외국인, 개인, 연기금
    return kospidetail.get_trading_value()

def hsi_update(): # hsi지수
    return crawling.get_hsi_indice()

def shanghai_update(): # 상하이지수
    return crawling.get_shanghai_indice()

def nikkei_update(): # 닛케이지수
    return crawling.get_nikkei_indice()

def dji_update(): # 다우존스지수
    return crawling.get_dji_indice()

def nas_update(): # 나스닥지수
    return crawling.get_nas_indice()

def spi_update(): # s&p500 지수
    return crawling.get_spi_indice()

def oilprice_update(): # wti 가격
    return crawling.get_oil_price()

def exchange_update(): # 환율
    return use_ecos.get_exchange()

##########################################일별 데이터 ##########################################

#########################################fb 갱신용 함수###############################################


def ecosDBupdate():
    day = datetime.today()
    if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 data갱신 안함
        return

    else:
        ## 환율
        print(day.strftime('%Y-%m-%d'))
        print('<-----환율 업데이트중-------------------------------->')
        exchanges = exchange_update()

        day_str = day.strftime('%Y-%m-%d')

        day_factors = exchanges

        fb.fb_update_ecos(day_str, day_factors)
        print('<-----환율 업데이트 완료---------------------------->')



def crawlingDBupdate():
    day = datetime.today()
    if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 data갱신 안함 ##
        return

    else:
        
        ## 시가, 고가, 저가, 종가, 거래량 데이터
        print('<-----시고저종거래량 업데이트중------------------->')
        open,high,low,close,volume = kospidetail_update()

        ## 대비, 등락률 데이터
        print('<-----대비,등락률 업데이트중------------------------>')
        daebi,updown = kospidaebiupdown_update()

        ## 기관, 외국인, 개인, 연기금 데이터
        print('<-----순매수 업데이트중----------------------------->')
        association,foreign,person,pension = kospitrading_update()

        ## 각국 지수 업데이트
        ## hsi(홍콩), shanghai(상하이종합), nikkei(닛케이), dji(다우존스), nas(나스닥), spi(S&P 500)
        print('<-----각국 지수 업데이트중-------------------------->')
        
        hsi = hsi_update()
        shanghai = shanghai_update()
        nikkei = nikkei_update()
        dji = dji_update()
        nas = nas_update()
        spi = spi_update()

        ## wti 데이터
        print('<-----유가 업데이트중-------------------------------->')
        wti = oilprice_update()

        today_str = day.strftime('%Y-%m-%d')
        
        today_factors =[close, daebi, updown, open, high,
                        low, volume, association, foreign, person, 
                        pension, hsi, shanghai, nas, spi,
                        dji, nikkei, 'NaN', 'NaN', 'NaN',
                        wti]
        fb.fb_update_crawling(today_str, today_factors)
        print('<-----금일 update 완료------------------------------>')

def dailykospi_android_update():  ## 안드로이드 차트를 그리기 위한 지수갱신
    if(isHoliday.isholiday(datetime.today()) or isHoliday.isweekend(datetime.today())): ## 공휴일,주말이면 data갱신 안함
        return

    else:
        today = datetime.today().strftime('%Y-%m-%d')
        open, high, low, close, volume = kospidetail_update()
        fb.fb_update_daioykospi_android(today, str(close))

def runmodel():
    data = fb.make_data()
    print('<-----dataset 완료------------------------------>')
    predict_kospi = model.run(data)
    print('<-----modelrun 완료------------------------------>')
    
    day = datetime.today()
    while(isHoliday.isweekend(day) or isHoliday.isholiday(day)):
        day = day - timedelta(days=1)

    origin_kospi = kospidetail.get_original_kospi(day)

    fb.upload_predict_kospi(predict_kospi,day,origin_kospi)
    


#runmodel()

# 매일 10:30 에 실행


## 각국 지수 업데이트
schedule.every().day.at("07:00").do(ecosDBupdate) ## 금일 환율 데이터 갱신 // 한국시간 16시

schedule.every().day.at("11:00").do(dailykospi_android_update) ## 안드로이드 차트에 들어가는 금일 코스피 업데이트// 한국시간 20시

schedule.every().day.at("23:00").do(crawlingDBupdate) ## 금일 요인들 모두 갱신// 한국시간 오전 8시 -- 한국시간으로는 전날꺼가 업데이트

schedule.every().saturday.at("03:00").do(runmodel)
## 매일 오전 8시에는 그 전날 데이터 갱신 완료

#runmodel()



# 매주 월요일 실행
#schedule.every().monday.do(job)
# 매주 수요일 13:15 에 실행
#schedule.every().wednesday.at("13:15").do(job)


while True:
    schedule.run_pending()
    time.sleep(1)

