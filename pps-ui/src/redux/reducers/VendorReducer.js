import {
  VENDOR_LOGIN_USER,
  VENDOR_LOGIN_FAILURE,
  VENDOR_LOGOUT,
  MESSAGE_SET_NULL,
  CREATE_PRODUCT,
  IS_PROMOTION_APPLLIED,
  PRODUCT_UPDATE,
  PRODUCT_GET_REQUEST,
  PRODUCTLIST_GET_REQUEST,
  PRODUCT_SAVE_VALUE,
  GET_ALL_PRODUCTS,
  GET_PRODUCT_COUNT,
} from "../actions/types"
import { registerconstants } from "../actions/registrationtypes"

const initialState = {
  loggedInUser: null,
  msg: "",
  msgSeverity: "",
  loginStatus: {
    success: false,
  },
  registerStatus: { registered: false },
  product: [],
  productName: "",
  isPromotion: false,
  updatedProduct: {},
  productDetails: {},
  productList: [],
  getProducts: [],
  countOfProducts: 0,
}
export default (state = initialState, action = {}) => {
  switch (action.type) {
    case registerconstants.REGISTER_SUCCESS:
      return {
        ...state,
        registerStatus: action.registerStatus,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
      }
    case registerconstants.REGISTER_FAILURE:
      return {
        ...state,
        registerStatus: action.registerStatus,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
      }
    case VENDOR_LOGIN_USER:
      // return { ...state, loggedInUser: action.userInfo };
      return { ...state, loginStatus: action.loginStatus }
    case VENDOR_LOGOUT:
      return { ...initialState }
    case CREATE_PRODUCT:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case MESSAGE_SET_NULL:
      return { ...state, msg: "", msgSeverity: "" }
    case VENDOR_LOGIN_FAILURE:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        loginStatus: action.loginStatus,
      }
    case PRODUCT_GET_REQUEST:
      return { ...state, productDetails: action.productDetails }
    case IS_PROMOTION_APPLLIED:
      return { ...state, isPromotion: action.isPromotion }
    case PRODUCT_UPDATE:
      return {
        ...state,
        updatedProduct: action.updatedProduct,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
      }
    case PRODUCTLIST_GET_REQUEST:
      return { ...state, productList: action.productList }
    case PRODUCT_SAVE_VALUE:
      return { ...state, productName: action.productName }
    case GET_ALL_PRODUCTS:
      return { ...state, getProducts: action.getProducts }
    case GET_PRODUCT_COUNT:
      return { ...state, countOfProducts: action.countOfProducts }
    default:
      return { ...state }
  }
}
