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



let videoReqHandler = function(req, res, pathname) {

    let file = dirPath + "/Client" + pathname;
    let range = req.headers.range;
    let positions = range.replace(/bytes=/, "").split("-");
    let start = parseInt(positions[0], 10);

    fs.stat(file, function(err, stats) {
        if (err) {
            throw err;
        } else {
            var total = stats.size;
            var end = positions[1] ? parseInt(positions[1], 10) : total - 1;
            var chunksize = (end - start) + 1;
            res.writeHead(206, {
                "Content-Range": "bytes " + start + "-" + end + "/" + total,
                "Accept-Ranges": "bytes",
                "Content-Length": chunksize,
                "Content-Type": "video/mp4"
            });
            var stream = fs.createReadStream(file, {
                start: start,
                end: end
            }).on("open", function () {
                stream.pipe(res);
            }).on("error", function (err) {
                res.end(err);
            });
        }
    })
}
