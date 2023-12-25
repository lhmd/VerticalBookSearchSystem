const koa = require("koa");
const app = new koa();

const staticfile = require('koa-static')
app.use(staticfile('dist'))

app.listen(6034, '0.0.0.0');