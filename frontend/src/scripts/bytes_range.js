//

async function fetchPartialContent(url, start, end) {
    const response = await fetch(url, {
        headers: {'Range': `bytes=${start}-${end}`}
    });
    const content = await response.arrayBuffer();
    return content;
}

export async function playVideo(videoUrl) {
    const video = document.createElement('video');
    const blob = await fetch(videoUrl).then(response => response.blob());

    const videoUrlObject = URL.createObjectURL(blob);
    video.src = videoUrlObject;
    document.body.appendChild(video);

    const CHUNK_SIZE = 1024 * 1024; // 1MB chunks
    let start = 0;
    while (start < blob.size) {
        const end = Math.min(start + CHUNK_SIZE, blob.size - 1);
        const chunk = await fetchPartialContent(videoUrl, start, end);
        console.log("chunk:" + chunk);
        const chunkBlob = new Blob([chunk], {type: 'video/mp4'});
        video.append(chunkBlob);
        start += CHUNK_SIZE;
    }
}