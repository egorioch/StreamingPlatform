export function formatTime(time) {
  let hours = (time / 3600) >= 1.0;

  if (hours) {
    let h = Math.floor(time / 3600);
    time = time - h * 3600;

    let m = Math.floor(time / 60);
    let s = Math.floor(time % 60);

    return h.lead0(2) + ":" + m.lead0(2) + ":" + s.lead0(2);
  } else {
    let m = Math.floor(time / 60);
    let s = Math.floor(time % 60);

    return m.lead0(2) + ":" + s.lead0(2);
  }
}

Number.prototype.lead0 = function (n) {
  let nz = "" + this;
  while (nz.length < n) {
    nz = "0" + nz;
  }
  return nz;
};

// преобразует Time в Number
//если введены ещё и часы, возвратит часы. иначе, возвращает лишь минуты и секунды
export function timeIntoNumber(time) {
  let array = time.split(":");

  if (array > 1) {
    return (parseInt(array[0], 10) * 60 * 60) + (parseInt(array[1], 10) * 60) + parseInt(array[2], 10);
  } else {
    return (parseInt(array[0], 10) * 60) + (parseInt(array[1], 10))
  }

}

export function timeWithoutExtension(timeWithExtension) {
  if (timeWithExtension.length > 5) {
    // console.log("timeWithExtension: " + timeWithExtension)
    let secondsMilliseconds = timeWithExtension.substr(0, timeWithExtension.length - 5);
    // console.log("secondsMilliseconds: " + secondsMilliseconds)
    return formatTime(secondsMilliseconds);
  } else {
    console.error("Length of string less than 5! " + timeWithExtension.length)
  }

}

// Функция возвращает диапазон значений: interval= imageArray.time === value(т.к. округление до секунд,
// может вернуться сразу несколько значений)
// currentImageNumberTime - это переменная, преобразованная из "секунды.миллисекунды.jpeg" в секунды
export function intervalFromImagesArray(value, imageArray) {

  value = timeIntoNumber(value);

  console.log("IMAGEARRAY: " + JSON.stringify(imageArray[29]))
  let tempArray = {};
  let i = 0;
  for (let img in imageArray) {
    console.log("IMAGE: " + imageArray[29].name)
    let currentImageNumberTime = timeIntoNumber(timeWithoutExtension(imageArray[img].name));
    console.log("currentImageNumberTime: " + currentImageNumberTime);
    console.log("value: " + value);
    if (currentImageNumberTime === value) {
      console.log('условие соблюдено!');
      tempArray[i].set(imageArray[img]);
      i++;
    }
  }

  console.log("новоиспеченный объект: " + JSON.stringify(tempArray));
}

//проверка на соответствие [00:15]
export function validateHhMm(inputField) {
  let regex = new RegExp(/^[0-5][0-9]:[0-5][0-9]$/);

  return regex.test(inputField);
}
