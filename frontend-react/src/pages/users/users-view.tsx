import { useEffect } from "react";
import { Table } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { loadUsers } from "./requests/user-requests";
import { RootState } from "../..";
import { User } from "./types";
import NavbarComponent from "../../components/navbar-component";
import { selectIsLoggedIn } from "../login/store/login-selectors";
import "./users-view.css";

const UsersView = () => {
  const dispatch = useDispatch();
  const isUserLoggedIn = useSelector(selectIsLoggedIn);

  const users = useSelector((state: RootState) => state.userReducer.users);

  useEffect(() => {
    dispatch(loadUsers());
  }, [dispatch]);

  if (!isUserLoggedIn) {
    return <></>;
  }

  return (
    <>
      <NavbarComponent />
      <div className="users-container">
        <div>Users view</div>
        <Table striped bordered hover className="mt-3">
          <thead>
            <tr>
              <th>E-Mail</th>
              <th>Alter</th>
              <th>Name</th>
            </tr>
          </thead>
          <tbody>
            {users.map((user: User) => (
              <tr>
                <td>{user.email}</td>
                <td>{user.age}</td>
                <td>{user.name}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </div>
    </>
  );
};

export default UsersView;
