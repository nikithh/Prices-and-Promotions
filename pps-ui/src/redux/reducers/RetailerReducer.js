import {
  LOGIN_USER,
  MESSAGE_SET_NULL,
  LOGIN_FAILURE,
  CREATE_CLUSTER,
  CREATE_ZONE,
  PRODUCTTOSTORE_POST_REQUEST,
  LOGOUT,
  FAILURE,
  CLUSTERLIST_GET_REQUEST,
  ZONELIST_GET_REQUEST,
  WELCOME_USER,
  ZONE_GET_REQUEST,
  CLUSTER_GET_REQUEST,
  STORE_POST_REQUEST,
  STORE_GET_REQUEST,
  STORELIST_GET_REQUEST,
  CATEGORIES_GET_REQUEST,
  PRODUCTS_GET_REQUEST,
  ZONE_SAVE_VALUE,
  CLUSTER_SAVE_VALUE,
  STORE_SAVE_VALUE,
  PRODUCTLIST_GET_REQUEST,
  PRODUCT_SAVE_VALUE,
  PRODUCT_GET_REQUEST,
  PROMOTION_POST_REQUEST,
  ZONECLUSTER_GET_REQUEST,
  ASSIGN_TO_CLUSTER,
  ASSIGN_TO_ZONE,
  RESET_STATUS_CODE,
  LEVEL_SAVE_VALUE,
  PROMOTIONS_GET_BYRANGE,
  GET_PROMOTIONS_CLUSTER,
  GET_PROMOTIONS_ZONE,
  STARTDATE_SAVE_VALUE,
  ENDDATE_SAVE_VALUE,
  IS_PROMOTION_APPLLIED,
  PRODUCT_UPDATE,
  PRODUCTDETAILS_NOTEFFECTIVEPRICECHANGE_GET_REQUEST,
  PRODUCTDETAILS_EFFECTIVEPRICECHANGE_GET_REQUEST,
  PRODUCT_CANCEL_EFFECTIVEPRICECHANGE,
  POST_EFFECTIVE_PRICE,
  CREATE_ADMIN,
  USER_TYPE,
  PRODUCTLIST_NONALCOHOLIC_GET_REQUEST,
  SELLPRODUCT_FIXEDPRICE_PUTREQUEST,
  CANCELPRODUCT_FIXEDPRICE_PUTREQUEST,
  CLEAR_PRODUCT_LIST,
  MESSAGE_SET,
  GET_DASHBOARD_DATA,
  CHECK_ASSIGNED_ZONE,
  CHECK_ASSIGNED_CLUSTER,
  CLEAR_ASSIGNED_PRICE,
  PENDING_PROMOTIONS,
  APPROVE_PROMOTION,
  GET_PRODUCT_ZONELIST,
  GET_PRODUCT_CLUSTERLIST,
  GET_ZONE_QUANTITY,
  GET_CLUSTER_QUANTITY,
  UPDATE_QUANTITY,
  UNASSIGNED_CLUSTERS,
  ASSIGN_ZONE_CLUSTER,
  CLEAR_PRODUCT_CLUSTER_LIST,
  CLEAR_CLUSTER_LIST,
  PRODUCT_POST_REQUEST,
} from "../actions/types"

const initialState = {
  loggedInUser: {
    token: "",
    userType: "",
    userName: "",
  },
  zones: [],
  clusters: [],
  stores: [],
  zone: "",
  cluster: "",
  store: "",
  categories: [],
  products: [],
  msg: "",
  msgSeverity: "",
  loginStatus: {
    success: false,
  },
  isvendor: false,
  zoneList: {},
  clusterList: {},
  productList: [],
  productName: "",
  updatedPrice: "",
  productDetails: { effectivePriceObj: {} },
  promotionDetails: {},
  zoneclusternames: [],
  statusCode: "",
  promotions: [],
  clusterPromotions: [],
  zonePromotions: [],
  startDate: "",
  endDate: "",
  levelOption: "",
  isPromotion: false,
  updatedProduct: {},
  priceChangeProductsList: [],
  nonAlcoholicProductList: [],
  dashboardData: {
    noOfBabyProductsPerZone: [],
    NoOfZones: 0,
    NoOfProducts: 0,
    totalNoOfActivePromotions: [],
    NoOfVendors: 0,
    NoOfClusters: 0,
    noOfAlcoholProductsPerZone: [],
    totalNoOfAlcoholProducts: 0,
    totalNoOfBabyProducts: 0,
    ZoneNames: [],
  },
  assignedPrice: "",
  pendingPromotions: [],
  productZoneList: [],
  productClusterList: [],
  quantityAssignedAtZone: 0,
  quantityAssignedAtCluster: 0,
  unassignedCluster: [],
}
export default (state = initialState, action = {}) => {
  switch (action.type) {
    case USER_TYPE:
      return { ...state, loggedInUser: action.loggedInUser }
    case LOGIN_USER:
      return {
        ...state,
        loginStatus: action.loginStatus,
      }
    case LOGOUT:
      return { ...initialState }
    case WELCOME_USER:
      return { ...state, userInfo: action.userInfo }
    case ZONE_GET_REQUEST:
      return { ...state, zones: action.zones }
    case CLUSTER_GET_REQUEST:
      return { ...state, clusters: action.clusters }
    case STORE_GET_REQUEST:
      return { ...state, stores: action.stores }
    case STORE_POST_REQUEST:
      return { ...state, msg: action.msg }
    case ASSIGN_ZONE_CLUSTER:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case FAILURE:
      return { ...state }
    case MESSAGE_SET_NULL:
      return { ...state, msg: "", msgSeverity: "" }
    case LOGIN_FAILURE:
      return {
        ...state,
        loginStatus: action.loginStatus,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
      }
    case CREATE_ZONE:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case CREATE_CLUSTER:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case ZONELIST_GET_REQUEST:
      return { ...state, zoneList: action.zoneList }
    case CLUSTERLIST_GET_REQUEST:
      return { ...state, clusterList: action.clusterList }
    case STORELIST_GET_REQUEST:
      return { ...state, storeList: action.storeList }
    case CATEGORIES_GET_REQUEST:
      return { ...state, categories: action.categories }
    case PRODUCTS_GET_REQUEST:
      return { ...state, products: action.products }
    case ZONE_SAVE_VALUE:
      return { ...state, zone: action.zone }
    case CLUSTER_SAVE_VALUE:
      return { ...state, cluster: action.cluster }
    case STORE_SAVE_VALUE:
      return { ...state, store: action.store }
    case PRODUCTTOSTORE_POST_REQUEST:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        products: action.products,
      }
    case PRODUCTLIST_GET_REQUEST:
      return { ...state, productList: action.productList }
    case PRODUCT_SAVE_VALUE:
      return { ...state, productName: action.productName }
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
    case PROMOTION_POST_REQUEST:
      return {
        ...state,
        promotionDetails: action.promotionDetails,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
      }
    case ZONECLUSTER_GET_REQUEST:
      return { ...state, zoneclusternames: action.zoneclusternames }
    case ASSIGN_TO_CLUSTER:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case ASSIGN_TO_ZONE:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case RESET_STATUS_CODE:
      return { ...state, statusCode: "" }
    case LEVEL_SAVE_VALUE:
      return { ...state, levelOption: action.levelOption }
    case PROMOTIONS_GET_BYRANGE:
      return { ...state, promotions: action.promotions }
    case GET_PROMOTIONS_CLUSTER:
      return { ...state, clusterPromotions: action.clusterPromotions }
    case GET_PROMOTIONS_ZONE:
      return { ...state, zonePromotions: action.zonePromotions }
    case STARTDATE_SAVE_VALUE:
      return { ...state, startDate: action.startDate }
    case ENDDATE_SAVE_VALUE:
      return { ...state, endDate: action.endDate }

    case PRODUCTDETAILS_NOTEFFECTIVEPRICECHANGE_GET_REQUEST:
      return {
        ...state,
        priceChangeProductsList: action.priceChangeProductsList,
      }
    case PRODUCTDETAILS_EFFECTIVEPRICECHANGE_GET_REQUEST:
      return {
        ...state,
        priceChangeProductsList: action.priceChangeProductsList,
      }
    case PRODUCT_CANCEL_EFFECTIVEPRICECHANGE:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case POST_EFFECTIVE_PRICE:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case CREATE_ADMIN:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case PRODUCTLIST_NONALCOHOLIC_GET_REQUEST:
      return {
        ...state,
        nonAlcoholicProductList: action.nonAlcoholicProductList,
      }
    case SELLPRODUCT_FIXEDPRICE_PUTREQUEST:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case CANCELPRODUCT_FIXEDPRICE_PUTREQUEST:
      return {
        ...state,
        msg: action.msg,
        msgSeverity: action.msgSeverity,
        statusCode: action.statusCode,
      }
    case CLEAR_PRODUCT_LIST:
      return { ...state, products: initialState.products }
    case CLEAR_PRODUCT_CLUSTER_LIST:
      return { ...state, productClusterList: initialState.productClusterList }
    case CLEAR_CLUSTER_LIST:
      return { ...state, clusters: initialState.clusters }
    case MESSAGE_SET:
      return { ...state, msgSeverity: action.msgSeverity, msg: action.msg }
    case PRODUCT_POST_REQUEST:
      return { ...state, msgSeverity: action.msgSeverity, msg: action.msg }
    case GET_DASHBOARD_DATA:
      return { ...state, dashboardData: action.dashboardData }
    case CHECK_ASSIGNED_ZONE:
      return {
        ...state,
        assignedPrice: action.assignedPrice,
      }
    case CHECK_ASSIGNED_CLUSTER:
      return {
        ...state,
        assignedPrice: action.assignedPrice,
      }
    case CLEAR_ASSIGNED_PRICE:
      return { ...state, assignedPrice: initialState.assignedPrice }
    case PENDING_PROMOTIONS:
      return {
        ...state,
        pendingPromotions: action.pendingPromotions.pendingPromotion,
      }
    case APPROVE_PROMOTION:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case GET_PRODUCT_ZONELIST:
      return { ...state, productZoneList: action.productZoneList }
    case GET_PRODUCT_CLUSTERLIST:
      return { ...state, productClusterList: action.productClusterList }
    case GET_ZONE_QUANTITY:
      return { ...state, quantityAssignedAtZone: action.quantityAssignedAtZone }
    case GET_CLUSTER_QUANTITY:
      return {
        ...state,
        quantityAssignedAtCluster: action.quantityAssignedAtCluster,
      }
    case UPDATE_QUANTITY:
      return { ...state, msg: action.msg, msgSeverity: action.msgSeverity }
    case UNASSIGNED_CLUSTERS:
      return { ...state, unassignedCluster: action.unassignedCluster }
    default:
      return { ...state }
  }
}
