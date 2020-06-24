import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Table, Button } from 'react-bootstrap';

import axiosHttp from './utils/axios';
import { mainPageConstants } from './reducer/MainPageReducer';
import Navbar from './components/Navbar';

import { RootState } from './index';

import './Main.scss';
import './Home.scss';
import { AxiosResponse } from 'axios';
import { User, MainReducerState } from './reducer/MainPageReducerTypes';

interface DispatchProps {
  setUsers: (value: Array<User>) => void;
}

interface HomeState {
  errorMessage: string;
}

type Props = DispatchProps & HomeState & MainReducerState;

/**
 * TODO: Add real action in dispatch
 * @param dispatch
 */
const mapDispatchToProps = (dispatch: any): DispatchProps => ({
  setUsers: (value: Array<User>) =>
    dispatch({ type: mainPageConstants.SET_USERS, payload: value }),
});

const mapStateToProps = (state: RootState) => ({
  users: state.mainReducer.users,
});

class Home extends Component<Props, HomeState> {
  state: HomeState = {
    errorMessage: '',
  };

  render() {
    return (
      <div className="container-home">
        <Navbar />
        <div className="centered-container-base">
          <Button
            onClick={() => {
              this.fetchUsers();
            }}
          >
            Refetch users
          </Button>
          <h1>User overview</h1>
          <div>
            <Table responsive>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Email</th>
                  <th>Name</th>
                  <th>Age</th>
                </tr>
              </thead>
              <tbody>
                {this.props.users.map((user) => (
                  <tr key={user.id}>
                    <td>{user.id}</td>
                    <td>{user.email}</td>
                    <td>{user.name}</td>
                    <td>{user.age}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
          </div>
        </div>
      </div>
    );
  }

  fetchUsers() {
    axiosHttp
      .get('/users')
      .then((response: AxiosResponse<Array<User>>) => {
        this.props.setUsers(response.data);
      })
      .catch((error) => {
        this.setState({ errorMessage: error.message });
      });
  }

  componentDidMount() {
    this.fetchUsers();
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);
