# 증권 정보 제공 App

---

> ## 목차

- ### [Description](#description-1)

- ### [Function](#Function-1)

  1. [주가 지수 정보](#주가-지수-정보-제공)
  2. [국내/외 증권기사](#국내외-증권-기사)
  3. [대형주 실시간 주가정보](#대형주-실시간-주가정보)
  4. [관심주 관리 기능](#관심주-관리-기능)

- ### [Development](#Development-1)

  1. [Machine Learning](#machine-learningml)
  2. [Android Studio](#android-studioui)
  3. [Server / DB](#server--db)

</br>

---

> ## Description

- ### 개발 주제

  1. KOSPI 지수에 영향을 미치는 요인 조사 및 상관관계 분석

  2. 머신러닝을 이용한 KOSPI 지수 흐름 예측
  3. 예측한 KOSPI 값과 각종 주가 정보를 사용자에게 제공할 어플리케이션 개발

- ### 개발 기간

  2021.05 ~ 2021.06

- ### 개발 인원

  4명

- ### 사용 기술(개발 환경)

  Python, Firebase DB, AWS, Android Studio, TensorFlow, Keras

- ### 역할 

  1. 서버 환경 구축
  2. 데이터 수집 모듈 개발
  3. 모델 실행, 데이터 수집 모듈 실행의 자동화 개발
  4. DB관리

- ### 소스코드

  1. [Android Studio](./AndroidStudio/myapplication1)
  2. [Server/DB](./server/model)

  </br>

---

> ## Function

1. ###  주가 지수 정보 

   - APP의 메인 화면
   - 09.01.11 ~ 21.06.02 까지의 KOSPI 지수 차트 제공
   - 실시간 해외 주가 지수 제공(1분 마다 갱신)
   - 근무일 기준 5일 후의 KOSPI 등락 여부 정보 제공

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/1.gif?raw=true">

   </br>

2. ### 국내/외 증권 기사

   - 국내/외 증권 뉴스 기사 제공
   - 국내, 국외 버튼으로 종류 선택 및 새로고침 버튼으로 기사 갱신

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/2.gif?raw=true">

   </br>

3. ### 대형주 실시간 주가정보

   - 대형주(시가 총액 순위 100위)의 실시간 주가 정보 제공
   - 선택 버튼으로 사용자가 원하는 종목 관리

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/3.gif?raw=true">

   </br>

4. ### 관심주 관리 기능

   - 선택한 관심종목들을 따로 관리하는 기능
   - 관심종목들의 실시간 주가정보 확인 및 삭제 기능

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/4.gif?raw=true">

   </br>

---

> ## Development

## 시스템 구성도

<img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/system%20diagram.PNG?raw=true">

- ### 모바일 어플리케이션

  1. > **지수정보 제공 모듈**

     - KOSPI 차트 정보 및 등락 정보 제공

     - 해외 지수 실시간 정보 제공

       

  2. > **증권 기사 제공 모듈**

     - 국내/외 기사 정보 제공

       

  3. > **대형주 정보 제공 모듈**

     - 대형주(시가총액 100위) 실시간 주가 정보 제공

       

  4. > **관심 종목 관리 모듈**

     - 관심 종목 실시간 주가 정보 제공

       </br>

- ### Firebase DB

  1. > **모델 예측 결과값**

     - { 예측 목표 날짜 : value } 형태로 저장

     - value = 0 or 1 ( 0 : 하락 or 동일 , 1 : 상승)

       

  2. > **모델 입력 데이터**

     - 데이터 수집 모듈이 저장한 데이터 

       </br>

- ### AWS

  1. > **데이터 수집 모듈**

     - 모델 실행에 필요한 데이터 수집 (ex : 다우지수, 나스닥 지수, 상하이 지수, 유가, 환율 등의 시계열 데이터)

       

  2. > **머신러닝 모델 실행 모듈**

     - 저장된 모델 파일에 입력값을 넣어 예측 실행

</br>

---

## Server & DB

1. ### 서버 구축

   - >**서버의 용도**

     다음과 같은 기능을 위해 서버 구축

     1. 데이터 수집

     2. 머신러닝 모델 실행

     3. 1,2 번의 자동화

        </br>

   - > **서버 구축**

     AWS EC2, ubuntu 운영체제로 인스턴스 생성

     </br>

   - > **서버 구성 파일**

     <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/serverfile3.PNG?raw=true">

     1. 데이터 수집 모듈 파일 (.py)
     2. 모델 실행 파일 (.py)
     3. 모델 파일(.h5)
     4. 자동화 실행 파일(.py)
     5. DB 권한 인가 파일(.json)

     </br>

   ---

2. ### DB, 데이터 수집 모듈

   - > **DB 구축**

     Firebase Firestore Database 사용

     </br>

   - > **DB에 저장되는 데이터**

     <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/DB3.PNG?raw=true">

     1. **모델 입력 데이터(dailydata)** 

        : 모델 실행에 필요한 입력 값으로 사용 // {*key* = 날짜 : *value* = array(모델 입력 데이터)}

     2. **KOSPI 종가 데이터(dailykospi_android)**  

        : 사용자에게 보여줄 KOSPI 차트의 값으로 사용 // {*key* = 날짜 : *value* = string(해당 날짜의 종가)}

     3. **모델 예측 결과값(predictedkospi_test)** 

        : 모델이 실행되고 나온 결과 값 // {*key* = 예측 목표 날짜 : *value* = string(해당 날짜의 지수 등락 여부)}

     4. **예측 시행날의 비교 값(origin)** 

        : 모델 예측 결과 값을 사용자에게 제공하기 위한 비교값 // {*key* = 예측 시행 날짜 : *value* = string(해당 날짜의 종가)}

     </br>

   - > **데이터 수집**

     모델 실행에 쓰이는 데이터 수집 방법은 두 가지로 **'웹페이지 크롤링'** 과  **'API  활용'**

     - > **크롤링(crawling)**

       1. '[네이버 증권'](https://finance.naver.com/) 웹페이지 크롤링을 통해 각국의 주가지수, 유가 등의 정보 획득
       2. 크롤링에 필요한 **'BeautifulSoup'** 패키지 사용

       ```python
       # -*- coding: utf-8 -*-
       from bs4 import BeautifulSoup
       from datetime import datetime
       import requests
       import time
       
       def get_kospi_daebiupdown(): ##kospi 대비, 등락률
           url= 'https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI'
           result=requests.get(url)
           result.encoding = 'utf-8'
           
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.number_1")
           updownpercent = float(indice[1].text.replace('%','').strip()) ## 등락률
           
           indice = soup.select("td.rate_down")
           daebi = float(indice[0].text.strip()) ## 대비
           updown = soup.select("img")[0]['alt'] 
           if updown == '하락':
               daebi = daebi * -1
               
           return daebi, updownpercent
       
       def get_hsi_indice():##홍콩 hsi 지수
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=HSI@HSI'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
       
       def get_shanghai_indice(): ## 상하이 상해지수
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=SHS@000001'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
           
       
       def get_nikkei_indice():## 일본 니케이
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=NII@NI225'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
       
       
       def get_dji_indice(): ## 미국 다우존스
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=DJI@DJI'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
       
       def get_nas_indice():  ## 미국 나스닥
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=NAS@IXIC&fdtc=0'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
       
       def get_spi_indice(): ## 미국 s&p 500
       
           url = 'https://finance.naver.com/world/sise.nhn?symbol=SPI@SPX'
           result=requests.get(url)
           result.encoding = 'utf-8'
       
           soup=BeautifulSoup(result.content, "html.parser")
           indice = soup.select("td.tb_td2")
           return indice[0].text.replace(',','')
       
       
       def get_oil_price(): ##wti 유가가격
           
           wti_url = 'https://finance.naver.com/marketindex/worldDailyQuote.nhn?marketindexCd=OIL_CL&fdtc=2'
           
           
           result=requests.get(wti_url)
           result.encoding = 'utf-8'
           
           soup=BeautifulSoup(result.content, "html.parser")
           
           indice = soup.select("td.num")
           wti = indice[0].text.strip()
       
           return wti
       
       ```

     </br>

     - > **API**

       API를 사용하여 각각의 데이터 획득

       1. [한국은행 경제통계시스템 : ecos API](https://ecos.bok.or.kr/jsp/openapi/OpenApiController.jsp) - '환율' 

          ```python
          # 한국은행의 환율 정보를 가져오는데 사용
          # -*- coding: utf-8 -*-
          import requests 
          import xml.etree.ElementTree as ET 
          from datetime import datetime
          from datetime import timedelta
          import time
          import isHoliday
          
          key = 'API_KEY'
          
          ## API 호출
          def runAPI(url):
              response = requests.get(url)  ## http 요청이 성공했을때 API의 리턴값을 가져옵니다.
              
              if response.status_code == 200:
                  try:
                      contents = response.text
                      ecosRoot = ET.fromstring(contents)
                      
                      if ecosRoot[0].text[:4] in ("INFO","ERRO"):  ## 오류 확인
                          print(ecosRoot[0].text + " : " + ecosRoot[1].text)  ## 오류메세지를 확인하고 처리합니다.
                          
                      else:
                          return(ecosRoot[1][10].text)    ## 결과값 확인
          
                  except Exception as e:    ##예외 프린트
                      print(str(e))
          
          def get_exchange(): ## 환율
          
              d = datetime.today().strftime('%Y%m%d')
              statisticcode = '036Y001'
              ## exchange_items = 미국달러, 일본엔, 유럽유로
              exchange_items = ['0000001', '0000002', '0000003']  
              exchanges = []
              for itemcode in exchange_items:
                      url = "http://ecos.bok.or.kr/api/StatisticSearch/"+key+"/xml/kr/1/5/"+statisticcode+"/DD/"+d+"/"+d+"/" + itemcode
                      exchanges.append(runAPI(url))
          
              return exchanges
          ```

          </br>

       2. ['GitHub 오픈소스 모듈 : PyKrx'](https://github.com/sharebook-kr/pykrx) - '시가', '저가', '고가', '종가', '거래량', '각 기관들의 KOSPI 시장 순매수금'

          ```python
          # kospi지수의 시가, 고가, 저가 종가, 거래량과 각 투자처(개인,기관,외국인,연기금)별 순거래금액을 가져온다.
          
          # -*- coding: utf-8 -*-
          import time
          from pykrx import stock
          from datetime import datetime,timedelta
          
          ## documents : https://github.com/sharebook-kr/pykrx
          def get_kospi_detail():
              day = (datetime.today()).strftime("%Y%m%d")
              df = stock.get_index_ohlcv_by_date(day, day, "1001")
              open = df.iloc[0, 0] # 시가
              high = df.iloc[0, 1] # 고가
              low = df.iloc[0, 2] # 저가
              close = df.iloc[0, 3] # 종가
              volume = int(df.iloc[0, 4] * 0.001) # 거래량
              return open,high,low,close,volume
              
          def get_trading_value():
              day = (datetime.today()).strftime("%Y%m%d")
              df = stock.get_market_trading_value_by_date(day, day, "KOSPI")
              association = int(df.iloc[0,0] * 0.000001) # 기관 순매수금
              person = int(df.iloc[0,2]* 0.000001) # 개인 순매수금
              foreign = int(df.iloc[0,3]* 0.000001) # 외국인 순매수금
              
              df = stock.get_market_trading_value_by_date(day, day, "KOSPI",detail=True)
              pension = int(df.iloc[0,6]* 0.000001) # 연기금 순매수금
              
              return association,foreign,person,pension
          ```

          </br>

   ---

3. ### DB 갱신과 모델 실행

   - > **DB 갱신**

     1. **모델 입력 데이터(dailydata)** 
        갱신 시각 : 매일 정해진 시점 (각국의 증권시장 마감 이후 : 한국시간 기준 8AM)

        </br>

     2. **KOSPI 종가 데이터(dailykospi_android)**  

        : 매일 정해진 시점 (한국의 증권시장 마감 이후 : 한국시간 기준 8PM)

        </br>

     3. **모델 예측 결과값(predictedkospi_test)** 

        : 모델 예측 실행 시점 (매주 토요일 : 한국시간 기준 12PM)

        </br>

     4. **예측 시행날의 비교 값(origin)** 

        : 모델 예측 실행 시점 (매주 토요일 : 한국시간 기준 12PM)

        

     </br>

   - > **모델 실행**









**Server** : 머신러닝 모델 실행, KOSPI 지수 예측에 필요한 데이터 수집, DB 데이터 갱신

**DB** : KOSPI 지수 예측에 필요한 데이터, KOSPI 차트 표현에 필요한 데이터, 지수 등락 예측 데이터 저장

- **Server와 DB의 실행 과정**

  1. [데이터 수집](#데이터-수집)
  2. [DB 갱신](#db-데이터-갱신)
  3. [예측 모델 실행](#예측-모델-실행)
  4. [DB에 예측 결과 값 저장](#KOSPI-등락-예측-정보-저장)
  5. [스케줄러](#스케줄러)

  </br>

---

1. ####  데이터 수집

   - 1. 

   ---

2. #### DB 데이터 갱신

   DB에서 매일 갱신하는 데이터 2가지

   1. dailydata (인공지능 학습 데이터) 
   2. dailykospi_android (앱의 'KOSPI 차트' 데이터)

   </br>

   > ### 1. dailydata

   - 해외 지수를 크롤링하는 시차 때문에 시간을 두고 DB갱신을 1,2차로 설정

     *ex) 5/29 (목)의 데이터 수집*

     1차 : 환율 데이터 갱신(한국시간 기준 5/29 (목) 16시)

     2차 : 환율 이외의 값 갱신(한국시간 기준 5/30(금) 8시)

   </br>

   ```python
   # -*- coding: utf-8 -*-
   from firebase_admin import credentials
   from firebase_admin import firestore
   from copy import deepcopy
   from pandas import Series,DataFrame
   from datetime import datetime,timedelta
   import isHoliday
   import pandas as pd
   import firebase_admin
   import crawling
   
   ## firebase 권한 획득
   cred = credentials.Certificate("team1-1a267-firebase-adminsdk.json")
   firebase_admin.initialize_app(cred, {'databaseURL' : 'https://team1-1a267.firebaseio.com'})
   db = firestore.client()
   
   ## 1차 갱신
   def fb_update_ecos(day_str, day_factors):
       print('<-----firebase 1차 갱신중----------------------------->')
       doc_ref = db.collection(u'data').document(u'dailydata')
       ## 환율 값을 제외한 나머지는 NaN 값
       day_factors = ['NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN',
                       'NaN','NaN','NaN','NaN','NaN','NaN','NaN']+day_factors+['NaN']
       doc_ref.set({day_str : day_factors}, merge = True)
   
   ## 2차 갱신
   def fb_update_crawling(today_str, today_factors):
       doc_ref = db.collection(u'data').document(u'dailydata')
       doc = doc_ref.get()
       dic = deepcopy(doc.to_dict())
       today_list = dic[today_str]
       
       ## 새로운 데이터 리스트1 + 1차 갱신한 환율 값 리스트 + 새로운 데이터 리스트2
       today_list = today_factors[:17] + today_list[17:20] + [today_factors[20]]
       today_list = list(map(str, today_list))
       
       doc_ref.update({today_str : today_list})
   ```

   </br>

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/firebase_2.png?raw=true">

   </br>

   > ### 2. dailykospi_android

   - 앱의 'KOSPI 차트'에 들어가는 매일의 KOSPI 지수
   - dailydata 데이터 수집시 구했던 종가 값을 사용

   ```python
    ## KOSPI 차트에 필요한 데이터 갱신
   def fb_update_daioykospi_android(today, kospi): ## 오늘 날짜, KOSPI 종가
       doc_ref = db.collection(u'data').document(u'dailykospi_android')
       doc_ref.set({today : kospi}, merge = True) #DB에 금일 날짜와 KOSPI 지수 추가
   ```

   </br>

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/dailykospi.PNG?raw=true">

   </br>

   ---

3. #### 예측 모델 실행

   - 모델 파일(파일명 : 75_LSTM.h5)을 실행해서 예측값을 return 받는 모듈

   ```python
   from copy import deepcopy
   import pandas as pd
   from pandas import Series,DataFrame
   import numpy as np
   
   import tensorflow as tf
   from tensorflow import keras
   from sklearn.preprocessing import MinMaxScaler
   from sklearn.model_selection import train_test_split
   from tensorflow.keras.models import Sequential
   from tensorflow.keras.layers import Dense
   from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
   from tensorflow.keras.layers import LSTM
   from tensorflow.keras.layers import Dropout
   from tensorflow.keras.losses import Huber
   from tensorflow.keras.optimizers import Adam
   from tensorflow.keras import optimizers
   from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
   from sklearn.metrics import r2_score
   from sklearn.metrics import mean_squared_error
   
   from tensorflow.keras.models import load_model
   import os
   
   def run(model_data): # model_data = dataframe 형태
       
       ## model_data에 컬럼 추가
       model_data['EMA_close5'] = model_data['close'].ewm(5).mean() # 지수이동 평균(5일)
       model_data['EMA_close10'] = model_data['close'].ewm(10).mean() # 지수이동 평균(10일) 
       model_data['EMA_close20'] = model_data['close'].ewm(20).mean() # 지수이동 평균(20일)
       model_data['EMA_close60'] = model_data['close'].ewm(60).mean() # 지수이동 평균(60일)
       model_data['EMA_close120'] = model_data['close'].ewm(120).mean() # 지수이동 평균(120일)
       model_data['disp5'] = (model_data['close']/model_data['EMA_close5']) * 100 # 이격도
       model_data['after4days_close'] = model_data['close'].shift(-4) #close를 4칸 위로 올린 컬럼
   
       # 빈값을 위해 interpolate
       model_data = model_data.interpolate(method = 'values', limit_direction = 'both')
       
       # 정확한 지수이동 평균값과 이격도값은 121번째 row부터 들어있기에 데이터 슬라이스
       model_data = model_data[120:]
       today_k=model_data['close'].iloc[-1]
       
   	# 데이터 전처리 - MinMaxScaler 적용
       new_scaler = MinMaxScaler()
       new_scaler.fit(model_data['close'].values.reshape(-1,1))
   
       for i in model_data.columns:
           if i == 'date': continue ## date 컬럼을 제외한 나머지 전처리
           model_data[i] = MinMaxScaler().fit_transform(model_data[i].values.reshape(-1, 1)).round(4)
   
       feature_cols = ['volume','shanghai','dji', 'nikkei', 'hsi', 
                       'won/US dollar', 'won/100en', 'won/euro','association','person',
                       'daebi', 'EMA_close5', 'EMA_close10', 'EMA_close20', 'EMA_close60', 
                       'EMA_close120', 'disp5', 'updown','after4days_close']
       
       label_cols = ['close']
   
   	###### 데이터셋 준비 완료
       
       base_dir = '' #모델 저장 위치 (서버용)
       file_name = '75_LSTM.h5'  #모델 이름(파일명)
       dir = os.path.join(base_dir, file_name)
       model = load_model(dir)  #모델 load
   	
       ## 5일 후의 KOSPI 지수를 예측하는 모델을 5번 반복 --> 5일 후 예측
       def find_pred():
           df = model_data[-24:]        
           for i in range(5): # 1-4번째 예측 값으로 빈칸을 채우고, 5번째 예측값을 예측 값으로 이용
               df_tmp = df[i:i+20] #20개씩 잘라서 예측
               my_final_x_test = df_tmp[feature_cols]
               my_final_x_test = np.array(my_final_x_test)
               my_final_x_test = my_final_x_test.reshape(1, 20, 19)
               my_final_y_pred = model.predict(my_final_x_test)
               
               ## 5번 돌았을 때
               if i == 4: 
                   return new_scaler.inverse_transform(my_final_y_pred)[0][0]
               ## 다음 예측을 위해 예측값을 데이터 프레임에 삽입
               else: 
                   df['after4days_close'].iloc[i+20] = my_final_y_pred[0][0]
   
       # return : 예측한 5일 뒤 코스피 값
       pred = find_pred()
       
       # 예측 값(pred) - 예측 시행날 코스피지수(today_k) > 0 --> 1 (상승)
       # 예측 값(pred) - 예측 시행날 코스피지수(today_k) <= 0 --> 0 (유지 or 하락)
       
       ud_5days = 0
       if pred - today_k > 0:
           ud_5days = 1
       else:
           ud_5days = 0
   
       return ud_5days
   
   ```

   </br>

   ---

4. #### KOSPI 등락 예측 정보 저장

   모델이 예측한 등락 정보를 DB에 저장(1 : 전주 금요일보다 상승 // 0 : 전주 금요일보다 하락 or 동일)

   ```python
   def upload_predict_kospi(predict_kospi_index, origin_day, origin_kospi):
       day = datetime.today()
       predictday = 0
       
       #근무일 기준 5일후 날짜 계산
       while predictday < 5:
           day += timedelta(days=1)
           if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 no count
               continue
           predictday += 1
   
       day_str = day.strftime("%Y-%m-%d")
       doc_ref = db.collection(u'data').document(u'predictedkospi_test')
       doc_ref.set({day_str : predict_kospi_index}) ##{예측 목표 날짜 : 예측 결과} 저장
       doc_ref_origin = db.collection(u'data').document(u'origin')
       origin_day = origin_day.strftime("%Y-%m-%d") 
       doc_ref_origin.set({origin_day : origin_kospi}) ##{예측 시행 날짜 : 예측 시행 날 종가} 저장
   ```

   </br>

   DB에 등락예측 정보를 저장한 상태 (5/28(금)에 예측한 6/4(금)의 KOSPI 등락 : 0)

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/predictkospi.PNG?raw=true">

   </br>

   ---

5. #### 스케줄러

   - 위에서 설명한 모듈들을 정해진 시간에 수행하는 파이썬 스케줄러 모듈
   - AWS EC2 서버에서 백그라운드로 실행

   ```python
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
   
   #########################################fb 갱신 함수###############################################
   ## 환율 정보 가져오기
   def ecosDBupdate():
       day = datetime.today()
       if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 data갱신 X
           return
       else:
           ## 환율
           exchanges = exchange_update()
           day_str = day.strftime('%Y-%m-%d')
           day_factors = exchanges
           fb.fb_update_ecos(day_str, day_factors)
           
   ## 크롤링 정보 가져오기
   def crawlingDBupdate():
       day = datetime.today()
       if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 data갱신 X
           return
       else:
           ## 시가, 고가, 저가, 종가, 거래량 데이터
           open,high,low,close,volume = kospidetail_update()
   
           ## 대비, 등락률 데이터
           daebi,updown = kospidaebiupdown_update()
   
           ## 기관, 외국인, 개인, 연기금 데이터
           association,foreign,person,pension = kospitrading_update()
   
           ## 각국 지수 업데이트
           ## hsi(홍콩), shanghai(상하이종합), nikkei(닛케이), dji(다우존스), nas(나스닥), spi(S&P 500), wti(유가)
           hsi = hsi_update()
           shanghai = shanghai_update()
           nikkei = nikkei_update()
           dji = dji_update()
           nas = nas_update()
           spi = spi_update()
           wti = oilprice_update()
           
           today_str = day.strftime('%Y-%m-%d')
           today_factors =[close, daebi, updown, open, high,
                           low, volume, association, foreign, person, 
                           pension, hsi, shanghai, nas, spi,
                           dji, nikkei, 'NaN', 'NaN', 'NaN',
                           wti]
           fb.fb_update_crawling(today_str, today_factors)
   
   ## 안드로이드 차트 데이터 갱신
   def dailykospi_android_update():
       
       ## 공휴일,주말이면 실행 x
       if(isHoliday.isholiday(datetime.today()) or isHoliday.isweekend(datetime.today())): 
           return
       else:
           today = datetime.today().strftime('%Y-%m-%d')
           open, high, low, close, volume = kospidetail_update()
           fb.fb_update_daioykospi_android(today, str(close))
   
   ## model 실행 함수
   def runmodel():
       data = fb.make_data()
       predict_kospi = model.run(data)
   
       day = datetime.today()
       while(isHoliday.isweekend(day) or isHoliday.isholiday(day)):
           day = day - timedelta(days=1)
           
       origin_kospi = kospidetail.get_original_kospi(day)
       fb.upload_predict_kospi(predict_kospi,day,origin_kospi)
   
   ## 스케줄러 ##
   
    ## 금일 환율 데이터 갱신 // 한국시간 16시
   schedule.every().day.at("07:00").do(ecosDBupdate)
   
    ## 안드로이드 차트에 들어가는 금일 코스피 업데이트// 한국시간 20시
   schedule.every().day.at("11:00").do(dailykospi_android_update)
   
   ## 금일 요인들 모두 갱신// 한국시간 오전 8시 -- 한국시간으로는 전날꺼가 업데이트
   schedule.every().day.at("23:00").do(crawlingDBupdate) 
   
   ## 매주 토요일마다 모델 실행
   schedule.every().saturday.at("03:00").do(runmodel) 
   
   ## 스케줄러 실행
   while True:
       schedule.run_pending()
       time.sleep(1)
   ```

   
