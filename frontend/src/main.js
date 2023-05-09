import '@babel/polyfill'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router/router'
import 'bootstrap/scss/bootstrap.scss'

createApp(App)
    .use(router)
    .mount('#app')

