import { setToken, delteToken } from './tokenUtils';
import axiosHttp from './axios';
import MockAdapter from 'axios-mock-adapter';

describe('test axiosRefreshLogic', () => {
  it('should put token into local storage and set authorization header', async () => {
    global.localStorage = new LocalStorageMock();
    let axios = axiosHttp;
    let mock = new MockAdapter(axios);

    let spy = jest.spyOn(axios, 'post');
    mock.onPost('/something').replyOnce(401);
    mock.onPost('/renewToken').reply(200, {
      jwtToken: 'something',
    });
    await axios
      .post('/something')
      .then((response) => {})
      .catch((error) => {});

    expect(spy).toHaveBeenCalledTimes(1);
  });
});

class LocalStorageMock {
  constructor() {
    this.store = {};
  }

  clear() {
    this.store = {};
  }

  getItem(key) {
    return this.store[key] || null;
  }

  setItem(key, value) {
    this.store[key] = value.toString();
  }

  removeItem(key) {
    delete this.store[key];
  }
}
