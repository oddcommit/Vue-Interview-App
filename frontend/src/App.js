import React, { Component } from 'react';
import { BrowserRouter, Switch, Route } from 'react-router-dom';
import axios from 'axios';
import Login from './Login';
import Home from './Home';
import './App.scss';

class App extends Component {
  constructor(props) {
    super();
    axios.defaults.baseURL = 'http://localhost:4667/api';
  }
  render() {
    return (
      <BrowserRouter>
        <Switch>
          <Route exact path='/' component={Login} />
          <Route exact path='/home' component={Home} />
        </Switch>
      </BrowserRouter>
    );
  }
}

export default App;
