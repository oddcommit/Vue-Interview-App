import { RootState } from "../../..";

export const selectIsLoggedIn = (state: RootState): boolean =>
  state.loginReducer.isLoggedIn;

export const selectShowLoginError = (state: RootState): boolean =>
  state.loginReducer.showLoginError;
