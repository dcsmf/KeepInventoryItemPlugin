# 死亡掉落保护插件
可以在玩家死亡时只保存特定的物品，其余掉落

## 构建环境
SpigotAPI的版本：1.16.5
Java版本：JDK16
gradle版本：7.3.3

## 构建方式
MacOs/Linux
```shell
./gradlew build
```
Windows
```shell
gradlew.bat build
```

## 游戏内使用方式：
进入游戏输入`/gamerule keepInventory true`开启死亡不掉落，以启用本插件
输入`/kii help`即可查询本插件所有命令