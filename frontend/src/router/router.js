import {createRouter, createWebHistory} from 'vue-router'
import StreamShotView from "@/views/StreamShotView.vue";
import VideoPlayer from "@/views/VideoPlayer.vue"

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
      name: 'VideoPlayer',
      component: VideoPlayer
    }

  ]
})