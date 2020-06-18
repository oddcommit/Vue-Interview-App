const mainPageConstants = {
  SET_USERS: true,
};

const initalState = {
  users: [],
};

const mainReducer = (state = initalState, action) => {
  switch (action.type) {
    case mainPageConstants.SET_USERS:
      return {
        ...state,
        users: action.payload,
      };
    default:
      return state;
  }
};

export { mainReducer, mainPageConstants };
