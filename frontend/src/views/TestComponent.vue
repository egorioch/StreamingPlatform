<template>
    <header class="page-header">
        <div class="container flow">
            <h1 class="page-title">Horizontal scrolling</h1>
            <p class="page-subtitle">Let's create a horizontal media scroller!</p>
        </div>
    </header>
    <h2 class="section-title">Individual elements</h2>

    <div v-for="image in images" :key="image.id" class="media-scroller snaps-inline">
        <div class="media-element">
            <img :src="image.content" alt="Image" width="180" height="120">
            <p class="title">{{ image.name }}</p>
        </div>

    </div>

</template>

<script>
import 'normalize.css'

export default {
}
</script>

<style lang="scss" scoped>

.media-scroller {
    --_spacer: var(--size-3);
    display: grid;
    gap: var(--_spacer);
    grid-auto-flow: column;
    grid-auto-columns: 21%;

    padding: 0 var(--_spacer) var(--_spacer);

    overflow-x: auto;
    overscroll-behavior-inline: contain;
}

.media-scroller--with-groups {
    grid-auto-columns: 80%;
}

.media-group {
    display: grid;
    gap: var(--_spacer);
    grid-auto-flow: column;
}

.media-element {
    display: grid;
    grid-template-rows: min-content;
    gap: var(--_spacer);
    padding: var(--_spacer);
    background: var(--surface-2);
    border-radius: var(--radius-2);
    box-shadow: var(--shadow-2);
}

.media-element > img {
    inline-size: 100%;
    aspect-ratio: 16 / 9;
    object-fit: cover;
}

.snaps-inline {
    scroll-snap-type: inline mandatory;
    scroll-padding-inline: var(--_spacer, 1rem);
}

.snaps-inline > * {
    scroll-snap-align: start;
}

/* general styling */

.container {
    inline-size: min(100% - 4rem, 70rem);
    margin-inline: auto;
}



</style>