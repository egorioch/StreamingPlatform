import {createRouter, createWebHistory} from 'vue-router'
import StreamShotView from "@/views/StreamShotView.vue";

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/shots",
      name: 'StreamShow',
      component: StreamShotView
    }
  ]
})