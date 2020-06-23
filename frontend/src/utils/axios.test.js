import { loginUser, logoutUser } from './loginUtils';
import axiosHttp from './axios';
import MockAdapter from 'axios-mock-adapter';

describe('test axiosRefreshLogic', () => {
  it('should try to renew token', async () => {
    global.localStorage = new LocalStorageMock();
    let mock = new MockAdapter(axiosHttp);

    let axiosSpy = jest.spyOn(axiosHttp, 'post');
    mock.onPost('/something').replyOnce(401).onGet('/something').replyOnce(200);
    mock.onPost('/renewToken').reply(200, {
      jwtToken: 'something',
    });

    await axiosHttp
      .post('/something', { skipAuthRefresh: true })
      .then((response) => {})
      .catch((error) => {});

    expect(axiosSpy).toHaveBeenCalledTimes(2);
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
