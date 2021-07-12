증권 정보 제공 App
목차
Description
Function
Development
Machine Learning

Android Studio

Server / DB

</br>

Description
개발 목적
국내 주식시장에 영향을 미치는 요인들을 분석, 파악 후 결과를 이용하여 예측한 시장 흐름 정보를 사용자에게 제공하기 위함

</br>

개발 기간
2021.05 ~ 2021.06

</br>

사용 기술(개발 환경)
Python, Firebase DB, AWS, Android Studio, TensorFlow, Keras

</br>

Function
KOSPI 지수 및 실시간 세계 주요 증권 지수 정보 제공
KOSPI지수 차트와 해외 증권 주요 지수를 실시간으로 알 수 있는 App의 메인 화면

차트 위에 근무일 기준 5일 후의 KOSPI지수 등락을 표현

KOSPI지수 차트는 09.01.11 ~ 현재(21.06.02)까지의 기록과 해외 지수는 미국, 유럽, 일본, 중국, 홍콩의 주요 주가지수를 나타냄



</br>

국내/외 증권정보 제공
국내/외 증권 뉴스 기사를 크롤링 하여 사용자에게 정보 제공

국내/외 버튼과 새로고침 버튼으로 뉴스 기사 종류 선택

뉴스 기사를 클릭시 원본 기사 링크 참조 가능



</br>

대형주 실시간 주가정보 제공
대형주 100개의 실시간 주가 정보 확인

선택 버튼으로 사용자가 원하는 종목을 따로 관리 



</br>

관심주 관리 기능 제공
선택한 관심종목들을 따로 관리할 수 있는 기능

관심종목들의 실시간 주가정보 확인 및 삭제 버튼으로 관심종목에서 삭제 가능



</br>

Development
Machine Learning
KOSPI 지수 예측에 사용한 일별 데이터(총 27개의 column)

close(종가)

daebi(대비)

updown(등락률)

open(시가)

high(고가)

low(저가)

volume(거래량)

association(기관 순매수금)

foreign(외국인 순매수금)

person(개인 순매수금)

pension(연기금 순매수금)

hsi(홍콩 hsi지수)

shanghai(중국 상해지수)

nasdaq(미국 나스닥지수)

spy(미국 spi500 지수)

dji(미국 다우지수)

nikkei(일본 니케이지수)

won/USdollar(달러환율)

won/100en(엔환율)

won/euro(유로환율)

WTI(유가)

ema(지수이동평균 : 5,10,20,60,120일)

disp(이격도 : 주가와 지수이동평균선의 괴리를 수치화하여 나타낸 것)

</br>

예측 과정

예측 시행 요일 및 예측 목표 요일

예측 시행 요일 : 매주 주식시장 마감날의 다음날

예측 목표 요일 : 근무일 기준 5일 후(일반적으로 그 다음 주 금요일)로, 매주 한 번씩 실행

	ex) 예측 시행 요일 : 토요일 	- - >	예측 목표 날 : 그 다음주 금요일

</br>

예측 단계 및 정보 제공

예측 시행날 기준 1일 후(월요일)의 KOSPI 예측 지수 저장

1 에서 얻은 예측 지수를 기존의 데이터에 합하여 2일 후(화요일)값 예측 가능

1, 2 의 과정을 5번 반복하여 5일 후(금요일)의 지수 예측

이번주의 마지막 지수(이번주 금요일) 5일후의 지수(다음주 금요일)와 비교하여 0 or 1로 등락 표현

</br>

서버 내 모델 실행코드

1
​
2
from copy import deepcopy
3
​
4
import pandas as pd
5
from pandas import Series,DataFrame
6
import numpy as np
7
​
8
import tensorflow as tf
9
from tensorflow import keras
10
from sklearn.preprocessing import MinMaxScaler
11
from sklearn.model_selection import train_test_split
12
​
13
from tensorflow.keras.models import Sequential
14
from tensorflow.keras.layers import Dense
15
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
16
from tensorflow.keras.layers import LSTM
17
from tensorflow.keras.layers import Dropout
18
​
19
from tensorflow.keras.losses import Huber
20
from tensorflow.keras.optimizers import Adam
21
from tensorflow.keras import optimizers
22
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
23
​
24
from sklearn.metrics import r2_score
25
from sklearn.metrics import mean_squared_error
26
​
27
from tensorflow.keras.models import load_model
28
import os
29
​
30
print('<-----model.py import complete----------->')
31
​
32
def run(model_data):
33
    
34
    model_data['EMA_close5'] = model_data['close'].ewm(5).mean()
35
    model_data['EMA_close10'] = model_data['close'].ewm(10).mean()  
36
    model_data['EMA_close20'] = model_data['close'].ewm(20).mean()
37
    model_data['EMA_close60'] = model_data['close'].ewm(60).mean()
38
    model_data['EMA_close120'] = model_data['close'].ewm(120).mean()
39
    model_data['disp5'] = (model_data['close']/model_data['EMA_close5']) * 100
40
    model_data['after4days_close'] = model_data['close'].shift(-4)
41
    model_data = model_data.interpolate(method = 'values', limit_direction = 'both')
42
    model_data = model_data[120:]
43
    today_k=model_data['close'].iloc[-1]
44
​
45
    new_scaler = MinMaxScaler()
46
    new_scaler.fit(model_data['close'].values.reshape(-1,1))
47
​
48
    for i in model_data.columns:
49
        if i == 'date': continue
50
        model_data[i] = MinMaxScaler().fit_transform(model_data[i].values.reshape(-1, 1)).round(4)
51
​
52
    feature_cols = ['volume','shanghai','dji', 'nikkei', 'hsi', 'won/US dollar', 'won/100en', 'won/euro','association','person','daebi',
53
       'EMA_close5', 'EMA_close10', 'EMA_close20', 'EMA_close60', 'EMA_close120', 'disp5', 'updown','after4days_close']
54
    label_cols = ['close']
55
​
56
​
57
    base_dir = '' #모델 저장 위치 서버용
58
    file_name = '75_LSTM.h5'  #모델 이름(파일명)
59
    dir = os.path.join(base_dir, file_name)
60
    model = load_model(dir)  #저장했던 모델 (dir에 있는거) 불러오는 코드
61
​
62
    print('model 불러오기 완료')
63
​
64
    def find_pred():
65
        
66
        df = model_data[-24:] # 예측값 하나만 뽑기 위해 원래 -24:
67
        
68
        for i in range(5): # 1-4번째 예측 값으로 빈칸을 채우고, 5번째 예측값을 예측 값으로 이용
69
            df_tmp = df[i:i+20] #20개씩 잘라서 예측
70
            my_final_x_test = df_tmp[feature_cols]
71
            my_final_x_test = np.array(my_final_x_test)
72
            my_final_x_test = my_final_x_test.reshape(1, 20, 19) # 예측하기 위한 정리
73
​
74
            my_final_y_pred = model.predict(my_final_x_test) # 예측하는 코드
75
​
76
            if i == 4: #5번째 예측을 마쳤으므로 실제 오늘의 kospi값과 지수들을 통해 예측한 오늘로부터 5일 뒤 값을 return
77
                return new_scaler.inverse_transform(my_final_y_pred)[0][0]
78
            else: # 예측한 값을 df에 채워서 다음 예측을 위해 사용하므로 예측 값을 저장한다.
79
                df['after4days_close'].iloc[i+20] = my_final_y_pred[0][0]
80
​
81
    # return : 오늘의 코스피 값, 오늘의 지수들로 예측한 5일 뒤 코스피 값
82
    pred = find_pred()
83
    print('함수실행')
84
    # 예측 값 - 오늘의 코스피지수 >0 이면 상승이므로 1, 유지 또는 하락의 경우 0
85
    ud_5days = 0
86
    if pred - today_k > 0:
87
        ud_5days = 1
88
    else:
89
        ud_5days = 0
90
    return ud_5days
91
​
</br>

Android Studio
Main 탭

차트

Main 탭의 차트 구성 부분

Firestore DB에서 가져온 09년 부터의 데이터를 차트로 표현

차트 위에 5일 후의 KOSPI지수 등락을 색이 있는 정삼각형, 역삼각형으로 표현

1
private void showChart() {
2
        FirebaseFirestore db = FirebaseFirestore.getInstance();
3
        DocumentReference docRef = db.collection("data").document("dailykospi_android");
4
​
5
        ArrayList<String> real_date = new ArrayList<String>();
6
        ArrayList<Float> real_kospi_index = new ArrayList<Float>();
7
​
8
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
9
            @Override
10
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
11
                if (task.isSuccessful() && getActivity()!=null) {
12
                    DocumentSnapshot document = task.getResult();
13
                    real_date.clear();
14
                    real_kospi_index.clear();
15
​
16
                    if (document.exists()) {
17
                        HashMap<String,Object> kospi = new HashMap<String,Object>(document.getData());
18
​
19
                        Object[] mapkey = kospi.keySet().toArray();
20
                        Arrays.sort(mapkey);
21
​
22
                        int count = 0;
23
​
24
                        for(Object nkey : mapkey) {
25
                            real_date.add(count, nkey.toString());
26
                            real_kospi_index.add(count, Float.valueOf(kospi.get(nkey).toString()));
27
                            count++;
28
                        }
29
                    } else {
30
                        Log.d("", "No such document");
31
                    }
32
                } else {
33
                    Log.d("", "get failed with ", task.getException());
34
                }
35
            }
36
        });
37
​
38
        DocumentReference docRef3 = db.collection("data").document("origin");
39
​
40
        docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
41
            @Override
42
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
43
                if (task.isSuccessful() && getActivity() != null) {
44
                    DocumentSnapshot document = task.getResult();
45
                    String date1 = new String();
46
                    String up_down1 = new String();
47
​
48
                    if (document.exists()) {
49
                        HashMap<String, Object> kospi = new HashMap<String, Object>(document.getData());
50
​
51
                        Object[] mapkey = kospi.keySet().toArray();
52
                        Arrays.sort(mapkey);
53
​
54
                        for (Object nkey : mapkey) {
55
                            date1 = nkey.toString();
56
                            up_down1 = kospi.get(nkey).toString();
57
                        }
58
​
59
                        what = new TextView(getContext());
60
                        what.setText(Html.fromHtml("[예측] " + date1 + " "  + "<FONT color=" + "#FF9800" + ">" + up_down1 + "</FONT>"));
61
                       }
62
                }
63
            }
64
        });
65
​
66
        DocumentReference docRef2 = db.collection("data").document("predictedkospi_test");
67
​
68
        docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
69
            @Override
70
            public void onComplete(@NonNull Task<DocumentSnapshot> task){
71
                if (task.isSuccessful() && getActivity()!=null) {
72
                    DocumentSnapshot document = task.getResult();
73
                    String date = new String();
74
                    String up_down = new String();
75
                    TextView show = (TextView)viewGroup.findViewById(R.id.show_predict);
76
​
77
                    if (document.exists()) {
78
                        HashMap<String,Object> kospi = new HashMap<String,Object>(document.getData());
79
​
80
                        Object[] mapkey = kospi.keySet().toArray();
81
                        Arrays.sort(mapkey);
82
​
83
                        for(Object nkey : mapkey) {
84
                            date = nkey.toString();
85
                            up_down = kospi.get(nkey).toString();
86
                        }
87
​
88
​
89
​
90
                        if (up_down.equals("1")){
91
                            Html.ImageGetter imageGetter = new Html.ImageGetter() {
92
                                @Override
93
                                public Drawable getDrawable(String source) {
94
                                    if (source.equals("up_triangle")){
95
                                        Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
96
                                        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
97
                                        return drawable;
98
                                    }
99
                                    return null;
100
                                }
101
                            };
102
                            Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
103
​
104
                            if (real_date.size()>0){
105
                                show.setText(what.getText());
106
                                show.append(" 기준 : " + date + " (");
107
                                show.append(htmlText);
108
                                show.append(")");
109
                                show.setTextColor(Color.parseColor("#FF555555"));
110
                            }
111
                        }
112
                        else if (up_down.equals("0")){
113
                            Html.ImageGetter imageGetter = new Html.ImageGetter() {
114
                                @Override
115
                                public Drawable getDrawable(String source) {
116
                                    if (source.equals("down_triangle")){
117
                                        Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
118
                                        drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
119
                                        return drawable;
120
                                    }
121
                                    return null;
122
                                }
123
                            };
124
                            Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
125
​
126
                            if (real_date.size()>0){
127
                                show.setText(what.getText());
128
                                show.append(" 기준 : " + date + " (");
129
                                show.append(htmlText);
130
                                show.append(")");
131
                                show.setTextColor(Color.parseColor("#FF555555"));
132
                            }
133
                        }
134
​
135
                        //차트 생성
136
                        ArrayList<Entry> values = new ArrayList<>();
137
                        for (int i=0;i<real_kospi_index.size();i++){
138
                            values.add(new Entry(real_kospi_index.get(i), i));
139
                        }
140
​
141
                        LineDataSet lineDataSet = new LineDataSet(values, "KOSPI");
142
                        lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.red));
143
                        lineDataSet.setDrawCircles(false);
144
                        lineDataSet.setValueTextSize(10);
145
​
146
                        String[] xaxes = new String[real_date.size()];
147
                        for (int i=0;i<real_date.size();i++)
148
                            xaxes[i] = real_date.get(i);
149
​
150
                        XAxis xAxis = lineChart.getXAxis();
151
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
152
                        xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
153
                        xAxis.setTextSize(11);
154
​
155
                        YAxis yAxisLeft = lineChart.getAxisLeft();
156
                        yAxisLeft.setDrawLabels(false);
157
                        yAxisLeft.setDrawAxisLine(false);
158
                        yAxisLeft.setDrawGridLines(false);
159
​
160
                        YAxis yAxisRight = lineChart.getAxisRight();
161
                        yAxisRight.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_700));
162
                        yAxisRight.setTextSize(13);
163
​
164
                        lineChart.setDescription(null);
165
                        lineChart.setTouchEnabled(true);
166
​
167
                        lineChart.getAxisLeft().setLabelCount(10, true);
168
                        lineChart.getAxisRight().setLabelCount(3,true);
169
​
170
                        Legend legend = lineChart.getLegend();
171
                        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
172
                        legend.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
173
​
174
                        lineChart.setData(new LineData(xaxes, lineDataSet));
175
                        lineChart.invalidate();
176
                    } else {
177
                        Log.d("", "No such document");
178
                    }
179
                } else {
180
                    Log.d("", "get failed with ", task.getException());
181
                }
182
            }
183
        });
184
​
185
        if (getActivity()!=null && real_date==null)
186
            showChart();
187
    }
실시간 지수

Main 탭의 실시간 해외 주요 주가지수 표현 부분

'네이버 시세' 웹사이트를 사용하여 실시간 지수를 크롤링

1
public void get_data(){
2
        if (getActivity()!=null){
3
            try{
4
                ndaq_arraylist.clear();
5
​
6
                Document doc1 = Jsoup.connect(ndaq_url).get();
7
                Elements contents1 = doc1.select("td[class=tb_td2]");
8
​
9
                for (Element e : contents1){
10
                    String content = e.select("span").text();
11
                    ndaq_arraylist.add(content);
12
                }
13
​
14
                spy_arraylist.clear();
15
​
16
                Document doc2 = Jsoup.connect(spy_url).get();
17
                Elements contents2 = doc2.select("td[class=tb_td2]");
18
​
19
                for (Element e : contents2){
20
                    String content = e.select("span").text();
21
                    spy_arraylist.add(content);
22
                }
23
​
24
                dji_arraylist.clear();
25
​
26
                Document doc3 = Jsoup.connect(dji_url).get();
27
                Elements contents3 = doc3.select("td[class=tb_td2]");
28
​
29
                for (Element e : contents3){
30
                    String content = e.select("span").text();
31
                    dji_arraylist.add(content);
32
                }
33
​
34
                stoxx50_arraylist.clear();
35
​
36
                Document doc4 = Jsoup.connect(stoxx50_url).get();
37
                Elements contents4 = doc4.select("td[class=tb_td2]");
38
​
39
                for (Element e : contents4){
40
                    String content = e.select("span").text();
41
                    stoxx50_arraylist.add(content);
42
                }
43
​
44
                topix_arraylist.clear();
45
​
46
                Document doc5 = Jsoup.connect(topix_url).get();
47
                Elements contents5 = doc5.select("dl[class=blind]");
48
​
49
                for (Element e : contents5){
50
                    String content = e.select("dd").text();
51
                    topix_arraylist.add(content);
52
                }
53
​
54
                nikkei_arraylist.clear();
55
​
56
                Document doc6 = Jsoup.connect(nikkei_url).get();
57
                Elements contents6 = doc6.select("td[class=tb_td2]");
58
​
59
                for (Element e : contents6){
60
                    String content = e.select("span").text();
61
                    nikkei_arraylist.add(content);
62
                }
63
​
64
                ssec_arraylist.clear();
65
​
66
                Document doc7 = Jsoup.connect(ssec_url).get();
67
                Elements contents7 = doc7.select("td[class=tb_td2]");
68
​
69
                for (Element e : contents7){
70
                    String content = e.select("span").text();
71
                    ssec_arraylist.add(content);
72
                }
73
​
74
                hsi_arraylist.clear();
75
​
76
                Document doc8 = Jsoup.connect(hsi_url).get();
77
                Elements contents8 = doc8.select("td[class=tb_td2]");
78
​
79
                for (Element e : contents8){
80
                    String content = e.select("span").text();
81
                    hsi_arraylist.add(content);
82
                }
83
            }catch(IOException err){
84
                err.printStackTrace();
85
            }
86
        }
87
​
88
        if (getActivity()!=null && ndaq_arraylist!=null && spy_arraylist!=null && dji_arraylist!=null && stoxx50_arraylist!=null && topix_arraylist!=null && nikkei_arraylist!=null && ssec_arraylist!=null && hsi_arraylist!=null
89
        && ndaq_arraylist.size()!=0 && spy_arraylist.size()!=0 && dji_arraylist.size()!=0 && stoxx50_arraylist.size()!=0 && topix_arraylist.size()!=0 && nikkei_arraylist.size()!=0 && ssec_arraylist.size()!=0 && hsi_arraylist.size()!=0){
90
            this.getActivity().runOnUiThread(new Runnable(){
91
                @Override
92
                public void run(){
93
                    String temp1 = ndaq_arraylist.get(0);
94
                    String temp1_1 = temp1.replace(",", "");
95
                    String temp1_2 = ndaq_arraylist.get(1);
96
                    temp1_2 = temp1_2.replace(",", "");
97
                    float compare1 = Float.parseFloat(temp1_1) - Float.parseFloat(temp1_2);
98
​
99
                    if (compare1>0){
100
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
101
                            @Override
102
                            public Drawable getDrawable(String source) {
103
                                if (source.equals("up_triangle")){
104
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
105
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
106
                                    return drawable;
107
                                }
108
                                return null;
109
                            }
110
                        };
111
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
112
​
113
                        ndaq_textview.setText(temp1 + "(" );
114
                        ndaq_textview.append(htmlText);
115
                        ndaq_textview.append(")");
116
                    }
117
                    else if (compare1<0){
118
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
119
                            @Override
120
                            public Drawable getDrawable(String source) {
121
                                if (source.equals("down_triangle")){
122
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
123
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
124
                                    return drawable;
125
                                }
126
                                return null;
127
                            }
128
                        };
129
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
130
​
131
                        ndaq_textview.setText(temp1 + "(" );
132
                        ndaq_textview.append(htmlText);
133
                        ndaq_textview.append(")");
134
                    }
135
                    else
136
                        ndaq_textview.setText(temp1 + "( - )");
137
​
138
                    String temp2 = spy_arraylist.get(0);
139
                    String temp2_1 = temp2.replace(",", "");
140
                    String temp2_2 = spy_arraylist.get(1);
141
                    temp2_2 = temp2_2.replace(",", "");
142
                    float compare2 = Float.parseFloat(temp2_1) - Float.parseFloat(temp2_2);
143
​
144
                    if (compare2>0){
145
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
146
                            @Override
147
                            public Drawable getDrawable(String source) {
148
                                if (source.equals("up_triangle")){
149
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
150
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
151
                                    return drawable;
152
                                }
153
                                return null;
154
                            }
155
                        };
156
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
157
​
158
                        spy_textview.setText(temp2 + "(" );
159
                        spy_textview.append(htmlText);
160
                        spy_textview.append(")");
161
                    }
162
                    else if (compare2<0){
163
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
164
                            @Override
165
                            public Drawable getDrawable(String source) {
166
                                if (source.equals("down_triangle")){
167
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
168
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
169
                                    return drawable;
170
                                }
171
                                return null;
172
                            }
173
                        };
174
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
175
​
176
                        spy_textview.setText(temp2 + "(" );
177
                        spy_textview.append(htmlText);
178
                        spy_textview.append(")");
179
                    }
180
                    else
181
                        spy_textview.setText(temp2 + "( - )");
182
​
183
                    String temp3 = dji_arraylist.get(0);
184
                    String temp3_1 = temp3.replace(",", "");
185
                    String temp3_2 = dji_arraylist.get(1);
186
                    temp3_2 = temp3_2.replace(",", "");
187
                    float compare3 = Float.parseFloat(temp3_1) - Float.parseFloat(temp3_2);
188
​
189
                    if (compare3>0){
190
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
191
                            @Override
192
                            public Drawable getDrawable(String source) {
193
                                if (source.equals("up_triangle")){
194
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
195
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
196
                                    return drawable;
197
                                }
198
                                return null;
199
                            }
200
                        };
201
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
202
​
203
                        dji_textview.setText(temp3 + "(" );
204
                        dji_textview.append(htmlText);
205
                        dji_textview.append(")");
206
                    }
207
                    else if (compare3<0){
208
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
209
                            @Override
210
                            public Drawable getDrawable(String source) {
211
                                if (source.equals("down_triangle")){
212
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
213
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
214
                                    return drawable;
215
                                }
216
                                return null;
217
                            }
218
                        };
219
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
220
​
221
                        dji_textview.setText(temp3 + "(" );
222
                        dji_textview.append(htmlText);
223
                        dji_textview.append(")");
224
                    }
225
                    else
226
                        dji_textview.setText(temp3 + "( - )");
227
​
228
                    String temp4 = stoxx50_arraylist.get(0);
229
                    String temp4_1 = temp4.replace(",", "");
230
                    String temp4_2 = stoxx50_arraylist.get(1);
231
                    temp4_2 = temp4_2.replace(",", "");
232
                    float compare4 = Float.parseFloat(temp4_1) - Float.parseFloat(temp4_2);
233
​
234
                    if (compare4>0){
235
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
236
                            @Override
237
                            public Drawable getDrawable(String source) {
238
                                if (source.equals("up_triangle")){
239
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
240
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
241
                                    return drawable;
242
                                }
243
                                return null;
244
                            }
245
                        };
246
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
247
​
248
                        stoxx50_textview.setText(temp4 + "(" );
249
                        stoxx50_textview.append(htmlText);
250
                        stoxx50_textview.append(")");
251
                    }
252
                    else if (compare4<0){
253
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
254
                            @Override
255
                            public Drawable getDrawable(String source) {
256
                                if (source.equals("down_triangle")){
257
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
258
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
259
                                    return drawable;
260
                                }
261
                                return null;
262
                            }
263
                        };
264
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
265
​
266
                        stoxx50_textview.setText(temp4 + "(" );
267
                        stoxx50_textview.append(htmlText);
268
                        stoxx50_textview.append(")");
269
                    }
270
                    else
271
                        stoxx50_textview.setText(temp4 + "( - )");
272
​
273
                    String temp5_0 = topix_arraylist.get(0);
274
                    String[] temp5_00 = temp5_0.split(" ");
275
                    String temp5 = temp5_00[14];
276
                    String temp5_1 = temp5.replace(",", "");
277
                    String temp5_2 = temp5_00[22];
278
                    temp5_2 = temp5_2.replace(",", "");
279
                    float compare5 = Float.parseFloat(temp5_1) - Float.parseFloat(temp5_2);
280
​
281
                    if (compare5>0){
282
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
283
                            @Override
284
                            public Drawable getDrawable(String source) {
285
                                if (source.equals("up_triangle")){
286
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
287
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
288
                                    return drawable;
289
                                }
290
                                return null;
291
                            }
292
                        };
293
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
294
​
295
                        topix_textview.setText(temp5 + "(" );
296
                        topix_textview.append(htmlText);
297
                        topix_textview.append(")");
298
                    }
299
                    else if (compare5<0){
300
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
301
                            @Override
302
                            public Drawable getDrawable(String source) {
303
                                if (source.equals("down_triangle")){
304
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
305
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
306
                                    return drawable;
307
                                }
308
                                return null;
309
                            }
310
                        };
311
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
312
​
313
                        topix_textview.setText(temp5 + "(" );
314
                        topix_textview.append(htmlText);
315
                        topix_textview.append(")");
316
                    }
317
                    else
318
                        topix_textview.setText(temp5 + "( - )");
319
​
320
                    String temp6 = nikkei_arraylist.get(0);
321
                    String temp6_1 = temp6.replace(",", "");
322
                    String temp6_2 = nikkei_arraylist.get(1);
323
                    temp6_2 = temp6_2.replace(",", "");
324
                    float compare6 = Float.parseFloat(temp6_1) - Float.parseFloat(temp6_2);
325
​
326
                    if (compare6>0){
327
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
328
                            @Override
329
                            public Drawable getDrawable(String source) {
330
                                if (source.equals("up_triangle")){
331
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
332
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
333
                                    return drawable;
334
                                }
335
                                return null;
336
                            }
337
                        };
338
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
339
​
340
                        nikkei_textview.setText(temp6 + "(" );
341
                        nikkei_textview.append(htmlText);
342
                        nikkei_textview.append(")");
343
                    }
344
                    else if (compare6<0){
345
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
346
                            @Override
347
                            public Drawable getDrawable(String source) {
348
                                if (source.equals("down_triangle")){
349
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
350
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
351
                                    return drawable;
352
                                }
353
                                return null;
354
                            }
355
                        };
356
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
357
​
358
                        nikkei_textview.setText(temp6 + "(" );
359
                        nikkei_textview.append(htmlText);
360
                        nikkei_textview.append(")");
361
                    }
362
                    else
363
                        nikkei_textview.setText(temp6 + "( - )");
364
​
365
                    String temp7 = ssec_arraylist.get(0);
366
                    String temp7_1 = temp7.replace(",", "");
367
                    String temp7_2 = ssec_arraylist.get(1);
368
                    temp7_2 = temp7_2.replace(",", "");
369
                    float compare7 = Float.parseFloat(temp7_1) - Float.parseFloat(temp7_2);
370
​
371
                    if (compare7>0){
372
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
373
                            @Override
374
                            public Drawable getDrawable(String source) {
375
                                if (source.equals("up_triangle")){
376
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
377
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
378
                                    return drawable;
379
                                }
380
                                return null;
381
                            }
382
                        };
383
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
384
​
385
                        ssec_textview.setText(temp7 + "(" );
386
                        ssec_textview.append(htmlText);
387
                        ssec_textview.append(")");
388
                    }
389
                    else if (compare7<0){
390
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
391
                            @Override
392
                            public Drawable getDrawable(String source) {
393
                                if (source.equals("down_triangle")){
394
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
395
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
396
                                    return drawable;
397
                                }
398
                                return null;
399
                            }
400
                        };
401
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
402
​
403
                        ssec_textview.setText(temp7 + "(" );
404
                        ssec_textview.append(htmlText);
405
                        ssec_textview.append(")");
406
                    }
407
                    else
408
                        ssec_textview.setText(temp7 + "( - )");
409
​
410
                    String temp8 = hsi_arraylist.get(0);
411
                    String temp8_1 = temp8.replace(",", "");
412
                    String temp8_2 = hsi_arraylist.get(1);
413
                    temp8_2 = temp8_2.replace(",", "");
414
                    float compare8 = Float.parseFloat(temp8_1) - Float.parseFloat(temp8_2);
415
​
416
                    if (compare8>0){
417
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
418
                            @Override
419
                            public Drawable getDrawable(String source) {
420
                                if (source.equals("up_triangle")){
421
                                    Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
422
                                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
423
                                    return drawable;
424
                                }
425
                                return null;
426
                            }
427
                        };
428
                        Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
429
​
430
                        hsi_textview.setText(temp8 + "(" );
431
                        hsi_textview.append(htmlText);
432
                        hsi_textview.append(")");
433
                    }
434
                    else if (compare8<0){
435
                        Html.ImageGetter imageGetter = new Html.ImageGetter() {
436
                            @Override
437
                            public Drawable getDrawable(String source) {
438
                                if (source.equals("down_triangle")){
439
                                    Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
440
                                    drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
441
                                    return drawable;
442
                                }
443
                                return null;
444
                            }
445
                        };
446
                        Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
447
​
448
                        hsi_textview.setText(temp8 + "(" );
449
                        hsi_textview.append(htmlText);
450
                        hsi_textview.append(")");
451
                    }
452
                    else
453
                        hsi_textview.setText(temp8 + "( - )");
454
                }
455
            });
456
        }
457
    }
</br>

News 탭

매일경제 국내 증권 뉴스와 매일경제 해외 증권 뉴스 웹사이트의 기사를 크롤링

국내 뉴스 기사를 가져오는 코드

1
class task1 extends AsyncTask<Void, Void, Void> {
2
        @Override
3
        protected Void doInBackground(Void... voids) {
4
            if (getActivity()!=null) {
5
                try {
6
                    listView.setVisibility(View.INVISIBLE);
7
​
8
                    contentList.clear();
9
                    titleList.clear();
10
​
11
                    Document doc = Jsoup.connect(url1).get();
12
                    Elements titles = doc.select("td[class=title]");
13
​
14
                    for (Element e : titles) {
15
                        String news_title = e.select("a").text();
16
                        String news_link = e.select("a").attr("href");
17
                        contentList.add(news_link);
18
                        titleList.add(news_title);
19
                    }
20
                } catch (IOException err1) {
21
                    err1.printStackTrace();
22
                }
23
            }
24
​
25
            if (getActivity()!=null){
26
                getActivity().runOnUiThread(new Runnable() {
27
                    @Override
28
                    public void run() {
29
                        adapter.notifyDataSetChanged();
30
                        listView.setVisibility(View.VISIBLE);
31
                    }
32
                });
33
            }
34
​
35
            return null;
36
        }
37
​
38
        @Override
39
        protected void onPreExecute() {
40
            super.onPreExecute();
41
        }
42
    }
</br>

해외 뉴스 기사를 가져오는 코드

1
class task2 extends AsyncTask<Void, Void, Void> {
2
        @Override
3
        protected Void doInBackground(Void... voids) {
4
            if (getActivity()!=null) {
5
                try {
6
                    listView.setVisibility(View.INVISIBLE);
7
​
8
                    contentList.clear();
9
                    titleList.clear();
10
​
11
                    Document doc = Jsoup.connect(url2).get();
12
                    Elements titles = doc.select("td[class=title]");
13
​
14
                    for (Element e : titles) {
15
                        String news_title = e.select("a").text();
16
                        String news_link = e.select("a").attr("href");
17
                        contentList.add(news_link);
18
                        titleList.add(news_title);
19
                    }
20
                } catch (IOException err1) {
21
                    err1.printStackTrace();
22
                }
23
            }
24
​
25
            if (getActivity()!=null){
26
                getActivity().runOnUiThread(new Runnable() {
27
                    @Override
28
                    public void run() {
29
                        adapter.notifyDataSetChanged();
30
                        listView.setVisibility(View.VISIBLE);
31
                    }
32
                });
33
            }
34
​
35
            return null;
36
        }
37
​
38
        @Override
39
        protected void onPreExecute() { super.onPreExecute(); }
40
    }
</br>

Event 탭

KOSPI 시가총액 100개의 실시간 주가 정보를 나타냄

주가 정보는 '네이버 시세' 웹사이트에서 크롤링 하였음

1초마다 주가 정보가 갱신되도록 설정

1
public class List_Window extends Fragment {
2
    ViewGroup viewGroup;
3
​
4
    Timer timer;
5
    TimerTask t;
6
    ExpandableListView listView;
7
    CustomAdapter adapter;
8
    ArrayList<GroupData> groupListDatas;
9
    ArrayList<ArrayList<ChildData>> childListDatas;
10
    private List<String> list;
11
    private EditText editSearch;
12
    int nowPosition;
13
    static Resources list_resource;
14
    String[] list_title = {"삼성전자", "SK하이닉스", "LG화학", "NAVER", "삼성바이오로직스", "카카오", "현대차", "삼성SDI", "셀트리온",
15
            "기아", "POSCO", "현대모비스", "LG전자", "삼성물산", "SK텔레콤", "LG생활건강", "SK이노베이션", "KB금융", "신한지주",
16
            "SK", "엔씨소프트", "삼성생명", "아모레퍼시픽", "한국전력", "삼성에스디에스", "삼성전기", "하나금융지주", "HMM", "KT&G",
17
            "포스코케미칼", "넷마블", "한국조선해양", "S-Oil", "롯데케미칼", "삼성화재", "대한항공", "하이브", "한온시스템", "한화솔루션",
18
            "LG디스플레이", "고려아연", "SK바이오팜", "금호석유", "우리금융지주", "KT", "현대제철", "현대글로비스", "기업은행", "미래에셋증권",
19
            "CJ제일제당", "한국타이어앤테크놀로", "한국금융지주", "아모레G", "LG유플러스", "현대중공업지주", "현대건설", "강원랜드", "코웨이", "SKC",
20
            "두산중공업", "이마트", "삼성중공업", "두산밥캣", "오리온", "LG이노텍", "맥쿼리인프라", "한미사이언스", "유한양행", "대우조선해양",
21
            "GS", "녹십자", "삼성카드", "한미약품", "쌍용C&E", "CJ대한통운", "GS건설", "삼성증권", "롯데지주", "호텔신라",
22
            "DB손해보험", "삼성엔지니어링", "롯데쇼핑", "NH투자증권", "한진칼", "키움증권", "신풍제약", "한국항공우주", "에스원", "일진머티리얼즈",
23
            "한국가스공사", "동서", "SK케미칼", "만도", "CJ", "휠라홀딩스", "GS리테일", "더존비즈온", "두산퓨얼셀", "대웅"};
24
    String[] list_code = {"005930", "000660", "051910", "035420", "207940", "035720", "005380", "006400", "068270",
25
            "000270", "005490", "012330", "066570", "028260", "017670", "051900", "096770", "105560", "055550",
26
            "034730", "036570", "032830", "090430", "015760", "018260", "009150", "086790", "011200", "033780",
27
            "003670", "251270", "009540", "010950", "011170", "000810", "003490", "352820", "018880", "009830",
28
            "034220", "010130", "326030", "011780", "316140", "030200", "004020", "086280", "024110", "006800",
29
            "097950", "161390", "071050", "002790", "032640", "267250", "000720", "035250", "021240", "011790",
30
            "034020", "139480", "010140", "241560", "271560", "011070", "088980", "008930", "000100", "042660",
31
            "078930", "006280", "029780", "128940", "003410", "000120", "006360", "016360", "004990", "008770",
32
            "005830", "028050", "023530", "005940", "180640", "039490", "019170", "047810", "012750", "020150",
33
            "036460", "026960", "285130", "204320", "001040", "081660", "007070", "012510", "336260", "003090"};
34
​
35
    @Nullable
36
    @Override
37
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
38
        viewGroup = (ViewGroup) inflater.inflate(R.layout.list_fragment, container, false);
39
        groupListDatas = new ArrayList<GroupData>();
40
        childListDatas = new ArrayList<ArrayList<ChildData>>();
41
        list = new ArrayList<String>();
42
        list_resource = getResources();
43
​
44
        listView = (ExpandableListView)viewGroup.findViewById(R.id.all_list);
45
​
46
        settingList();
47
        Collections.sort(list, cmpAsc);
48
        setData();
49
​
50
        adapter = new CustomAdapter(container.getContext(), groupListDatas, childListDatas);
51
        listView.setAdapter(adapter);
52
​
53
        listView.setGroupIndicator(null);
54
​
55
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
56
            @Override
57
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id){
58
                nowPosition = groupPosition;
59
                tempTask();
60
​
61
                return false;
62
            }
63
        });
64
​
65
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
66
            @Override
67
            public void onGroupExpand(int groupPosition){
68
                int groupCount = adapter.getGroupCount();
69
                for (int i=0;i<groupCount;i++){
70
                    if (!(i==groupPosition)) {
71
                        listView.collapseGroup(i);
72
                    }
73
                }
74
            }
75
        });
76
​
77
        editSearch = (EditText) viewGroup.findViewById(R.id.edit_search);
78
        editSearch.setBackgroundColor(getResources().getColor(R.color.white));
79
​
80
        editSearch.addTextChangedListener(new TextWatcher() {
81
            @Override
82
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
83
​
84
                //t.cancel();
85
​
86
                for (int i=0;i<list_title.length;i++)
87
                    listView.collapseGroup(i);
88
            }
89
​
90
            @Override
91
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
92
                String text = editSearch.getText().toString();
93
                search(text);
94
            }
95
​
96
            @Override
97
            public void afterTextChanged(Editable editable) { }
98
        });
99
​
100
        return viewGroup;
101
    }
102
​
103
    public void tempTask(){
104
        if (getActivity()!=null){
105
            t = new TimerTask(){
106
                @Override
107
                public void run(){
108
                    get_real_time_data();
109
                }
110
            };
111
            timer = new Timer();
112
            timer.schedule(t, 0, 1000);
113
        }
114
    }
115
​
116
    public void get_real_time_data(){
117
        String[] temp = new String[15];
118
        String url = "https://finance.naver.com/item/main.nhn?code=";
119
​
120
        if (getActivity() != null && nowPosition<adapter.getGroupCount() && nowPosition>=0 && adapter.getGroupCount()!=0) {
121
            try {
122
                for (int i = 0; i < list_title.length; i++) {
123
                    if (list.get(nowPosition).equals(list_title[i])) {
124
                        url = url + list_code[i];
125
                        break;
126
                    }
127
                }
128
                int size = 0;
129
​
130
                Document doc = Jsoup.connect(url).get();
131
                Elements elements = doc.select("dl[class=blind]");
132
                Elements elements1 = elements.select("dd");
133
​
134
                for (Element e : elements1) {
135
                    temp[size] = e.text();
136
                    size++;
137
                }
138
​
139
​
140
            } catch (IOException err) {
141
                err.printStackTrace();
142
            }
143
        }
144
​
145
        if (getActivity()!=null && nowPosition<adapter.getGroupCount() && nowPosition>=0 && adapter.getGroupCount()!=0){
146
            this.getActivity().runOnUiThread(new Runnable(){
147
                @Override
148
                public void run(){
149
                    String[] temp1 = temp[3].split(" ");
150
                    String[] temp2 = temp[4].split(" ");
151
                    String[] temp3 = temp[5].split(" ");
152
                    String[] temp4 = temp[6].split(" ");
153
                    String[] temp5 = temp[7].split(" ");
154
                    String[] temp6 = temp[8].split(" ");
155
                    String[] temp7 = temp[9].split(" ");
156
                    String[] temp8 = temp[10].split(" ");
157
                    String[] temp9 = temp[11].split(" ");
158
​
159
                    childListDatas.get(nowPosition).clear();
160
                    childListDatas.get(nowPosition).add(new ChildData(temp1[1],temp1[3] + " " + temp1[4],temp2[1],temp3[1],temp4[1],temp6[1],temp5[1],temp7[1],temp8[1],temp9[1]));
161
                    adapter.notifyDataSetChanged();
162
                }
163
            });
164
        }
165
    }
166
​
167
    private void setData(){
168
        if (getActivity()!=null){
169
            for(int i=0;i<list.size();i++) {
170
                groupListDatas.add(new GroupData(list.get(i)));
171
                childListDatas.add(new ArrayList<ChildData>());
172
                childListDatas.get(i).add(new ChildData("wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait"));
173
            }
174
        }
175
    }
176
​
177
    Comparator<String> cmpAsc = new Comparator<String>() {
178
        @Override
179
        public int compare(String o1, String o2) { return o1.compareTo(o2); }
180
    };
181
​
182
    private void settingList() {
183
        if (getActivity()!=null){
184
            list.clear();
185
​
186
            for (int i=0;i<list_title.length;i++){
187
                list.add(list_title[i]);
188
            }
189
        }
190
    }
191
​
192
    public void search(String charText) {
193
        if (getActivity()!=null){
194
            charText = charText.toLowerCase(Locale.getDefault());
195
​
196
            list.clear();
197
            childListDatas.clear();
198
            groupListDatas.clear();
199
​
200
            if (charText.length() == 0){
201
                settingList();
202
            }
203
​
204
            else {
205
                for(int i = 0;i<list_title.length;i++) {
206
                    if (list_title[i].toLowerCase().contains(charText))
207
                        list.add(list_title[i]);
208
                }
209
            }
210
            Collections.sort(list, cmpAsc);
211
            setData();
212
            adapter.notifyDataSetChanged();
213
        }
214
    }
215
}
</br>

My 탭

Event 탭에서 선택해서 담은 종목들을 관리할 수 있는 탭

1
public class My_Window extends Fragment{
2
    ViewGroup viewGroup;
3
​
4
    static Timer timer;
5
    static ExpandableListView listView;
6
    static My_CustomAdapter adapter;
7
    static ArrayList<GroupData> groupListDatas;
8
    static ArrayList<ArrayList<ChildData>> childListDatas;
9
    static List<String> list;
10
    static int nowPosition;
11
​
12
    static public ArrayList<String> my_list_title = new ArrayList<String>();
13
    static public ArrayList<String> my_list_code = new ArrayList<String>();
14
    static public ArrayList<String> temp_title = new ArrayList<String>();
15
    static public ArrayList<String> temp_code = new ArrayList<String>();
16
​
17
    int cnt = 0;
18
    static Context ctt;
19
    static Activity act;
20
    static Resources my_resource;
21
​
22
    @Nullable
23
    @Override
24
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
25
        viewGroup=(ViewGroup) inflater.inflate(R.layout.my_fragment, container, false);
26
        groupListDatas = new ArrayList<GroupData>();
27
        childListDatas = new ArrayList<ArrayList<ChildData>>();
28
        list = new ArrayList<String>();
29
        ctt = getContext();
30
        act = getActivity();
31
        my_resource = getResources();
32
​
33
​
34
        if (temp_title.size()!=0){
35
            for (int i=0;i<temp_title.size();i++){
36
                my_list_title.add(temp_title.get(i));
37
                my_list_code.add(temp_code.get(i));
38
            }
39
​
40
            temp_title.clear();
41
            temp_code.clear();
42
​
43
            SaveTitleData(getContext(), my_list_title);
44
            SaveCodeData(getContext(), my_list_code);
45
            my_list_title = ReadTitleData(getContext());
46
            my_list_code = ReadCodeData(getContext());
47
        }
48
        else{
49
            my_list_title = ReadTitleData(getContext());
50
            my_list_code = ReadCodeData(getContext());
51
        }
52
​
53
        listView = (ExpandableListView)viewGroup.findViewById(R.id.my_list);
54
        settingList();
55
        setData();
56
​
57
        adapter = new My_CustomAdapter(getContext(), groupListDatas, childListDatas);
58
        listView.setAdapter(adapter);
59
​
60
        listView.setGroupIndicator(null);
61
​
62
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
63
            @Override
64
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id){
65
                nowPosition = groupPosition;
66
                tempTask();
67
​
68
                return false;
69
            }
70
        });
71
​
72
        listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
73
            @Override
74
            public void onGroupExpand(int groupPosition){
75
                int groupCount = adapter.getGroupCount();
76
                for (int i=0;i<groupCount;i++){
77
                    if (!(i==groupPosition)) {
78
                        listView.collapseGroup(i);
79
                    }
80
                }
81
            }
82
        });
83
​
84
        return viewGroup;
85
    }
86
​
87
    public static Context getAppContext(){
88
        return ctt;
89
    }
90
​
91
    public void tempTask(){
92
        if (getActivity()!=null){
93
            TimerTask t = new TimerTask(){
94
                @Override
95
                public void run(){
96
                    get_real_time_data();
97
                }
98
            };
99
            timer = new Timer();
100
            timer.schedule(t, 0, 1000);
101
        }
102
    }
103
​
104
    public void get_real_time_data(){
105
        String[] temp = new String[15];
106
        String url = "https://finance.naver.com/item/main.nhn?code=";
107
​
108
        if (getActivity()!=null && nowPosition<my_list_title.size() && nowPosition>=0 && adapter.getGroupCount()!=0){
109
            try{
110
                for(int i=0;i<my_list_title.size();i++){
111
                    if (list.get(nowPosition).equals(my_list_title.get(i))) {
112
                        url = url + my_list_code.get(i);
113
                        break;
114
                    }
115
                }
116
                int size = 0;
117
​
118
                Document doc = Jsoup.connect(url).get();
119
                Elements elements = doc.select("dl[class=blind]");
120
                Elements elements1 = elements.select("dd");
121
​
122
                for (Element e : elements1){
123
                    temp[size] = e.text();
124
                    size++;
125
                }
126
​
127
​
128
            }catch(IOException err){
129
                err.printStackTrace();
130
            }
131
        }
132
​
133
        if (getActivity()!=null && temp!=null && nowPosition<my_list_title.size() && nowPosition>=0 && adapter.getGroupCount()!=0){
134
            this.getActivity().runOnUiThread(new Runnable(){
135
                @Override
136
                public void run(){
137
                    String[] temp1 = temp[3].split(" ");
138
                    String[] temp2 = temp[4].split(" ");
139
                    String[] temp3 = temp[5].split(" ");
140
                    String[] temp4 = temp[6].split(" ");
141
                    String[] temp5 = temp[7].split(" ");
142
                    String[] temp6 = temp[8].split(" ");
143
                    String[] temp7 = temp[9].split(" ");
144
                    String[] temp8 = temp[10].split(" ");
145
                    String[] temp9 = temp[11].split(" ");
146
​
147
                    childListDatas.get(nowPosition).clear();
148
                    childListDatas.get(nowPosition).add(new ChildData(temp1[1],temp1[3] + " " + temp1[4],temp2[1],temp3[1],temp4[1],temp6[1],temp5[1],temp7[1],temp8[1],temp9[1]));
149
                    adapter.notifyDataSetChanged();
150
                }
151
            });
152
        }
153
    }
154
​
155
    static void setData(){
156
        if (act!=null){
157
            groupListDatas.clear();
158
            childListDatas.clear();
159
​
160
            for(int i=0;i<list.size();i++) {
161
                groupListDatas.add(new GroupData(list.get(i)));
162
                childListDatas.add(new ArrayList<ChildData>());
163
                childListDatas.get(i).add(new ChildData("wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait"));
164
            }
165
        }
166
    }
167
​
168
    static void settingList() {
169
        if (act!=null){
170
            list.clear();
171
            my_list_code.clear();
172
​
173
            list = ReadTitleData(ctt);
174
            my_list_title = ReadTitleData(ctt);
175
            my_list_code = ReadCodeData(ctt);
176
        }
177
    }
178
​
179
    static void SaveTitleData(Context context, ArrayList<String> my_list_title){
180
        if (act!=null){
181
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
182
            SharedPreferences.Editor editor = preferences.edit();
183
            JSONArray a = new JSONArray();
184
​
185
            for (int i=0;i<my_list_title.size();i++){
186
                a.put(my_list_title.get(i));
187
            }
188
​
189
            if (!my_list_title.isEmpty()){
190
                editor.putString("titles_json", a.toString());
191
            }
192
            else{
193
                editor.putString("titles_json", null);
194
            }
195
​
196
            editor.apply();
197
        }
198
    }
199
​
200
    static void SaveCodeData(Context context, ArrayList<String> my_list_code){
201
        if (act!=null){
202
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
203
            SharedPreferences.Editor editor = preferences.edit();
204
            JSONArray a = new JSONArray();
205
​
206
            for (int i=0;i<my_list_code.size();i++){
207
                a.put(my_list_code.get(i));
208
            }
209
​
210
            if (!my_list_code.isEmpty()){
211
                editor.putString("codes_json", a.toString());
212
            }
213
            else{
214
                editor.putString("codes_json", null);
215
            }
216
​
217
            editor.apply();
218
        }
219
    }
220
​
221
    static ArrayList<String> ReadTitleData(Context context){
222
        if (act!=null){
223
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
224
            String json = preferences.getString("titles_json", null);
225
            ArrayList<String> temp = new ArrayList<String>();
226
​
227
            if (json!=null){
228
                try{
229
                    JSONArray a = new JSONArray(json);
230
​
231
                    for (int i=0;i<a.length();i++){
232
                        String tmp = a.optString(i);
233
                        temp.add(tmp);
234
                    }
235
                }catch(JSONException e){
236
                    e.printStackTrace();
237
                }
238
            }
239
​
240
            return temp;
241
        }
242
​
243
        return null;
244
    }
245
​
246
    static ArrayList<String> ReadCodeData(Context context){
247
        if (act!=null){
248
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
249
            String json = preferences.getString("codes_json", null);
250
            ArrayList<String> temp = new ArrayList<String>();
251
​
252
            if (json!=null){
253
                try{
254
                    JSONArray a = new JSONArray(json);
255
​
256
                    for (int i=0;i<a.length();i++){
257
                        String tmp = a.optString(i);
258
                        temp.add(tmp);
259
                    }
260
                }catch(JSONException e){
261
                    e.printStackTrace();
262
                }
263
            }
264
​
265
            return temp;
266
        }
267
​
268
        return null;
269
    }
270
}
</br>

Server & DB
Server & DB가 필요한 이유

Server : KOSPI 지수 예측에 필요한 데이터를 수집, DB 데이터 갱신

DB : KOSPI 지수 예측에 필요한 데이터, KOSPI 차트 표현에 필요한 데이터, 지수 등락 예측 데이터 저장

위의 기능들을 정해진 시간에 자동으로 수행하기 위함

</br>

Server & DB 실행

Server와 DB의 실행 과정

모델예측에 필요한 데이터 수집

DB 갱신

예측 모델 실행

DB에 예측 결과 값 저장

</br>

모델 예측에 필요한 데이터 수집
데이터 수집 방법은 두 가지로 웹페이지 크롤링과 제공되는 API  활용

크롤링(crawling)

'네이버 증권' 웹페이지 크롤링을 통해 각국의 주가지수, 유가 등의 정보 획득

</br>

1
##---------<crawling.py>----------##
2
​
3
# -*- coding: utf-8 -*-
4
from bs4 import BeautifulSoup
5
from datetime import datetime
6
import requests
7
import time
8
print('<-----crawling.py import complete------------------>')
9
​
10
def get_kospi_daebiupdown(): ##kospi 대비, 등락률
11
​
12
    url= 'https://finance.naver.com/sise/sise_index_day.nhn?code=KOSPI'
13
    result=requests.get(url)
14
    result.encoding = 'utf-8'
15
    
16
    soup=BeautifulSoup(result.content, "html.parser")
17
    indice = soup.select("td.number_1")
18
    updownpercent = float(indice[1].text.replace('%','').strip())
19
​
20
    indice = soup.select("td.rate_down")
21
    daebi = float(indice[0].text.strip())
22
​
23
    updown = soup.select("img")[0]['alt']
24
    if updown == '하락':
25
        daebi = daebi * -1
26
​
27
    return daebi, updownpercent
28
​
29
def get_hsi_indice():##홍콩 hsi 지수
30
​
31
    url = 'https://finance.naver.com/world/sise.nhn?symbol=HSI@HSI'
32
    result=requests.get(url)
33
    result.encoding = 'utf-8'
34
​
35
    soup=BeautifulSoup(result.content, "html.parser")
36
    indice = soup.select("td.tb_td2")
37
    return indice[0].text.replace(',','')
38
​
39
def get_shanghai_indice(): ## 상하이 상해지수
40
​
41
    url = 'https://finance.naver.com/world/sise.nhn?symbol=SHS@000001'
42
    result=requests.get(url)
43
    result.encoding = 'utf-8'
44
​
45
    soup=BeautifulSoup(result.content, "html.parser")
46
    indice = soup.select("td.tb_td2")
47
    return indice[0].text.replace(',','')
48
    
49
​
50
def get_nikkei_indice():## 일본 니케이
51
​
52
    url = 'https://finance.naver.com/world/sise.nhn?symbol=NII@NI225'
53
    result=requests.get(url)
54
    result.encoding = 'utf-8'
55
​
56
    soup=BeautifulSoup(result.content, "html.parser")
57
    indice = soup.select("td.tb_td2")
58
    return indice[0].text.replace(',','')
59
​
60
​
61
def get_dji_indice(): ## 미국 다우존스
62
​
63
    url = 'https://finance.naver.com/world/sise.nhn?symbol=DJI@DJI'
64
    result=requests.get(url)
65
    result.encoding = 'utf-8'
66
​
67
    soup=BeautifulSoup(result.content, "html.parser")
68
    indice = soup.select("td.tb_td2")
69
    return indice[0].text.replace(',','')
70
​
71
def get_nas_indice():  ## 미국 나스닥
72
​
73
    url = 'https://finance.naver.com/world/sise.nhn?symbol=NAS@IXIC&fdtc=0'
74
    result=requests.get(url)
75
    result.encoding = 'utf-8'
76
​
77
    soup=BeautifulSoup(result.content, "html.parser")
78
    indice = soup.select("td.tb_td2")
79
    return indice[0].text.replace(',','')
80
​
81
def get_spi_indice(): ## 미국 s&p 500
82
​
83
    url = 'https://finance.naver.com/world/sise.nhn?symbol=SPI@SPX'
84
    result=requests.get(url)
85
    result.encoding = 'utf-8'
86
​
87
    soup=BeautifulSoup(result.content, "html.parser")
88
    indice = soup.select("td.tb_td2")
89
    return indice[0].text.replace(',','')
90
​
91
​
92
def get_oil_price(): ##wti 유가가격
93
    
94
    wti_url = 'https://finance.naver.com/marketindex/worldDailyQuote.nhn?marketindexCd=OIL_CL&fdtc=2'
95
    
96
    
97
    result=requests.get(wti_url)
98
    result.encoding = 'utf-8'
99
    
100
    soup=BeautifulSoup(result.content, "html.parser")
101
    
102
    indice = soup.select("td.num")
103
    wti = indice[0].text.strip()
104
​
105
    return wti
106
​
</br>

API

한국은행 경제통계시스템 : ecos API - '환율' 

'GitHub 오픈소스 모듈 : PyKrx' - '시가', '저가', '고가', '종가', '거래량', '각 기관들의 KOSPI 시장 순매수금'

</br>

위의 두 모듈을 사용하여 해당 데이터 획득

</br>

1
##---------<use_ecos.py>----------##
2
​
3
# 한국은행의 환율 정보를 가져오는데 사용
4
​
5
# -*- coding: utf-8 -*-
6
import requests 
7
import xml.etree.ElementTree as ET 
8
from datetime import datetime
9
from datetime import timedelta
10
import time
11
import isHoliday
12
print('<-----use_ecos.py import complete----------------->')
13
key = 'RHXCFZZB7E5KGJLPID'
14
​
15
## API 호출
16
def runAPI(url):
17
    response = requests.get(url)  ## http 요청이 성공했을때 API의 리턴값을 가져옵니다.
18
    
19
    if response.status_code == 200:
20
        try:
21
            contents = response.text
22
            ecosRoot = ET.fromstring(contents)
23
            
24
            if ecosRoot[0].text[:4] in ("INFO","ERRO"):  ## 오류 확인
25
                print(ecosRoot[0].text + " : " + ecosRoot[1].text)  ## 오류메세지를 확인하고 처리합니다.
26
                
27
            else:
28
                return(ecosRoot[1][10].text)    ## 결과값 확인
29
​
30
        except Exception as e:    ##예외 프린트
31
            print(str(e))
32
​
33
def get_exchange(): ## 환율
34
​
35
    d = datetime.today().strftime('%Y%m%d')
36
    statisticcode = '036Y001'
37
    ## exchange_items = 미국달러, 일본엔, 유럽유로
38
    exchange_items = ['0000001', '0000002', '0000003']  
39
    exchanges = []
40
    for itemcode in exchange_items:
41
            url = "http://ecos.bok.or.kr/api/StatisticSearch/"+key+"/xml/kr/1/5/"+statisticcode+"/DD/"+d+"/"+d+"/" + itemcode
42
            exchanges.append(runAPI(url))
43
​
44
    return exchanges
</br>

1
##---------<kospidetail.py>----------##
2
​
3
# kospi지수의 시가, 고가, 저가 종가, 거래량과 각 투자처(개인,기관,외국인,연기금)별 순거래금액을 가져온다.
4
​
5
# -*- coding: utf-8 -*-
6
import time
7
from pykrx import stock
8
from datetime import datetime,timedelta
9
print('<-----kospidetail.py import complete--------------->')
10
## documents : https://github.com/sharebook-kr/pykrx
11
def get_kospi_detail():
12
    day = (datetime.today()).strftime("%Y%m%d")#####################
13
    df = stock.get_index_ohlcv_by_date(day, day, "1001")
14
    open = df.iloc[0, 0] # 시가
15
    high = df.iloc[0, 1] # 고가
16
    low = df.iloc[0, 2] # 저가
17
    close = df.iloc[0, 3] # 종가
18
    volume = int(df.iloc[0, 4] * 0.001) # 거래량
19
    return open,high,low,close,volume
20
    
21
def get_trading_value():
22
    day = (datetime.today()).strftime("%Y%m%d")###########################
23
    df = stock.get_market_trading_value_by_date(day, day, "KOSPI")
24
    association = int(df.iloc[0,0] * 0.000001) # 기관 순매수금
25
    person = int(df.iloc[0,2]* 0.000001) # 개인 순매수금
26
    foreign = int(df.iloc[0,3]* 0.000001) # 외국인 순매수금
27
    
28
    df = stock.get_market_trading_value_by_date(day, day, "KOSPI",detail=True)
29
    pension = int(df.iloc[0,6]* 0.000001) # 연기금 순매수금
30
    
31
    return association,foreign,person,pension
</br>

DB 데이터 갱신
1.에서 '크롤링'과 'API'를 사용하여 수집한 데이터는 일별데이터이므로 매일 DB를 갱신하는 코드를 작성

데이터가 바뀌는 시간 또한 다르기 때문에 (각 국가의 장 마감시간의 차이) 시간차를 두고 DB갱신을 1,2차로 설정

ex) 5/29 (목)의 데이터 수집

1차 : 환율 데이터 갱신(한국시간 기준 5/29 (목) 16시)

2차 : 환율 이외의 값 갱신(한국시간 기준 5/30(금) 8시)

</br>

1
##---------<fb.py>----------##
2
​
3
# -*- coding: utf-8 -*-
4
from firebase_admin import credentials
5
from firebase_admin import firestore
6
from copy import deepcopy
7
from pandas import Series,DataFrame
8
from datetime import datetime,timedelta
9
import isHoliday
10
import pandas as pd
11
import firebase_admin
12
import crawling
13
​
14
print('<-----fb_dailyupdate.py import complete----------->')
15
cred = credentials.Certificate("team1-1a267-firebase-adminsdk.json")
16
firebase_admin.initialize_app(cred, {'databaseURL' : 'https://team1-1a267.firebaseio.com'})
17
​
18
db = firestore.client()
19
​
20
print('<-----firebase 권한획득완료-------------------------->')
21
​
22
def fb_update_ecos(day_str, day_factors):
23
    print('<-----firebase 1차 갱신중----------------------------->')
24
    doc_ref = db.collection(u'data').document(u'dailydata')
25
​
26
    day_factors = ['NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN','NaN',
27
                    'NaN','NaN','NaN','NaN','NaN','NaN','NaN']+day_factors+['NaN']
28
    
29
    doc_ref.set({day_str : day_factors}, merge = True)
30
    
31
    
32
    print('<-----firebase 1차 갱신완료-------------------------->')
33
    
34
​
35
​
36
def fb_update_crawling(today_str, today_factors):
37
    print('<-----firebase 2차 갱신중----------------------------->')
38
    
39
    doc_ref = db.collection(u'data').document(u'dailydata')
40
    
41
    doc = doc_ref.get()
42
​
43
    dic = deepcopy(doc.to_dict())
44
​
45
    today_list = dic[today_str]
46
    
47
    today_list = today_factors[:17] + today_list[17:20] + [today_factors[20]]
48
    today_list = list(map(str, today_list))
49
    
50
​
51
    doc_ref.update({today_str : today_list})
52
    print('<-----firebase 2차 갱신완료--------------------------->')
</br>

위의 코드에 추가로 Application UI에 표현될 KOSPI 차트에 필요한 '지수'데이터 또한 갱신

def fb_update_daioykospi_android(today, kospi): ## KOSPI 차트표현에 필요한 데이터 갱신
    print('<-----안드로이드차트용 kospi지수 갱신중----------->')

    doc_ref = db.collection(u'data').document(u'dailykospi_android')
    doc_ref.set({today : kospi}, merge = True) #DB에 금일 날짜와 KOSPI 지수 추가
    print('<-----안드로이드차트용 kospi지수 갱신완료--------->')
</br>

아래 사진은 갱신된 FireBase DB(예측에 필요한 데이터 : dailydata // UI에 쓰이는 데이터 : dailykospi_android)



</br>



</br>

예측 모델 실행
</br>

가중치값들이 저장되어있는 모델 파일(파일명 : 75_LSTM.h5)을 실행해서 예측값을 return받는 모듈

1
## 모델파일 실행 모듈
2
##---------<model.py>----------##
3
​
4
## 머신러닝 라이브러리 tensorflow, keras import
5
from copy import deepcopy
6
​
7
import pandas as pd
8
from pandas import Series,DataFrame
9
import numpy as np
10
​
11
import tensorflow as tf
12
from tensorflow import keras
13
from sklearn.preprocessing import MinMaxScaler
14
from sklearn.model_selection import train_test_split
15
​
16
from tensorflow.keras.models import Sequential
17
from tensorflow.keras.layers import Dense
18
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
19
from tensorflow.keras.layers import LSTM
20
from tensorflow.keras.layers import Dropout
21
​
22
from tensorflow.keras.losses import Huber
23
from tensorflow.keras.optimizers import Adam
24
from tensorflow.keras import optimizers
25
from tensorflow.keras.callbacks import EarlyStopping, ModelCheckpoint
26
​
27
from sklearn.metrics import r2_score
28
from sklearn.metrics import mean_squared_error
29
​
30
from tensorflow.keras.models import load_model
31
import os
32
​
33
print('<-----model.py import complete----------->')
34
​
35
​
36
​
37
​
38
def run(model_data):
39
    
40
    model_data['EMA_close5'] = model_data['close'].ewm(5).mean() # 지수이동 평균(5일)
41
    model_data['EMA_close10'] = model_data['close'].ewm(10).mean() # 지수이동 평균(10일) 
42
    model_data['EMA_close20'] = model_data['close'].ewm(20).mean() # 지수이동 평균(20일)
43
    model_data['EMA_close60'] = model_data['close'].ewm(60).mean() # 지수이동 평균(60일)
44
    model_data['EMA_close120'] = model_data['close'].ewm(120).mean() # 지수이동 평균(120일)
45
    model_data['disp5'] = (model_data['close']/model_data['EMA_close5']) * 100 # 이격도
46
    model_data['after4days_close'] = model_data['close'].shift(-4) #close를 4칸 위로 올린 컬럼
47
​
48
    # 빈값을 위해 interpolate
49
    model_data = model_data.interpolate(method = 'values', limit_direction = 'both')
50
    
51
    # 정확한 지수이동 평균값과 이격도값은 121번째 row부터 들어있기에 데이터 슬라이스
52
    model_data = model_data[120:]
53
    today_k=model_data['close'].iloc[-1]
54
    
55
    # 데이터 전처리 - MinMaxScaler 적용
56
    new_scaler = MinMaxScaler()
57
    new_scaler.fit(model_data['close'].values.reshape(-1,1))
58
​
59
    
60
    for i in model_data.columns:
61
        if i == 'date': continue
62
        model_data[i] = MinMaxScaler().fit_transform(model_data[i].values.reshape(-1, 1)).round(4)
63
​
64
    feature_cols = ['volume','shanghai','dji', 'nikkei', 'hsi', 'won/US dollar', 'won/100en', 'won/euro','association','person','daebi',
65
       'EMA_close5', 'EMA_close10', 'EMA_close20', 'EMA_close60', 'EMA_close120', 'disp5', 'updown','after4days_close']
66
    label_cols = ['close']
67
​
68
    ###### 데이터셋 준비 완료
69
    
70
    base_dir = '' #모델 저장 위치 (서버용)
71
    file_name = '75_LSTM.h5'  #모델 이름(파일명)
72
    dir = os.path.join(base_dir, file_name)
73
    model = load_model(dir)  #저장했던 모델 (dir에 있는거) 불러오는 코드
74
​
75
    print('model 불러오기 완료')
76
    
77
    '''
78
    find_pred 
79
    : 다음날 예측을 5번 돌려서 마지막 도출된 값이 5일 후 예측된 값
80
    '''
81
    def find_pred():
82
        
83
        df = model_data[-24:] # 예측값 하나만 뽑기 위해 원래 -24:
84
        
85
        for i in range(5): # 1-4번째 예측 값으로 빈칸을 채우고, 5번째 예측값을 예측 값으로 이용
86
            df_tmp = df[i:i+20] #20개씩 잘라서 예측
87
            my_final_x_test = df_tmp[feature_cols]
88
            my_final_x_test = np.array(my_final_x_test)
89
            my_final_x_test = my_final_x_test.reshape(1, 20, 19) # 예측하기 위한 정리
90
​
91
            my_final_y_pred = model.predict(my_final_x_test) # 예측하는 코드
92
​
93
            if i == 4: #5번째 예측을 마쳤으므로 실제 오늘의 kospi값과 지수들을 통해 예측한 오늘로부터 5일 뒤 값을 return
94
                return new_scaler.inverse_transform(my_final_y_pred)[0][0]
95
            else: # 예측한 값을 df에 채워서 다음 예측을 위해 사용하므로 예측 값을 저장한다.
96
                df['after4days_close'].iloc[i+20] = my_final_y_pred[0][0]
97
​
98
    # return : 예측한 5일 뒤 코스피 값
99
    pred = find_pred()
100
    print('함수실행')
101
    # 예측 값(pred) - 데이터의 마지막날 코스피지수(today_k) > 0 이면 상승이므로 1, 유지 또는 하락의 경우 0
102
    ud_5days = 0
103
    if pred - today_k > 0:
104
        ud_5days = 1
105
    else:
106
        ud_5days = 0
107
​
108
    return ud_5days
109
​
</br>

KOSPI 등락 예측 정보 DB에 저장
모델이 예측한 등락 정보를 DB에 저장(1 : 전주 금요일보다 상승 // 0 : 전주 금요일보다 하락 or 동일)

1
##---------<fb.py>----------##
2
​
3
def upload_predict_kospi(predict_kospi_index, origin_day, origin_kospi):
4
    day = datetime.today()
5
    predictday = 0
6
    
7
    #근무일 기준 5일후를 측정하기 위함
8
    while predictday < 5:
9
        day += timedelta(days=1)
10
        if(isHoliday.isholiday(day) or isHoliday.isweekend(day)): ## 공휴일,주말이면 no count
11
            continue
12
        predictday += 1
13
​
14
    day_str = day.strftime("%Y-%m-%d")
15
​
16
    doc_ref = db.collection(u'data').document(u'predictedkospi_test')
17
    
18
    doc_ref.set({day_str : predict_kospi_index})
19
​
20
    doc_ref_origin = db.collection(u'data').document(u'origin')
21
    
22
    origin_day = origin_day.strftime("%Y-%m-%d")
23
​
24
    doc_ref_origin.set({origin_day : origin_kospi})
</br>

DB에 등락예측 정보를 저장한 상태 (5/28(금)에 예측한 6/4(금)의 KOSPI 등락 : 0)



