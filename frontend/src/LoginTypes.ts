export interface LoginUiState extends LoginPost {
  errorMessage: string;
}

export interface LoginPost {
  email: string;
  password: string;
}

export interface LoginResponse {
  jwtToken: string;
}
