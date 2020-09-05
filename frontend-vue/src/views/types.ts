export interface LoginPost {
  email: string;
  password: string;
}
export interface LoginResponse {
  jwtToken: string;
}
