## netty版网关  
本次作业对netty自身设计的理解和netty的使用都很好的覆盖到, 如何处理channelHandler之间的关系, 如何处理返回结果, 如何集中处理exception等.  

### filter模块  
这部分的实现处理得比较简单, 直接沿用了netty的责任链模式的设计. 也看过一些同学的写法, 是另外设计的filter模块, 我觉得这并不符合netty设计理念.  

###router模块  
因为老师给的方法是传入一个serverList从中选一个, 我对router的认识也不足, 就简单写了个轮询机制, 而且是很粗暴的用new的方式整合进去的. 由于我觉得这一块不是很重点, 所以没设计成注册中心的模式.  

###client模块
这个是整个作业的重点, 是影响整个网关性能的关键模块, 如果不能及时处理请求就会导致整个数据流的阻塞. 
  
在前面的两个版本由于用的是同步阻塞的client和调用方法, RPS始终上不去. 尝试配异步的client模块但是发现还面临需要解析方法和内容的问题, 最后我选择一步到位使用池化版本的netty client, 这样整个request就能原样的送到backend服务器.   

但无论是使用池化的netty client还是使用第三方的异步请求库都面临一个问题, 无法拿到某个request对应的response, 通过查看百度和小米的response之后我决定使用requestID的方案. 即发送时传入requestID, 让服务端每个请求都将requestID返回回来从而达到识别response的目的