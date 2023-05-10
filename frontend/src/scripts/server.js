//отправка пикчи на сервер
export async function loadPictureToServer(canvas, currentTime) {
  let imageBlob = await new Promise(resolve => canvas.toBlob(resolve, 'image/jpeg'));
  let formData = new FormData();
  formData.append("image", imageBlob, `${currentTime}.jpeg`);

  await fetch(`http://localhost:8080/video/load`, {
    method: 'POST',
    body: formData,
  });
}

export async function loadPictureFromServer() {
  let response = await fetch("http://localhost:8080/video/all_images")
    .then(resp => resp.json())
    .catch(error => 'Failed to get images! ' + error);

  for (let obj in response) {
    console.log("obj: " + obj);
  }
}
