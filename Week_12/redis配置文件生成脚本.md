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

