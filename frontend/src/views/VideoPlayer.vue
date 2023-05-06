<!--<template>-->
<!--  <div>{{address.title}}</div>-->
<!--  <div class="container mt-5">-->
<!--      <video src="/video/sunset.mp4" width="720px" height="480px" controls preload="none"></video>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->

<!--export default {-->
<!--    data() {-->
<!--        return {-->
<!--            address: this.$route.params,-->
<!--            video: {}-->
<!--        }-->
<!--    },-->
<!--    //  async created() {-->
<!--    //     const header = this.request.headers.get('range');-->
<!--    //-->
<!--    //     let resp = await fetch(url + this.address.title, {-->
<!--    //         headers: {'Range': header},-->
<!--    //         method: "GET",-->
<!--    //     })-->
<!--    //-->
<!--    //      if(resp.ok) {-->
<!--    //          const videoBlob = await resp.blob();-->
<!--    //          const videoUrl = URL.createObjectURL(videoBlob);-->
<!--    //-->
<!--    //          const video = document.createElement('video');-->
<!--    //          video.src = videoUrl;-->
<!--    //-->
<!--    //          document.body.appendChild(video);-->
<!--    //      }  else {-->
<!--    //          console.log('Ошибка загрузки видео');-->
<!--    //      }-->
<!--    //         /*.then(response => response.blob())-->
<!--    //         .then(blob => {-->
<!--    //             const videoUrl = URL.createObjectURL(blob);-->
<!--    //             const video = document.createElement('video');-->
<!--    //             video.src = videoUrl;-->
<!--    //             document.body.appendChild(video);-->
<!--    //         })-->
<!--    //         .catch(error => console.log(error));*/-->
<!--    //-->
<!--    //     console.log("resp: " + resp);-->
<!--    // }-->

<!--    async created() {-->
<!--        const url = 'http://localhost:8080/video/';-->
<!--        const rangeHeader = 'bytes=0-';-->
<!--        const headers = new Headers();-->
<!--        headers.append('Range', rangeHeader);-->
<!--        const response = await fetch(url + this.address.title, { headers });-->
<!--        if (response.ok) {-->
<!--            const contentLength = response.headers.get('Content-Length');-->
<!--            const contentType = response.headers.get('Content-Type');-->
<!--            const contentRange = response.headers.get('Content-Range');-->
<!--            const videoBlob = await response.blob();-->
<!--            const videoUrl = URL.createObjectURL(videoBlob);-->
<!--            const video = document.createElement('video');-->
<!--            video.src = videoUrl;-->
<!--            video.type = contentType;-->
<!--            video.setAttribute('data-content-range', contentRange);-->
<!--            video.setAttribute('data-content-length', contentLength);-->
<!--            this.videoElement = video;-->
<!--        } else {-->
<!--            console.error(`Failed to fetch video: ${response.status} ${response.statusText}`);-->
<!--        }-->
<!--    }-->
<!--}-->
<!--</script>-->

<template>
    <div>
<!--        <video ref="videoPlayer" controls></video>-->
<!--        <button @click="playVideo">Play</button>-->
<!--        <button @click="pauseVideo">Pause</button>-->
<!--        <button @click="toggleFullScreen">Toggle Full Screen</button>-->
<!--        <button @click="captureFrame">Capture Frame</button>-->
    </div>
</template>

<script>
import {playVideo} from '/src/scripts/bytes_range';

export default {
    data() {
        return {
            videoUrl: '',
            videoSource: null,
            videoPlayer: null
        };
    },
    async created() {
        //const title = 'video/sunset';
        const url = `http://localhost:8080/video/sunset`;
/*

        const response = await fetch(url, {
            headers: { 'Range': 'bytes=0-' },
            method: 'GET',
        });

        if (response.ok) {
            const contentType = response.headers.get('Content-Type');
            console.log('CONTENT-TYPE:' + contentType);
            this.videoUrl = URL.createObjectURL(await response.blob());
            this.videoSource = { type: contentType, src: this.videoUrl };
        } else {
            console.error('Failed to retrieve video');
        }*/
        await playVideo(url)
    },

    /*mounted() {
        this.videoPlayer = this.$refs.videoPlayer;
        this.videoPlayer.src = this.videoSource;
    },
    methods: {
        playVideo() {
            this.videoPlayer.play();
        },
        pauseVideo() {
            this.videoPlayer.pause();
        },
        toggleFullScreen() {
            if (document.fullscreenElement) {
                document.exitFullscreen();
            } else {
                this.$refs.videoPlayer.requestFullscreen();
            }
        },
        captureFrame() {
            const canvas = document.createElement('canvas');
            canvas.width = this.videoPlayer.videoWidth;
            canvas.height = this.videoPlayer.videoHeight;
            const context = canvas.getContext('2d');
            context.drawImage(this.videoPlayer, 0, 0, canvas.width, canvas.height);
            const dataUrl = canvas.toDataURL('image/png');
            const link = document.createElement('a');
            link.download = 'mine.png';
            link.href = dataUrl;
            link.click();
        },
    },*/
};
</script>