import {createRouter, createWebHistory} from 'vue-router'
import StreamShotView from "@/views/StreamShotView.vue";
// import VideoPlayer from "@/views/VideoPlayer.vue"
import AbstractPlayer from "@/views/AbstractPlayer.vue";

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/shots",
      name: 'StreamShow',
      component: StreamShotView
    },
    {
      path: "/video/:title",
      name: 'AbstractPlayer',
      component: AbstractPlayer
    }

  ]
})