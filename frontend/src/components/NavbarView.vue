<template>
    <nav class="navbar navbar-expand-lg navbar-light bg-custom">
        <div class="container-fluid">
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarTogglerDemo01"
                    aria-controls="navbarTogglerDemo01" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarTogglerDemo01">
                <a class="navbar-brand" href="/frontend/public">Streamme</a>
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a href="/video/sunset" class="nav-link">Video</a>
                    </li>
                    <li class="nav-item">
                        <a href="/video/screens" class="nav-link">Screenshots</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
    <router-view/>
</template>

<script setup>
</script>

<style>
.bg-custom {
    background: lightsalmon;
    color: whitesmoke;
    border-radius: 0 0 10px 10px;
}
</style>