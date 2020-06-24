import React, { Component } from 'react';
import axiosHttp from './utils/axios';
import './Login.scss';
import { LoginUiState, LoginPost, LoginResponse } from './LoginTypes';
import { AxiosAuthRefreshRequestConfig } from 'axios-auth-refresh';
import { Button, Form, Alert } from 'react-bootstrap';
import { loginUser, logoutUser } from './utils/loginUtils';
import { AxiosResponse } from 'axios';

class Login extends Component {
  state: LoginUiState = {
    email: '',
    password: '',
    errorMessage: '',
  };

  public loginClicked(): void {
    let emailAndPasswordPostRequest: LoginPost = {
      email: this.state.email,
      password: this.state.password,
    };

    let axiosConfig: AxiosAuthRefreshRequestConfig = { skipAuthRefresh: true };

    axiosHttp
      .post('/login', emailAndPasswordPostRequest, axiosConfig)
      .then((response: AxiosResponse<LoginResponse>) => {
        let jwtToken: string = response.data.jwtToken;
        loginUser(jwtToken);
      })
      .catch((error) => {
        logoutUser();
        this.setState({ errorMessage: error.response.data.errorMessage });
      });
  }

  private updateStateEmail = (e: any): void => {
    this.setState({ email: e.target.value });
  };

  private updateStatePassword = (e: any): void => {
    this.setState({ password: e.target.value });
  };

  render() {
    let errorMessage: string = this.state.errorMessage;
    let usernameOrPasswordIncorrectAlert;

    if (errorMessage) {
      usernameOrPasswordIncorrectAlert = (
        <Alert className="username-password-incorrect" variant="danger">
          {errorMessage}
        </Alert>
      );
    }

    return (
      <div className="container-centered">
        <div className="card-style-container">
          <h2>Login</h2>
          <Form.Control
            id="email"
            onChange={this.updateStateEmail}
            aria-describedby="email"
            type="email"
            placeholder="Enter email"
          />
          <Form.Control
            id="password"
            onChange={this.updateStatePassword}
            aria-describedby="password"
            type="password"
            placeholder="Enter password"
          />
          {usernameOrPasswordIncorrectAlert}
          <Button
            id="login-button"
            variant="primary"
            onClick={() => this.loginClicked()}
          >
            Login
          </Button>
        </div>
      </div>
    );
  }
}

export default Login;
