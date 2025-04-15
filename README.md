# spring-boot-mq-demo

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