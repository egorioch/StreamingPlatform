<template>
    <div class="common-layer">
        <navbar-view></navbar-view>
    </div>

</template>

<script>
import NavbarView from "@/components/NavbarView.vue";

export default {
  components: {NavbarView}
}
</script>

<style>
body {
    background: linear-gradient(to top left, powderblue, pink);
    overflow-x: hidden;
    overflow-y: hidden;
}
</style>