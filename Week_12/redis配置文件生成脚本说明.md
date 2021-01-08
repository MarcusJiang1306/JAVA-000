用以下脚本配合redis.conf生成出来的server配置文件
```shell script
#!/bin/sh
raw_port="6379"
filename="redis-${1}.conf"
pid="redis_${1}.pid"
log="redis-${1}.log"
rawpid="redis_6379.pid"
rawlog="redis.log"

cp redis.conf ${filename}
if test ! -d /var/lib/redis${1} ; then
mkdir /var/lib/redis${1}
fi
sed -i s/"port ${raw_port}"/"port ${1}"/ ${filename}
sed -i s/${rawpid}/${pid}/ ${filename}
sed -i s/${rawlog}/${log}/ ${filename}
sed -i s#lib/redis#lib/redis${1}# ${filename}
sed -i '286a\replicaof 0.0.0.0 6379' ${filename}
```

每个文件的diff（其中之一）
```shell script
diff redis.conf redis-6380.conf
92c92
< port 6379
---
> port 6380
158c158
< pidfile /var/run/redis_6379.pid
---
> pidfile "/var/run/redis_6380.pid"
171c171
< logfile /var/log/redis/redis.log
---
> logfile "/var/log/redis/redis-6380.log"
253c253
< dbfilename dump.rdb
---
> dbfilename "dump.rdb"
263c263
< dir /var/lib/redis
---
> dir "/var/lib/redis6380"
974c974
< # cluster-announce-port 6379
---
> # cluster-announce-port 6380
```

用以下脚本配合redis-sentinel.conf源文件生成的sentinel配置文件
```shell script
#!/bin/sh
raw_port="26379"
filename="redis-sentinel-${1}.conf"
pid="redis-sentinel_${1}.pid"
log="sentinel-${1}.log"
rawpid="redis-sentinel.pid"
rawlog="sentinel.log"

cp redis-sentinel.conf ${filename}

sed -i s/"port ${raw_port}"/"port ${1}"/ ${filename}
sed -i s/${rawpid}/${pid}/ ${filename}
sed -i s/${rawlog}/${log}/ ${filename}
```

用以下脚本配合redis-cluster.conf源文件生成的cluster配置文件
```shell script
#!/bin/sh
if test ! -d /var/lib/redis-cluster_${1} ; then
mkdir /var/lib/redis-cluster_${1}
fi

cp redis-cluster.conf redis-cluster_${1}.conf
sed -i s/6379/${1}/ redis-cluster_${1}.conf
```

在redis5.x版本可以用redis-cli来create cluster，是在ruby安装后才知道的，不知道能不能不装ruby。。
```shell script
redis-cli --cluster create 0.0.0.0:7001 0.0.0.0:7002 0.0.0.0:7003 0.0.0.0:7004 0.0.0.0:7005 0.0.0.0:7006 --cluster-replicas 1
```

但是很奇怪的是不知道为什么每个master的replica不是被平均分配的
```shell script
>>> Performing hash slots allocation on 6 nodes...
Master[0] -> Slots 0 - 5460
Master[1] -> Slots 5461 - 10922
Master[2] -> Slots 10923 - 16383
Adding replica 0.0.0.0:7004 to 0.0.0.0:7001
Adding replica 0.0.0.0:7005 to 0.0.0.0:7002
Adding replica 0.0.0.0:7006 to 0.0.0.0:7003
>>> Trying to optimize slaves allocation for anti-affinity
[WARNING] Some slaves are in the same host as their master
M: 80ebab23ec1b922dc0a7985a5188626b02a60015 0.0.0.0:7001
   slots:[0-5460] (5461 slots) master
M: dbb289229603a685f3b8609ca0a42ea518a92ce2 0.0.0.0:7002
   slots:[5461-10922] (5462 slots) master
M: 71a8c49c23233df107de9cddaa53fcbf994bdcd5 0.0.0.0:7003
   slots:[10923-16383] (5461 slots) master
S: dbb712d0e3ec5a9cf214104e6848c154f0cff33c 0.0.0.0:7004
   replicates dbb289229603a685f3b8609ca0a42ea518a92ce2
S: 2028e6d79a70b7f172aa612b2dc2e384294180d6 0.0.0.0:7005
   replicates 71a8c49c23233df107de9cddaa53fcbf994bdcd5
S: d062150f3d2ec29a0cb0af75692eb419b59f07ff 0.0.0.0:7006
   replicates 80ebab23ec1b922dc0a7985a5188626b02a60015
Can I set the above configuration? (type 'yes' to accept): yes
>>> Nodes configuration updated
>>> Assign a different config epoch to each node
>>> Sending CLUSTER MEET messages to join the cluster
Waiting for the cluster to join
...
>>> Performing Cluster Check (using node 0.0.0.0:7001)
M: 80ebab23ec1b922dc0a7985a5188626b02a60015 0.0.0.0:7001
   slots:[0-5460] (5461 slots) master
   1 additional replica(s)
S: dbb712d0e3ec5a9cf214104e6848c154f0cff33c 127.0.0.1:7004
   slots: (0 slots) slave
   replicates dbb289229603a685f3b8609ca0a42ea518a92ce2
S: 2028e6d79a70b7f172aa612b2dc2e384294180d6 127.0.0.1:7005
   slots: (0 slots) slave
   replicates 71a8c49c23233df107de9cddaa53fcbf994bdcd5
M: dbb289229603a685f3b8609ca0a42ea518a92ce2 127.0.0.1:7002
   slots:[5461-10922] (5462 slots) master
   1 additional replica(s)
M: 71a8c49c23233df107de9cddaa53fcbf994bdcd5 127.0.0.1:7003
   slots:[10923-16383] (5461 slots) master
   1 additional replica(s)
S: d062150f3d2ec29a0cb0af75692eb419b59f07ff 127.0.0.1:7006
   slots: (0 slots) slave
   replicates 80ebab23ec1b922dc0a7985a5188626b02a60015
[OK] All nodes agree about slots configuration.
>>> Check for open slots...
>>> Check slots coverage...
[OK] All 16384 slots covered.
```