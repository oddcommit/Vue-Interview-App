import {
  MainConstants,
  MainReducerState,
  UserAction,
} from './MainPageReducerTypes';

const mainPageConstants: MainConstants = {
  SET_USERS: 'SET_USERS',
};

const initalState: MainReducerState = {
  users: [],
};

const mainReducer = (
  state = initalState,
  action: UserAction
): MainReducerState => {
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
