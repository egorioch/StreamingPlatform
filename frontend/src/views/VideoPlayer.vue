<template>
    <div class="container">
        <div class="video-player">
            <video ref="videoPlayer" controls muted="muted" autoplay width="650">
                <source :src="videoUrl" :type="videoType">
            </video>
            <button v-on:click="playVideo()">Play</button>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      videoUrl: '',
      videoType: ''
    }
  },
  mounted() {
    const video = this.$refs.videoPlayer;

    video.addEventListener('timeupdate', async () => {
      const duration = video.duration;
      console.log('duration: ' + duration);
      const currentTime = video.currentTime;
      console.log('currentTime: ' + currentTime);
      let bufferedEnd = 0;
      if (currentTime !== 0) {
        bufferedEnd = video.buffered.end(0);
      }
      console.log('bufferedEnd: ' + bufferedEnd);

      if (bufferedEnd - currentTime < 10) {
        const response = await fetch('/video/sunset', {
          headers: {
            Range: `bytes=${bufferedEnd}-`
          }
        });

        const buffer = await response.arrayBuffer();
        const videoSourceBuffer = videoSourceBuffer || new MediaSource().addSourceBuffer('video/mp4; codecs="avc1.42E01E, mp4a.40.2"');
        videoSourceBuffer.appendBuffer(buffer);
      }
    });
  },
  methods: {
    async playVideo() {
      const response = await axios.get('http://localhost:8080/video/sunset', {
        headers: {Range: 'bytes=0-12900'},
        responseType: 'arraybuffer'
      });

      console.log('responseData: ' + response.data)
      const videoBlob = new Blob([response.data], {type: 'video/mp4'});
      const videoUrl = URL.createObjectURL(videoBlob);
      console.log("videoUrl:" + videoUrl);
      this.videoUrl = videoUrl;
      this.$refs.videoPlayer.src = videoUrl;
      const video = this.$refs.videoPlayer;
      if (video.canPlayType('video/mp4')) {
        this.videoType = 'video/mp4';
      } else if (video.canPlayType('video/webm')) {
        this.videoType = 'video/webm';
      }
      console.log('videoType: ' + this.videoType);

      await video.play();
    }
  }
}
</script>