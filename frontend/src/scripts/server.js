//отправка пикчи на сервер
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

