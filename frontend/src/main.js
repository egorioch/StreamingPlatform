import '@babel/polyfill'
import {createApp} from 'vue'
import App from './App.vue'
import router from './router/router'
import 'bootstrap/scss/bootstrap.scss'
import 'lightbox2/src/css/lightbox.css'

createApp(App)
  .use(router)
  .mount('#app')


import 'bootstrap/dist/js/bootstrap'
import 'lightbox2/src/js/lightbox'
