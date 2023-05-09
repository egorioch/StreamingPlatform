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
      videoType: '',
      time: null,
      size: null,
      start: null,
      end: null,
      chunk: 1024
    }
  },
  mounted() {
    // const video = this.$refs.videoPlayer;
    (async () => {
      if (this.time == null && this.size == null) {
        const response = await fetch('http://localhost:8080/video/sunset/info', {
          method: 'GET',
          headers: {
            'Content-Type': 'application/json'
          }
        });

        let ans = await response.json();
        console.log(JSON.stringify(ans));

        this.time = ans.duration;
        this.size = ans.size;
        let x = (this.offsetLeft)/$(this).width;
      }
    })();

    this.$refs.videoPlayer.addEventListener('timeupdate', function() {

    });
  },
  methods: {
    async playVideo() {
      const video = this.$refs.videoPlayer

      if (this.start == null && this.end == null) {
        this.start = 0;
        this.end = 0;
        console.log('start-овые значения установлены!')
      }

      this.end = this.size / 2 - 2;
      //video.buffered[0] = (this.size / 2);

      const response = await axios.get('http://localhost:8080/video/sunset', {
        //headers: {Range: `bytes=0-`},
        responseType: 'arraybuffer'
      });

      console.log('responseData: ' + response.data)
      const videoBlob = new Blob([response.data], {type: 'video/mp4'});
      const videoUrl = URL.createObjectURL(videoBlob);
      console.log("videoUrl:" + videoUrl);
      this.videoUrl = videoUrl;
      this.$refs.videoPlayer.src = videoUrl;
      console.log('buffered часть: ' + video.played.length);
      if (video.canPlayType('video/mp4')) {
        this.videoType = 'video/mp4';
      } else if (video.canPlayType('video/webm')) {
        this.videoType = 'video/webm';
      }
      console.log('videoType: ' + this.videoType);

      let playPromise = document.querySelector('video').play();
      if (playPromise !== undefined) {
        playPromise.then(function () {

        }).catch(function (error) {
          console.log(error)
        });
      }
    }
  }
}
</script>