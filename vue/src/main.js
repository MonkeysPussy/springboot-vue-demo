import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'

import '@/assets/css/globel.css'
// 统一导入el-icon图标
import * as ElIconModules from '@element-plus/icons-vue'
const app = createApp(App)

// 统一注册el-icon图标
for(let iconName in ElIconModules){
    app.component(iconName,ElIconModules[iconName])
}

// createApp(App).use(store).use(ElementPlus).use(ElementPlus, {locale: zhCn, size: 'small'}).use(router).mount('#app')
app.use(store).use(ElementPlus).use(ElementPlus, {locale: zhCn, size: 'small'}).use(router).mount('#app')