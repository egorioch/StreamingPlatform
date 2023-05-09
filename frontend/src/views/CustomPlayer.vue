<template>
    <div class="video-player" :style="{ width: videoWidth + 'px', height: videoHeight + 'px' }">
        <video ref="video" @click="togglePlayback" src="/video/sunset.mp4">
            <source :src="src" type="video/mp4">
        </video>
        <div class="controls">
            <button @click="togglePlayback">{{ playing ? 'Pause' : 'Play' }}</button>
            <div class="progress-play">
                <span id="progress">
                    <span id="total"> {{ total }}
                        <span id="buffered">
                          <span id="current"></span>
                        </span>
                    </span>
                </span>
                <span id="time">
                    <span id="currentTime">{{ currentVideoTime }}</span>
                    <span id="duration" >{{ videoDuration }}</span>
                </span>
            </div>
            <button @click="toggleSize">{{ resized ? 'Restore Size' : 'Resize' }}</button>
            <button @click="captureFrame">Capture Frame</button>
        </div>
    </div>
</template>

<script>
import {formatTime} from "@/scripts/player_formatting";

export default {
  name: 'VideoPlayer',
  props: {
    src: {type: String, required: true, default: '/video/sunset.mp4'},
    width: {type: Number, default: 720},
    height: {type: Number, default: 480}
  },
  data() {
    return {
      video: this.$refs.video,
      playing: false,
      resized: false,
      videoWidth: this.width,
      videoHeight: this.height,
      //элементы плеера
      total: '',
      buffered: '',
      progress: '',
      videoDuration: '',
      currentVideoTime: '',
      hasHours: false,
    }
  },
  created() {
    (async () => {
      const response = await fetch('http://localhost:8080/video/sunset/info', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json'
        }
      });

      if (response.ok) {
        let ans = await response.json();
        console.log(JSON.stringify(ans));

        this.videoDuration = ans.duration;
        this.hasHours = (this.videoDuration / 3600) >= 1.0;
        this.videoDuration = formatTime(this.videoDuration, this.hasHours);

        this.size = ans.size;

        console.log('dura: ' + this.videoDuration);

      }
    })()
  },
  mounted() {
    this.$refs.video.addEventListener('canplay', function () {
      this.hasHours = (this.duration / 3600) >= 1.0;
      // secondsToTime(this.duration);
      this.videoDuration = formatTime(this.duration, this.hasHours);

    }, false);

    this.$refs.video.addEventListener('timeupdate', function() {
      document.getElementById('currentTime').textContent = this.currentTime + '';
      console.log(this.currentTime);
    })
  },

  methods: {
    togglePlayback() {
      if (this.playing) {
        this.$refs.video.pause();
      } else {
        this.$refs.video.play();
      }
      this.playing = !this.playing;
    },
    toggleSize() {
      if (this.resized) {
        this.videoWidth = this.width;
        this.videoHeight = this.height;
      } else {
        this.videoWidth = window.innerWidth;
        this.videoHeight = window.innerHeight;
      }
      this.resized = !this.resized;
    },
    captureFrame() {
      const canvas = document.createElement('canvas');
      canvas.width = this.videoWidth;
      canvas.height = this.videoHeight;
      const ctx = canvas.getContext('2d');
      ctx.drawImage(this.$refs.video, 0, 0, this.videoWidth, this.videoHeight);
      const link = document.createElement('a');
      link.download = 'frame.png';
      link.href = canvas.toDataURL();
      link.click();
    }
  }
}
</script>

<style scoped>
.video-player {
    position: relative;
}

.video-player video {
    width: 100%;
    height: 100%;
    object-fit: cover;
}

.controls {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 10px;
}

#progress {
    width: 290px;
}

#total {
    width: 100%;
    background: #999;
}

#buffered {
    background: #ccc;
}

#current {
    background: #eee;
    line-height: 0;
    height: 10px;
}

#time {
    color: #999;
    font-size: 12pt;
}

.controls button {
    margin: 0 10px;
}
</style>