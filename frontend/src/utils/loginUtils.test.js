import { loginUser, logoutUser } from './loginUtils';
import axiosHttp from './axios';

describe('test localstorage and authorization header', () => {
  it('should put token into local storage and set authorization header', () => {
    //given
    global.localStorage = new LocalStorageMock();
    let localstorage = global.localStorage;
    let token = 'something';

    //when
    loginUser(token);

    //then
    expect(axiosHttp.defaults.headers['Authorization']).toBe(`Bearer ${token}`);
    expect(localstorage.getItem('jwtToken')).toBe(token);
  });

  it('should remove token from local storage and set authorization header', () => {
    //given
    global.localStorage = new LocalStorageMock();
    let localstorage = global.localStorage;
    axiosHttp.defaults.headers = {
      Authorization: `Bearer test`,
    };
    localstorage.setItem('jwtToken', 'something');

    //when
    logoutUser();

    //then
    let authorizationHeader = axiosHttp.defaults.headers['Authorization'];
    expect(authorizationHeader).toBe(undefined);
    expect(global.localStorage.getItem('jwtToken')).toBe(null);
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
