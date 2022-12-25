export interface AxiosBackendError {
  httpStatus: number;
  errorMessage: string;
}

export interface JwtTokenReqestOrResponse {
  jwtToken: string;
}
