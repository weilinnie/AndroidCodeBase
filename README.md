#Introduction

这是 winiex 开发 Android 程序时使用的基本功能代码基。它包含了常用的功能的实现：譬如，获取网络状态，操作 Preference，以及图片的获取、缓存等。

#DOING

1. 完成设置缺省缓存参数的 ImageCache 类。
2. 完善 DiskImageCache 类。

#TODO

1. 实现 PreferenceUtils。
2. 实现 Android 版本代号和版本号之间进行转化的类。
3. 完成解决了中英文混排换行不规则问题的自定义 TextView。

#IDEAS

##TECHNICAL

1. HttpImageWorker 需要使用 DiskImageCache，而 DiskImageCache 需要使用 MemImageCache。
2. ResourceImageWorker 和 DiskImageWorker 使用 MemImageCache 即可。

## PRODUCT
