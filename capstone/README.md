# 증권 정보 제공 App

---

> ## 목차

- ### Description

- ### Function

  1. KOSPI 지수 및 실시간 세계 주요 증권 지수 정보 제공

     - KOSPI지수 차트와 해외 증권 주요 지수를 실시간으로 알 수 있는 App의 메인 화면
     - 차트 위에 5일 후의 KOSPI지수 등락을 표현
     - KOSPI지수 차트는 09.01.11 ~ 현재(21.06.02)까지의 기록과 해외 지수는 미국, 유럽, 일본, 중국, 홍콩의 주요 주가지수를 나타냄

     <이미지1>

     </br>

  2. 국내/외 증권정보 제공

     - 국내/외 증권 뉴스 기사를 크롤링 하였음
     - 국내/외 버튼과 새로고침 버튼으로 뉴스 기사 종류 선택 가능
     - 뉴스 기사를 클릭하면 원본 기사 링크 참조 가능

     <이미지2>

     </br>

  3. 대형주 실시간 주가정보 제공

     - 대형주 100개의 실시간 주가 정보 확인 가능
     - 선택 버튼으로 원하는 종목 따로 관리 가능

     <이미지3>

     </br>

  4. 관심주 관리 기능 제공

     - 선택했던 관심종목들을 따로 관리할 수 있는 기능
     - 삭제 버튼으로 관심종목에서 삭제 가능

     <이미지4>

     </br>

- ### Development

  1. Machine Learning
  2. Android Studio
  3. Server / DB

- ###  Review

</br>

---

> ## Description

- ### 개발 목적

  캡스톤 디자인 교과목에서 제작한 결과물로, 한 프로젝트의 설계, 구현을 포함하여 개발 과정을 경험해 보기 위해 진행한 프로젝트

- ### 개발 기간

  2021.05 ~ 2021.06

- ### 사용 기술(개발 환경)

  Python, Firebase DB, AWS, Android Studio, TensorFlow, Keras

</br>

---

> ## Function

1. ### KOSPI 지수 및 실시간 세계 주요 증권 지수 정보 제공

   

2. ### 국내/외 증권정보 제공

3. ### 대형주 실시간 주가정보 제공

4. ### 관심주 관리 기능 제공

</br>

---

> ## Development

1. ### Machine Learning

   

2. ### Android Studio

   

3. ### Server / DB

   - Server

     1. 모델 예측에 필요한 데이터 수집

        개발 초기 모델 예측에 필요한 데이터 컬럼이 확실히 결정되지 않았기 때문에 수집할 수 있는 데이터를 모두 수집하여 DB에 저장한 후 추후 모델이 필요한 데이터 컬럼을 선택하여 쓰기로 하였다.

        </br>

        데이터 수집 방법에는 두가지를 사용하였다.

        - 크롤링(crawling)

          각국의 주가지수와 유가 데이터등을 가져올 때 사용하였다. 값을 가져오는 사이트는 '[네이버 증권'](https://finance.naver.com/) 웹페이지를 사용하였다.

        ```python
        # <crawling.py>
        
        # -*- coding: utf-8 -*-
        from bs4 import BeautifulSoup
        from datetime import datetime
        import requests
        import time
        print('<-----crawling.py import complete------------------>')
        
        def get_kospi_daebiupdown(): ##kospi
        
            url= 'https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI'
            result=requests.get(url)
            result.encoding = 'utf-8'
            
            soup=BeautifulSoup(result.content, "html.parser")
            indice = soup.select("td.number_1")
            updownpercent = float(indice[1].text.replace('%','').strip())
        
            indice = soup.select("td.rate_down")
            daebi = float(indice[0].text.strip())
        
            updown = soup.select("img")[0]['alt']
            if updown == '하락':
                daebi = daebi * -1
        
            return daebi, updownpercent
        
        def get_hsi_indice():##홍콩 hsi
        
            url = 'https://finance.naver.com/world/sise.nhn?symbol=HSI@HSI'
            result=requests.get(url)
            result.encoding = 'utf-8'
        
            soup=BeautifulSoup(result.content, "html.parser")
            indice = soup.select("td.tb_td2")
            return indice[0].text.replace(',','')
        
        def get_shanghai_indice(): ## 상하이 shanghai
        
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
        
        
        def get_oil_price(): ##wti
            
            wti_url = 'https://finance.naver.com/marketindex/worldDailyQuote.nhn?marketindexCd=OIL_CL&fdtc=2'
            
            
            result=requests.get(wti_url)
            result.encoding = 'utf-8'
            
            soup=BeautifulSoup(result.content, "html.parser")
            
            indice = soup.select("td.num")
            wti = indice[0].text.strip()
        
            return wti
        
        ```

        </br>

        - API활용

          크롤링을 적용할 수 없는 사이트의 경우 해당 정보를 제공하는 api를 사용하였다.

        ```python
        # <use_ecos.py>
        # 한국은행의 환율 정보를 가져오는데 사용하였다.
        
        # -*- coding: utf-8 -*-
        import requests 
        import xml.etree.ElementTree as ET 
        from datetime import datetime
        from datetime import timedelta
        import time
        import isHoliday
        print('<-----use_ecos.py import complete----------------->')
        key = 'RHXCFZZB7E5KGJLPIDOT'
        
        ## 정의된 OpenAPI URL을 호출합니다.
        def runAPI(url):
            response = requests.get(url)  ## http 요청이 성공했을때 API의 리턴값을 가져옵니다.
            
            if response.status_code == 200:
                try:
                    contents = response.text
                    ecosRoot = ET.fromstring(contents)
                    
                    if ecosRoot[0].text[:4] in ("INFO","ERRO"):  ## 호출결과에 오류가 있었는지 확인합니다.
                        print(ecosRoot[0].text + " : " + ecosRoot[1].text)  ## 오류메세지를 확인하고 처리합니다.
                        
                    else:
                        return(ecosRoot[1][10].text)    ## 결과값을 활용하여 필요한 프로그램을 작성합니다.
        
                except Exception as e:    ##예외가 발생했을때 처리합니다.
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

        ```python
        # <kospidetail.py>
        # kospi지수의 시가, 고가, 저가 종가, 거래량과 각 투자처(개인,기관,외국인,연기금)별 순거래금액을 가져온다.
        
        # -*- coding: utf-8 -*-
        import time
        from pykrx import stock
        from datetime import datetime,timedelta
        print('<-----kospidetail.py import complete--------------->')
        ## documents : https://github.com/sharebook-kr/pykrx
        def get_kospi_detail():
            day = (datetime.today()).strftime("%Y%m%d")#####################
            df = stock.get_index_ohlcv_by_date(day, day, "1001")
            open = df.iloc[0, 0]
            high = df.iloc[0, 1]
            low = df.iloc[0, 2]
            close = df.iloc[0, 3]
            volume = int(df.iloc[0, 4] * 0.001)
            return open,high,low,close,volume
            
        def get_trading_value():
            day = (datetime.today()).strftime("%Y%m%d")###########################
            df = stock.get_market_trading_value_by_date(day, day, "KOSPI")
            association = int(df.iloc[0,0] * 0.000001)
            person = int(df.iloc[0,2]* 0.000001)
            foreign = int(df.iloc[0,3]* 0.000001)
            
            df = stock.get_market_trading_value_by_date(day, day, "KOSPI",detail=True)
            pension = int(df.iloc[0,6]* 0.000001)
            
            return association,foreign,person,pension
        
        def get_original_kospi(day):
            day = day.strftime("%Y%m%d")
            df = stock.get_index_ohlcv_by_date(day, day, "1001")
            close = df.iloc[0,3]
            return close
        ```

        </br>

     2. 수집한 데이터를 DB에 저장

        1.에서 수집한 데이터들은 날마다 새로 갱신이 되는 데이터이므로 매일 DB를 갱신하는 코드를 작성하였다. 데이터를 가져오는 시각에 맞춰 1차, 2차 DB갱신을 나누었다.

        ```python
        #<fb.py>
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
        
        print('<-----fb_dailyupdate.py import complete----------->')
        cred = credentials.Certificate("team1-1a267-firebase-adminsdk.json")
        firebase_admin.initialize_app(cred, {'databaseURL' : 'https://team1-1a267.firebaseio.com'})
        
        db = firestore.client()
        
        print('<-----firebase 권한획득완료-------------------------->')
        
        def fb_update_ecos(day_str, day_factors):
            print('<-----firebase 1차 갱신중----------------------------->')
            doc_ref = db.collection(u'data').document(u'dailydata')
        
            day_factors = ['NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN',
                            'NaN','NaN','NaN','NaN','NaN','NaN','NaN']+day_factors+['NaN']
            
            doc_ref.set({day_str : day_factors}, merge = True)
            
            
            print('<-----firebase 1차 갱신완료-------------------------->')
            
        
        
        def fb_update_crawling(today_str, today_factors):
            print('<-----firebase 2차 갱신중----------------------------->')
            
            doc_ref = db.collection(u'data').document(u'dailydata')
            
            doc = doc_ref.get()
        
            dic = deepcopy(doc.to_dict())
        
            today_list = dic[today_str]
            
            today_list = today_factors[:17] + today_list[17:20] + [today_factors[20]]
            today_list = list(map(str, today_list))
            
        
            doc_ref.update({today_str : today_list})
            print('<-----firebase 2차 갱신완료--------------------------->')
        ```

        </br>

     3. DB에서 필요한 데이터를 가져와서 모델 실행

        DB에 저장된 데이터를 가져와 모델을 실행한다. 자세한 설명은 주석으로 남겨놓았다.

        ```python
        # <model.py>
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
        
        print('<-----model.py import complete----------->')
        
        def run(model_data):
            
            model_data['EMA_close5'] = model_data['close'].ewm(5).mean()
            model_data['EMA_close10'] = model_data['close'].ewm(10).mean()  
            model_data['EMA_close20'] = model_data['close'].ewm(20).mean()
            model_data['EMA_close60'] = model_data['close'].ewm(60).mean()
            model_data['EMA_close120'] = model_data['close'].ewm(120).mean()
            model_data['disp5'] = (model_data['close']/model_data['EMA_close5']) * 100
            model_data['after4days_close'] = model_data['close'].shift(-4)
            model_data = model_data.interpolate(method = 'values', limit_direction = 'both')
            model_data = model_data[120:]
            today_k=model_data['close'].iloc[-1]
        
            new_scaler = MinMaxScaler()
            new_scaler.fit(model_data['close'].values.reshape(-1,1))
        
            for i in model_data.columns:
                if i == 'date': continue
                model_data[i] = MinMaxScaler().fit_transform(model_data[i].values.reshape(-1, 1)).round(4)
        
            feature_cols = ['volume','shanghai','dji', 'nikkei', 'hsi', 'won/US dollar', 'won/100en', 'won/euro','association','person','daebi',
               'EMA_close5', 'EMA_close10', 'EMA_close20', 'EMA_close60', 'EMA_close120', 'disp5', 'updown','after4days_close']
            label_cols = ['close']
        
        	###### 데이터셋 준비 완료
            
            base_dir = '' #모델 저장 위치 서버용
            file_name = '75_LSTM.h5'  #모델 이름(파일명)
            dir = os.path.join(base_dir, file_name)
            model = load_model(dir)  #저장했던 모델 (dir에 있는거) 불러오는 코드
        
            print('model 불러오기 완료')
        
            def find_pred():
                
                df = model_data[-24:] # 예측값 하나만 뽑기 위해 원래 -24:
                
                for i in range(5): # 1-4번째 예측 값으로 빈칸을 채우고, 5번째 예측값을 예측 값으로 이용
                    df_tmp = df[i:i+20] #20개씩 잘라서 예측
                    my_final_x_test = df_tmp[feature_cols]
                    my_final_x_test = np.array(my_final_x_test)
                    my_final_x_test = my_final_x_test.reshape(1, 20, 19) # 예측하기 위한 정리
        
                    my_final_y_pred = model.predict(my_final_x_test) # 예측하는 코드
        
                    if i == 4: #5번째 예측을 마쳤으므로 실제 오늘의 kospi값과 지수들을 통해 예측한 오늘로부터 5일 뒤 값을 return
                        return new_scaler.inverse_transform(my_final_y_pred)[0][0]
                    else: # 예측한 값을 df에 채워서 다음 예측을 위해 사용하므로 예측 값을 저장한다.
                        df['after4days_close'].iloc[i+20] = my_final_y_pred[0][0]
        
            # return : 오늘의 코스피 값, 오늘의 지수들로 예측한 5일 뒤 코스피 값
            pred = find_pred()
            print('함수실행')
            # 예측 값 - 오늘의 코스피지수 >0 이면 상승이므로 1, 유지 또는 하락의 경우 0
            ud_5days = 0
            if pred - today_k > 0:
                ud_5days = 1
            else:
                ud_5days = 0
        
            return ud_5days
        
        ```

        

     4. KOSPI 등락 예측 정보 DB에 저장

        모델이 예측한 등락 정보를 DB에 저장하면 완료된다.

        ```python
        # <fb.py>
        def upload_predict_kospi(predict_kospi_index, origin_day, origin_kospi):
            day = datetime.today()
            predictday = 0
            
            while predictday < 5:
                day += timedelta(days=1)
                if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 no count
                    continue
                predictday += 1
        
            day_str = day.strftime("%Y-%m-%d")
        
            doc_ref = db.collection(u'data').document(u'predictedkospi_test')
            
            doc_ref.set({day_str : predict_kospi_index})
        
            doc_ref_origin = db.collection(u'data').document(u'origin')
            
            origin_day = origin_day.strftime("%Y-%m-%d")
        
            doc_ref_origin.set({origin_day : origin_kospi})
        ```

        

   - DB

     1. 모델 예측에 필요한 데이터 저장

        Server에서 수집한 데이터를 DB에 저장하는데 필요한 과정. 크롤링하는 시간과 API를 사용하는 시간이 다르므로 특정 날짜에 API로 가져온 데이터가 저장되면 이것을 몇 시간 후 크롤링하여 가져온 데이터와 합쳐서 다시 저장하는 방식

        - 크롤링한 데이터

          ```python
          def fb_update_crawling(today_str, today_factors):
              print('<-----firebase 2차 갱신중----------------------------->')
              
              doc_ref = db.collection(u'data').document(u'dailydata')
              
              doc = doc_ref.get()
          
              dic = deepcopy(doc.to_dict())
          
              today_list = dic[today_str]
              
              today_list = today_factors[:17] + today_list[17:20] + [today_factors[20]]
              today_list = list(map(str, today_list))
              
          
              doc_ref.update({today_str : today_list})
              print('<-----firebase 2차 갱신완료--------------------------->')
          ```

          

        - API로 가져온 데이터

          ```python
          def fb_update_ecos(day_str, day_factors):
              print('<-----firebase 1차 갱신중----------------------------->')
              doc_ref = db.collection(u'data').document(u'dailydata')
          
              day_factors = ['NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN',
                              'NaN','NaN','NaN','NaN','NaN','NaN','NaN']+day_factors+['NaN']
              
              doc_ref.set({day_str : day_factors}, merge = True)
              
              
              print('<-----firebase 1차 갱신완료-------------------------->')
          ```

          

        

     2. Application의 코스피 차트에 필요한 과거 데이터 저장

        Application에서 사용자에게 보여질 코스피 차트를 표현하는 것에 필요한 데이터를 저장하는 과정

        ```python
        def fb_update_daioykospi_android(today, kospi):
            print('<-----안드로이드차트용 kospi지수 갱신중----------->')
        
            doc_ref = db.collection(u'data').document(u'dailykospi_android')
            doc_ref.set({today : kospi}, merge = True)
            print('<-----안드로이드차트용 kospi지수 갱신완료--------->')
        ```

        

     3. 모델 예측 결과 데이터 저장

        모델 예측 결과인 KOSPI지수 등락 여부를 저장하는 과정

        ```python
        def upload_predict_kospi(predict_kospi_index, origin_day, origin_kospi):
            day = datetime.today()
            predictday = 0
            
            while predictday < 5:
                day += timedelta(days=1)
                if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 no count
                    continue
                predictday += 1
        
            day_str = day.strftime("%Y-%m-%d")
        
            doc_ref = db.collection(u'data').document(u'predictedkospi_test')
            
            doc_ref.set({day_str : predict_kospi_index})
        
            doc_ref_origin = db.collection(u'data').document(u'origin')
            
            origin_day = origin_day.strftime("%Y-%m-%d")
        
            doc_ref_origin.set({origin_day : origin_kospi})
        ```

</br>

---

> ## Review

