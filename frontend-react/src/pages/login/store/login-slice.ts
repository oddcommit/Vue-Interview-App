import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { LoginReducerState } from "../types";

const loginSlice = createSlice({
  name: "loginSlice",
  initialState: {
    isLoggedIn: false,
    showLoginError: false,
    isLoginLoading: false,
  } as LoginReducerState,
  reducers: {
    setLoggedIn(state, action: PayloadAction<boolean>) {
      state.isLoggedIn = action.payload;
    },
    setLoginError(state, action: PayloadAction<boolean>) {
      state.showLoginError = action.payload;
      state.isLoginLoading = false;
    },
    setLoginLoading(state, action: PayloadAction<boolean>) {
      state.isLoginLoading = action.payload;
    },
  },
});

export const {
  setLoggedIn,
  setLoginError,
  setLoginLoading,
} = loginSlice.actions;

export default loginSlice.reducer;
