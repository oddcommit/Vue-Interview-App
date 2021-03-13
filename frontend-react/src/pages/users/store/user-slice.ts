import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { User, UserState } from "../types";

const userSlice = createSlice({
  name: "userSlice",
  initialState: {
    users: [],
  } as UserState,
  reducers: {
    setUsers(state, action: PayloadAction<Array<User>>) {
      state.users = action.payload;
    },
  },
});

export const { setUsers } = userSlice.actions;

export default userSlice.reducer;
