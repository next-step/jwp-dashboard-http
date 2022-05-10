# HTTP 서버 구현하기

## STEP 1
- HTTP GET 메소드 호출 시 파일 읽어 Response로 응답하기 
![](https://user-images.githubusercontent.com/63947424/166715523-eae39b97-d4f9-4b9b-a9c2-8d99a5ac628e.png)
![](https://user-images.githubusercontent.com/63947424/166715531-703af3ae-c513-404a-9aa4-b7d37142a71c.png)

- HTTP GET 메소드 css 파일 대상으로 호출 시 css content-type으로 파일 읽어 Response로 응답하기  
![](https://user-images.githubusercontent.com/63947424/166715554-6b1521f6-2d1e-4746-8c01-6a657d5cb38b.png)
![](https://user-images.githubusercontent.com/63947424/166715557-78d91177-d7b3-4088-8767-d883da8d4a89.png)

- HTTP POST login url 호출 시 사용자 로그인이 성공하면 302 코드로 Response 응답
![](https://user-images.githubusercontent.com/63947424/166715536-f860a26c-b028-4869-947e-9b7f7210381c.png)
![](https://user-images.githubusercontent.com/63947424/166715560-058569a5-5c87-4409-872c-15936b03bba5.png)

- HTTP POST login url 호출 시 사용자 로그인이 실패하면 401 코드로 Response 응답
![](https://user-images.githubusercontent.com/63947424/166717153-69c6110e-e35f-4d9d-a251-aacb3b9fb518.png)
![](https://user-images.githubusercontent.com/63947424/166717158-bb4af74e-9b32-4a55-b8eb-d5fcc8c0e238.png)

- HTTP POST register url 호출 시 사용자 등록이 성공하면 302 코드로 Response 응답
![](https://user-images.githubusercontent.com/63947424/166715543-89f7cba5-5c08-4ea5-a45c-d728c773a08a.png)
![](https://user-images.githubusercontent.com/63947424/166715564-0f0609c3-ac31-4e3b-9896-e77e5877b17c.png)


- 파일의 경로를 알아내는 방법
```
1. getClass().getClassLoader().getResource(fileName) : 시스템 클래스로더를 사용
2. Thread.currentThread().getContextClassLoader().getResource(fileName): 쓰레드마다 다른 클래스 로더를 사용할 수 있으므로 현재 실행 중인 쓰레드의 클래스 로더를 얻어와서 해당 클래스 로더에게 resource 찾는 일을 위임
```
- inputStream에서 바이트로 변환한 값 문자열로 변경하기 
```
new String(inputStream.readAllBytes())
```
- 버퍼의 크기를 설정하지 않은 버퍼의 기본 크기
```
BufferedInputStream에서 버퍼의 사이즈가 주어지지 않을 경우 기본 버퍼 사이즈는 8192로 되어있다. 
```
- 301 vs 302 
```
301 : URL가 영구적으로 이동했음을 나타냄 (도메인을 변경하거나 새로운 URL구조로 개편했을 때) -> 검색엔진이 페이지 랭킹, 평가점수를 새로운 URL로 이동 
302 : URL가 일시적으로 이동했음을 나타냄 (기존 URL을 보존한 채 새로운 URL로 이동할 때) -> 검색엔진이 페이지 랭킹, 평가점수 등 기존 URL에 그대로 유지
```
- BufferedOutputStream vs ByteArrayOutputStream 
```
ByteArrayOutputStream: 메모리의 바이트 배열에 데이터를 쓴다.BufferedOutputStream: 입출력 작업을 효율적으로 만들기 위해 다른 기본 스트림을 래핑하고 기본 스트림에 대한 버퍼링을 제공한다.(ByteArrayOutputStream의 단점을 보완, 자체 입출력은 기반 스트림에 위임)
```
- 버퍼링 사용 이유
```
큰 데이터 블록을 파일 시스템에 쓰는 것이 바이트 단위로 쓰는 것보다 더 효율적이기 때문에 버퍼에 데이터 조각들을 수집한 다음 한 번에 디스크에 쓰는 것이 효율적이다.
효율적인 전송을 위해 BufferedOutputStream 필터를 연결하여 스트림에서 버퍼링 사용이 가능하다.
```
- ✨✨✨ URL 접속 이외의 css, js, favicon등의 정적파일이 호출 되는 방법 (추가공부 필요)?
```
브라우저 탭의 작은 아이콘을 불러오기 위해 자동으로 GET favicon.ico request를 보낸다. 
```


## STEP 1
- RequestMapper를 이용하여 요청 URI에 따라서 Controller 다르게 사용하도록 구현

- E-Tag를 생성해보고 E-Tag와 함께 요청이 오면 E-Tag를 이용하여 변경 여부를 확인 후 동일하면 그대로 304 Not Modified응답을, 변경되거나 캐시에 없으면 200 Ok 요청과 함께 body를 응답
![](https://user-images.githubusercontent.com/63947424/167609637-3efeab43-7a36-4fda-9bc5-623f6677b1f3.png)
![](https://user-images.githubusercontent.com/63947424/167609641-cc02da38-f255-477d-90ac-1aef1e8ac78f.png)


