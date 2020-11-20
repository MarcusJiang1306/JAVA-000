###ShardingSphere编译指北  
说实话, 我第一次看见这么大个repo是有点发怵的, 公司也有个repo, 里头7 8个module, 连编译带test要二三十分钟, 点下去了, 如果你要等着他干活意味着上个厕所回来再泡杯茶, 而shardingsphere几十个模块, 光文件夹都看着晕。  
但无论遇到什么困难, 微笑的面对他, 于是乎就有了这篇踩坑记
1. 克隆项目  
前期准备：想给开源项目提交代码不像自己开个仓库那么简单, 一般来说是先fork到自己仓库, 修改的时候对自己的仓库进行修改是比较妥当的, 也不会污染到项目本身  
另外, 由于shardingsphere的路径有些加起来特别长, Git在windows创建文件的时候是通过windowsAPI的, 因为windowsAPI的限制, 路径最大长度被限定在260, 所以在git需要开启一个设置来让超长路径名的文件正确生成, 运行 'git config --global core.longpaths true' 来开启 至此, 前期准备结束（如果没有开启这个config 在项目下载完成之后会被系统自动删除很多文件导致编译时报缺少子模块）  
对于我这种没梯子的来说直接在GitHub下大项目也是个灾难级动作, 好在之前学到一个方法, 借道gitee下载GitHub项目, 下载完成再修改remote地址  
使用gitee下载GitHub项目: https://blog.csdn.net/luobing4365/article/details/105658274/  
修改remote地址: https://blog.csdn.net/qq_36688143/article/details/88540657  
以及设置upstream地址&fork同步: https://www.bilibili.com/video/BV1Vb411A7z2  

2. 编译打包
首先整个项目是基于1.8的, 装了其他版本的jdk请切换到1.8  
接下来是第一次编译, 因为项目相互依赖子模块用的是latest版本, 也就是snapshot版本, 如果你编译的那个模块刚好有依赖其他模块, 而那个模块又没生成 就会报找不到依赖的情况, 这时候就需要手动先编译那个子模块再重新编译, 我这里遇到缺失的模块是shardingsphere-test-fixture, 好在shardingsphere之间的模块都相对独立, 只有少部分互相依赖的情况, 大家如果遇到其他情况就先编译那个就好.  
由于初次编译可能遇到找不到包的情况 所以建议把测试和各种检查都关掉以加快速度, 可开启以下参数 '-Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Drat.skip=true -Dcheckstyle.skip=true'  
在我编译的时候遇到个神奇的问题就是我配的阿里云的仓库, 说 "Could not find artifact com.google.guava:listenablefuture:jar:sources:9999.0-empty-to-avoid-conflict-with-guava in alimaven" 但是我在本地是找得到这个jar的, 不知道为什么, 但是项目依旧能编译打包完成 
通过以上的操作 基本大部分雷区都踩完了 可以愉快的开启你的贡献代码的生活了