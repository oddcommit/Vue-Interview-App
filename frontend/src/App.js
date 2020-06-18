import React, { Component } from 'react';
import { Router, Switch, Route } from 'react-router-dom';
import Login from './Login';
import Home from './Home';
import './App.scss';

import { Provider } from 'react-redux';
import { createStore } from 'redux';
import { combineReducers } from 'redux';

import { tokenReducer } from './reducer/TokenReducer';
import { mainReducer } from './reducer/MainPageReducer';
import Spinner from 'react-bootstrap/Spinner';
import axiosHttp from './Axios';
import { createBrowserHistory } from 'history';

let allReducers = combineReducers({
  jwtTokenReducer: tokenReducer,
  mainReducer: mainReducer,
});

const store = createStore(allReducers);
let history = createBrowserHistory();

class App extends Component {
  state = {
    isLoading: true,
    componentDidUpdateFromRefreshToken: false,
  };

  componentDidMount() {
    let jwtToken = localStorage.getItem('jwtToken');
    if (jwtToken) {
      axiosHttp
        .post('/renewToken', { skipAuthRefresh: true })
        .then((tokenRefreshResponse) => {
          let jwtToken = tokenRefreshResponse.data.jwtToken;
          axiosHttp.defaults.headers.common = {
            Authorization: `Bearer ${jwtToken}`,
          };
          this.setState({
            isLoading: false,
            componentDidUpdateFromRefreshToken: true,
          });
          return Promise.resolve();
        })
        .catch((error) => {
          localStorage.removeItem('jwtToken');
          delete axiosHttp.defaults.headers.common['Authorization'];
          history.push('/');
          this.setState({ isLoading: false });
          return Promise.reject(error);
        });
    } else {
      this.setState({ isLoading: false });
    }
  }

  componentDidUpdate() {
    if (this.state.componentDidUpdateFromRefreshToken) {
      this.setState({ componentDidUpdateFromRefreshToken: false });
      history.push('/home');
    }
  }

  render() {
    if (this.state.isLoading) {
      return (
        <div className="loading-screen">
          <Spinner animation="grow" />
          <div>Loading...</div>
        </div>
      );
    } else {
      return (
        <Provider store={store}>
          <Router history={history}>
            <Switch>
              <Route exact path="/" component={Login} />
              <Route exact path="/home" component={Home} />
            </Switch>
          </Router>
        </Provider>
      );
    }
  }
}

export default App;
