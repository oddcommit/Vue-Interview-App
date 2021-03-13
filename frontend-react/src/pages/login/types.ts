export interface LoginResponse {
  jwtToken: string;
}

export interface UserLoginData {
  email: string;
  password: string;
}

export interface LoginReducerState {
  isLoggedIn: boolean;
  showLoginError: boolean;
  isLoginLoading: boolean;
}
