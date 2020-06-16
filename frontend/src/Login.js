import React, { Component } from 'react';
import './Login.scss';
import { Button, Form } from 'react-bootstrap';

class Login extends Component {
    render() {
        return (
            <div class="container-centered">
                <div class="card-style-container">
                    <h2>Login</h2>
                    <Form.Control id="email" aria-describedby="email" type="email" placeholder="Enter email" />
                    <Form.Control id="email" aria-describedby="email" type="password" placeholder="Enter password" />
                    <Button variant="primary">Login</Button>
                </div>
            </div>
        );
    }
}

export default Login;