import {
  ADMIN_LOGIN,
  ADMIN_LOGOUT,
  PROMOTION_ALERT,
  PROMOTION_CLUSTER_ALERT,
} from "../actions/types"

const initialState = {
  msg: "",
  msgSeverity: "",
  loginStatus: {
    success: false,
  },
  promotionAlert: {},
  promotionClusterAlert: {},
}

export default (state = initialState, action = {}) => {
  switch (action.type) {
    case ADMIN_LOGIN:
      return { ...state, loginStatus: action.loginStatus }
    case ADMIN_LOGOUT:
      return { ...initialState }
    case PROMOTION_ALERT:
      return { ...state, promotionAlert: action.promotionAlert }
    case PROMOTION_CLUSTER_ALERT:
      return { ...state, promotionClusterAlert: action.promotionClusterAlert }
    default:
      return { ...state }
  }
}
