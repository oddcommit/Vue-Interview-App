import React, { Component } from 'react';
import axios from 'axios';
import './Login.scss';
import { Button, Form, Alert } from 'react-bootstrap';

class Login extends Component {

    state = {
        persons: [],
        isLoginError: false
    }

    loginClicked() {
        this.setState({ isLoginError: false });
        axios.get(`https://jsonplaceholder.typicode.com/users`)
            .then(res => {
                const multiblePersons = res.data;
                this.setState({ persons: multiblePersons, isLoginError: true });
                console.log(this.state.persons);
            })
    }

    render() {
        return (
            <div className="container-centered">
                <div className="card-style-container">
                    <h2>Login</h2>
                    <Form.Control id="email" aria-describedby="email" type="email" placeholder="Enter email" />
                    <Form.Control id="password" aria-describedby="email" type="password" placeholder="Enter password" />
                    {this.state.isLoginError ?
                        <Alert className="username-password-incorrect" variant="danger">
                            Sorry, username or password was not correct!
                    </Alert> : null}

                    <Button variant="primary" onClick={() => this.loginClicked()}>Login</Button>
                </div>
            </div>
        );
    }
}

export default Login;