# JIEKE-AI SDK 使用指南 | 一款能够快速将coze创建的智能体对接到自己的业务系统的SDK | 开源工具

**作者：令狐荣豪** | **个人博客：linghu.blog.csdn.net**  **|  个人微信：codelinghu  |  个人公众号：codelinghu**

## 开源项目概述

📜 该项目主要封装了coze api接口，用于公司、个人项目，能够帮助公司或个人开发者更好的将coze 与 个人或企业业务系统进行交互连接。

<p align="center">
  <img alt="PRs welcome!" src="https://img.shields.io/static/v1?label=PRs&message=WELCOME&style=for-the-badge&color=4A90E2&labelColor=222222" />

<img alt="GitHub license" src="https://img.shields.io/github/license/devgabrieldejesus/readme-model?color=4A90E2&label=LICENSE&logo=3C424B&logoColor=3C424B&style=for-the-badge&labelColor=222222" /> <a href="https://github.com/devgabrieldejesus">
    <img alt="Follow devgabrieldejesus" src="https://img.shields.io/static/v1?label=Follow&message=devgabrieldejesus&style=for-the-badge&color=4A90E2&labelColor=222222" />
  </a>

> 目前coze的个人版是不提供sdk的，只有企业版才提供sdk，这个开源的sdk项目能够帮助个人开发者免费使用coze的api接口。

## Why？ | 这个开源项目解决的痛点

**coze是字节推出的一款ai智能体编程平台，目前分为海外、国内版本，国内版本分为个人和企业版本。目前个人版本没有sdk，需要升级到企业版，花钱才有sdk，这个项目能让个人开发者和企业都免费用上个人版本。**

- 能够快速将coze创建的智能体对接到自己的业务系统中。
- coze 推出的企业版sdk过于复杂，各种认证，各种Auth，个人觉得没必要这么复杂，明明就是几个api的事情，非要用coze那一套Auth应用。

- **API 接口封装**：提供 Java 接口，方便开发者调用 coze 平台的 API 功能。
- **智能体功能**：支持创建自定义智能体，用于处理特定类型的内容（如新闻推送、个性化推荐等）。
- **快速初始化**：支持快速创建和管理聊天会话，简化开发流程。

## 🚀 特点

- **开源**：免费提供，适合个人或企业使用。
- **Java 语言**：基于 Maven 项目工程，支持 Java 编程语言的开发。
- **企业级功能**：主要面向企业版用户，个人版暂未提供 SDK。

💡此项目主要通过maven项目工程搭建、服务的编程语言为：Java

## 🗃 迭代情况

- 0.0.1
  - 能够调用coze平台自定义的智能体

## 🛠快速入门

### 💻 引入依赖

首先引入这个开源工具的POM依赖

```xml
<dependency>
    <groupId>io.github.jackieling</groupId>
    <artifactId>jieke-ai-sdk</artifactId>
    <version>1.0</version>
</dependency>
```

### 💻 获取Coze token

等录coze官网：[主页 - 扣子](https://www.coze.cn/home)

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/743cacacf6a746d383850fd74e8c4657.png)

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/38d79d894ef641bf853d56c136bff4db.png)

点击确定以后，这里可以获取到token值，请务必保存好这个token，后面会用到。

### 💻 创建智能体

在coze官网创建一个智能体，这里我准备用**新闻推送**这个案例做演示。创建一个新闻推送的demo智能体：

![image-20250323161127767](C:\Users\Administrator\AppData\Roaming\Typora\typora-user-images\image-20250323161127767.png)

创建智能体成功以后，在插件这个位置，添加一个头条新闻的插件：

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/51e0990846db44c0b52a3ea3b775dd5a.png)

左边的提示词模板为：

```xml
# 角色
你是一个专业的新闻推送员，专注于为用户提供AI相关的最新新闻资讯。

## 技能
### 技能 1: 推送AI新闻
1. 使用getToutiaoNews搜索关键词“人工智能最新新闻”。
2.整理新闻内容包含新闻标题、发布时间、主要内容和链接。
===回复示例===
- 📰 新闻标题：<新闻具体标题>
- 📅 发布时间：<新闻发布的具体时间>
- 💡 新闻概要：<对新闻核心内容的简要概括，不超过100字>
===示例结束===

## 限制:
- 只推送与AI相关的新闻，拒绝回答与AI无关的话题。
- 所输出的内容必须按照给定的格式进行组织，不能偏离框架要求。
- 新闻概要部分不能超过 100 字。
```

这些工作完成以后你直接点击右上方发布智能体即可。

当你发布智能体以后，你点击进入智能体，将botId存一下，后面要用到：

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/4655593129ce41bcb6e00ab6285a705e.png)

### 📈 代码编写

再上面的工作中，我们需要获取到的重要信息为：

- token
- 智能体的botId

如果你获取到了这两个重要信息，那接下来我们就开始用代码来实现了。

#### 📈 初始化聊天

我们在引入jieke-ai-sdk以后首先需要初始化聊天，可以直接用 `new JieKeAiClient.Builder`初始化。

初始化一定需要用到我们的 `token` 和 `botId`。 至于userId你可以自己定义。

```java
JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("7482689320084734004")
                .userId("123123")
                .build();
```

完整代码：

```java
import com.jieke.coze.client.JieKeAiClient;

import java.io.IOException;

/**
 * @Author: linghu
 * @CreateTime: 2025-03-20
 * @Description: 测试
 */

public class Demo {
    private static final String TOKEN ="你自己的coze token值";

    public static void main(String[] args) throws IOException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("你自己的coze botId值")
                .userId("123123")//这个随便写
                .build();
        String chatResponse = chatClient.initializeChat();
        System.out.println("初始化聊天响应: " + chatResponse);
    }
}
```

初始化聊天以后会得到如下结果：

```json
{
    "data": {
        "id": "7484917103724789798",
        "conversation_id": "7484917103724773414",
        "bot_id": "7482689320084734004",
        "created_at": 1742718068,
        "last_error": {
            "code": 0,
            "msg": ""
        },
        "status": "in_progress"
    },
    "code": 0,
    "msg": ""
}
```

我们需要的值是：

- id
- conversation_id

我们一定要保存好上面这两个值，其中status表示我们的智能体会话创建状态。

#### 📈 查看对话详情

我们在初始化聊天以后，拿到了id和conversation_id，现在要通过 `retrieveChat()`获取当前这个会话执行情况，那就需要 传入：

```java
JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId("7482692532908965888")//上一个步骤获取到的conversation_id
                .chatId("7482695826272272420")//其实就是上一个步骤获取到的id
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);
```

执行结果为：

```java
{
    "code": 0,
    "data": {
        "bot_id": "7482689320084734004",
        "completed_at": 1742200897,
        "conversation_id": "7482692532908965888",
        "created_at": 1742200887,
        "id": "7482695826272272420",
        "status": "completed",
        "usage": {
            "input_count": 2073,
            "output_count": 210,
            "token_count": 2283
        }
    },
    "detail": {
        "logid": "202503231640567EEE5B80AE340BE37C10"
    },
    "msg": ""
}
```

通过这个会话响应结果的 `status`我们可以判断执行情况：complete，即便是执行成功完成的意思。

#### 📈 获取消息列表

当我们通过对话详情的status判断出会话处于complete状态，接下来就可以直接获取智能体回复的消息内容了。

我们可以直接通过 `getMessageList()`获取智能体回复的消息内容

```java
String messageListResponse = conversationClient.getMessageList();
System.out.println("消息列表响应: " + messageListResponse);
```

执行结果为：

```json
{
    "code": 0,
    "data": [
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "{\"name\":\"toutiaoxinwen-getToutiaoNews\",\"arguments\":{\"q\":\"八卦新闻\"},\"plugin_id\":7362080779243094070,\"plugin_name\":\"toutiaoxinwen\",\"api_id\":7362080779243110454,\"api_name\":\"getToutiaoNews\",\"plugin_type\":1}",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200889,
            "id": "7482695835113799692",
            "role": "assistant",
            "type": "function_call",
            "updated_at": 1742200889
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "{\"news\":[{\"title\":\"特大八卦新闻\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/2403b55e9607fd75bc3a8653a3186f3f~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-12-26 19:40\",\"url\":\"https://toutiao.com/group/7452683749739610687/\",\"summary\":\"内娱这瓜是一个接一个！刘大锤曝光岳岳八天七夜会三女，可真是令人咋舌。这岳岳本就不温不火，这下算是“出名”了。先去女化妆师家工作一小时，又和白衣美女在街头拥吻，还深夜去女爱豆左卓家。他还解释这都是正常交往，网友哪能买账！上月就有女生爆料被他玩弄感情，睡完就消失。\",\"media_name\":\"人生感悟\",\"categories\":[\"news_entertainment/gossip/other\",\"news_entertainment/interpretedcontent\",\"news_entertainment/gossip\",\"news_entertainment\"]},{\"summary\":\"序最近娱乐圈风波不断，明星八卦层出不穷今天我们就一一拆解。剧组丑闻：男星私生活惹争议，行业再曝底线失守一位知名男星在剧组期间被爆出招妓丑闻，引发娱乐圈关于职业操守的讨论。网友扒出他近几年在荧幕上的状态也大不如前，浮肿的面容让人认不出当年的英俊形象。\",\"media_name\":\"乐天派风声\",\"categories\":[\"news_entertainment/gossip/other\",\"news_entertainment/gossip\",\"news_entertainment/interpretedcontent\",\"news_entertainment\"],\"title\":\"剧组丑闻、娜扎情感争议、顶流竞争：娱乐圈最新八卦热议\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/7c8a189a84bc44816e417caf18d8c967~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-11-29 09:27\",\"url\":\"https://toutiao.com/group/7442179816234680866/\"},{\"time\":\"2025-02-18 09:16\",\"url\":\"https://toutiao.com/group/7472560783541404186/\",\"summary\":\"· 饺子导演被多个账号假冒并带货：随着《哪吒之魔童闹海》票房成绩出色，导演饺子备受关注，多个短视频平台出现仿冒他的账号吸粉带货。2月7日至今，抖音已累计回查处置400余个仿冒导演杨宇（饺子）的账号，目前对新增仿冒行为仍在持续回查及拦截中。\",\"media_name\":\"星闻娱乐\",\"categories\":[\"news_entertainment/other\",\"news_entertainment\"],\"title\":\"国内娱乐八卦新闻：\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/06a7ab64ccf8ae4e226f4dda8cdf1fc1~tplv-vvloioitz3-6:190:124.jpeg\"},{\"title\":\"阿尔卡拉斯首秀后，遭八卦新闻缠身：将和拉杜卡努参加温网混双？\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/6b623e122674bb90b7794292b3d3a4c8~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-06-19 13:27\",\"url\":\"https://toutiao.com/group/7382081678845444660/\",\"summary\":\"就在昨天，2024ATP女王杯男单第一轮中，卫冕冠军、头号种子阿尔卡拉斯克服次盘2:5落后并化解3个盘点，最终以6-1 7-5战胜塞伦多洛，晋级第二轮。尽管这是西班牙人本赛季首次出现在草地上，但他没有表现出任何不适应的迹象，他的状态进入的很快，并且一直保持着很专注的状态。\",\"media_name\":\"网球之家\",\"categories\":[\"news_sports/tennis\",\"news_sports/after_competition\",\"news_sports\"]},{\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/2407880b699b4ff78ea2a62f1bf88091~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-05-24 12:34\",\"url\":\"https://cdsbrss.cdsb.com/toutiao_no_video.php?url=https%3A%2F%2Fstatic.cdsb.com%2Fmicropub%2FArticles%2F202405%2F3d3ac1a9ed6a464f40d02a4e329a0971.html\",\"summary\":\"据西班牙媒体报道，去年因伪造舒马赫采访引起争议的一家德国媒体，现已被判赔偿舒马赫家人20万欧元。德国丰克媒体集团旗下《时事》杂志在2023年4月在封面刊登舒马赫面带微笑的照片，并配以《迈克尔·舒马赫，首个专访!》的头条标题，但字体较小的副标题则注明:“听上去能以假乱真。”\",\"media_name\":\"红星新闻\",\"categories\":[\"news_world/other\",\"news_world\"],\"title\":\"德国八卦杂志发布“舒马赫专访”，现被判赔偿20万欧元\"}]}",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200890,
            "id": "7482695840482476071",
            "role": "assistant",
            "type": "tool_response",
            "updated_at": 1742200890
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "=====\n- 📰 新闻标题: 特大八卦新闻\n- 📝 内容简介: 内娱瓜不断！刘大锤曝光岳岳八天七夜会三女，先去女化妆师家，又和白衣美女街头拥吻，还深夜去女爱豆左卓家。他称是正常交往，网友不买账。上月就有女生爆料被其玩弄感情、睡完消失。\n- 🔗 新闻网址: https://toutiao.com/group/7452683749739610687/\n=====",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200889,
            "id": "7482695835113783308",
            "reasoning_content": "",
            "role": "assistant",
            "type": "answer",
            "updated_at": 1742200894
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "{\"msg_type\":\"generate_answer_finish\",\"data\":\"{\\\"finish_reason\\\":0,\\\"FinData\\\":\\\"\\\"}\",\"from_module\":null,\"from_unit\":null}",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200897,
            "id": "7482695873097449535",
            "role": "assistant",
            "type": "verbose",
            "updated_at": 1742200897
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "新闻推送的频率和时间可以设置吗？",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200897,
            "id": "7482695873097465919",
            "role": "assistant",
            "type": "follow_up",
            "updated_at": 1742200897
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "如何筛选和整理新闻以满足用户的兴趣和需求？",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200897,
            "id": "7482695873097482303",
            "role": "assistant",
            "type": "follow_up",
            "updated_at": 1742200897
        },
        {
            "bot_id": "7482689320084734004",
            "chat_id": "7482695826272272420",
            "content": "有没有其他类型的新闻，如科技、娱乐、体育等？",
            "content_type": "text",
            "conversation_id": "7482692532908965888",
            "created_at": 1742200897,
            "id": "7482695873097498687",
            "role": "assistant",
            "type": "follow_up",
            "updated_at": 1742200897
        }
    ],
    "detail": {
        "logid": "20250323164056E0B7628A48B25E2D458C"
    },
    "msg": ""
}
```

到这里你会发现你已经能通过我们的sdk工具完成coze api的调用，通过sdk实现调用自己定义的新闻推送智能体，完成新闻推送。你可以在自己的业务系统中取出上述json里的`content`值。

上述sdk的使用相当于你在coze官方UI使用自己的智能体：

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6c2f2b1fd4c146efbc16f74496eeb5af.png)

#### 📈 完整代码

这个完整代码是上述步骤代码 的集合：

**注意你需要自己替换里面的参数配置。**

```java
import com.jieke.coze.client.JieKeAiClient;
import java.io.IOException;

public class CozeClientTest {
    private static final String TOKEN = "你自己的token";
    
    public static void main(String[] args) throws IOException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("你自己的botId")
                .userId("123123")
                .build();
        String chatResponse = chatClient.initializeChat();
        System.out.println("初始化聊天响应: " + chatResponse);

        // 2. 查看对话详情
        JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId("你自己的conversation_id")
                .chatId("你自己的chatId")
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);

        // 3. 查看消息列表
        String messageListResponse = conversationClient.getMessageList();
        System.out.println("消息列表响应: " + messageListResponse);
    }
}

```

## 🚀 总结

> OK，上述便是我的开源工具jieke-ai-sdk工具的使用教程了，欢迎大家来使用，也可以帮忙维护哈。

## 💻项目源码

大家可以一起维护：https://github.com/JackieLing/ai-jieke-sdk

- [JackieLing/ai-jieke-sdk: 洁客ai的sdk](https://github.com/JackieLing/ai-jieke-sdk)