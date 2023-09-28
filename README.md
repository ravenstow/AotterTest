# AotterTest
#### 只用在Aotter筆試，編寫計算廣告SDK在Client端的列表UI所插入的廣告內容IMP(曝光量)，廣告UI區塊可見50%以上且超過一秒才算曝光一次

### 內容
- 使用Compose UI寫簡單的列表UI，資料內容從免費API透過RestFul方式取得
- MVVM + Clean架構: UI + ViewModel + Usecase + Repository為主要層級
- 使用RetroFit + Moshi並選用NewYork Times的API實作Https溝通取得書籍相關資料並呈現
  
### Note:
API限制一分鐘五次，所以讀取錯誤的話請一分鐘後再試
或者可在BookList Composable中備好的變數切換demo用的List<Book>
