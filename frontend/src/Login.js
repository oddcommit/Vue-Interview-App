import React, { Component } from 'react';
import './Login.scss';

class Login extends Component {
    render() {
        return (
            <div class="container-centered">
                <h2>Login</h2>
                <input type="email" class="form-control" id="email" aria-describedby="email" placeholder="Enter email" />
                <input type="password" class="form-control" id="password" aria-describedby="password" placeholder="Password" />

            </div>
        );
    }
}

export default Login;