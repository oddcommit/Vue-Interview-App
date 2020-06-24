import * as serviceWorker from './serviceWorker';

import React from 'react';
import ReactDOM from 'react-dom';
import { createStore, combineReducers } from 'redux';
import { Provider } from 'react-redux';

import { loginReducer } from './reducer/LoginReducer';
import { mainReducer } from './reducer/MainPageReducer';
import App from './App';

import './index.css';

let allReducers = combineReducers({
  loginReducer: loginReducer,
  mainReducer: mainReducer,
});

export type RootState = ReturnType<typeof allReducers>;

const store = createStore(allReducers);

ReactDOM.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>,
  document.getElementById('root')
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
