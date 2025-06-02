# AI Agent Demo

这是一个模拟 AI 助手类应用的 Android 示例项目，采用 Kotlin 编写，支持 Markdown 渲染、卡片模块、IM 聊天气泡样式、自定义插件渲染等功能。

## ✨ 项目亮点

- 支持 Markdown 格式消息渲染（兼容 CommonMark）
- 实现流式对话渲染体验（simulateStreaming）
- 支持表格、流程图、气泡卡片等扩展渲染
- 自定义视图类型扩展，插件式插拔能力
- IM 对话流样式设计，区分用户与 AI 消息样式
- XML 编写 UI，兼容性强，非 Compose 实现

## 📦 技术栈

- Kotlin
- RecyclerView 多类型适配器
- Markwon Markdown 渲染
- Material Design 悬浮按钮
- 自定义视图 + 插件注册体系

## 📁 使用说明

1. 克隆项目：
    ```bash
    git clone https://github.com/Empty0Qc/AiAgentDemo
    ```

2. 使用 Android Studio 打开并构建运行即可。

---

#### 🇺🇸 English Version 

```markdown
# AI Agent Demo

An Android demo project simulating an AI assistant chat application, built in Kotlin. It features Markdown rendering, card modules, IM-style message bubbles, and plugin-based extensibility.

## ✨ Features

- CommonMark-compatible Markdown rendering
- Simulated streaming-style responses (simulateStreaming)
- Extended support for tables, flowcharts, and custom plugin cards
- Plugin architecture for custom render types
- IM-style chat layout with distinct roles (User vs Assistant)
- XML-based layout (No Jetpack Compose)

## 📦 Tech Stack

- Kotlin
- RecyclerView (multi-type adapter)
- Markwon Markdown rendering
- Material Design FloatingActionButton
- Plugin-based architecture with custom renderers

## 📁 Getting Started

1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/AiAgentDemo.git
    ```

2. Open with Android Studio and run the project.
