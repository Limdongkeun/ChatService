﻿# ChatService



## docker 
redis 이미지를 다운받습니다. 👍
```
$ docker pull redis
```

docker 기반의 redis 설치가 완료되었습니다.

redis를 기동해 보도록 하겠습니다.

✅ redis 실행

redis는 6379를 기본 포트로 사용합니다.
```
$ sudo docker run -p 6379:6379 redis
```
✅ redis 접속

redis에 접속하기 전에 redis가 설치된 docker 컨테이너 내부로 접속합니다.
```
$ docker exec -it redis /bin/bash
```
💡 참고

만약 아래와 같은 에러가 발생한다면 다음과 같이 접속하시면 됩니다.

OCI runtime exec failed: exec failed: container_linux.go:380: starting container process caused: exec: "/bin/bash": stat /bin/bash: no such file or directory: unknown
```
$ docker exec -it redis /bin/sh
```
컨테이너 내부로 접속하였고 redis에 접속해 보겠습니다.

redis-cli 명령어를 이용하여 redis에 접속합니다.

접속이 완료되면 IP:port > 프롬프트로 변경됩니다.

/data# redis-cli

127.0.0.1:6379>

✅ redis 버전 확인
info 명령어를 사용하면 다양한 정보를 알 수 있으며, redis version도 확인 가능합니다.

127.0.0.1:6379> info

Server
redis_version:4.0.14
kotlin

📢 Redis 기본 명령어
앞서 소개한 redis의 데이터 구조 중 strings, list, set의 기본 명령어에 대해서 알아봅시다.

✅ strings

키-값 구조로 데이터를 저장하고 조회해보도록 하겠습니다.

set 명령어를 이용하여 key - value를 저장합니다.

get 명령어를 이용하여 key에 해당하는 value를 조회합니다.

del 명령어를 이용하여 key를 삭제합니다.

✅ list

순서가 유지되고 중복을 허용하는 문자열 모음입니다.

lpush: [ lpush key value ]

left push이며 왼쪽부터 즉, index 0부터 데이터를 저장합니다.

lpop: [ lpop key ]

left pop이며, list의 index 0 부터 데이터를 추출합니다.

rpush: [ rpush key value ]

right push이며 오른쪽부터 즉, index last부터 데이터를 저장합니다.

rpop: [ rpop key ]

right pop이며, list의 index last 부터 데이터를 추출합니다.

lrange: [ lrange key start end ]

list의 데이터를 start부터 end까지의 데이터를 추출합니다.

lrange에서 end를 -1로 선언하면 해당 list의 데이터를 전부 추출합니다.

✅ set

정렬되지 않고 순서가 없는 문자열 모음입니다.

set에서는 value를 member로 표시합니다.

sadd: [ sadd key member ]

set의 key에 member를 추가합니다.

srem: [ srem key member]

set의 key에 member를 삭제합니다.

smembers: [ smembers key ]

set의 key의 모든 member를 조회합니다.
