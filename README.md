<h1> 
    <a href="https://magician-io.com">Magician-Concurrent</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-8+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-Concurrent 是一个并发编程工具包，把需要并发执行的代码传入这个工具包内，线程我们帮您管理

## 运行环境

JDK8+

## 文档

[https://magician-io.com](https://magician-io.com)

## 示例

### 导入依赖
```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-Concurrent</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- This is the logging package, you must have it or the console will not see anything, any logging package that can bridge with slf4j is supported -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.12</version>
</dependency>
```

### 并发编程

同步执行

```java
@HttpHandler(path="/")
public class DemoHandler implements HttpBaseHandler {

    @Override
    public void request(MagicianRequest magicianRequest, MagicianResponse response) {
        // response data
        magicianRequest.getResponse()
                .sendJson(200, "{'status':'ok'}");
    }
}
```

异步执行

```java
Magician.createHttp()
    .scan("handler所在的包名")
    .bind(8080);
```

### 生产者与消费者模型

```java
@WebSocketHandler(path = "/websocket")
public class DemoSocketHandler implements WebSocketBaseHandler {
   
    @Override
    public void onOpen(WebSocketSession webSocketSession) {
     
    }
   
    @Override
    public void onClose(WebSocketSession webSocketSession) {
        
    }

    @Override
    public void onMessage(WebSocketSession webSocketSession, byte[] message) {

    }
}
```