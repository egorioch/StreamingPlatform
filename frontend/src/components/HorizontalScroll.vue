<template>
    <div class="horizontal-scroll">
        <div class="scroll-container" ref="scrollContainer">
            <div v-for="image in images" :key="image.id" class="image-item">
                <img :src="image.content" id="imgId" alt="Image" width="180" height="120">

            </div>
<!--            <img id="ItemPreview" src="" alt="Image"/>-->
            <div id="currentImage">
                <!--                <img :src="">-->
            </div>
        </div>
    </div>
</template>

<script>
import {checkImgUrl} from "@/scripts/server";

export default {
  data() {
    return {
      images: [],
      currentImageUrl: ''
    };
  },
  async mounted() {
    try {
      this.images = await this.fetchImages();
      this.addScrollListener();
    } catch (error) {
      console.error('Failed to get images!', error);
    }
  },
  methods: {
    async fetchImages() {
      try {
        const response = await fetch("http://localhost:8080/video/all_images");
        const data = await response.json();
        const result = data.reduce((acc, image) => {
          image.content = "data:image/jpeg;base64," + image.content;
          checkImgUrl(image.content);

          acc[image.id] = image;
          return acc;
        }, {});

        return result;
      } catch (error) {
        throw new Error('Failed to get images! ' + error);
      }
    },
    addScrollListener() {
      const scrollContainer = this.$refs.scrollContainer;
      scrollContainer.addEventListener('wheel', this.handleScroll);
    },
    handleScroll(event) {
      const scrollContainer = this.$refs.scrollContainer;
      scrollContainer.scrollLeft += event.deltaY;
      event.preventDefault();
    },
    // urlByImageSrc(src) {
    //   const byteArray = new Uint8Array(src);
    //   const bigBlob = new Blob([byteArray], {type: 'image/jpeg'});
    //   return URL.createObjectURL(bigBlob);
    // }
  }
};
</script>

<style scoped>
.horizontal-scroll {
    width: 100%;
    overflow-x: auto;
    white-space: nowrap;
}

.scroll-container {
    display: inline-block;
}

.image-item {
    display: inline-block;
    padding: 10px;
}
</style>