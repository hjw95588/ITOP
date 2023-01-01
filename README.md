# ITOP

自研科技运营平台

## 快速开始
1.**添加Maven依赖**

Kylin和Kylin-Flow的依赖需要通过Maven--install命令，打包到本地Maven库或通过解压REALEASE版到本地Maven库。
```bash
<dependency>
    <groupId>com.ebchinatech</groupId>
    <artifactId>Kylin</artifactId>
    <version>${kylin.version}</version>
</dependency>
<dependency>
    <groupId>com.ebchinatech</groupId>
    <artifactId>Kylin-Flow</artifactId>
    <version>${kylin-flow.version}</version>
</dependency>
```
2.**启动类添加配置**

在启动类添加组件和mapper文件扫描包的配置。
```bash
@ComponentScan(basePackages = {"com.ebchinatech.kylin.web.service", "com.ebchinatech.kylin.framework", "com.ebchinatech.itop"})
@MapperScan(basePackages = {"com.ebchinatech.kylin.web.mapper", "com.ebchinatech.itop.**.mapper"})
```
3.**业务代码生成**

```bash
GenController可以逆向工程生成java、vue、js、xml等文件，可满足基本的CURD操作。按照工程中controller、domain、mapper、service等
目录结构和代码风格完成业务代码的开发.
```

