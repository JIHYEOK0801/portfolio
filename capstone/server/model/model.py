
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


    #base_dir = 'C:\\Users\\wkdwl\\Desktop\\data\\'  #모델 저장 위치
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

    print("어제 코스피 값은 ", today_k)
    print("예측한 5일 후의 코스피 값은", pred)

    return ud_5days
