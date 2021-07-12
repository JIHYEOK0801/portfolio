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
#cred = credentials.Certificate("C:\\Users\\wkdwl\\Desktop\\JH\\code\\.vscode\\team1-1a267-firebase-adminsdk-v9932-63d62a617d.json")
cred = credentials.Certificate("team1-1a267-firebase-adminsdk-v9932-63d62a617d.json")
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


def fb_update_daioykospi_android(today, kospi):
    print('<-----안드로이드차트용 kospi지수 갱신중----------->')

    doc_ref = db.collection(u'data').document(u'dailykospi_android')
    doc_ref.set({today : kospi}, merge = True)
    print('<-----안드로이드차트용 kospi지수 갱신완료--------->')

def make_data():
    db = firestore.client()
    data_doc_ref = db.collection(u'data').document(u'dailydata')
    data_doc = data_doc_ref.get()
    data_dic = deepcopy(data_doc.to_dict())
    data_keylist = sorted(list(data_dic.keys())) ## keylist = YYYY-mm-dd 날짜 string

    data = {
    'date' : data_keylist,
    'close' : [float(data_dic[key][0]) for key in data_keylist],
    'daebi' : [float(data_dic[key][1]) for key in data_keylist],
    'updown' : [float(data_dic[key][2]) for key in data_keylist],
    'open' : [float(data_dic[key][3]) for key in data_keylist],
    'high' : [float(data_dic[key][4]) for key in data_keylist],
    'low' : [float(data_dic[key][5]) for key in data_keylist],
    'volume' : [float(data_dic[key][6]) for key in data_keylist],
    'association' : [float(data_dic[key][7]) for key in data_keylist],
    'foreign' : [float(data_dic[key][8]) for key in data_keylist],
    'person' : [float(data_dic[key][9]) for key in data_keylist],
    'pension' : [float(data_dic[key][10]) for key in data_keylist],
    'hsi' : [float(data_dic[key][11]) for key in data_keylist],
    'shanghai' : [float(data_dic[key][12]) for key in data_keylist],
    'nasdaq' : [float(data_dic[key][13]) for key in data_keylist],
    'spy' : [float(data_dic[key][14]) for key in data_keylist],
    'dji' :	[float(data_dic[key][15]) for key in data_keylist],
    'nikkei' : [float(data_dic[key][16]) for key in data_keylist],
    'won/US dollar' : [float(data_dic[key][17]) for key in data_keylist],
    'won/100en' : [float(data_dic[key][18]) for key in data_keylist],
    'won/euro' : [float(data_dic[key][19]) for key in data_keylist],
    'WTI' : [float(data_dic[key][20]) for key in data_keylist]
    }

    data = DataFrame(data)

    data = data.interpolate(method='values', limit_direction='both')

    return data

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


