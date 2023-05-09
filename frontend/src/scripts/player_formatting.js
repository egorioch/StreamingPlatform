export function formatTime(time, hours) {
  if (hours) {
    let h = Math.floor(time / 3600);
        time = time - h * 3600;

    let m = Math.floor(time / 60);
    let s = Math.floor(time % 60);

    return h.lead0(2)  + ":" + m.lead0(2) + ":" + s.lead0(2);
  } else {
    let m = Math.floor(time / 60);
    let s = Math.floor(time % 60);

    return m.lead0(2) + ":" + s.lead0(2);
  }
}

export function secondsToTime(secondsMilliseconds) {
  console.log('secondsMilli: ' + secondsMilliseconds)
  let formattedSeconds = (secondsMilliseconds).split('.')[0];
  console.log('formatted seconds:' + formattedSeconds);
  let hours = 0;
  let minutes = 0;
  let seconds = 0;

  if ((hours = Math.floor(formattedSeconds / 3600)) !== 0) { formattedSeconds = formattedSeconds - hours * 3600 }
  if ((minutes = Math.floor(formattedSeconds / 60)) !== 0) { formattedSeconds = formattedSeconds - minutes * 3600}
  seconds = formattedSeconds;

  console.log('hours: ' + hours + ", minutes: " + minutes + ", seconds: " + seconds);
}

Number.prototype.lead0 = function(n) {
  let nz = "" + this;
  while (nz.length < n) {
    nz = "0" + nz;
  }
  return nz;
};

