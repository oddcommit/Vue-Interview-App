import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { GeneralSlice } from "./types";

const generalSlice = createSlice({
  name: "generalSlice",
  initialState: {
    isApplicationLoading: true,
  } as GeneralSlice,
  reducers: {
    setIsApplicationLoading(state, action: PayloadAction<boolean>) {
      state.isApplicationLoading = action.payload;
    },
  },
});

export const { setIsApplicationLoading } = generalSlice.actions;

export default generalSlice.reducer;
