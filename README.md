# spring-boot-mq-demo

以下是 ActiveMQ、RocketMQ、RabbitMQ 和 Kafka 的对比分析，从核心特性、适用场景和优缺点等方面进行总结：



### **1. 核心特性对比**
| **特性**              | **ActiveMQ**                     | **RocketMQ**                          | **RabbitMQ**                       | **Kafka**                            |
|-----------------------|-----------------------------------|----------------------------------------|-------------------------------------|--------------------------------------|
| **协议支持**          | JMS、AMQP、STOMP、MQTT           | 自定义协议（基于 TCP）                | AMQP、MQTT、STOMP                  | 自定义协议（基于 TCP）               |
| **消息模式**          | 点对点、发布订阅                 | 发布订阅、顺序消息                     | 点对点、发布订阅、复杂路由         | 发布订阅（分区级顺序）               |
| **吞吐量**            | 中等（万级 TPS）                 | 高（十万级 TPS）                      | 中高（万级 TPS）                   | 极高（百万级 TPS）                   |
| **延迟**              | 中等                             | 低                                     | 极低（微秒级）                     | 中高（毫秒级）                       |
| **可靠性**            | 高（支持持久化）                 | 极高（金融级可靠性）                  | 高（ACK 机制、持久化）             | 高（副本机制、持久化）               |
| **消息顺序性**        | 不支持                           | 分区内严格顺序                        | 单队列顺序                         | 分区内严格顺序                       |
| **事务支持**          | 支持 JMS 事务                    | 支持分布式事务                         | 支持轻量级事务                     | 支持生产者事务（有限）               |
| **扩展性**            | 一般（主从架构）                 | 高（分布式架构）                      | 中等（集群扩展复杂）               | 极高（分布式、水平扩展）             |
| **生态与语言支持**    | Java 为主，支持多语言客户端      | Java 为主                              | 多语言支持（Erlang + 客户端）      | 多语言支持（Scala/Java + 客户端）    |



### **2. 适用场景**
| **消息队列**          | **典型场景**                                                                 |
|-----------------------|-----------------------------------------------------------------------------|
| **ActiveMQ**          | 传统企业应用、JMS 规范兼容场景、中小规模消息处理（如订单系统、日志收集）     |
| **RocketMQ**          | 高吞吐金融交易、顺序消息（如支付清算、订单流水）、分布式事务场景            |
| **RabbitMQ**          | 实时消息处理、复杂路由（如即时通讯、任务队列、IoT 设备通信）                |
| **Kafka**             | 大数据日志流处理、高吞吐量场景（如用户行为追踪、监控数据采集、流式计算）    |



### **3. 优缺点总结**
#### **ActiveMQ**
- **优点**：支持多种协议、兼容 JMS、轻量级、易于部署。
- **缺点**：吞吐量较低、扩展性有限、社区活跃度下降。

#### **RocketMQ**
- **优点**：高吞吐、严格顺序消息、金融级可靠性、支持事务消息。
- **缺点**：生态相对较小、多语言支持有限（Java 为主）。

#### **RabbitMQ**
- **优点**：低延迟、灵活的路由机制、多语言支持、易于集成。
- **缺点**：集群扩展复杂、高吞吐场景性能受限。

#### **Kafka**
- **优点**：超高吞吐量、水平扩展性强、适合大数据生态、持久化能力强。
- **缺点**：消息延迟较高、配置复杂、功能相对单一（仅发布订阅）。



### **4. 选型建议**
- **高吞吐 + 大数据流处理**：选择 **Kafka**。
- **金融级可靠 + 顺序消息**：选择 **RocketMQ**。
- **低延迟 + 复杂路由**：选择 **RabbitMQ**。
- **传统 JMS 兼容场景**：选择 **ActiveMQ**（逐步被替代中）。


### **5. 补充说明**
- **Kafka** 和 **RocketMQ** 更适合分布式架构和云原生场景。
- **RabbitMQ** 的插件机制（如延迟队列）可扩展功能。
- **ActiveMQ** 的下一代版本 **ActiveMQ Artemis** 性能有所提升，但生态仍较弱。

根据具体业务需求（如吞吐量、延迟、消息顺序性、生态集成）选择最合适的消息中间件。

## ActiveMQ

[使用 Docker 运行 ActiveMQ](https://hub.docker.com/r/webcenter/activemq)

```bash
docker pull webcenter/activemq:latest
docker run --name='activemq' -it --rm -P webcenter/activemq:latest
```

The account admin is "admin" and password is "admin".

All settings are the default ActiveMQ's settings.

Web Console on ActiveMQ: [http://localhost:32770/admin/index.jsp](http://localhost:32770/admin/index.jsp)

## RocketMQ

[使用 Docker 运行 RocketMQ](https://rocketmq.apache.org/zh/docs/quickStart/02quickstartWithDocker)

1、拉取 RocketMQ 镜像

这里以 5.3.2 版本镜像为例，介绍部署过程。

```bash
docker pull apache/rocketmq:5.3.2
```

2、创建容器共享网络

RocketMQ 中有多个服务，需要创建多个容器，创建 docker 网络便于容器间相互通信。

```bash
docker network create rocketmq
```

3、启动 NameServer

```bash
# 启动 NameServer
docker run -d --name rmqnamesrv -p 9876:9876 --network rocketmq apache/rocketmq:5.3.2 sh mqnamesrv

# 验证 NameServer 是否启动成功
docker logs -f rmqnamesrv
```

> 我们可以看到 'The Name Server boot success..'， 表示NameServer 已成功启动。

4、启动 Broker + Proxy

NameServer 成功启动后，我们启动 Broker 和 Proxy。

```ps1
# 配置 Broker 的 IP 地址
echo "brokerIP1=127.0.0.1" > broker.conf

# 启动 Broker 和 Proxy
docker run -d ^
--name rmqbroker ^
--net rocketmq ^
-p 10912:10912 -p 10911:10911 -p 10909:10909 ^
-p 8080:8080 -p 8081:8081 \
-e "NAMESRV_ADDR=rmqnamesrv:9876" ^
-v C:/Docker/rocketmq-5.3.2/broker.conf:/home/rocketmq/rocketmq-5.3.2/conf/broker.conf ^
apache/rocketmq:5.3.2 sh mqbroker --enable-proxy \
-c /home/rocketmq/rocketmq-5.3.2/conf/broker.conf

# 验证 Broker 是否启动成功
docker exec -it rmqbroker bash -c "tail -n 10 /home/rocketmq/logs/rocketmqlogs/proxy.log"
```

> 我们可以看到 'The broker boot success..'， 表示 Broker 已成功启动。

至此，一个单节点副本的 RocketMQ 集群已经部署起来了，我们可以利用脚本进行简单的消息收发。

## RabbitMQ

[使用 Docker 运行 RabbitMQ](https://hub.docker.com/_/rabbitmq)

1、拉取镜像

```bash
docker pull rabbitmq:4.0.9-management
```

2、运行

```bash
docker run -d --hostname my-rabbit -p 5672:5672 -p 5671:5671 -p 15671:15671 -p 15672:15672 --name some-rabbit rabbitmq:4.0.9-management
```

## Kafka

[使用 Docker 运行 Kafka](https://kafka.apache.org/quickstart)

1、拉取镜像

```bash
docker pull apache/kafka:4.0.0
```

2、运行

```bash
docker run -d --name kafka -p 9092:9092 apache/kafka:4.0.0
```