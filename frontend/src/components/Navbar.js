import React, { Component } from 'react';
import { Link } from 'react-router-dom';

/*
 * Obviously this is not a good pattern.
 * Use absolute imports instead:
 * https://github.com/facebook/create-react-app/issues/5645
 * was sadly not working.....
 */
import { logoutUser } from '../utils/loginUtils'; //

class Navbar extends Component {
  render() {
    return (
      <nav>
        <div className="nav-link">
          <Link to="/" onClick={() => logoutUser()}>
            Logout
          </Link>
        </div>
      </nav>
    );
  }
}

export default Navbar;
