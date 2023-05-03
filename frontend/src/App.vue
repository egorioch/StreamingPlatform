<template>
    <div class="container-fluid">
    <navbar-view></navbar-view>
    <video-player></video-player>
    </div>
</template>

<script>
import NavbarView from "@/views/NavbarView.vue";
import VideoPlayer from "@/views/VideoPlayer.vue";

export default {
    components: {NavbarView, VideoPlayer}
}
</script>