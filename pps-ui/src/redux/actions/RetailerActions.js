import axios from "axios"
import i18n from "i18next"
import {
  STORELIST_GET_REQUEST,
  CATEGORIES_GET_REQUEST,
  PRODUCTS_GET_REQUEST,
  ZONE_SAVE_VALUE,
  CLUSTER_SAVE_VALUE,
  STORE_SAVE_VALUE,
  PRODUCTTOSTORE_POST_REQUEST,
  STORE_GET_REQUEST,
  LOGIN_USER,
  CREATE_CLUSTER,
  CREATE_ZONE,
  CLUSTERLIST_GET_REQUEST,
  LOGIN_FAILURE,
  LOGOUT,
  WELCOME_USER,
  ZONE_GET_REQUEST,
  ZONELIST_GET_REQUEST,
  RETAILER_BASE_URL,
  CLUSTER_GET_REQUEST,
  STORE_POST_REQUEST,
  FAILURE,
  MESSAGE_SET_NULL,
  PRODUCTLIST_GET_REQUEST,
  PRODUCT_SAVE_VALUE,
  PRODUCT_GET_REQUEST,
  PROMOTION_POST_REQUEST,
  ZONECLUSTER_GET_REQUEST,
  ASSIGN_TO_CLUSTER,
  ASSIGN_TO_ZONE,
  PRODUCT_POST_REQUEST,
  RESET_STATUS_CODE,
  LEVEL_SAVE_VALUE,
  PROMOTIONS_GET_BYRANGE,
  GET_PROMOTIONS_CLUSTER,
  GET_PROMOTIONS_ZONE,
  STARTDATE_SAVE_VALUE,
  ENDDATE_SAVE_VALUE,
  FROMDATE_SAVE_VALUE,
  TODATE_SAVE_VALUE,
  PROFITPERCENT_SAVE_VALUE,
  IS_PROMOTION_APPLLIED,
  PRODUCT_UPDATE,
  PRODUCTDETAILS_NOTEFFECTIVEPRICECHANGE_GET_REQUEST,
  PRODUCTDETAILS_EFFECTIVEPRICECHANGE_GET_REQUEST,
  PRODUCT_CANCEL_EFFECTIVEPRICECHANGE,
  POST_EFFECTIVE_PRICE,
  CREATE_ADMIN,
  USER_TYPE,
  VENDOR_LOGOUT,
  ADMIN_LOGOUT,
  PRODUCTLIST_NONALCOHOLIC_GET_REQUEST,
  SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
  CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
  CLEAR_PRODUCT_LIST,
  GET_DASHBOARD_DATA,
  CHECK_ASSIGNED_ZONE,
  CHECK_ASSIGNED_CLUSTER,
  CLEAR_ASSIGNED_PRICE,
  MESSAGE_SET,
  PENDING_PROMOTIONS,
  APPROVE_PROMOTION,
  GET_PRODUCT_ZONELIST,
  GET_PRODUCT_CLUSTERLIST,
  GET_ZONE_QUANTITY,
  UPDATE_QUANTITY,
  GET_CLUSTER_QUANTITY,
  UNASSIGNED_CLUSTERS,
  ASSIGN_ZONE_CLUSTER,
  CLEAR_PRODUCT_CLUSTER_LIST,
  CLEAR_CLUSTER_LIST,
} from "./types"

const TOKEN = () => {
  if (sessionStorage.getItem("userType") === "Retailer") {
    return `BearerR ${sessionStorage.getItem("token")}`
  }

  return `BearerA ${sessionStorage.getItem("token")}`
}

export const login = (loginDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/retailer/authenticate`, loginDetails)
    .then((res) => {
      sessionStorage.setItem("token", res.data.jwt)
      sessionStorage.setItem("userType", "Retailer")
      dispatch({
        type: USER_TYPE,
        loggedInUser: {
          token: res.data.jwt,
          userType: "Retailer",
          userName: res.data.userName,
        },
      })
      dispatch({
        type: LOGIN_USER,
        loginStatus: { success: true, errorMsg: "", data: res.data },
        userInfo: loginDetails,
      })
    })
    .catch(() => {
      dispatch({
        type: LOGIN_FAILURE,
        loginStatus: {
          success: false,
          errorMsg: i18n.t("login.invalidCredentials"),
        },
        msg: i18n.t("login.invalidCredentials"),
        msgSeverity: "error",
      })
    })
}
export const fetchUserDetails = (loginDetails) => async (dispatch) => {
  dispatch({ type: WELCOME_USER, userInfo: loginDetails })
}
export const postZone = (zoneDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/location-management/zone`, zoneDetails, {
      headers: { Authorization: TOKEN() },
    })
    .then(() => {
      dispatch({
        type: CREATE_ZONE,
        msg: "Zone Created Succesfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 404) {
        dispatch({
          type: CREATE_ZONE,
          msg: "Sorry Zone already exists",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CREATE_ZONE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: CREATE_ZONE,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const postCluster = (cluster, zone) => async (dispatch) => {
  await axios
    .put(`${RETAILER_BASE_URL}/location-management/${zone}/cluster`, cluster, {
      headers: { Authorization: TOKEN() },
    })
    .then(() => {
      dispatch({
        type: CREATE_CLUSTER,
        msg: `Cluster for Zone ${zone} is Created Successfully`,
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 404) {
        dispatch({
          type: CREATE_CLUSTER,
          msg: response.data.message,
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CREATE_CLUSTER,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: CREATE_CLUSTER,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const getZones = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/location-management/zones/names`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: ZONE_GET_REQUEST, zones: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getClusters = (zone) => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/location-management/${zone}/clusters/names`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: CLUSTER_GET_REQUEST, clusters: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const postStore = (store, zone, cluster) => async (dispatch) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/location-management/${zone}/${cluster}/store`,
      store,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({ type: STORE_POST_REQUEST, msg: "Store Created Succesfully" })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const logout = () => (dispatch) => {
  dispatch({ type: LOGOUT })
  dispatch({ type: VENDOR_LOGOUT })
  dispatch({ type: ADMIN_LOGOUT })
}

export const messageSetNull = () => (dispatch) => {
  dispatch({ type: MESSAGE_SET_NULL })
}
export const getZoneList = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/location-management/zone-map`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: ZONELIST_GET_REQUEST, zoneList: res.data })
    })
}

export const getClusterList = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/location-management/cluster-map`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: CLUSTERLIST_GET_REQUEST, clusterList: res.data })
    })
}

// group Actions

export const postGroup = (groupDetails) => async (dispatch) => {
  await axios
    .post(`${RETAILER_BASE_URL}/group-management/group`, groupDetails, {
      headers: { Authorization: TOKEN() },
    })
    .then(() => {
      dispatch({
        type: CREATE_ZONE,
        msg: "Group Created Succesfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 400) {
        dispatch({
          type: CREATE_ZONE,
          msg: "Sorry Group already exists",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CREATE_ZONE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: CREATE_ZONE,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

// admin

export const createAdmin = (adminDetails) => async (dispatch) => {
  await axios
    .post(
      `${RETAILER_BASE_URL}/vendor-retailer-management/admin`,
      adminDetails,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: CREATE_ADMIN,
        msg: "User Created Succesfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 400) {
        dispatch({
          type: CREATE_ADMIN,
          msg: "Sorry username already exists",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CREATE_ADMIN,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: CREATE_ADMIN,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const getStoreList = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/location-management/store`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: STORELIST_GET_REQUEST, storeList: res.data })
    })
}

export const getCategories = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/categories`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: CATEGORIES_GET_REQUEST, categories: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getProducts = (category) => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/${category}/products`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: PRODUCTS_GET_REQUEST, products: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const saveZoneValue = (zoneValue) => (dispatch) => {
  dispatch({ type: ZONE_SAVE_VALUE, zone: zoneValue })
}

export const saveClusterValue = (clusterValue) => (dispatch) => {
  dispatch({ type: CLUSTER_SAVE_VALUE, cluster: clusterValue })
}

export const saveStoreValue = (storeValue) => (dispatch) => {
  dispatch({ type: STORE_SAVE_VALUE, store: storeValue })
}

export const saveUnassignedCluster = (unassignedCluster) => (dispatch) => {
  dispatch({ type: UNASSIGNED_CLUSTERS, unassignedCluster })
}

export const postProductToStore = (zone, cluster, store, products) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${zone}/${cluster}/${store}/product`,
      products,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({
        type: PRODUCTTOSTORE_POST_REQUEST,
        msg: "Product Added to Store Succesfully",
        msgSeverity: "success",
        products: [],
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 400) {
        dispatch({
          type: PRODUCTTOSTORE_POST_REQUEST,
          msg: "Quantity Insufficient",
          msgSeverity: "warning",
          products: [],
        })
      }
    })
}

export const getStores = (zone, cluster) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/location-management/${zone}/${cluster}/stores/names`,
      { headers: { Authorization: TOKEN() } }
    )
    .then((res) => {
      dispatch({ type: STORE_GET_REQUEST, stores: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getPromotionsInRange = (fromDate, toDate, levelOption) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/${levelOption}/data?filter=%7B%22startDate%22:%22${fromDate}%22,%22endDate%22:%22${toDate}%22%7D`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: PROMOTIONS_GET_BYRANGE, promotions: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const getProductList = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/products/names`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: PRODUCTLIST_GET_REQUEST, productList: res.data })
    })
}

export const saveProductValue = (productValue) => (dispatch) => {
  dispatch({ type: PRODUCT_SAVE_VALUE, productName: productValue })
}

export const getProductDetails = (productName) => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/product/${productName}`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: PRODUCT_GET_REQUEST, productDetails: res.data })
    })
}
export const isPromotionApplied = (productName) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/${productName}/product/promotion`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: IS_PROMOTION_APPLLIED, isPromotion: res.data })
    })
}
export const updateProduct = (updatedProduct, productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${productName}/product`,
      updatedProduct,
      { headers: { Authorization: TOKEN() } }
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

export const getZoneClusterNames = (clusterPattern) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/location-management/clusters/regex/${clusterPattern}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: ZONECLUSTER_GET_REQUEST, zoneclusternames: res.data })
    })
}

export const assignToCluster = (
  clusterDetails,
  zoneName,
  clusterName,
  productName
) => async (dispatch) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${productName}/${zoneName}/${clusterName}/products`,
      clusterDetails,
      { headers: { Authorization: TOKEN() } }
    )
    .then((res) => {
      dispatch({
        type: ASSIGN_TO_CLUSTER,
        msg: "Product Asigned to Cluster Succesfully",
        statusCode: res.status,
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product is already associated with cluster"
      ) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: "Product is already associated with cluster",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Quantity Insufficient"
      ) {
        dispatch({ type: MESSAGE_SET_NULL })
        dispatch({
          type: ASSIGN_TO_CLUSTER,
          msg: "Quantity assigned is high, please enter a lower quantity",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Product price is below minimum selling price"
      ) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg:
            "Profit percentage is very low, please enter a higher percentage",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({ type: MESSAGE_SET_NULL })
        dispatch({
          type: ASSIGN_TO_CLUSTER,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      } else {
        dispatch({ type: MESSAGE_SET_NULL })
        dispatch({
          type: ASSIGN_TO_CLUSTER,
          msg: "Something went wrong ,please try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      }
    })
}

export const assignToZone = (zoneDetails, zoneName, productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${zoneName}/${productName}/product`,
      zoneDetails,
      { headers: { Authorization: TOKEN() } }
    )
    .then((res) => {
      dispatch({
        type: ASSIGN_TO_ZONE,
        msg: "Product Asigned to Zone Succesfully",
        statusCode: res.status,
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product is already associated with zone"
      ) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: "Product is already associated with zone",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Quantity Insufficient"
      ) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: "Quantity assigned is high, please enter a lower quantity",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Product price is below minimum selling price"
      ) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg:
            "Profit percentage is very low, please enter a higher percentage",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 400) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: response.data.message,
          msgSeverity: "warning",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      } else {
        dispatch({ type: MESSAGE_SET_NULL })
        dispatch({
          type: ASSIGN_TO_ZONE,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      }
    })
}

export const postPromotion = (
  promotionDetails,
  productName,
  levelOption
) => async (dispatch) => {
  dispatch({ type: MESSAGE_SET_NULL })
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/product/promotion/${productName}/${levelOption}`,
      promotionDetails,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      if (res.data.status) {
        dispatch({
          type: PROMOTION_POST_REQUEST,
          msg: "promotion applied Succesfully",
          msgSeverity: "success",
        })
      } else {
        dispatch({
          type: PROMOTION_POST_REQUEST,
          msg: "promotion cannot not applied",
          msgSeverity: "error",
        })
      }
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 400) {
        dispatch({
          type: PROMOTION_POST_REQUEST,
          msg: "Sorry something went wrong",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: PROMOTION_POST_REQUEST,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: PROMOTION_POST_REQUEST,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const getEffectivePrice = (parameter, productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/product/effectivePrice/${productName}`,
      parameter,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: POST_EFFECTIVE_PRICE,
        msg: "Assigned Price Successfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message ===
          "Effective price is already defined for this product"
      ) {
        dispatch({
          type: POST_EFFECTIVE_PRICE,
          msg: "Effective Price Already Exist",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message ===
          "Sorry cannot change price of product in given date range"
      ) {
        dispatch({
          type: POST_EFFECTIVE_PRICE,
          msg:
            "Promotion already in effect for date range, cannot change effective price",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({
          type: POST_EFFECTIVE_PRICE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: POST_EFFECTIVE_PRICE,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const getPricesInRange = (startDate, endDate, currentDate) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/data?filter=%7B%22startDate%22:%22${startDate}%22,%22endDate%22:%22${endDate}%22,%22currentDate%22:%22${currentDate}%22%7D`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: PRODUCTS_GET_REQUEST, products: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const cancelEffectivePrice = (productName, promotionId) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/` +
        `product/${productName}/${promotionId}`,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({ type: PRODUCT_POST_REQUEST, msg: "Withdraw Done!" })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const resetStatusCode = () => (dispatch) => {
  dispatch({ type: RESET_STATUS_CODE })
}

export const cancelPromotion = (details, productName, levelOption) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/` +
        `product/promotion/cancel/${productName}/${levelOption}`,
      details,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({
        type: PRODUCT_POST_REQUEST,
        msg: "Cancel Successful!",
        msgSeverity: "success",
      })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const withdrawPromotion = (
  details,
  productName,
  levelOption,
  promotionId
) => async (dispatch) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/` +
        `product/promotion/withdraw/${productName}/${levelOption}/${promotionId}`,
      details,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({
        type: PRODUCT_POST_REQUEST,
        msg: "Withdraw Successful!",
        msgSeverity: "success",
      })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const saveLevelValue = (level) => (dispatch) => {
  dispatch({ type: LEVEL_SAVE_VALUE, levelOption: level })
}

export const getPromotionsIncluster = (
  productName,
  zoneName,
  clusterName
) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/promotions/${productName}/${zoneName}/${clusterName}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_PROMOTIONS_CLUSTER, clusterPromotions: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}
export const getPromotionsInzone = (productName, zoneName) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/promotions/${productName}/${zoneName}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_PROMOTIONS_ZONE, zonePromotions: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const saveStartDate = (start) => (dispatch) => {
  dispatch({ type: STARTDATE_SAVE_VALUE, startDate: start })
}

export const saveEndDate = (end) => (dispatch) => {
  dispatch({ type: ENDDATE_SAVE_VALUE, endDate: end })
}

export const saveFromDate = (from) => (dispatch) => {
  dispatch({ type: FROMDATE_SAVE_VALUE, fromDate: from })
}

export const saveToDate = (to) => (dispatch) => {
  dispatch({ type: TODATE_SAVE_VALUE, toDate: to })
}

export const saveEffectivePercentage = (pp) => (dispatch) => {
  dispatch({ type: PROFITPERCENT_SAVE_VALUE, profitPercentage: pp })
}

export const getNotEffecticePriceChangeProducts = () => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/effectivePriceNotInEffect`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: PRODUCTDETAILS_NOTEFFECTIVEPRICECHANGE_GET_REQUEST,
        priceChangeProductsList: res.data,
      })
    })
}

export const getEffecticePriceChangeProducts = () => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/effectivePriceInEffect`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: PRODUCTDETAILS_EFFECTIVEPRICECHANGE_GET_REQUEST,
        priceChangeProductsList: res.data,
      })
    })
}

export const cancelProductEffectivePriceChange = (productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/product/effectivePrice/cancel/${productName}`,
      {},
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: PRODUCT_CANCEL_EFFECTIVEPRICECHANGE,
        msg: "Cancelled Product Price Change Successfully",
      })
    })
    .catch((err) => {
      const { response } = err
      if (response.status === 403) {
        dispatch({
          type: PRODUCT_CANCEL_EFFECTIVEPRICECHANGE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: PRODUCT_CANCEL_EFFECTIVEPRICECHANGE,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const getNonAlcoholicProductList = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/product-list`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({
        type: PRODUCTLIST_NONALCOHOLIC_GET_REQUEST,
        nonAlcoholicProductList: res.data,
      })
    })
}

export const sellProductFixedPrice = (productName) => async (dispatch) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${productName}/product/makeFixed`,
      {},
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
        msg: "Set To Sell At Fixed Price Successfully",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "This product already has a fixed price"
      ) {
        dispatch({
          type: SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Product is being sold at fixed price",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({
          type: SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const cancelProductFixedPrice = (productName) => async (dispatch) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/${productName}/product/cancelFixed`,
      {},
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
        msg:
          "Fixed Price Cancelled, Promotions and Effective Price Can Be Applied",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message ===
          "This product's fixed price has already been cancelled"
      ) {
        dispatch({
          type: CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Fixed Price has already been cancelled",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({
          type: CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
        })
      } else {
        dispatch({
          type: CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
        })
      }
    })
}

export const clearProductList = (productList) => (dispatch) => {
  dispatch({ type: CLEAR_PRODUCT_LIST, products: productList })
}

export const clearProductClusterList = (productClusterList) => (dispatch) => {
  dispatch({
    type: CLEAR_PRODUCT_CLUSTER_LIST,
    productClusterList,
  })
}

export const clearClusterList = (clusters) => (dispatch) => {
  dispatch({ type: CLEAR_CLUSTER_LIST, clusters })
}

export const getDashboardData = () => async (dispatch) => {
  await axios
    .get(`${RETAILER_BASE_URL}/product-management/dashboard`, {
      headers: { Authorization: TOKEN() },
    })
    .then((res) => {
      dispatch({ type: GET_DASHBOARD_DATA, dashboardData: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}
export const checkAssignedZone = (productName, zoneName) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/price/${productName}/${zoneName}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: CHECK_ASSIGNED_ZONE,
        assignedPrice: res.data,
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product is not assigned to this zone"
      ) {
        dispatch({
          type: CHECK_ASSIGNED_ZONE,
          statusCode: response.status,
          assignedPrice: "",
        })
        dispatch({
          type: MESSAGE_SET,
          msg: "Product is not assigned to the zone",
          msgSeverity: "error",
        })
      } else if (
        response.status === 400 &&
        response.data.message ===
          "Product is not assigned to this zone, but to cluster"
      ) {
        dispatch({
          type: CHECK_ASSIGNED_ZONE,
          statusCode: response.status,
          assignedPrice: "",
        })
        dispatch({
          type: MESSAGE_SET,
          msg: "Product is not assigned to this zone, but to cluster",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CHECK_ASSIGNED_ZONE,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
          assignedPrice: "",
        })
      } else {
        dispatch({
          type: CHECK_ASSIGNED_ZONE,
          assignedPrice: "",
        })
        dispatch({
          type: MESSAGE_SET,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "error",
        })
      }
    })
}

export const checkAssignedCluster = (
  productName,
  zoneName,
  clusterName
) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/price/${productName}/${zoneName}/${clusterName}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: CHECK_ASSIGNED_CLUSTER,
        assignedPrice: res.data,
        msg: "Product is assigned to Cluster",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product is not assigned to the cluster"
      ) {
        dispatch({
          type: CHECK_ASSIGNED_CLUSTER,
          statusCode: response.status,
          assignedPrice: "",
        })
        dispatch({
          type: MESSAGE_SET,
          msg: "Product is not assigned to the cluster",
          msgSeverity: "error",
        })
      } else if (response.status === 403) {
        dispatch({
          type: CHECK_ASSIGNED_CLUSTER,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
          assignedPrice: "",
        })
      } else {
        dispatch({
          type: CHECK_ASSIGNED_CLUSTER,
          assignedPrice: "",
        })
        dispatch({
          type: MESSAGE_SET,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "error",
        })
      }
    })
}

export const clearAssignedPrice = (assignedPriceAlt) => (dispatch) => {
  dispatch({ type: CLEAR_ASSIGNED_PRICE, assignedPrice: assignedPriceAlt })
}

export const getPendingPromotions = (productName) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/product/promotion/${productName}`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: PENDING_PROMOTIONS, pendingPromotions: res.data })
    })
    .catch(() => {
      dispatch({ type: FAILURE })
    })
}

export const approvePromotions = (promotionId, productName, status) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/product/promotion/${productName}/${promotionId}/${status}`,
      {},
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      if (status === "APPROVED") {
        dispatch({
          type: APPROVE_PROMOTION,
          msg: "Accepted Successfully",
          msgSeverity: "success",
        })
      } else if (status === "REJECTED") {
        dispatch({
          type: APPROVE_PROMOTION,
          msg: "Rejected Successfully",
          msgSeverity: "warning",
        })
      }
    })
    .catch(() => {
      dispatch({
        type: APPROVE_PROMOTION,
        msg: "Something went wrong, please try again.",
        msgSeverity: "warning",
      })
    })
}

export const getZonesForProduct = (productName) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/${productName}/product/zones/names`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_PRODUCT_ZONELIST, productZoneList: res.data })
    })
}

export const getClustersForProduct = (productName, zone) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/${productName}/${zone}/product/clusters/names`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_PRODUCT_CLUSTERLIST, productClusterList: res.data })
    })
}

export const getZoneQuantity = (productName, zone) => async (dispatch) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/${productName}/${zone}/quantity`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({ type: GET_ZONE_QUANTITY, quantityAssignedAtZone: res.data })
    })
}

export const getClusterQuantity = (productName, zone, cluster) => async (
  dispatch
) => {
  await axios
    .get(
      `${RETAILER_BASE_URL}/product-management/products/${productName}/${zone}/${cluster}/quantity`,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then((res) => {
      dispatch({
        type: GET_CLUSTER_QUANTITY,
        quantityAssignedAtCluster: res.data,
      })
    })
}

export const updateQuantity = (increaseQty, productName, levelOption) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/products/update/${levelOption}/${productName}`,
      increaseQty,
      {
        headers: { Authorization: TOKEN() },
      }
    )
    .then(() => {
      dispatch({
        type: UPDATE_QUANTITY,
        msg: "Updated Quantity Succesfully",
        msgSeverity: "success",
      })
    })
    .catch(() => {
      dispatch({
        type: UPDATE_QUANTITY,
        msg: "Something went wrong, please try again.",
        msgSeverity: "warning",
      })
    })
}

export const postZoneCluster = (finalDetails, productName) => async (
  dispatch
) => {
  await axios
    .put(
      `${RETAILER_BASE_URL}/product-management/assign/${productName}`,
      finalDetails,
      { headers: { Authorization: TOKEN() } }
    )
    .then(() => {
      dispatch({
        type: ASSIGN_ZONE_CLUSTER,
        msg: "Price Assigned Succesfully",
        msgSeverity: "success",
      })
    })
    .catch((err) => {
      const { response } = err
      if (
        response.status === 400 &&
        response.data.message === "Product is already associated with zone"
      ) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: "Product is already associated with zone",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Quantity Insufficient"
      ) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: "Quantity assigned is high, please enter a lower quantity",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Quantity Insufficient"
      ) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: "Product is already associated with ZONE",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (
        response.status === 400 &&
        response.data.message === "Product price is below minimum selling price"
      ) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg:
            "Profit percentage is very low, please enter a higher percentage",
          msgSeverity: "error",
          statusCode: response.status,
        })
      } else if (response.status === 400) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: response.data.message,
          msgSeverity: "warning",
          statusCode: response.status,
        })
      } else if (response.status === 403) {
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: "Something went wrong ,please logout and try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      } else {
        dispatch({ type: MESSAGE_SET_NULL })
        dispatch({
          type: ASSIGN_ZONE_CLUSTER,
          msg: "Something went wrong ,please  try again",
          msgSeverity: "warning",
          statusCode: response.status,
        })
      }
    })
}
