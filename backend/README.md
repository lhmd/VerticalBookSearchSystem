# README

## 完成进度

目前已经完成的接口：
1. 注册 （还没实现验证用户输入信息）
2. 登录
3. 修改用户信息 （还没实现验证用户输入信息）
4. 修改密码
5. 所有接口都完成了

## 未完成

1. 数据到了就可以插入到Elastic的容器了。

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

## 接口 Endpoints

### V1 版本 接口

`http://10.73.103.130:1212/auth/register`

`http://10.73.103.130:1212/auth/login`

`http://10.73.103.130:1212/profile/update`

`http://10.73.103.130:1212/profile/update-password`

`http://10.73.103.130:1212/api/deprecated-search?q=Classic&type=CATEGORY`

`http://10.73.103.130:1212/api/search`

`http://10.73.103.130:1212/api/interest`

`http://10.73.103.130:1212/api/category`

`http://10.73.103.130:1212/api/bookLanguages`

`http://10.73.103.130:1212/api/bookPublishers`

### V2 版本 接口

`http://10.73.103.130:1212/v2/auth/register`

`http://10.73.103.130:1212/v2/auth/login`

`http://10.73.103.130:1212/v2/profile/update`

`http://10.73.103.130:1212/v2/profile/update-password`

`http://10.73.103.130:1212/v2/api/deprecated-search?q=Classic&type=CATEGORY`

`http://10.73.103.130:1212/v2/api/search`

`http://10.73.103.130:1212/v2/api/interest`

`http://10.73.103.130:1212/v2/api/category`

`http://10.73.103.130:1212/v2/api/bookLanguages`

`http://10.73.103.130:1212/v2/api/bookPublishers`

### 不同之处

原来V1的设计是：不同接口在响应的时候都是没有一个标准的数据结构，比如说/search接口：

```json
{
    "success": true,
    "message": "Search result",
    "books": [ // <= 每个接口都有不同的属性用来传响应的数据
        {
            "uuid": "5045f2e5-f0e5-40bc-8e97-df5d21452711",
            "name": "Astounding Science-fiction",
            "category": "Science fiction",
            "publisher": "",
            "pages": 1008,
            "publishYear": 1951,
            "bookLanguage": "en",
            "isbn": "",
            "imageUrl": "https://www.felixan.ca/direct/bookcovers/5045f2e5-f0e5-40bc-8e97-df5d21452711.jpg",
            "source": "Google Books"
        },
        ...
        {
        	...
        }
    ]
}
```

但是在V2接口设计则是这样子，异常处理也有更大的发展空间：


```json
{
    "success": true,
    "message": "成功!",
    "code": "2000", // <= 不同异常的码也不同，前端可以多更多用户交互。
    "result": [ // <= 所有接口的数据部分都是以result为属性名。提高了统一性，同时代码的耦合性降低、代码的维护空间更广、代码的可读性提高以及代码量减少。
        {
            "uuid": "5045f2e5-f0e5-40bc-8e97-df5d21452711",
            "name": "Astounding Science-fiction",
            "category": "Science fiction",
            "publisher": "",
            "pages": 1008,
            "publishYear": 1951,
            "bookLanguage": "en",
            "isbn": "",
            "imageUrl": "https://www.felixan.ca/direct/bookcovers/5045f2e5-f0e5-40bc-8e97-df5d21452711.jpg",
            "source": "Google Books"
        },
        ...
        {
        		...
        }
    ]
}
```



## 参考资料

1. [参考1](https://blog.csdn.net/weixin_44088785/article/details/89478635)
2. [参考2](https://blog.csdn.net/qq_41879343/article/details/111827824)
3. [springboot 全局异常顺序 自定义异常优先级-掘金 (juejin.cn)](https://juejin.cn/s/springboot 全局异常顺序 自定义异常优先级)
4. [Spring Boot项目优雅的全局异常处理方式（全网最新） - 掘金 (juejin.cn)](https://juejin.cn/post/6959520793063587848)
5. 