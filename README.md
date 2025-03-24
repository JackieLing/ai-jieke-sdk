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

- 0.2
  - 修复bug，传参错误已修复
  - 简化了调用流程，只需要提供`token`、`BotId`、`userID`即可

- 0.0.1
  - 能够调用coze平台自定义的智能体

## 🛠快速入门

### 💻 引入依赖

首先引入这个开源工具的POM依赖

```xml
 <dependency>
     <groupId>io.github.jackieling</groupId>
     <artifactId>jieke-ai-sdk</artifactId>
     <version>0.2</version>
 </dependency>
```

### 💻 获取Coze token

等录coze官网：[主页 - 扣子](https://www.coze.cn/home)

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/743cacacf6a746d383850fd74e8c4657.png)

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/38d79d894ef641bf853d56c136bff4db.png)

点击确定以后，这里可以获取到token值，请务必保存好这个token，后面会用到。

### 💻 创建智能体

在coze官网创建一个智能体，这里我准备用**新闻推送**这个案例做演示。创建一个新闻推送的demo智能体：

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6c2f2b1fd4c146efbc16f74496eeb5af.png)

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

#### 📈 初始化聊天+查看对话详情+获取消息列表

我们在引入jieke-ai-sdk以后首先需要初始化聊天，可以直接用 `new JieKeAiClient.Builder`初始化。

初始化一定需要用到我们的 `token` 和 `botId`。 至于userId你可以自己定义。

```java
JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("写你的botId")
                .userId("123123")
    			.additionalMessage(Message.builder()
                        .contentType("text")
                        .content("人工智能")
                        .role("user")
                        .type("question")
                        .build())
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
     private static final String TOKEN = "填你自己的token";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("填你自己的botId")
                .userId("123123")
                .additionalMessage(Message.builder()
                        .contentType("text")
                        .content("人工智能")
                        .role("user")
                        .type("question")
                        .build())
                .build();
        String chatResponse = chatClient.initializeChat();
        Gson gson = new Gson();
        ChatResponse response = gson.fromJson(chatResponse, ChatResponse.class);

        // 获取 id
        String id = response.getData().getId();
        String conversationId = response.getData().getConversation_id();
        log.info("智能体SDK收到参数---->chatId:{}",id);
        log.info("智能体SDK收到参数---->conversation_Id:{}",conversationId);

        Thread.sleep(6000);

        // 2. 查看对话详情
        JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId(conversationId)
                .chatId(id)
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);

//         3. 查看消息列表
        String messageListResponse = conversationClient.getMessageList();
        System.out.println("消息列表响应: " + messageListResponse);
    }
}
```

初始化聊天以后会得到如下结果：

```xml
Request Body: {"additional_messages":[{"role":"user","content_type":"text","type":"question","content":"人工智能"}],"user_id":"123123","bot_id":"7485191777520009228"}
10:59:57.164 [main] INFO com.linghu.Demo - 智能体SDK收到参数---->chatId:7485205416863924234
10:59:57.166 [main] INFO com.linghu.Demo - 智能体SDK收到参数---->conversation_Id:7485205416863907850
对话详情响应: {"code":0,"data":{"bot_id":"7485191777520009228","conversation_id":"7485205416863907850","created_at":1742785197,"id":"7485205416863924234","status":"in_progress"},"detail":{"logid":"202503241100035C19ED03C3B7801A4CDF"},"msg":""}
消息列表响应: {"code":0,"data":[{"bot_id":"7485191777520009228","chat_id":"7485205416863924234","content":"{\"name\":\"toutiaoxinwen-getToutiaoNews\",\"arguments\":{\"q\":\"人工智能最新新闻\"},\"plugin_id\":7362080779243094070,\"plugin_name\":\"toutiaoxinwen\",\"api_id\":7362080779243110454,\"api_name\":\"getToutiaoNews\",\"plugin_type\":1}","content_type":"text","conversation_id":"7485205416863907850","created_at":1742785199,"id":"7485205429807497243","role":"assistant","type":"function_call","updated_at":1742785199},{"bot_id":"7485191777520009228","chat_id":"7485205416863924234","content":"{\"news\":[{\"media_name\":\"全国党媒信息公共平台\",\"categories\":[\"science_all/other\",\"news_finance/other\",\"news_tech/artificial_intelligence\",\"news_tech\",\"news_finance\",\"science_all\"],\"title\":\"2024世界人工智能大会：从“+AI”到“AI+”，新技术重塑千行百业\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/03151cb5cfb34300bdd4ab4894d7bd9c~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-07-06 10:37\",\"url\":\"https://api-m.hubpd.com/transfer?nextUrl=https%3A%2F%2Fwww.hubpd.com%2Fhubpd%2Frss%2Ftoutiao%2Findex.html\u0026contentId=8358680908402631382\",\"summary\":\"来源：【人民网】人民网上海7月5日电 （记者葛俊俊、董志雯、王文娟）7月4日，2024世界人工智能大会暨人工智能全球治理高级别会议在上海世博中心开幕。\"},{\"title\":\"芯片、算法、数据多管齐下 人工智能产业加速实现核心技术自主可控\",\"cover\":\"\",\"time\":\"2025-03-17 09:36\",\"url\":\"https://3w.huanqiu.com/a/2ac16b/4LtYFp07AsP?agt=143\",\"summary\":\"来源：证券日报 原标题：芯片、算法、数据多管齐下 人工智能产业加速实现核心技术自主可控科技创新的宏伟蓝图正加速绘就。2025年《政府工作报告》（以下简称《报告》）明确提出，推进高水平科技自立自强。\",\"media_name\":\"环球网\",\"categories\":[\"news_finance/other\",\"news_tech/artificial_intelligence\",\"news_tech\",\"news_finance\"]},{\"cover\":\"\",\"time\":\"2024-07-02 07:35\",\"url\":\"https://m.thepaper.cn/newsDetail_forward_27918678?from=toutiao\",\"summary\":\"2024世界人工智能大会暨人工智能全球治理高级别会议（简称“WAIC 2024”）即将于7月4日开幕，围绕核心技术、智能终端、应用赋能三大板块，大会将为观众带来众多首发新秀和打卡亮点。据主办方介绍，今年的大会展览持续扩容升级，展览面积超5.\",\"media_name\":\"澎湃新闻\",\"categories\":[\"news_finance/other\",\"news_tech/artificial_intelligence\",\"news_tech\",\"news_finance\"],\"title\":\"2024世界人工智能大会五大看点：25款人形机器人亮相，大模型继续“涌现”\"},{\"url\":\"http://m.ce.cn/ttt/202503/24/t20250324_39328206.shtml\",\"summary\":\"来源：经济日报近年来，人工智能赋能各行各业蓬勃发展，成为推动经济高质量增长的重要力量。从传统制造业到现代服务业，从能源领域到医疗健康，人工智能的应用场景不断拓展，为产业发展带来了前所未有的变革。助推传统产业升级在传统产业中，人工智能的应用推动了生产流程的智能化升级。\",\"media_name\":\"中国经济网\",\"categories\":[\"news_finance/other\",\"news_tech/artificial_intelligence\",\"news_tech\",\"news_finance\"],\"title\":\"“人工智能+”赋能千行百业\",\"cover\":\"\",\"time\":\"2025-03-24 06:49\"},{\"media_name\":\"澎湃新闻\",\"categories\":[\"news_finance/other\",\"news_tech/artificial_intelligence\",\"news_tech\",\"news_finance\"],\"title\":\"2024世界人工智能大会将于7月4日开幕，展品数量超1500项\",\"cover\":\"https://p6-img.searchpstatp.com/tos-cn-i-vvloioitz3/0aec21c057124ee2c5307572804070d7~tplv-vvloioitz3-6:190:124.jpeg\",\"time\":\"2024-06-20 10:43\",\"url\":\"https://m.thepaper.cn/newsDetail_forward_27795856?from=toutiao\",\"summary\":\"6月20日上午，上海市政府新闻办举行新闻发布会，介绍2024世界人工智能大会暨人工智能全球治理高级别会议筹备进展情况，并回答记者提问。新闻发布会现场。澎湃新闻记者 俞凯 摄澎湃新闻（thepaper.\"}]}","content_type":"text","conversation_id":"7485205416863907850","created_at":1742785200,"id":"7485205429807726619","role":"assistant","type":"tool_response","updated_at":1742785200}],"detail":{"logid":"2025032411000301921ACC8CFA3E3EE977"},"msg":""}

```

我们需要的值是：

- id
- conversation_id

我们一定要保存好上面这两个值，其中status表示我们的智能体会话创建状态。

> 你可以根据你的业务系统的需要，去获取响应的信息。

上述sdk的使用相当于你在coze官方UI使用自己的智能体：

![在这里插入图片描述](https://i-blog.csdnimg.cn/direct/6c2f2b1fd4c146efbc16f74496eeb5af.png)

#### 📈 完整代码

这个完整代码是上述步骤代码 的集合：

**注意你需要自己替换里面的参数配置。**

```java
package com.linghu;

import com.google.gson.Gson;
import com.jieke.coze.client.JieKeAiClient;
import com.jieke.coze.model.Message;
import com.jieke.coze.model.response.ChatResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @Author: linghu
 * @CreateTime: 2025-03-24
 * @Description: 测试用
 */
@Slf4j
public class Demo {
    private static final String TOKEN = "你自己的token";

    public static void main(String[] args) throws IOException, InterruptedException {
        // 1. 初始化聊天
        JieKeAiClient chatClient = new JieKeAiClient.Builder(TOKEN)
                .botId("你自己的botId")
                .userId("123123")
                .additionalMessage(Message.builder()
                        .contentType("text")
                        .content("人工智能")//填写关键词
                        .role("user")
                        .type("question")
                        .build())
                .build();
        String chatResponse = chatClient.initializeChat();
        Gson gson = new Gson();
        ChatResponse response = gson.fromJson(chatResponse, ChatResponse.class);

        // 获取 id
        String id = response.getData().getId();
        String conversationId = response.getData().getConversation_id();
        log.info("智能体SDK收到参数---->chatId:{}",id);
        log.info("智能体SDK收到参数---->conversation_Id:{}",conversationId);

        Thread.sleep(6000);

        // 2. 查看对话详情
        JieKeAiClient conversationClient = new JieKeAiClient.Builder(TOKEN)
                .conversationId(conversationId)
                .chatId(id)
                .build();
        String conversationResponse = conversationClient.retrieveChat();
        System.out.println("对话详情响应: " + conversationResponse);

//         3. 查看消息列表
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