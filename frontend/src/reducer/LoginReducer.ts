import {
  LoginReducerState,
  LoginConstants,
  LoginAction,
} from './LoginReducerTypes';

const loginConstants: LoginConstants = {
  SET_LOGGED_IN: 'SET_LOGGED_IN',
};

const initalState: LoginReducerState = {
  isLoggedIn: false,
};

const loginReducer = (
  state: LoginReducerState = initalState,
  action: LoginAction
): LoginReducerState => {
  switch (action.type) {
    case loginConstants.SET_LOGGED_IN:
      return {
        ...state,
        isLoggedIn: action.payload.isLoggedIn,
      };
    default:
      return state;
  }
};

export { loginReducer, loginConstants };
