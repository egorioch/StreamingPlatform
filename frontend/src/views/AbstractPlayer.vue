<template>
    <div class="abstract-video-player">
        <div class="video-player">
            <video
                    ref="video"
                    poster="/shrek.png"
                    class="video"
                    id="content-video"
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

                <button class="fullscreen-button" @click="fullscreen()">
                    <span class="fullscreen-text">[]</span>
                </button>
            </div>

        </div>

    </div>
</template>

<script>
import {formatTime} from "@/scripts/player_formatting";
import {loadPictureToServer} from "@/scripts/server";

export default {
    data() {
        return {
            videoDuration: '00:00',
            videoCurrentTime: '00:00',
            canvasWidth: 0,
            canvasHeight: 0,
        }
    },
    mounted() {
        /**
         * возвращаем видео для конкретной страницы
         */
        document.addEventListener('DOMContentLoaded', function () {
            const address = window.location.pathname.split("/").pop();
            fetch(`http://localhost:8080/video/${address}`, {
                headers: {
                    'Range': 'bytes=0-'
                }
            })
                .then(resp => resp.arrayBuffer())
                .then(videoBytes => {
                    const blob = new Blob([videoBytes], {type: 'video/mp4'})
                    let videoUrl = URL.createObjectURL(blob);
                    document.getElementById('content-video').src = videoUrl;
                })
                .catch(err => "error: " + err)
        });

        //
        this.$refs.video.addEventListener('loadedmetadata', function () {
            this.canvasWidth = this.videoWidth;
            this.canvasHeight = this.videoHeight;
        })

        //установка длительности видео
        this.$refs.video.addEventListener('canplay', function () {
            document.getElementById('duration').textContent = formatTime(this.duration);
        });

        //обновление времени по событию "timeupdate"
        this.$refs.video.addEventListener('timeupdate', function () {
            this.videoCurrentTime = this.currentTime;
            document.getElementById('currentTime').textContent = formatTime(this.currentTime)
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
                e.target.textContent = '❚❚'
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
            let offsetWidth = document.getElementsByClassName('video-progress')[0].offsetWidth;
            const progressTime = (e.offsetX / offsetWidth) * this.$refs.video.duration
            this.$refs.video.currentTime = progressTime;
        },

        /**
         * создание холста по событию нажатия на кнопку 'Screenshot'
         * отправка данных в бд
         */
        screenshot() {
            let canvas = document.createElement('canvas');
            canvas.width = 1280;
            canvas.height = 720;

            let ctx = canvas.getContext('2d');

            ctx.drawImage(this.$refs.video, 0, 0, canvas.width, canvas.height);

            // document.getElementById('my_pic').appendChild(ctx.canvas);

            let currentTime = this.$refs.video.currentTime.toPrecision(4) + "";
            loadPictureToServer(canvas, currentTime);
        },

        fullscreen() {
          const videoElement = document.getElementById("content-video");
            if (videoElement.requestFullscreen) {
                videoElement.requestFullscreen();
            }
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

.abstract-video-player {
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
    max-width: 1080px;
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
    transform: translateY(100%) translateY(0px);
    transition: 0.3s;
    flex-wrap: wrap;
    background: rgba(0, 0, 0, 0.6);
}

.video-player:hover .player-controls {
    transform: translateY(-7px);
}

.play-button {
    width: 10%;
    border: none;
    background: black;
    color: white;
    padding: 10px;
}

.fullscreen-button {
    width: 5%;
    border: none;
    position: relative;
    right: -10px;
    color: white;
    background: rgba(0, 0, 0, 0);
}

.fullscreen-text {
    letter-spacing: 15px;
    font-size: 23px;
    font-weight: bold;
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
    height: 15px;
}


</style>