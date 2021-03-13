import { AnyAction } from "@reduxjs/toolkit";
import { AxiosResponse } from "axios";
import { ThunkDispatch } from "redux-thunk";
import axiosHttp from "../../../utils/axios";
import { setUsers } from "../store/user-slice";
import { User } from "../types";

export const loadUsers = () => async (
  dispatch: ThunkDispatch<unknown, unknown, AnyAction>
): Promise<void> => {
  axiosHttp
    .get("/users")
    .then((response: AxiosResponse<Array<User>>) => {
      dispatch(setUsers(response.data));
    })
    .catch((error) => {
      console.log("error");
    });
};
