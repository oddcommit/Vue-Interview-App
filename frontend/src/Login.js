import React, { Component } from 'react';
import axios from 'axios';
import './Login.scss';
import { Button, Form, Alert } from 'react-bootstrap';
// import { useHistory } from "react-router-dom";

class Login extends Component {
    // handleClick = e => {
    //     this.props.history.push("/home");
    // };


    state = {
        email: '',
        password: '',
        errorMessage: '',
        isLoginError: false
    }

    loginClicked(history) {
        this.setState({ isLoginError: false });

        let context = this;
        axios.post('/login', {
            email: this.state.email,
            password: this.state.password
        })
            .then(res => {
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
        return (
            <div className="container-centered">
                <div className="card-style-container">
                    <h2>Login</h2>
                    <Form.Control id="email" onChange={this.updateStateEmail} aria-describedby="email" type="email" placeholder="Enter email" />
                    <Form.Control id="password" onChange={this.updateStatePassword} aria-describedby="email" type="password" placeholder="Enter password" />
                    {this.state.errorMessage ?
                        <Alert className="username-password-incorrect" variant="danger">
                            {this.state.errorMessage}
                        </Alert> : null}

                    <Button variant="primary" onClick={() => this.loginClicked(history)}>Login</Button>
                </div>
            </div>
        );
    }
}

export default Login;