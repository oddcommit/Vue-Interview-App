export interface UserState {
  users: Array<User>;
}

export interface User {
  id: number;
  name: string;
  email: string;
  age: number;
}
