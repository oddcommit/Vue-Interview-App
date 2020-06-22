const loginConstants = {
  SET_LOGGED_IN: 'SET_LOGGED_IN',
};

const initalState = {
  isLoggedIn: false,
};

const loginReducer = (state = initalState, action) => {
  switch (action.type) {
    case loginConstants.SET_LOGGED_IN:
      return {
        ...state,
        isLoggedIn: action.payload.isLoggedIn,
      };
    default:
      return state;
  }
};
export { loginReducer, loginConstants };
