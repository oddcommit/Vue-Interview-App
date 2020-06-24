import React, { Component } from 'react';
import { Router, Switch, Route } from 'react-router-dom';
import Spinner from 'react-bootstrap/Spinner';

import Login from './Login';
import Home from './Home';

import axiosHttp from './utils/axios';
import { history } from './utils/history';
import { loginUser, logoutUser } from './utils/loginUtils';

import './App.scss';

class App extends Component {
  state = {
    isLoading: true,
  };

  componentDidMount() {
    let jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      axiosHttp
        .post('/renewToken', { skipAuthRefresh: true })
        .then((tokenRefreshResponse) => {
          loginUser(tokenRefreshResponse.data.jwtToken);
          setApplicationLoading(false);
        })
        .catch(() => {
          this.logoutUserAndRenderRoutes();
        });
    } else {
      this.logoutUserAndRenderRoutes();
    }
  }

  logoutUserAndRenderRoutes() {
    setApplicationLoading(false);
    logoutUser();
  }

  setApplicationLoading(value = false) {
    this.setState({
      isLoading: value,
    });
  }

  render() {
    return (
      <Router history={history}>
        {this.state.isLoading ? (
          <div className="loading-screen">
            <Spinner animation="grow" />
            <div>Loading...</div>
          </div>
        ) : (
          <Switch>
            <Route exact path="/" component={Login} />
            <Route exact path="/home" component={Home} />
          </Switch>
        )}
      </Router>
    );
  }
}

export default App;
