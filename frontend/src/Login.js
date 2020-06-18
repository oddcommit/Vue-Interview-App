import React, { Component } from 'react';
import axiosHttp from './Axios';
import './Login.scss';
import { Button, Form, Alert } from 'react-bootstrap';

class Login extends Component {
    state = {
        email: '',
        password: '',
        errorMessage: '',
        isLoginError: false
    }

    loginClicked(history) {
        this.setState({ isLoginError: false });

        let context = this;
        axiosHttp.post('/login', {
            email: this.state.email,
            password: this.state.password
        }, { skipAuthRefresh: true })
            .then(response => {
                let jwtToken = response.data.jwtToken;
                localStorage.setItem('jwtToken', jwtToken);
                axiosHttp.defaults.headers.common = { 'Authorization': `Bearer ${jwtToken}` }
                history.push('/home');
            })
            .catch(function (error) {
                context.setState({ errorMessage: error.response.data.errorMessage });
            })
    }

    updateStateEmail = e => {
        this.setState({ email: e.target.value });
    };

    updateStatePassword = e => {
        this.setState({ password: e.target.value });
    };

    render() {
        const { history } = this.props;
        let usernameOrPasswordIncorrectAlert;
        if (this.state.errorMessage) {
            usernameOrPasswordIncorrectAlert =
                <Alert className="username-password-incorrect" variant="danger">
                    {this.state.errorMessage}
                </Alert>;
        }

        return (
            <div className="container-centered">
                <div className="card-style-container">
                    <h2>Login</h2>
                    <Form.Control id="email" onChange={this.updateStateEmail} aria-describedby="email" type="email" placeholder="Enter email" />
                    <Form.Control id="password" onChange={this.updateStatePassword} aria-describedby="email" type="password" placeholder="Enter password" />
                    {usernameOrPasswordIncorrectAlert}
                    <Button variant="primary" onClick={() => this.loginClicked(history)}>Login</Button>
                </div>
            </div>
        );
    }
}

export default Login;