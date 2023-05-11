<template>
    <div>
        <horizontal-scroll>

        </horizontal-scroll>
    </div>
</template>

<script>
import HorizontalScroll from "@/components/HorizontalScroll.vue";
import {loadPictureFromServer} from "@/scripts/server";

export default {
  components: {
    HorizontalScroll
  },
  data() {
    return {
      imageList: {}
    }
  },
  async created() {
    this.imageList = await loadPictureFromServer();
    console.log('imageList: ' + this.imageList);
  }

}

</script>