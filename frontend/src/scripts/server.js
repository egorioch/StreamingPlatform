//отправка пикчи на сервер
export function loadPictureToServer(dataURI, timeOnTheLine) {
  let xhr = new XMLHttpRequest();
  xhr.open('POST', `http://localhost:8080/video/${timeOnTheLine}/load`, true);
  xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
  console.log(dataURI)
  let encodedString = dataURI.replace(/^data:image\/?[A-z]*;base64,/);
  xhr.send(encodedString);

  let decodedString;
  try {
    decodedString = window.atob(encodedString);
  } catch (error) {
    console.error('Строка говна: ', error);
  }

  if (decodedString) {
    console.log(decodedString); // Output: "Hello, world!"
  } else {
    console.error('Раскодированная строка говна: ');
  }

  xhr.onreadystatechange = function() {
    if (xhr.readyState === 4 && xhr.status === 200) {
      console.log('Изображение загружено успешно!')
    } else {
      console.error('Ошибка при загрузке изображения на сервер...')
    }
  }
}
