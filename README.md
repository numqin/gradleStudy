**注意项目未兼容 android 8.0 的图标问题**

## 项目完成的主要内容

- [x] 域名自动配置

- [x] 权限自动配置

- [x] 签名自动配置

- [x] 渠道包自动配置
- [x] 包名自动配置
- [x] 自动配置资源文件
- [x] 自定义 apk 名

## 项目的结果

Release 版本生成 7 个 apk 文件

```groovy
//mode0 为前缀的代表是同一类 apk 即包名相同的 apk，它们之间只是渠道不同 
mode0 {
    dimension "mode"
    signingConfig signingConfigs.signing0
    buildConfigField("String", "DOMAIN", '"http://domian"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "fuwuqi", permission_name: "com.qinlei.packagedemo.JPUSH_MESSAGE"]
}
mode0_xiaomi {
    dimension "mode"
    signingConfig signingConfigs.signing0
    buildConfigField("String", "DOMAIN", '"http://domian"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "xiaomi", permission_name: "com.qinlei.packagedemo.JPUSH_MESSAGE"]
}
mode0_huawei {
    dimension "mode"
    signingConfig signingConfigs.signing0
    buildConfigField("String", "DOMAIN", '"http://domian"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "huawei", permission_name: "com.qinlei.packagedemo.JPUSH_MESSAGE"]
}
mode0_baidu {
    dimension "mode"
    signingConfig signingConfigs.signing0
    buildConfigField("String", "DOMAIN", '"http://domian"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "baidu", permission_name: "com.qinlei.packagedemo.JPUSH_MESSAGE"]
}
mode0_360 {
    dimension "mode"
    signingConfig signingConfigs.signing0
    buildConfigField("String", "DOMAIN", '"http://domian"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "360", permission_name: "com.qinlei.packagedemo.JPUSH_MESSAGE"]
}

//这个与 mode0 的包名不同，可以在一个手机中安装 mode0 和 mode1，它们是不冲突的
//并且重新定义了签名文件、域名（请求的网络地址）、自定义的权限
mode1 {
    dimension "mode"
    applicationId 'com.qinlei.package1'
    signingConfig signingConfigs.signing1
    buildConfigField("String", "DOMAIN", '"http://domian1"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "fuwuqi", permission_name: "com.qinlei.package1.JPUSH_MESSAGE"]
}
//这个与 mode0、mode1 的包名不同，相当于第三种 apk。与 mode1 类似
mode2 {
    dimension "mode"
    applicationId 'com.qinlei.package2'
    signingConfig signingConfigs.signing2
    buildConfigField("String", "DOMAIN", '"http://domian2"')
    manifestPlaceholders = [UMENG_CHANNEL_VALUE: "fuwuqi", permission_name: "com.qinlei.package2.JPUSH_MESSAGE"]
}
```

## 资源文件

按 productFlavors  定义的名字定义相应的文件夹，打对应 productFlavors  的时候会进行覆盖

这个可以修改，应用名，图标等

![1544089250008](https://github.com/numqin/gradleStudy/blob/master/screenshots/1544089250008.png?raw=true)

## 打包命令

生成所有 productFlavors 的 release 版本 

```groovy
gradle aR
//或者
gradel assembleRelease
```

生成某一个 productFlavors 的 release 版本

```groovy
gradle assemblemode0Release //其中 mode0 是你定义的 productFlavors 的名字
```

