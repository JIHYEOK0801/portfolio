# 증권 정보 제공 App

---

> ## 목차

- ### [Description](#description-1)

- ### [Function](#Function-1)

- ### [Development](#Development-1)

  1. [Machine Learning](#machine-learning)
  2. [Android Studio](#android-studio)
  3. [Server / DB](#server--db)


</br>

---

> ## Description

- ### 개발 주제

- ### 개발 목적

  국내 주식시장에 영향을 미치는 요인들을 분석, 파악 후 결과를 이용하여 예측한 시장 흐름 정보를 사용자에게 제공하기 위함

  </br>

- ### 개발 기간

  2021.05 ~ 2021.06

  </br>

- ### 사용 기술(개발 환경)

  Python, Firebase DB, AWS, Android Studio, TensorFlow, Keras

</br>

---

> ## Function

1. ### KOSPI 지수 및 실시간 세계 주요 증권 지수 정보 제공

   - KOSPI지수 차트와 해외 증권 주요 지수를 실시간으로 알 수 있는 App의 메인 화면
   - 차트 위에 근무일 기준 5일 후의 KOSPI지수 등락을 표현
   - KOSPI지수 차트는 09.01.11 ~ 현재(21.06.02)까지의 기록과 해외 지수는 미국, 유럽, 일본, 중국, 홍콩의 주요 주가지수를 나타냄

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/1.gif?raw=true">

   </br>

2. ### 국내/외 증권정보 제공

   - 국내/외 증권 뉴스 기사를 크롤링 하여 사용자에게 정보 제공
   - 국내/외 버튼과 새로고침 버튼으로 뉴스 기사 종류 선택
   - 뉴스 기사를 클릭시 원본 기사 링크 참조 가능

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/2.gif?raw=true">

   </br>

3. ### 대형주 실시간 주가정보 제공

   - 대형주 100개의 실시간 주가 정보 확인
   - 선택 버튼으로 사용자가 원하는 종목을 따로 관리 

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/3.gif?raw=true">

   </br>

4. ### 관심주 관리 기능 제공

   - 선택한 관심종목들을 따로 관리할 수 있는 기능
   - 관심종목들의 실시간 주가정보 확인 및 삭제 버튼으로 관심종목에서 삭제 가능

   <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/4.gif?raw=true">

   </br>

---

> ## Development

## Machine Learning

- > ### KOSPI 지수 예측에 사용한 일별 데이터(총 27개의 column)

  1. *close(종가)*
  2. *daebi(대비)*
  3. *updown(등락률)*

  4. *open(시가)*

  5. *high(고가)*

  6. *low(저가)*

  7. *volume(거래량)*

  8. *association(기관 순매수금)*

  9. *foreign(외국인 순매수금)*

  10. *person(개인 순매수금)*

  11. *pension(연기금 순매수금)*

  12. *hsi(홍콩 hsi지수)*

  13. *shanghai(중국 상해지수)*

  14. *nasdaq(미국 나스닥지수)*

  15. *spy(미국 spi500 지수)*

  16. *dji(미국 다우지수)*

  17. *nikkei(일본 니케이지수)*

  18. *won/USdollar(달러환율)*

  19. *won/100en(엔환율)*

  20. *won/euro(유로환율)*

  21. *WTI(유가)*

  22. *ema(지수이동평균 : 5,10,20,60,120일)*

  23. *disp(이격도 : 주가와 지수이동평균선의 괴리를 수치화하여 나타낸 것)*

</br>

- > ### 예측 과정

  - **예측 시행 요일 및 예측 목표 요일**

    - *예측 시행 요일* : 매주 주식시장 마감날의 다음날

    - *예측 목표 요일* : 근무일 기준 5일 후(일반적으로 그 다음 주 금요일)로, 매주 한 번씩 실행

      ​	*ex)* *예측 시행 요일* : 토요일 	- - >	*예측 목표 날* : 그 다음주 금요일

    </br>

  - **예측 단계 및 정보 제공**

    1. 예측 시행날 기준 1일 후(월요일)의 KOSPI 예측 지수 저장
    2. 1 에서 얻은 예측 지수를 기존의 데이터에 합하여 2일 후(화요일)값 예측 가능
    3. 1, 2 의 과정을 5번 반복하여 5일 후(금요일)의 지수 예측
    4. 이번주의 마지막 지수(이번주 금요일) 5일후의 지수(다음주 금요일)와 비교하여 0 or 1로 등락 표현

  </br>

- > ### 서버 내 모델 실행코드

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

  

</br>

---

## Android Studio

- Main 탭

  - 차트

    - Main 탭의 차트 구성 부분
    - Firestore DB에서 가져온 09년 부터의 데이터를 차트로 표현
    - 차트 위에 5일 후의 KOSPI지수 등락을 색이 있는 정삼각형, 역삼각형으로 표현

    ```java
    private void showChart() {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference docRef = db.collection("data").document("dailykospi_android");
    
            ArrayList<String> real_date = new ArrayList<String>();
            ArrayList<Float> real_kospi_index = new ArrayList<Float>();
    
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() && getActivity()!=null) {
                        DocumentSnapshot document = task.getResult();
                        real_date.clear();
                        real_kospi_index.clear();
    
                        if (document.exists()) {
                            HashMap<String,Object> kospi = new HashMap<String,Object>(document.getData());
    
                            Object[] mapkey = kospi.keySet().toArray();
                            Arrays.sort(mapkey);
    
                            int count = 0;
    
                            for(Object nkey : mapkey) {
                                real_date.add(count, nkey.toString());
                                real_kospi_index.add(count, Float.valueOf(kospi.get(nkey).toString()));
                                count++;
                            }
                        } else {
                            Log.d("", "No such document");
                        }
                    } else {
                        Log.d("", "get failed with ", task.getException());
                    }
                }
            });
    
            DocumentReference docRef3 = db.collection("data").document("origin");
    
            docRef3.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful() && getActivity() != null) {
                        DocumentSnapshot document = task.getResult();
                        String date1 = new String();
                        String up_down1 = new String();
    
                        if (document.exists()) {
                            HashMap<String, Object> kospi = new HashMap<String, Object>(document.getData());
    
                            Object[] mapkey = kospi.keySet().toArray();
                            Arrays.sort(mapkey);
    
                            for (Object nkey : mapkey) {
                                date1 = nkey.toString();
                                up_down1 = kospi.get(nkey).toString();
                            }
    
                            what = new TextView(getContext());
                            what.setText(Html.fromHtml("[예측] " + date1 + " "  + "<FONT color=" + "#FF9800" + ">" + up_down1 + "</FONT>"));
                           }
                    }
                }
            });
    
            DocumentReference docRef2 = db.collection("data").document("predictedkospi_test");
    
            docRef2.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>(){
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task){
                    if (task.isSuccessful() && getActivity()!=null) {
                        DocumentSnapshot document = task.getResult();
                        String date = new String();
                        String up_down = new String();
                        TextView show = (TextView)viewGroup.findViewById(R.id.show_predict);
    
                        if (document.exists()) {
                            HashMap<String,Object> kospi = new HashMap<String,Object>(document.getData());
    
                            Object[] mapkey = kospi.keySet().toArray();
                            Arrays.sort(mapkey);
    
                            for(Object nkey : mapkey) {
                                date = nkey.toString();
                                up_down = kospi.get(nkey).toString();
                            }
    
    
    
                            if (up_down.equals("1")){
                                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                    @Override
                                    public Drawable getDrawable(String source) {
                                        if (source.equals("up_triangle")){
                                            Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                            drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                            return drawable;
                                        }
                                        return null;
                                    }
                                };
                                Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
    
                                if (real_date.size()>0){
                                    show.setText(what.getText());
                                    show.append(" 기준 : " + date + " (");
                                    show.append(htmlText);
                                    show.append(")");
                                    show.setTextColor(Color.parseColor("#FF555555"));
                                }
                            }
                            else if (up_down.equals("0")){
                                Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                    @Override
                                    public Drawable getDrawable(String source) {
                                        if (source.equals("down_triangle")){
                                            Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                            drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                            return drawable;
                                        }
                                        return null;
                                    }
                                };
                                Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
    
                                if (real_date.size()>0){
                                    show.setText(what.getText());
                                    show.append(" 기준 : " + date + " (");
                                    show.append(htmlText);
                                    show.append(")");
                                    show.setTextColor(Color.parseColor("#FF555555"));
                                }
                            }
    
                            //차트 생성
                            ArrayList<Entry> values = new ArrayList<>();
                            for (int i=0;i<real_kospi_index.size();i++){
                                values.add(new Entry(real_kospi_index.get(i), i));
                            }
    
                            LineDataSet lineDataSet = new LineDataSet(values, "KOSPI");
                            lineDataSet.setColor(ContextCompat.getColor(getContext(), R.color.red));
                            lineDataSet.setDrawCircles(false);
                            lineDataSet.setValueTextSize(10);
    
                            String[] xaxes = new String[real_date.size()];
                            for (int i=0;i<real_date.size();i++)
                                xaxes[i] = real_date.get(i);
    
                            XAxis xAxis = lineChart.getXAxis();
                            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                            xAxis.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_500));
                            xAxis.setTextSize(11);
    
                            YAxis yAxisLeft = lineChart.getAxisLeft();
                            yAxisLeft.setDrawLabels(false);
                            yAxisLeft.setDrawAxisLine(false);
                            yAxisLeft.setDrawGridLines(false);
    
                            YAxis yAxisRight = lineChart.getAxisRight();
                            yAxisRight.setTextColor(ContextCompat.getColor(getContext(), R.color.purple_700));
                            yAxisRight.setTextSize(13);
    
                            lineChart.setDescription(null);
                            lineChart.setTouchEnabled(true);
    
                            lineChart.getAxisLeft().setLabelCount(10, true);
                            lineChart.getAxisRight().setLabelCount(3,true);
    
                            Legend legend = lineChart.getLegend();
                            legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
                            legend.setTextColor(ContextCompat.getColor(getContext(), R.color.red));
    
                            lineChart.setData(new LineData(xaxes, lineDataSet));
                            lineChart.invalidate();
                        } else {
                            Log.d("", "No such document");
                        }
                    } else {
                        Log.d("", "get failed with ", task.getException());
                    }
                }
            });
    
            if (getActivity()!=null && real_date==null)
                showChart();
        }
    ```

    

  - 실시간 지수

    - Main 탭의 실시간 해외 주요 주가지수 표현 부분

    - '[네이버 시세](https://finance.naver.com/world/)' 웹사이트를 사용하여 실시간 지수를 크롤링

      ```java
      public void get_data(){
              if (getActivity()!=null){
                  try{
                      ndaq_arraylist.clear();
      
                      Document doc1 = Jsoup.connect(ndaq_url).get();
                      Elements contents1 = doc1.select("td[class=tb_td2]");
      
                      for (Element e : contents1){
                          String content = e.select("span").text();
                          ndaq_arraylist.add(content);
                      }
      
                      spy_arraylist.clear();
      
                      Document doc2 = Jsoup.connect(spy_url).get();
                      Elements contents2 = doc2.select("td[class=tb_td2]");
      
                      for (Element e : contents2){
                          String content = e.select("span").text();
                          spy_arraylist.add(content);
                      }
      
                      dji_arraylist.clear();
      
                      Document doc3 = Jsoup.connect(dji_url).get();
                      Elements contents3 = doc3.select("td[class=tb_td2]");
      
                      for (Element e : contents3){
                          String content = e.select("span").text();
                          dji_arraylist.add(content);
                      }
      
                      stoxx50_arraylist.clear();
      
                      Document doc4 = Jsoup.connect(stoxx50_url).get();
                      Elements contents4 = doc4.select("td[class=tb_td2]");
      
                      for (Element e : contents4){
                          String content = e.select("span").text();
                          stoxx50_arraylist.add(content);
                      }
      
                      topix_arraylist.clear();
      
                      Document doc5 = Jsoup.connect(topix_url).get();
                      Elements contents5 = doc5.select("dl[class=blind]");
      
                      for (Element e : contents5){
                          String content = e.select("dd").text();
                          topix_arraylist.add(content);
                      }
      
                      nikkei_arraylist.clear();
      
                      Document doc6 = Jsoup.connect(nikkei_url).get();
                      Elements contents6 = doc6.select("td[class=tb_td2]");
      
                      for (Element e : contents6){
                          String content = e.select("span").text();
                          nikkei_arraylist.add(content);
                      }
      
                      ssec_arraylist.clear();
      
                      Document doc7 = Jsoup.connect(ssec_url).get();
                      Elements contents7 = doc7.select("td[class=tb_td2]");
      
                      for (Element e : contents7){
                          String content = e.select("span").text();
                          ssec_arraylist.add(content);
                      }
      
                      hsi_arraylist.clear();
      
                      Document doc8 = Jsoup.connect(hsi_url).get();
                      Elements contents8 = doc8.select("td[class=tb_td2]");
      
                      for (Element e : contents8){
                          String content = e.select("span").text();
                          hsi_arraylist.add(content);
                      }
                  }catch(IOException err){
                      err.printStackTrace();
                  }
              }
      
              if (getActivity()!=null && ndaq_arraylist!=null && spy_arraylist!=null && dji_arraylist!=null && stoxx50_arraylist!=null && topix_arraylist!=null && nikkei_arraylist!=null && ssec_arraylist!=null && hsi_arraylist!=null
              && ndaq_arraylist.size()!=0 && spy_arraylist.size()!=0 && dji_arraylist.size()!=0 && stoxx50_arraylist.size()!=0 && topix_arraylist.size()!=0 && nikkei_arraylist.size()!=0 && ssec_arraylist.size()!=0 && hsi_arraylist.size()!=0){
                  this.getActivity().runOnUiThread(new Runnable(){
                      @Override
                      public void run(){
                          String temp1 = ndaq_arraylist.get(0);
                          String temp1_1 = temp1.replace(",", "");
                          String temp1_2 = ndaq_arraylist.get(1);
                          temp1_2 = temp1_2.replace(",", "");
                          float compare1 = Float.parseFloat(temp1_1) - Float.parseFloat(temp1_2);
      
                          if (compare1>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              ndaq_textview.setText(temp1 + "(" );
                              ndaq_textview.append(htmlText);
                              ndaq_textview.append(")");
                          }
                          else if (compare1<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              ndaq_textview.setText(temp1 + "(" );
                              ndaq_textview.append(htmlText);
                              ndaq_textview.append(")");
                          }
                          else
                              ndaq_textview.setText(temp1 + "( - )");
      
                          String temp2 = spy_arraylist.get(0);
                          String temp2_1 = temp2.replace(",", "");
                          String temp2_2 = spy_arraylist.get(1);
                          temp2_2 = temp2_2.replace(",", "");
                          float compare2 = Float.parseFloat(temp2_1) - Float.parseFloat(temp2_2);
      
                          if (compare2>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              spy_textview.setText(temp2 + "(" );
                              spy_textview.append(htmlText);
                              spy_textview.append(")");
                          }
                          else if (compare2<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              spy_textview.setText(temp2 + "(" );
                              spy_textview.append(htmlText);
                              spy_textview.append(")");
                          }
                          else
                              spy_textview.setText(temp2 + "( - )");
      
                          String temp3 = dji_arraylist.get(0);
                          String temp3_1 = temp3.replace(",", "");
                          String temp3_2 = dji_arraylist.get(1);
                          temp3_2 = temp3_2.replace(",", "");
                          float compare3 = Float.parseFloat(temp3_1) - Float.parseFloat(temp3_2);
      
                          if (compare3>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              dji_textview.setText(temp3 + "(" );
                              dji_textview.append(htmlText);
                              dji_textview.append(")");
                          }
                          else if (compare3<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              dji_textview.setText(temp3 + "(" );
                              dji_textview.append(htmlText);
                              dji_textview.append(")");
                          }
                          else
                              dji_textview.setText(temp3 + "( - )");
      
                          String temp4 = stoxx50_arraylist.get(0);
                          String temp4_1 = temp4.replace(",", "");
                          String temp4_2 = stoxx50_arraylist.get(1);
                          temp4_2 = temp4_2.replace(",", "");
                          float compare4 = Float.parseFloat(temp4_1) - Float.parseFloat(temp4_2);
      
                          if (compare4>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              stoxx50_textview.setText(temp4 + "(" );
                              stoxx50_textview.append(htmlText);
                              stoxx50_textview.append(")");
                          }
                          else if (compare4<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              stoxx50_textview.setText(temp4 + "(" );
                              stoxx50_textview.append(htmlText);
                              stoxx50_textview.append(")");
                          }
                          else
                              stoxx50_textview.setText(temp4 + "( - )");
      
                          String temp5_0 = topix_arraylist.get(0);
                          String[] temp5_00 = temp5_0.split(" ");
                          String temp5 = temp5_00[14];
                          String temp5_1 = temp5.replace(",", "");
                          String temp5_2 = temp5_00[22];
                          temp5_2 = temp5_2.replace(",", "");
                          float compare5 = Float.parseFloat(temp5_1) - Float.parseFloat(temp5_2);
      
                          if (compare5>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              topix_textview.setText(temp5 + "(" );
                              topix_textview.append(htmlText);
                              topix_textview.append(")");
                          }
                          else if (compare5<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              topix_textview.setText(temp5 + "(" );
                              topix_textview.append(htmlText);
                              topix_textview.append(")");
                          }
                          else
                              topix_textview.setText(temp5 + "( - )");
      
                          String temp6 = nikkei_arraylist.get(0);
                          String temp6_1 = temp6.replace(",", "");
                          String temp6_2 = nikkei_arraylist.get(1);
                          temp6_2 = temp6_2.replace(",", "");
                          float compare6 = Float.parseFloat(temp6_1) - Float.parseFloat(temp6_2);
      
                          if (compare6>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              nikkei_textview.setText(temp6 + "(" );
                              nikkei_textview.append(htmlText);
                              nikkei_textview.append(")");
                          }
                          else if (compare6<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              nikkei_textview.setText(temp6 + "(" );
                              nikkei_textview.append(htmlText);
                              nikkei_textview.append(")");
                          }
                          else
                              nikkei_textview.setText(temp6 + "( - )");
      
                          String temp7 = ssec_arraylist.get(0);
                          String temp7_1 = temp7.replace(",", "");
                          String temp7_2 = ssec_arraylist.get(1);
                          temp7_2 = temp7_2.replace(",", "");
                          float compare7 = Float.parseFloat(temp7_1) - Float.parseFloat(temp7_2);
      
                          if (compare7>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              ssec_textview.setText(temp7 + "(" );
                              ssec_textview.append(htmlText);
                              ssec_textview.append(")");
                          }
                          else if (compare7<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              ssec_textview.setText(temp7 + "(" );
                              ssec_textview.append(htmlText);
                              ssec_textview.append(")");
                          }
                          else
                              ssec_textview.setText(temp7 + "( - )");
      
                          String temp8 = hsi_arraylist.get(0);
                          String temp8_1 = temp8.replace(",", "");
                          String temp8_2 = hsi_arraylist.get(1);
                          temp8_2 = temp8_2.replace(",", "");
                          float compare8 = Float.parseFloat(temp8_1) - Float.parseFloat(temp8_2);
      
                          if (compare8>0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("up_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.up_triangle);
                                          drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"up_triangle\" width=20 height=20>", imageGetter, null);
      
                              hsi_textview.setText(temp8 + "(" );
                              hsi_textview.append(htmlText);
                              hsi_textview.append(")");
                          }
                          else if (compare8<0){
                              Html.ImageGetter imageGetter = new Html.ImageGetter() {
                                  @Override
                                  public Drawable getDrawable(String source) {
                                      if (source.equals("down_triangle")){
                                          Drawable drawable = getResources().getDrawable(R.drawable.down_triangle);
                                          drawable.setBounds( 0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()*5/13);
                                          return drawable;
                                      }
                                      return null;
                                  }
                              };
                              Spanned htmlText = Html.fromHtml("<img src=\"down_triangle\" width=20 height=20>", imageGetter, null);
      
                              hsi_textview.setText(temp8 + "(" );
                              hsi_textview.append(htmlText);
                              hsi_textview.append(")");
                          }
                          else
                              hsi_textview.setText(temp8 + "( - )");
                      }
                  });
              }
          }
      ```

      </br>

- News 탭

  - [매일경제 국내 증권 뉴스](https://vip.mk.co.kr/newSt/news/news_list.php?sCode=21)와 [매일경제 해외 증권 뉴스](https://vip.mk.co.kr/newSt/news/news_list.php?sCode=108) 웹사이트의 기사를 크롤링

  - 국내 뉴스 기사를 가져오는 코드

    ```java
    class task1 extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                if (getActivity()!=null) {
                    try {
                        listView.setVisibility(View.INVISIBLE);
    
                        contentList.clear();
                        titleList.clear();
    
                        Document doc = Jsoup.connect(url1).get();
                        Elements titles = doc.select("td[class=title]");
    
                        for (Element e : titles) {
                            String news_title = e.select("a").text();
                            String news_link = e.select("a").attr("href");
                            contentList.add(news_link);
                            titleList.add(news_title);
                        }
                    } catch (IOException err1) {
                        err1.printStackTrace();
                    }
                }
    
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            listView.setVisibility(View.VISIBLE);
                        }
                    });
                }
    
                return null;
            }
    
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
        }
    ```

    </br>

  - 해외 뉴스 기사를 가져오는 코드

    ```java
    class task2 extends AsyncTask<Void, Void, Void> {
            @Override
            protected Void doInBackground(Void... voids) {
                if (getActivity()!=null) {
                    try {
                        listView.setVisibility(View.INVISIBLE);
    
                        contentList.clear();
                        titleList.clear();
    
                        Document doc = Jsoup.connect(url2).get();
                        Elements titles = doc.select("td[class=title]");
    
                        for (Element e : titles) {
                            String news_title = e.select("a").text();
                            String news_link = e.select("a").attr("href");
                            contentList.add(news_link);
                            titleList.add(news_title);
                        }
                    } catch (IOException err1) {
                        err1.printStackTrace();
                    }
                }
    
                if (getActivity()!=null){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();
                            listView.setVisibility(View.VISIBLE);
                        }
                    });
                }
    
                return null;
            }
    
            @Override
            protected void onPreExecute() { super.onPreExecute(); }
        }
    ```

    </br>

- Event 탭

  - KOSPI 시가총액 100개의 실시간 주가 정보를 나타냄

  - 주가 정보는 '[네이버 시세](https://finance.naver.com/item/main.nhn?code)' 웹사이트에서 크롤링 하였음

  - 1초마다 주가 정보가 갱신되도록 설정

    ```java
    public class List_Window extends Fragment {
        ViewGroup viewGroup;
    
        Timer timer;
        TimerTask t;
        ExpandableListView listView;
        CustomAdapter adapter;
        ArrayList<GroupData> groupListDatas;
        ArrayList<ArrayList<ChildData>> childListDatas;
        private List<String> list;
        private EditText editSearch;
        int nowPosition;
        static Resources list_resource;
        String[] list_title = {"삼성전자", "SK하이닉스", "LG화학", "NAVER", "삼성바이오로직스", "카카오", "현대차", "삼성SDI", "셀트리온",
                "기아", "POSCO", "현대모비스", "LG전자", "삼성물산", "SK텔레콤", "LG생활건강", "SK이노베이션", "KB금융", "신한지주",
                "SK", "엔씨소프트", "삼성생명", "아모레퍼시픽", "한국전력", "삼성에스디에스", "삼성전기", "하나금융지주", "HMM", "KT&G",
                "포스코케미칼", "넷마블", "한국조선해양", "S-Oil", "롯데케미칼", "삼성화재", "대한항공", "하이브", "한온시스템", "한화솔루션",
                "LG디스플레이", "고려아연", "SK바이오팜", "금호석유", "우리금융지주", "KT", "현대제철", "현대글로비스", "기업은행", "미래에셋증권",
                "CJ제일제당", "한국타이어앤테크놀로", "한국금융지주", "아모레G", "LG유플러스", "현대중공업지주", "현대건설", "강원랜드", "코웨이", "SKC",
                "두산중공업", "이마트", "삼성중공업", "두산밥캣", "오리온", "LG이노텍", "맥쿼리인프라", "한미사이언스", "유한양행", "대우조선해양",
                "GS", "녹십자", "삼성카드", "한미약품", "쌍용C&E", "CJ대한통운", "GS건설", "삼성증권", "롯데지주", "호텔신라",
                "DB손해보험", "삼성엔지니어링", "롯데쇼핑", "NH투자증권", "한진칼", "키움증권", "신풍제약", "한국항공우주", "에스원", "일진머티리얼즈",
                "한국가스공사", "동서", "SK케미칼", "만도", "CJ", "휠라홀딩스", "GS리테일", "더존비즈온", "두산퓨얼셀", "대웅"};
        String[] list_code = {"005930", "000660", "051910", "035420", "207940", "035720", "005380", "006400", "068270",
                "000270", "005490", "012330", "066570", "028260", "017670", "051900", "096770", "105560", "055550",
                "034730", "036570", "032830", "090430", "015760", "018260", "009150", "086790", "011200", "033780",
                "003670", "251270", "009540", "010950", "011170", "000810", "003490", "352820", "018880", "009830",
                "034220", "010130", "326030", "011780", "316140", "030200", "004020", "086280", "024110", "006800",
                "097950", "161390", "071050", "002790", "032640", "267250", "000720", "035250", "021240", "011790",
                "034020", "139480", "010140", "241560", "271560", "011070", "088980", "008930", "000100", "042660",
                "078930", "006280", "029780", "128940", "003410", "000120", "006360", "016360", "004990", "008770",
                "005830", "028050", "023530", "005940", "180640", "039490", "019170", "047810", "012750", "020150",
                "036460", "026960", "285130", "204320", "001040", "081660", "007070", "012510", "336260", "003090"};
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            viewGroup = (ViewGroup) inflater.inflate(R.layout.list_fragment, container, false);
            groupListDatas = new ArrayList<GroupData>();
            childListDatas = new ArrayList<ArrayList<ChildData>>();
            list = new ArrayList<String>();
            list_resource = getResources();
    
            listView = (ExpandableListView)viewGroup.findViewById(R.id.all_list);
    
            settingList();
            Collections.sort(list, cmpAsc);
            setData();
    
            adapter = new CustomAdapter(container.getContext(), groupListDatas, childListDatas);
            listView.setAdapter(adapter);
    
            listView.setGroupIndicator(null);
    
            listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id){
                    nowPosition = groupPosition;
                    tempTask();
    
                    return false;
                }
            });
    
            listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
                @Override
                public void onGroupExpand(int groupPosition){
                    int groupCount = adapter.getGroupCount();
                    for (int i=0;i<groupCount;i++){
                        if (!(i==groupPosition)) {
                            listView.collapseGroup(i);
                        }
                    }
                }
            });
    
            editSearch = (EditText) viewGroup.findViewById(R.id.edit_search);
            editSearch.setBackgroundColor(getResources().getColor(R.color.white));
    
            editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
    
                    //t.cancel();
    
                    for (int i=0;i<list_title.length;i++)
                        listView.collapseGroup(i);
                }
    
                @Override
                public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    String text = editSearch.getText().toString();
                    search(text);
                }
    
                @Override
                public void afterTextChanged(Editable editable) { }
            });
    
            return viewGroup;
        }
    
        public void tempTask(){
            if (getActivity()!=null){
                t = new TimerTask(){
                    @Override
                    public void run(){
                        get_real_time_data();
                    }
                };
                timer = new Timer();
                timer.schedule(t, 0, 1000);
            }
        }
    
        public void get_real_time_data(){
            String[] temp = new String[15];
            String url = "https://finance.naver.com/item/main.nhn?code=";
    
            if (getActivity() != null && nowPosition<adapter.getGroupCount() && nowPosition>=0 && adapter.getGroupCount()!=0) {
                try {
                    for (int i = 0; i < list_title.length; i++) {
                        if (list.get(nowPosition).equals(list_title[i])) {
                            url = url + list_code[i];
                            break;
                        }
                    }
                    int size = 0;
    
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.select("dl[class=blind]");
                    Elements elements1 = elements.select("dd");
    
                    for (Element e : elements1) {
                        temp[size] = e.text();
                        size++;
                    }
    
    
                } catch (IOException err) {
                    err.printStackTrace();
                }
            }
    
            if (getActivity()!=null && nowPosition<adapter.getGroupCount() && nowPosition>=0 && adapter.getGroupCount()!=0){
                this.getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        String[] temp1 = temp[3].split(" ");
                        String[] temp2 = temp[4].split(" ");
                        String[] temp3 = temp[5].split(" ");
                        String[] temp4 = temp[6].split(" ");
                        String[] temp5 = temp[7].split(" ");
                        String[] temp6 = temp[8].split(" ");
                        String[] temp7 = temp[9].split(" ");
                        String[] temp8 = temp[10].split(" ");
                        String[] temp9 = temp[11].split(" ");
    
                        childListDatas.get(nowPosition).clear();
                        childListDatas.get(nowPosition).add(new ChildData(temp1[1],temp1[3] + " " + temp1[4],temp2[1],temp3[1],temp4[1],temp6[1],temp5[1],temp7[1],temp8[1],temp9[1]));
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    
        private void setData(){
            if (getActivity()!=null){
                for(int i=0;i<list.size();i++) {
                    groupListDatas.add(new GroupData(list.get(i)));
                    childListDatas.add(new ArrayList<ChildData>());
                    childListDatas.get(i).add(new ChildData("wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait"));
                }
            }
        }
    
        Comparator<String> cmpAsc = new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) { return o1.compareTo(o2); }
        };
    
        private void settingList() {
            if (getActivity()!=null){
                list.clear();
    
                for (int i=0;i<list_title.length;i++){
                    list.add(list_title[i]);
                }
            }
        }
    
        public void search(String charText) {
            if (getActivity()!=null){
                charText = charText.toLowerCase(Locale.getDefault());
    
                list.clear();
                childListDatas.clear();
                groupListDatas.clear();
    
                if (charText.length() == 0){
                    settingList();
                }
    
                else {
                    for(int i = 0;i<list_title.length;i++) {
                        if (list_title[i].toLowerCase().contains(charText))
                            list.add(list_title[i]);
                    }
                }
                Collections.sort(list, cmpAsc);
                setData();
                adapter.notifyDataSetChanged();
            }
        }
    }
    ```

    </br>

- My 탭

  - Event 탭에서 선택해서 담은 종목들을 관리할 수 있는 탭

    ```java
    public class My_Window extends Fragment{
        ViewGroup viewGroup;
    
        static Timer timer;
        static ExpandableListView listView;
        static My_CustomAdapter adapter;
        static ArrayList<GroupData> groupListDatas;
        static ArrayList<ArrayList<ChildData>> childListDatas;
        static List<String> list;
        static int nowPosition;
    
        static public ArrayList<String> my_list_title = new ArrayList<String>();
        static public ArrayList<String> my_list_code = new ArrayList<String>();
        static public ArrayList<String> temp_title = new ArrayList<String>();
        static public ArrayList<String> temp_code = new ArrayList<String>();
    
        int cnt = 0;
        static Context ctt;
        static Activity act;
        static Resources my_resource;
    
        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
            viewGroup=(ViewGroup) inflater.inflate(R.layout.my_fragment, container, false);
            groupListDatas = new ArrayList<GroupData>();
            childListDatas = new ArrayList<ArrayList<ChildData>>();
            list = new ArrayList<String>();
            ctt = getContext();
            act = getActivity();
            my_resource = getResources();
    
    
            if (temp_title.size()!=0){
                for (int i=0;i<temp_title.size();i++){
                    my_list_title.add(temp_title.get(i));
                    my_list_code.add(temp_code.get(i));
                }
    
                temp_title.clear();
                temp_code.clear();
    
                SaveTitleData(getContext(), my_list_title);
                SaveCodeData(getContext(), my_list_code);
                my_list_title = ReadTitleData(getContext());
                my_list_code = ReadCodeData(getContext());
            }
            else{
                my_list_title = ReadTitleData(getContext());
                my_list_code = ReadCodeData(getContext());
            }
    
            listView = (ExpandableListView)viewGroup.findViewById(R.id.my_list);
            settingList();
            setData();
    
            adapter = new My_CustomAdapter(getContext(), groupListDatas, childListDatas);
            listView.setAdapter(adapter);
    
            listView.setGroupIndicator(null);
    
            listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener(){
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id){
                    nowPosition = groupPosition;
                    tempTask();
    
                    return false;
                }
            });
    
            listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener(){
                @Override
                public void onGroupExpand(int groupPosition){
                    int groupCount = adapter.getGroupCount();
                    for (int i=0;i<groupCount;i++){
                        if (!(i==groupPosition)) {
                            listView.collapseGroup(i);
                        }
                    }
                }
            });
    
            return viewGroup;
        }
    
        public static Context getAppContext(){
            return ctt;
        }
    
        public void tempTask(){
            if (getActivity()!=null){
                TimerTask t = new TimerTask(){
                    @Override
                    public void run(){
                        get_real_time_data();
                    }
                };
                timer = new Timer();
                timer.schedule(t, 0, 1000);
            }
        }
    
        public void get_real_time_data(){
            String[] temp = new String[15];
            String url = "https://finance.naver.com/item/main.nhn?code=";
    
            if (getActivity()!=null && nowPosition<my_list_title.size() && nowPosition>=0 && adapter.getGroupCount()!=0){
                try{
                    for(int i=0;i<my_list_title.size();i++){
                        if (list.get(nowPosition).equals(my_list_title.get(i))) {
                            url = url + my_list_code.get(i);
                            break;
                        }
                    }
                    int size = 0;
    
                    Document doc = Jsoup.connect(url).get();
                    Elements elements = doc.select("dl[class=blind]");
                    Elements elements1 = elements.select("dd");
    
                    for (Element e : elements1){
                        temp[size] = e.text();
                        size++;
                    }
    
    
                }catch(IOException err){
                    err.printStackTrace();
                }
            }
    
            if (getActivity()!=null && temp!=null && nowPosition<my_list_title.size() && nowPosition>=0 && adapter.getGroupCount()!=0){
                this.getActivity().runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        String[] temp1 = temp[3].split(" ");
                        String[] temp2 = temp[4].split(" ");
                        String[] temp3 = temp[5].split(" ");
                        String[] temp4 = temp[6].split(" ");
                        String[] temp5 = temp[7].split(" ");
                        String[] temp6 = temp[8].split(" ");
                        String[] temp7 = temp[9].split(" ");
                        String[] temp8 = temp[10].split(" ");
                        String[] temp9 = temp[11].split(" ");
    
                        childListDatas.get(nowPosition).clear();
                        childListDatas.get(nowPosition).add(new ChildData(temp1[1],temp1[3] + " " + temp1[4],temp2[1],temp3[1],temp4[1],temp6[1],temp5[1],temp7[1],temp8[1],temp9[1]));
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    
        static void setData(){
            if (act!=null){
                groupListDatas.clear();
                childListDatas.clear();
    
                for(int i=0;i<list.size();i++) {
                    groupListDatas.add(new GroupData(list.get(i)));
                    childListDatas.add(new ArrayList<ChildData>());
                    childListDatas.get(i).add(new ChildData("wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait", "wait"));
                }
            }
        }
    
        static void settingList() {
            if (act!=null){
                list.clear();
                my_list_code.clear();
    
                list = ReadTitleData(ctt);
                my_list_title = ReadTitleData(ctt);
                my_list_code = ReadCodeData(ctt);
            }
        }
    
        static void SaveTitleData(Context context, ArrayList<String> my_list_title){
            if (act!=null){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                JSONArray a = new JSONArray();
    
                for (int i=0;i<my_list_title.size();i++){
                    a.put(my_list_title.get(i));
                }
    
                if (!my_list_title.isEmpty()){
                    editor.putString("titles_json", a.toString());
                }
                else{
                    editor.putString("titles_json", null);
                }
    
                editor.apply();
            }
        }
    
        static void SaveCodeData(Context context, ArrayList<String> my_list_code){
            if (act!=null){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor editor = preferences.edit();
                JSONArray a = new JSONArray();
    
                for (int i=0;i<my_list_code.size();i++){
                    a.put(my_list_code.get(i));
                }
    
                if (!my_list_code.isEmpty()){
                    editor.putString("codes_json", a.toString());
                }
                else{
                    editor.putString("codes_json", null);
                }
    
                editor.apply();
            }
        }
    
        static ArrayList<String> ReadTitleData(Context context){
            if (act!=null){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String json = preferences.getString("titles_json", null);
                ArrayList<String> temp = new ArrayList<String>();
    
                if (json!=null){
                    try{
                        JSONArray a = new JSONArray(json);
    
                        for (int i=0;i<a.length();i++){
                            String tmp = a.optString(i);
                            temp.add(tmp);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
    
                return temp;
            }
    
            return null;
        }
    
        static ArrayList<String> ReadCodeData(Context context){
            if (act!=null){
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
                String json = preferences.getString("codes_json", null);
                ArrayList<String> temp = new ArrayList<String>();
    
                if (json!=null){
                    try{
                        JSONArray a = new JSONArray(json);
    
                        for (int i=0;i<a.length();i++){
                            String tmp = a.optString(i);
                            temp.add(tmp);
                        }
                    }catch(JSONException e){
                        e.printStackTrace();
                    }
                }
    
                return temp;
            }
    
            return null;
        }
    }
    ```

    </br>

---

## Server & DB

- > ### **Server** & **DB**가 필요한 이유

**Server** : KOSPI 지수 예측에 필요한 데이터를 수집, DB 데이터 갱신

**DB** : KOSPI 지수 예측에 필요한 데이터, KOSPI 차트 표현에 필요한 데이터, 지수 등락 예측 데이터 저장

*위의 기능들을 정해진 시간에 자동으로 수행하기 위함*

</br>

- > ### **Server** & **DB** 실행

  - **Server와 DB의 실행 과정**

    1. [모델예측에 필요한 데이터 수집](#모델-예측에-필요한-데이터-수집)
    2. [DB 갱신](#db-데이터-갱신)
    3. [예측 모델 실행](#예측-모델-실행)
    4. [DB에 예측 결과 값 저장](#KOSPI-등락-예측-정보-DB에-저장)

    </br>

    

  1. ####  모델 예측에 필요한 데이터 수집

     데이터 수집 방법은 두 가지로 **웹페이지 크롤링**과 제공되는 **API  활용**

     - > ### 크롤링(crawling)

       '[네이버 증권'](https://finance.naver.com/) 웹페이지 크롤링을 통해 각국의 주가지수, 유가 등의 정보 획득

       </br>

     ```python
     ##---------<crawling.py>----------##
     
     # -*- coding: utf-8 -*-
     from bs4 import BeautifulSoup
     from datetime import datetime
     import requests
     import time
     print('<-----crawling.py import complete------------------>')
     
     def get_kospi_daebiupdown(): ##kospi 대비, 등락률
     
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

     - > ### API

       [한국은행 경제통계시스템 : ecos API](https://ecos.bok.or.kr/jsp/openapi/OpenApiController.jsp) - '환율' 

       ['GitHub 오픈소스 모듈 : PyKrx'](https://github.com/sharebook-kr/pykrx) - '시가', '저가', '고가', '종가', '거래량', '각 기관들의 KOSPI 시장 순매수금'

       </br>

       위의 두 모듈을 사용하여 해당 데이터 획득

       </br>

     ```python
     ##---------<use_ecos.py>----------##
     
     # 한국은행의 환율 정보를 가져오는데 사용
     
     # -*- coding: utf-8 -*-
     import requests 
     import xml.etree.ElementTree as ET 
     from datetime import datetime
     from datetime import timedelta
     import time
     import isHoliday
     print('<-----use_ecos.py import complete----------------->')
     key = 'RHXCFZZB7E5KGJLPID'
     
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

     ```python
     ##---------<kospidetail.py>----------##
     
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
         open = df.iloc[0, 0] # 시가
         high = df.iloc[0, 1] # 고가
         low = df.iloc[0, 2] # 저가
         close = df.iloc[0, 3] # 종가
         volume = int(df.iloc[0, 4] * 0.001) # 거래량
         return open,high,low,close,volume
         
     def get_trading_value():
         day = (datetime.today()).strftime("%Y%m%d")###########################
         df = stock.get_market_trading_value_by_date(day, day, "KOSPI")
         association = int(df.iloc[0,0] * 0.000001) # 기관 순매수금
         person = int(df.iloc[0,2]* 0.000001) # 개인 순매수금
         foreign = int(df.iloc[0,3]* 0.000001) # 외국인 순매수금
         
         df = stock.get_market_trading_value_by_date(day, day, "KOSPI",detail=True)
         pension = int(df.iloc[0,6]* 0.000001) # 연기금 순매수금
         
         return association,foreign,person,pension
     ```

     </br>

  2. #### DB 데이터 갱신

     - 1.에서 '크롤링'과 'API'를 사용하여 수집한 데이터는 일별데이터이므로 매일 DB를 갱신하는 코드를 작성

     - 데이터가 바뀌는 시간 또한 다르기 때문에 (각 국가의 장 마감시간의 차이) 시간차를 두고 DB갱신을 1,2차로 설정

       *ex) 5/29 (목)의 데이터 수집*

       1차 : 환율 데이터 갱신(한국시간 기준 5/29 (목) 16시)

       2차 : 환율 이외의 값 갱신(한국시간 기준 5/30(금) 8시)

     </br>

     ```python
     ##---------<fb.py>----------##
     
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

     위의 코드에 추가로 Application UI에 표현될 KOSPI 차트에 필요한 '지수'데이터 또한 갱신

     ```python
     def fb_update_daioykospi_android(today, kospi): ## KOSPI 차트표현에 필요한 데이터 갱신
         print('<-----안드로이드차트용 kospi지수 갱신중----------->')
     
         doc_ref = db.collection(u'data').document(u'dailykospi_android')
         doc_ref.set({today : kospi}, merge = True) #DB에 금일 날짜와 KOSPI 지수 추가
         print('<-----안드로이드차트용 kospi지수 갱신완료--------->')
     ```

     </br>

     아래 사진은 갱신된 FireBase DB(예측에 필요한 데이터 : dailydata // UI에 쓰이는 데이터 : dailykospi_android)

     

     <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/firebase_2.png?raw=true">

     </br>

     <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/dailykospi.PNG?raw=true">

     </br>

  3. #### 예측 모델 실행

     </br>

     가중치값들이 저장되어있는 모델 파일(파일명 : 75_LSTM.h5)을 실행해서 예측값을 return받는 모듈

     ```python
     ## 모델파일 실행 모듈
     ##---------<model.py>----------##
     
     ## 머신러닝 라이브러리 tensorflow, keras import
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
             if i == 'date': continue
             model_data[i] = MinMaxScaler().fit_transform(model_data[i].values.reshape(-1, 1)).round(4)
     
         feature_cols = ['volume','shanghai','dji', 'nikkei', 'hsi', 'won/US dollar', 'won/100en', 'won/euro','association','person','daebi',
            'EMA_close5', 'EMA_close10', 'EMA_close20', 'EMA_close60', 'EMA_close120', 'disp5', 'updown','after4days_close']
         label_cols = ['close']
     
     	###### 데이터셋 준비 완료
         
         base_dir = '' #모델 저장 위치 (서버용)
         file_name = '75_LSTM.h5'  #모델 이름(파일명)
         dir = os.path.join(base_dir, file_name)
         model = load_model(dir)  #저장했던 모델 (dir에 있는거) 불러오는 코드
     
         print('model 불러오기 완료')
     	
         '''
         find_pred 
         : 다음날 예측을 5번 돌려서 마지막 도출된 값이 5일 후 예측된 값
         '''
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
     
         # return : 예측한 5일 뒤 코스피 값
         pred = find_pred()
         print('함수실행')
         # 예측 값(pred) - 데이터의 마지막날 코스피지수(today_k) > 0 이면 상승이므로 1, 유지 또는 하락의 경우 0
         ud_5days = 0
         if pred - today_k > 0:
             ud_5days = 1
         else:
             ud_5days = 0
     
         return ud_5days
     
     ```

     </br>

  4. #### KOSPI 등락 예측 정보 DB에 저장

     모델이 예측한 등락 정보를 DB에 저장(1 : 전주 금요일보다 상승 // 0 : 전주 금요일보다 하락 or 동일)

     ```python
     ##---------<fb.py>----------##
     
     def upload_predict_kospi(predict_kospi_index, origin_day, origin_kospi):
         day = datetime.today()
         predictday = 0
         
         #근무일 기준 5일후를 측정하기 위함
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

     DB에 등락예측 정보를 저장한 상태 (5/28(금)에 예측한 6/4(금)의 KOSPI 등락 : 0)

     <img src = "https://github.com/JIHYEOK0801/record/blob/main/capstone/img/predictkospi.PNG?raw=true">

