const express = require('express');
const cors = require('cors');
const app = express();
const PORT = 3000;

app.get('/sse', cors(), (req, res) => {
  res.set({
    'Content-Type': 'text/event-stream',
    'Cache-Control': 'no-cache',
    'Connection': 'keep-alive'
  });
  res.flushHeaders();

  const messages = [
    {type: 'stage', content: '🧠 思考开始...', stage: 'thought', event: 'start', role: 'bot'},
    {type: 'markdown', content: '- 我正在分析你的打车需求...', stage: 'thought', event: 'stream', append: true, role: 'bot'},
    {type: 'card', content: '{"title":"推荐打车点","desc":"国贸地铁东口","cta":"导航"}', stage: 'thought', event: 'stream', role: 'bot'},
    {type: 'stage', content: '✅ 思考结束', stage: 'thought', event: 'end', role: 'bot'},
    {type: 'stage', content: '💬 开始正文...', stage: 'answer', event: 'start', role: 'bot'},
    {type: 'markdown', content: '- 步行 100m 至国贸东口', stage: 'answer', event: 'stream', append: true, role: 'bot'},
    {type: 'card', content: '{"title":"快车推荐","desc":"约 15 元，2 分钟内到达","cta":"呼叫快车"}', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'markdown', content: '### 🚕 附加出行建议 \n - 可以考虑地铁：国贸站换乘1号线 \n - 高峰期建议错峰打车', stage: 'answer', event: 'stream', append: true, role: 'bot'},
    {type: 'card', content: '{"title":"共享单车","desc":"2 分钟可骑行至地铁口","cta":"扫码骑行"}', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'table', content: '{"header":["方案","价格","时间"],"rows":[["快车","15元","5分钟"],["地铁","3元","10分钟"]]}', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'flowchart', content: 'https://i.miji.bid/2025/06/02/c9b601d0baf5099d79c8982ac323b152.jpeg', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'custom_card', content: '{"title":"AI 建议","desc":"当前天气良好，适合步行","cta":"查看天气"}', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'markdown', content: '#### ✅ 小贴士 \n - 可收藏常用出发点 \n - 避免高峰期等待', stage: 'answer', event: 'stream', role: 'bot'},
    {type: 'done', content: '✅ 回复结束', role: 'bot'}
  ];

  let i = 0;
  const interval = setInterval(() => {
    const msg = messages[i];
    res.write(`data: ${JSON.stringify(msg)}\n\n`);
    i++;
    if (i >= messages.length) {
      clearInterval(interval);
      res.write('event: end\ndata: [DONE]\n\n');
    }
  }, 1000);

  req.on('close', () => {
    clearInterval(interval);
  });
});

app.listen(PORT, () => {
  console.log('SSE server listening on port', PORT);
});
