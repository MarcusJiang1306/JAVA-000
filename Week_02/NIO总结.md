![Image text](src/main/resources/nio_bio%20test.png)
![Image text](src/main/resources/nio_multiple%20thread%20test.png)
![Image text](src/main/resources/nio_thread%20pool%20test.png)
##nio测试
在bio的模式下线程会大量的处于空闲状态导致资源浪费，并发量上不去  
使用了多线程之后并发量有效提升但仍弱于线程池模式  
线程池有效解决了创建会大量消耗资源的情况使性能更高