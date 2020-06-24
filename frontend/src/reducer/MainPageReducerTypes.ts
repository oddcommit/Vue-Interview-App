export interface MainReducerState {
  users: Array<User>;
}

export interface User {
  id: number;
  name: string;
  email: string;
  age: number;
}

export interface UserAction {
  payload: Array<User>;
  type: string;
  SET_LOGGED_IN: string;
}

export interface MainConstants {
  SET_USERS: 'SET_USERS';
}
