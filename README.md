问题: 当rabbitmq宕机时，消息会丢失。
消息持久化：
    boolean durable = true时，才能开启持久化操作。
    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
    问题： 当该消息队列已经存在时，我们无法修改durable的值(代码正确但是程序运行报错)，rabbitmq不允许重新定义已经存在的队列。