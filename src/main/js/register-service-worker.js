'use strict';

//export default async function register () {
function register() {

  const registration = navigator.serviceWorker.register('/sw.js');

  firebase.initializeApp({
    'messagingSenderId': '951211685543'
  });
  const messaging = firebase.messaging();

  messaging.useServiceWorker(registration);

  try {
    messaging.requestPermission();
  } catch (e) {
    console.log('Unable to get permission', e);
    return;
  }

  navigator.serviceWorker.addEventListener('message', event => {
    if (event.data === 'newData') {
      //showData();
    }
  });

  const currentToken = messaging.getToken();
  fetch('/register', {method: 'post', body: currentToken});
  //showData();

  messaging.onTokenRefresh(() => {
    console.log('token refreshed');
    const newToken = messaging.getToken();
    fetch('/register', {method: 'post', body: currentToken});
  });

}

module.exports.register = register;