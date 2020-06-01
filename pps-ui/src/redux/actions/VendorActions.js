import axios from "axios"
import { registerconstants } from "./registrationtypes"
import {
  VENDOR_LOGIN_USER,
  VENDOR_LOGIN_FAILURE,
  VENDOR_LOGOUT,
  RETAILER_BASE_URL,
  MESSAGE_SET_NULL,
  CREATE_PRODUCT,
  PRODUCT_GET_REQUEST,
  PRODUCT_UPDATE,
  FAILURE,
  PRODUCTLIST_GET_REQUEST,
  PRODUCT_SAVE_VALUE,
  USER_TYPE,
  GET_ALL_PRODUCTS,
  GET_PRODUCT_COUNT,
} from "./types"

const VTOKEN = () => {
  return `BearerV ${sessionStorage.getItem("token")}`
}

export const registration = (registrationdetails) => async (dispatch) => {
  await axios
    .post(
      `${RETAILER_BASE_URL}/vendor-retailer-management/vendor`,
      registrationdetails
    )
    .then((res) => {
      const body = { dest: registrationdetails.email, body: res.data }
      axios
        .post(
          "https://us-central1-price-promotion-system.cloudfunctions.net/sendMail",
          body
        )
        .then(() => {
          dispatch({
            type: registerconstants.REGISTER_SUCCESS,
            registerStatus: { registered: true },
            msg:
              "Account registered,please verify your mail by clicking on the link sent to you",
            msgSeverity: "success",
          })
        })
        .catch(() => {
          dispatch({
            type: registerconstants.REGISTER_FAILURE,
            registerStatus: { registered: false, error: true },
            msg: "something went wrong while sending mail",
            msgSeverity: "error",
          })
        })
    })
    .catch((res) => {
      dispatch({
        type: registerconstants.REGISTER_FAILURE,
        registerStatus: { registered: false, error: true },
        msg: res.response.data.message,
        msgSeverity: "error",
      })
    })
}
export const postProduct = (productDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/product-management/product`, productDetails, {
      headers: {
        Authorization: VTOKEN(),
        "Content-Type": "multipart/form-data",
      },
    })
    .then(() => {
      dispatch({
        type: CREATE_PRODUCT,
        msg: "Product Added Succesfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product with name already exists"
      ) {
        dispatch({
          type: CREATE_PRODUCT,
          msg: "Product with name already exists, try again",
          msgSeverity: "error",
        })
      } else if (
        response.status === 400 &&
        response.data.message === "No image uploaded"
      ) {
        dispatch({
          type: CREATE_PRODUCT,
          msg: "Please upload images for the product",
          msgSeverity: "error",
        })
      } else {
        dispatch({
          type: CREATE_PRODUCT,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}
export const vendorLogin = (loginDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/vendor/authenticate`, loginDetails)
    .then((res) => {
      sessionStorage.setItem("token", res.data.jwt)
      sessionStorage.setItem("userType", "vendor")
      dispatch({
        type: USER_TYPE,
        loggedInUser: {
          token: res.data.jwt,
          userType: "vendor",
          userName: res.data.userName,
        },
      })
      dispatch({
        type: VENDOR_LOGIN_USER,
        loginStatus: { success: true, msg: "", data: res.data },
        userInfo: loginDetails,
      })
    })
    .catch((res) => {
      dispatch({
        type: VENDOR_LOGIN_FAILURE,
        loginStatus: { success: false },
        msg:
          res.response.data.message === "Access Denied"
            ? "Invalid Username/Password"
            : res.response.data.message,
        msgSeverity: "error",
      })
    })
}
export const vendorlogout = () => (dispatch) => {
  dispatch({ type: VENDOR_LOGOUT })
}
export const messageSetNull = () => (dispatch) => {
  dispatch({ type: MESSAGE_SET_NULL })
}
export const getProductList = (userName) => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/product-list/${userName}`, {
      headers: { Authorization: VTOKEN() },
    })
    .then((res) => {
      dispatch({ type: PRODUCTLIST_GET_REQUEST, productList: res.data })
    })
}
export const getProductDetails = (productName) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/${productName}/product/vendor`,
      {
        headers: { Authorization: VTOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: PRODUCT_GET_REQUEST, productDetails: res.data })
    })
}

export const updateProduct = (updatedProduct, productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${productName}/product`,
      updatedProduct,
      { headers: { Authorization: VTOKEN() } }
    )
    .then(() => {
      dispatch({ type: PRODUCT_UPDATE, msg: "Updated Sucessfully" })
    })
    .catch(() => {
      dispatch({
        type: FAILURE,
        msg: "try again",
        msgSeverity: "error",
      })
    })
}
export const saveProductValue = (productValue) => (dispatch) => {
  dispatch({ type: PRODUCT_SAVE_VALUE, productName: productValue })
}

export const getAllProducts = (vendorName, pageNo, rowsPerPage) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/vendor/${vendorName}?pageNo=${pageNo}&rowsPerPage=${rowsPerPage}`,
      {
        headers: { Authorization: VTOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_ALL_PRODUCTS, getProducts: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getProductCount = (vendorName) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/vendor/${vendorName}/count`,
      {
        headers: { Authorization: VTOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_PRODUCT_COUNT, countOfProducts: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}
