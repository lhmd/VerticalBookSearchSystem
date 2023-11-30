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

## 接口 Endpoints

`http://10.73.103.130:1212/auth/register`

`http://10.73.103.130:1212/auth/login`

`http://10.73.103.130:1212/profile/update`

`http://10.73.103.130:1212/profile/update-password`

`http://10.73.103.130:1212/api/deprecated-search?q=Classic&type=CATEGORY`

`http://10.73.103.130:1212/api/search`

`http://10.73.103.130:1212/api/interest`

`http://10.73.103.130:1212/api/category`

`http://10.73.103.130:1212/api/bookLanguages`

## 参考资料

1. [参考1](https://blog.csdn.net/weixin_44088785/article/details/89478635)
2. [参考2](https://blog.csdn.net/qq_41879343/article/details/111827824)
3. 