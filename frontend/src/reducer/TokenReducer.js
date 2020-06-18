const tokenConstants = {
  SET_JWT_TOKEN: 'SET_JWT_TOKEN',
  SET_LOGGED_IN: 'SET_LOGGED_IN',
};

const initalState = {
  jwtToken: '',
};

const tokenReducer = (state = initalState, action) => {
  switch (action.type) {
    case tokenConstants.SET_JWT_TOKEN:
      return {
        ...state,
        jwtToken: action.payload.jwtToken,
      };
    default:
      return state;
  }
};
export { tokenReducer, tokenConstants };
