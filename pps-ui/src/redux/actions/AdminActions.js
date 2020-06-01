import axios from "axios"
import i18n from "i18next"
import {
  ADMIN_LOGIN,
  RETAILER_BASE_URL,
  USER_TYPE,
  MESSAGE_SET,
  PROMOTION_ALERT,
  FAILURE,
  PROMOTION_CLUSTER_ALERT,
} from "./types"

const TOKEN = () => {
  if (sessionStorage.getItem("userType") === "Retailer") {
    return `BearerR ${sessionStorage.getItem("token")}`
  }

  return `BearerA ${sessionStorage.getItem("token")}`
}

// eslint-disable-next-line import/prefer-default-export
export const login = (loginDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/admin/authenticate`, loginDetails)
    .then((res) => {
      sessionStorage.setItem("token", res.data.jwt)
      sessionStorage.setItem("userType", "admin")
      dispatch({
        type: USER_TYPE,
        loggedInUser: {
          token: res.data.jwt,
          userType: "admin",
          userName: res.data.userName,
        },
      })
      dispatch({
        type: ADMIN_LOGIN,
        loginStatus: { success: true, errorMsg: "", data: res.data },
        userInfo: loginDetails,
      })
    })
    .catch(() => {
      dispatch({
        type: MESSAGE_SET,
        msg: i18n.t("login.invalidCredentials"),
        msgSeverity: "error",
      })
    })
}

export const getPromotionAlert = (productName, zoneName, appliedDate) => async (
  dispatch
) => {
  await axios
    .post(
      `${RETAILER_BASE_URL}/product-management/product/status/${productName}/${zoneName}`,
      appliedDate,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: PROMOTION_ALERT, promotionAlert: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getPromotionClusterAlert = (
  productName,
  zoneName,
  clusterName,
  appliedDate
) => async (dispatch) => {
  await axios
    .post(
      `${RETAILER_BASE_URL}/product-management/product/status/${productName}/${zoneName}/${clusterName}`,
      appliedDate,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: PROMOTION_CLUSTER_ALERT,
        promotionClusterAlert: res.data,
      })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}
