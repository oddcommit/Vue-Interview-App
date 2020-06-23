import React, { Component } from 'react';
import { connect } from 'react-redux';
import { Table } from 'react-bootstrap';
import { Link } from 'react-router-dom';

import axiosHttp from './utils/axios';
import { mainPageConstants } from './reducer/MainPageReducer';
import { logoutUser } from './utils/loginUtils';

import './Main.scss';
import './Home.scss';

const mapStateToProps = (state) => ({
  users: state.mainReducer.users,
});

const mapDispatchToProps = (dispatch) => ({
  setUsers: (value) =>
    dispatch({ type: mainPageConstants.SET_USERS, payload: value }),
});

class Home extends Component {
  logout = () => {
    logoutUser();
  };

  render() {
    return (
      <div className="container-home">
        <nav>
          <div className="nav-link">
            <Link to="/" onClick={() => this.logout()}>
              Logout
            </Link>
          </div>
        </nav>
        <div className="centered-container-base">
          <div
            onClick={() => {
              axiosHttp
                .get('/users')
                .then((response) => {
                  this.props.setUsers(response.data);
                })
                .catch((error) => {
                  this.setState({ errorMessage: error.message });
                });
            }}
          >
            Click me!
          </div>
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

  componentDidMount() {
    let context = this;
    axiosHttp
      .get('/users')
      .then((response) => {
        context.props.setUsers(response.data);
      })
      .catch(function (error) {
        context.setState({ errorMessage: error.message });
      });
  }
}

export default connect(mapStateToProps, mapDispatchToProps)(Home);
