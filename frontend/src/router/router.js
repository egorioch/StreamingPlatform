import {createRouter, createWebHistory} from 'vue-router'
import AbstractPlayer from "@/views/AbstractPlayer.vue";
import ScreenshotPage from "@/views/ScreenshotPage.vue"
import TestComponent from "@/views/TestComponent.vue";

export default createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: "/video/:title",
      name: 'AbstractPlayer',
      component: AbstractPlayer
    },
    {
      path: "/video/screens",
      name: 'ScreenshotPage',
      component: ScreenshotPage
    },
    {
      path: "/test",
      name: "TestComponent",
      component: TestComponent
    }

  ]
})