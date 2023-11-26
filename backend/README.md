# README

## 完成进度

目前已经完成的接口：
1. 注册 （还没实现验证用户输入信息）
2. 登录
3. 修改用户信息 （还没实现验证用户输入信息）
4. 修改密码
5. `POST /api/search?q=QUERY&type=TYPE`
6. `POST /api/add-book`
7. 

## 未完成

1. 爬虫 + 收集数据 + 整理数据

## 运行指南

### Elasticsearch

```shell
docker run --name es01 --net elastic -d -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.17.15
```

### Spring boot

```sh
./gradlew bootJar # macOS/Linux
java -jar build/libs/sem-backend-0.0.1-SNAPSHOT.jar 
```

## 接口调研例子

```sh
chinhwajie@chinmac ~$ curl -X POST http://localhost:1212/api/search\?q\=Oxford\&type\=PUBLISHER | jq    
[
  {
    "uuid": "63342e9d-15de-471e-ab17-1390bd81d966",
    "name": "1984",
    "category": "Classic",
    "publisher": "Oxford University Press",
    "pages": 894,
    "publishYear": 1939,
    "bookLanguage": "English",
    "imageUrl": "https://picsum.photos/697/708?image=96",
    "isbn": "6068095950026"
  },
  {
    "uuid": "bc65c52c-ab31-4b5d-b713-9b0ea9026a69",
    "name": "The Lord of the Rings",
    "category": "Classic",
    "publisher": "Oxford University Press",
    "pages": 141,
    "publishYear": 2009,
    "bookLanguage": "English",
    "imageUrl": "https://picsum.photos/429/344?image=877",
    "isbn": "8773831404389"
  }
]

```



```sh
chinhwajie@chinmac ~$ curl --location 'http://localhost:1212/auth/login' \                               
--header 'Content-Type: application/json' \
--data '{
    "username": "sa",
    "password": "as"
}' | jq
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100  1290    0  1244  100    46  23653    874 --:--:-- --:--:-- --:--:-- 27446
{
  "success": true,
  "message": "登录成功",
  "user": {
    "id": 1,
    "username": "sa",
    "password": "as",
    "email": "asdasd.chasdj@outlook.com",
    "phone": "18927398172",
    "gender": "MALE",
    "address": "aksjhdkjsahd",
    "interest": "Classic"
  },
  "book": [
    {
      "uuid": "bc65c52c-ab31-4b5d-b713-9b0ea9026a69",
      "name": "The Lord of the Rings",
      "category": "Classic",
      "publisher": "Oxford University Press",
      "pages": 141,
      "publishYear": 2009,
      "bookLanguage": "English",
      "imageUrl": "https://picsum.photos/429/344?image=877",
      "isbn": "8773831404389"
    },
    {
      "uuid": "63342e9d-15de-471e-ab17-1390bd81d966",
      "name": "1984",
      "category": "Classic",
      "publisher": "Oxford University Press",
      "pages": 894,
      "publishYear": 1939,
      "bookLanguage": "English",
      "imageUrl": "https://picsum.photos/697/708?image=96",
      "isbn": "6068095950026"
    },
    {
      "uuid": "c575aa24-bc98-4a23-8a2d-6775e024e7d4",
      "name": "The Hunger Games",
      "category": "Classic",
      "publisher": "Doubleday",
      "pages": 240,
      "publishYear": 1926,
      "bookLanguage": "English",
      "imageUrl": "https://picsum.photos/543/661?image=198",
      "isbn": "4240767867008"
    },
    {
      "uuid": "d6b6bf36-d2cd-4733-ba54-28457a797437",
      "name": "The Great Gatsby",
      "category": "Classic",
      "publisher": "Random House",
      "pages": 995,
      "publishYear": 1930,
      "bookLanguage": "English",
      "imageUrl": "https://picsum.photos/629/719?image=322",
      "isbn": "1099411226754"
    }
  ]
}
```



## 参考资料

1. [参考1](https://blog.csdn.net/weixin_44088785/article/details/89478635)
2. [参考2](https://blog.csdn.net/qq_41879343/article/details/111827824)
3. 