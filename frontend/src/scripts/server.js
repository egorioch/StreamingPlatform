/**
 * загружает сделанный скриншот на сервер
 * @param canvas - холст, содержащий пропертис скрина
 * @param currentTime - время(секунды.миллисекунды), когда был сделан скрин
 * @returns {Promise<void>}
 */
export async function loadPictureToServer(canvas, currentTime) {
  let imageBlob = await new Promise(resolve => canvas.toBlob(resolve, 'image/jpeg'));
  console.log("imageBlob: " + JSON.stringify(imageBlob));
  let formData = new FormData();
  console.log('ImageURL(to server): ' + URL.createObjectURL(imageBlob));
  formData.append("image", imageBlob, `${currentTime}.jpeg`);

  await fetch(`http://localhost:8080/video/load`, {
    method: 'POST',
    body: formData,
  });
}

/**
 * запрашиывает у сервера все скриншоты
 * @returns {Promise<T|string>}
 */
export async function loadPictureFromServer() {
  return await fetch("http://localhost:8080/video/all_images")
    .then(resp => resp.json())
    .then(
      data => {
        const result = data.reduce((acc, image) => {
          const bigBlob = new Blob([image.content], {type: 'image/jpeg'});
          image.content = URL.createObjectURL(bigBlob);
          acc[image.id] = image;
          return acc;
        }, {});

        return result;
      }
    )
    .catch(error => 'Failed to get images! ' + error);
}

/**
 * проверяет валидна ли созданная ссылка
 * @param url
 * @returns {Promise<unknown>}
 */
export function checkImgUrl(url) {
  return new Promise((resolve) => {
    const img = new Image();
    img.onload = function () {
      console.log('URL верен')
      resolve(true);
    }
    img.onerror = function () {
      console.log('URL неверен')
      resolve(false)
    }
    img.src = url
  })
}

/**
 * информация о страницы(время длительности видео(в сек.), размер файла в байтах)
 * @returns {Promise<void>}
 */
export async function getContentLength() {
  let response = await fetch('http://localhost:8080/video/sunset/info', {
    headers: {
      'Content-Type': 'application/json'
    }
  }).then(response => response.json())
    .catch(err => console.log("error: " + err));


  console.log('response header: ' + JSON.stringify(response));
}

export async function getVideoSrc() {
  fetch('http://localhost:8080/video/sunset', {
    headers: {
      'Range': 'bytes=0-'
    }
  })
    .then(resp => resp.arrayBuffer())
    .then(videoBytes => {
      const blob = new Blob([videoBytes], { type: 'video/mp4' })
      const videoUrl = URL.createObjectURL(blob);

      return new Promise((resolve, reject) => {
        console.log('videoURL: ' + videoUrl);
        resolve(videoUrl);
        reject('error with create promise!');
      });
    })
    .catch(err => "error: " + err)


}