//отправка пикчи на сервер
export function loadPictureToServer(dataURI, timeOnTheLine) {
  let xhr = new XMLHttpRequest();
  xhr.open('POST', `/video/${timeOnTheLine}/load`, true);
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  xhr.send(encodeURI('image=' + dataURI));

  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      console.log('Изображение загружено успешно!')
    } else {
      console.error('Ошибка при загрузке изображения на сервер...')
    }
  }
}