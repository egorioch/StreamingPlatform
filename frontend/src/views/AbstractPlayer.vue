<template>
    <div class="video-player">
        <video
                ref="video"
                src="/video/sunset.mp4"
                poster="/mine.png"
                class="video"
        ></video>

        <div class="player-controls">
            <div class="video-progress" @click="progress($event)">
                <div class="video-progress-filled"></div>
            </div>

            <button class="play-button" title="Play" @click="play($event)">►</button>
            <button class="button" @click="screenshot()">Screenshot</button>


            <input
                    type="range"
                    class="volume"
                    min="0"
                    max="1"
                    step="0.01"
                    value="1"
                    @mousemove="volume($event)"
            />

            <div class="time">
                <span class="current" id="currentTime">{{ videoCurrentTime }}</span>
                /
                <span class="duration" id="duration">{{ videoDuration }}</span>
            </div>
        </div>
    </div>

    <div id="my_pic"></div>
</template>

<script>
import {formatTime} from "@/scripts/player_formatting";
// import {loadPictureToServer} from "@/scripts/server";

export default {
  data() {
    return {
      videoDuration: '00:00',
      videoCurrentTime: '00:00'
    }
  },
  mounted() {
    this.$refs.video.addEventListener('canplay', function () {
      let hasHours = (this.duration / 3600) >= 1.0;
      document.getElementById('duration').textContent = formatTime(this.duration, hasHours);
    });

    this.$refs.video.addEventListener('timeupdate', function () {
      this.videoCurrentTime = this.currentTime;
      let hasHours = (this.currentTime / 3600) >= 1.0;
      document.getElementById('currentTime').textContent = formatTime(this.currentTime, hasHours)
    });

    this.$refs.video.addEventListener('timeupdate', function () {
      const percentage = (this.currentTime / this.duration) * 100
      document.getElementsByClassName('video-progress-filled')[0].style.width = `${percentage}%`
    });

  },
  methods: {
    //Play and Pause button
    play(e) {
      if (this.$refs.video.paused) {
        this.$refs.video.play()
        e.target.textContent = '❚ ❚'
      } else {
        this.$refs.video.pause()
        e.target.textContent = '►'
      }
    },
    volume(e) {
      this.$refs.video.volume = e.target.value
    },
    //изменение progress-бара при нажатии
    progress(e) {
      console.log('offsetx: ' + e.offsetX);
      let offsetWidth = document.getElementsByClassName('video-progress')[0].offsetWidth;
      const progressTime = (e.offsetX / offsetWidth) * this.$refs.video.duration
      this.$refs.video.currentTime = progressTime
    },
    async screenshot() {
      let canvas = document.createElement('canvas');
      canvas.width = 720;
      console.log('width: ' + canvas.width);
      canvas.height = 480;
      let ctx = canvas.getContext('2d');
      ctx.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height);
      document.getElementById('my_pic').appendChild(ctx.canvas);
      let currentTime = this.$refs.video.currentTime + "";

      let imageBlob = await new Promise(resolve => canvas.toBlob(resolve, 'image/jpeg'));
      let formData = new FormData();
      formData.append("image", imageBlob, "image.png");

      await fetch(`http://localhost:8080/video/${currentTime}/load`, {
        method: 'POST',
        body: formData,
        // headers: {
        //   'Content-Type': 'multipart/form-data'
        // }
      });
      //loadPictureToServer(dataURI, this.$refs.video.currentTime);

    }
  }
}


</script>

<style>
html {
    box-sizing: border-box;
}

*,
*:before,
*:after {
    box-sizing: inherit;
}

body {
    padding: 0;
    display: flex;
    /*background: 333;*/
    min-height: 100vh;
    /*background: linear-gradient(135deg, #000000 0%, #333 48%, #686868 100%);*/
    background-size: cover;
    align-items: center;
    justify-content: center;
}

.video-player {
    max-width: 750px;
    position: relative;
    overflow: hidden;
}

.video {
    width: 100%;
}

.player-controls {
    display: flex;
    position: absolute;
    bottom: 0;
    width: 100%;
    transform: translateY(100%) translateY(-5px);
    transition: 0.3s;
    flex-wrap: wrap;
    background: rgba(0, 0, 0, 0.6);
}

.video-player:hover .player-controls {
    transform: translateY(0);
}

.play-button {
    width: 10%;
    border: none;
    background: black;
    color: white;
    padding: 10px;
}

.play-button:focus {
    outline: none;
}

.time {
    font-size: 1em;
    color: white;
    width: 15%;
    text-align: center;
    font-family: Arial, Helvetica, sans-serif;
    display: flex;
    align-items: center;
    justify-content: center;
}

input[type="range"] {
    -webkit-appearance: none;
    background: transparent;
    margin: 0;
    width: 55%;
    padding: 0 10px;
}

input[type="range"]:focus {
    outline: none;
}

input[type="range"]::-webkit-slider-runnable-track {
    width: 100%;
    height: 10px;
    cursor: pointer;
    background: black;
}

input[type="range"]::-webkit-slider-thumb {
    -webkit-appearance: none;
    height: 10px;
    width: 10px;
    background: orangered;
    cursor: pointer;
}

.video-progress {
    position: relative;
    display: flex;
    width: 100%;
    height: 5px;
    transition: 0.3s;
    background: rgba(0, 0, 0, 0.6);
    cursor: pointer;
}

.video-progress-filled {
    width: 0;
    background: orangered;
}

.video-player:hover .video-progress {
    height: 20px;
}

</style>