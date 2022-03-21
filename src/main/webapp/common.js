export async function api(url = '', method = 'GET', data = {}) {
  const response = await fetch(url, {
    method: method,
    mode: 'cors',
    cache: 'no-cache',
    credentials: 'same-origin',
    headers: {
      'Content-Type': 'application/json'
    },
    redirect: 'follow',
    referrerPolicy: 'no-referrer',
    body: method != 'GET' ? JSON.stringify(data) : null
  });
  return response.json();
}

export function $(elementName) {
    return document.getElementById(elementName);
}