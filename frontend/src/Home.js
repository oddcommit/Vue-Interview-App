import React, { Component } from 'react';
import axiosHttp from './Axios';

class Home extends Component {
    render() {
        return (
            <div>
                <div>Home</div>
            </div>
        );
    }

    componentDidMount() {
        let context = this;
        axiosHttp.get('/users')
            .then(response => {
                console.log(response.data);
            })
            .catch(function (error) {
                context.setState({ errorMessage: error.message });
            })
    }
}

export default Home;
