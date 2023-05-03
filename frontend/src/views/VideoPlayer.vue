<template>
  <div class="video-player">
    <video ref="videoPlayer" :src="videoSrc"></video>
    <div class="video-controls">
      <button @click="togglePlay">{{ isPlaying ? 'Pause' : 'Play' }}</button>
      <button @click="toggleFullScreen">{{ isFullScreen ? 'Exit Fullscreen' : 'Fullscreen' }}</button>
      <button @click="saveFrame">Save Frame</button>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    videoSrc: String,
  },
  data() {
    return {
      isPlaying: false,
      isFullScreen: false,
    };
  },
  methods: {
    togglePlay() {
      const video = this.$refs.videoPlayer;
      this.isPlaying ? video.pause() : video.play();
      this.isPlaying = !this.isPlaying;
    },
    toggleFullScreen() {
      const video = this.$refs.videoPlayer;
      this.isFullScreen ? document.exitFullscreen() : video.requestFullscreen();
      this.isFullScreen = !this.isFullScreen;
    },
    saveFrame() {
      const video = this.$refs.videoPlayer;
      const canvas = document.createElement('canvas');
      canvas.width = video.videoWidth;
      canvas.height = video.videoHeight;
      canvas.getContext('2d').drawImage(video, 0, 0, canvas.width, canvas.height);
      const image = canvas.toDataURL('image/png');
      const link = document.createElement('a');
      link.href = image;
      link.download = 'video-frame.png';
      link.click();
    },
  },
};
</script>

<style>
.video-player {
  position: relative;
  width: 100%;
  height: 0;
  padding-bottom: 56.25%; /* 16:9 aspect ratio */
}

.video-player video {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.video-controls {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  justify-content: center;
  padding: 10px;
  background-color: rgba(0, 0, 0, 0.5);
}

.video-controls button {
  margin: 0 10px;
  padding: 5px 10px;
  font-size: 16px;
  color: white;
  background-color: transparent;
  border: 1px solid white;
  border-radius: 5px;
  cursor: pointer;
}

.video-controls button:hover {
  background-color: white;
  color: black;
}
</style>






