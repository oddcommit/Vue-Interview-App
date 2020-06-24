export interface LoginReducerState {
  isLoggedIn: boolean;
}

export interface Payload {
  isLoggedIn: boolean;
}

export interface LoginAction {
  payload: Payload;
  type: string;
  SET_LOGGED_IN: string;
}

export interface LoginConstants {
  SET_LOGGED_IN: 'SET_LOGGED_IN';
}
