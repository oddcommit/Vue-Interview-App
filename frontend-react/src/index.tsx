import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import { Provider } from "react-redux";
import { createStore, combineReducers, applyMiddleware } from "redux";
import thunk from "redux-thunk";
import reportWebVitals from "./reportWebVitals";
import loginSlice from "./pages/login/store/login-slice";
import generalSlice from "./reducer/general-reducer/general-slice";
import userSlice from "./pages/users/store/user-slice";
import "./index.css";

let combinedReducers = combineReducers({
  generalReducer: generalSlice,
  userReducer: userSlice,
  loginReducer: loginSlice,
});

const store = createStore(combinedReducers, applyMiddleware(thunk));
export type RootState = ReturnType<typeof combinedReducers>;

const root = ReactDOM.createRoot(document.getElementById("root") as Element);

/*
root.render(
  <React.StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </React.StrictMode>
);
**/

root.render(
  <Provider store={store}>
    <App />
  </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
