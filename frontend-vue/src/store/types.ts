import { User } from "@/views/types";

export interface RootState {
  loginError: string;
  isLoggedIn: boolean;
  users: Array<User>;
}
