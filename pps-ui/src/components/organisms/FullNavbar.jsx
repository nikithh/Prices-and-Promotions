import AppBar from "@material-ui/core/AppBar"
import CssBaseline from "@material-ui/core/CssBaseline"
// import Divider from "@material-ui/core/Divider"
import Drawer from "@material-ui/core/Drawer"
import Hidden from "@material-ui/core/Hidden"
import IconButton from "@material-ui/core/IconButton"
import List from "@material-ui/core/List"
import ListItem from "@material-ui/core/ListItem"
import ListItemIcon from "@material-ui/core/ListItemIcon"
import ListItemText from "@material-ui/core/ListItemText"
import EventNoteIcon from "@material-ui/icons/EventNote"
import ViewListRoundedIcon from "@material-ui/icons/ViewListRounded"
import { makeStyles, useTheme, withStyles } from "@material-ui/core/styles"
import Tab from "@material-ui/core/Tab"
import Toolbar from "@material-ui/core/Toolbar"
import Tooltip from "@material-ui/core/Tooltip"
import AddShoppingCartIcon from "@material-ui/icons/AddShoppingCart"
import InsertChartIcon from "@material-ui/icons/InsertChart"
import CategoryIcon from "@material-ui/icons/Category"
import EventBusyIcon from "@material-ui/icons/EventBusy"
import FormatListBulletedIcon from "@material-ui/icons/FormatListBulleted"
import GroupIcon from "@material-ui/icons/Group"
import ShoppingCartIcon from "@material-ui/icons/ShoppingCart"
import LocationCityIcon from "@material-ui/icons/LocationCity"
import MenuIcon from "@material-ui/icons/Menu"
import PublicIcon from "@material-ui/icons/Public"
import StoreIcon from "@material-ui/icons/Store"
import PersonAddIcon from "@material-ui/icons/PersonAdd"
import LocalOfferIcon from "@material-ui/icons/LocalOffer"
import clsx from "clsx"
import React from "react"
import ReactFlagsSelect from "react-flags-select"
import "react-flags-select/css/react-flags-select.css"
import "react-flags-select/scss/react-flags-select.scss"
import { useTranslation } from "react-i18next"
import { connect } from "react-redux"
import { BrowserRouter as Router, Link, Route, Switch } from "react-router-dom"
import PropTypes from "prop-types"
import ExpandLess from "@material-ui/icons/ExpandLess"
import ExpandMore from "@material-ui/icons/ExpandMore"
import Collapse from "@material-ui/core/Collapse"
import { logout } from "../../redux/actions/RetailerActions"
import Login from "../Login"
import AddGroup from "../retailer/AddGroup"
import CancelPromotionRouter from "../retailer/CancelPromotionRouter"
import Dashboard from "../utils/Dashboard"
import ClusterForm from "../retailer/ClusterForm"
import ClusterPromotionRouter from "../retailer/ClusterPromotionRouter"
import ProductRouter from "../retailer/ProductRouter"
import QueryOnDateRouter from "../retailer/QueryOnDateRouter"
import StoreForm from "../retailer/StoreForm"
import ViewClusters from "../retailer/ViewClusters"
import ViewZones from "../retailer/ViewZones"
import WithdrawPromotionClusterRouter from "../retailer/WithdrawPromotionClusterRouter"
import WithdrawPromotionZoneRouter from "../retailer/WithdrawPromotionZoneRouter"
import ZoneClusterRouter from "../retailer/ZoneClusterRouter"
import ZoneForm from "../retailer/ZoneForm"
import ZonePromotionRouter from "../retailer/ZonePromotionRouter"
import PriceOnDate from "../retailer/PriceOnDate"
import AssignPriceToProduct from "../retailer/AssignPriceToProduct"
import CancelNotEffectivePriceChange from "../retailer/CancelNotEffectivePriceChange"
import CancelEffectivePriceChange from "../retailer/CancelEffectivePriceChange"
import AddProduct from "../vendor/AddProduct"
import EditItemPrice from "../vendor/EditItemPrice"
import SelectProduct from "../vendor/SelectProduct"
import VendorLogin from "../vendor/VendorLogin"
import Registration from "../vendor/Registration"
import CreateAdmin from "../retailer/CreateAdmin"
import AdminLogin from "../admin/AdminLogin"
import SellCancelProductFixedPriceRouter from "../retailer/SellCancelProductFixedPriceRouter"
import ApprovePromotionsRouter from "../retailer/ApprovePromotionsRouter"
import IncreaseZoneQtyRouter from "../retailer/IncreaseZoneQtyRouter"
import ViewProducts from "../vendor/ViewProducts"
import IncreaseClusterQtyRouter from "../retailer/IncreaseClusterQtyRouter"

const drawerWidth = 250
const useStyles = makeStyles((theme) => ({
  root: {
    display: "flex",
  },
  drawer: {
    [theme.breakpoints.up("sm")]: {
      width: drawerWidth,
      flexShrink: 0,
    },
  },
  appBar: {
    [theme.breakpoints.up("sm")]: {
      width: `calc(100% - ${drawerWidth}px)`,
      marginLeft: drawerWidth,
    },
  },
  menuButton: {
    marginRight: theme.spacing(2),
    [theme.breakpoints.up("sm")]: {
      display: "none",
    },
  },
  // necessary for content to be below app bar
  toolbar: theme.mixins.toolbar,
  drawerPaper: {
    width: drawerWidth,
  },
  content: {
    flexGrow: 1,
    padding: theme.spacing(3),
  },
  nested: {
    paddingLeft: theme.spacing(4),
  },
}))

const StyledTab = withStyles({
  root: {
    color: "white",
    fontFamily: "Oswald, sans-serif",
    fontSize: "18px",
  },
})(Tab)

function FullNavbar(props) {
  const classes = useStyles()
  const { t, i18n } = useTranslation()
  const theme = useTheme()
  const [open] = React.useState(false)
  const { container, loggedInUser } = props
  const [mobileOpen, setMobileOpen] = React.useState(false)
  const [qtyOpen, qtySetOpen] = React.useState(false)
  const [adminOpen, adminSetOpen] = React.useState(false)
  const [priceOpen, priceSetOpen] = React.useState(false)
  const [promotionOpen, promotionSetOpen] = React.useState(false)
  const [locationOpen, locationSetOpen] = React.useState(false)

  const handleDrawerToggle = () => {
    setMobileOpen(!mobileOpen)
  }

  const handleClickQty = () => {
    qtySetOpen(!qtyOpen)
  }
  const handleClickAdmin = () => {
    adminSetOpen(!adminOpen)
  }
  const handleClickPrice = () => {
    priceSetOpen(!priceOpen)
  }
  const handleClickPromotion = () => {
    promotionSetOpen(!promotionOpen)
  }
  const handleClickLocation = () => {
    locationSetOpen(!locationOpen)
  }

  const Retailerdrawer = (
    <div>
      <div className={classes.toolbar} />
      {/* Dashboard */}
      <List>
        <Link to="/dashboard">
          <Tooltip title="Dashboard" placement="right">
            <ListItem button id="dashboard-btn">
              <ListItemIcon>
                <InsertChartIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("welcome.dashboard")}
              />
            </ListItem>
          </Tooltip>
        </Link>

        {/* Add product to store */}
        <Link to="/products/store">
          <Tooltip title="Add Products to Store" placement="right">
            <ListItem button id="add-product-store-btn">
              <ListItemIcon>
                <StoreIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("welcome.addProducttoStore")}
              />
            </ListItem>
          </Tooltip>
        </Link>

        {/* Create group */}
        <Link to="/group">
          <Tooltip title="Create a Group" placement="right">
            <ListItem button id="create-group-btn">
              <ListItemIcon>
                <CategoryIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("welcome.addGroup")}
              />
            </ListItem>
          </Tooltip>
        </Link>

        {/* Admin Actions */}
        {loggedInUser.userType === "Retailer" && (
          <>
            <Tooltip title="Admin Actions" placement="right">
              <ListItem button id="admin-dropdown" onClick={handleClickAdmin}>
                <ListItemIcon>
                  <ViewListRoundedIcon />
                </ListItemIcon>
                <ListItemText primary={t("welcome.adminActions")} />
                {adminOpen ? <ExpandLess /> : <ExpandMore />}
              </ListItem>
            </Tooltip>
            <Collapse in={adminOpen} timeout="auto" unmountOnExit>
              <List component="div" disablePadding>
                <Link to="/admin">
                  <Tooltip title="Create Admin" placement="right">
                    <ListItem
                      button
                      id="create-admin-btn"
                      className={classes.nested}
                    >
                      <ListItemIcon>
                        <PersonAddIcon />
                      </ListItemIcon>
                      <ListItemText
                        className="list-item-text"
                        primary={t("welcome.createAdmin")}
                      />
                    </ListItem>
                  </Tooltip>
                </Link>

                <Link to="/approvepromotion">
                  <Tooltip title="Approve Promotion" placement="right">
                    <ListItem
                      button
                      id="approve-promotion-btn"
                      className={classes.nested}
                    >
                      <ListItemIcon>
                        <GroupIcon />
                      </ListItemIcon>
                      <ListItemText
                        className="list-item-text"
                        primary={t("welcome.approvePromotions")}
                      />
                    </ListItem>
                  </Tooltip>
                </Link>
              </List>
            </Collapse>
          </>
        )}

        {/* Prices */}
        <Tooltip title="Prices Menu" placement="right">
          <ListItem button id="price-dropdown" onClick={handleClickPrice}>
            <ListItemIcon>
              <ViewListRoundedIcon />
            </ListItemIcon>
            <ListItemText primary={t("welcome.prices")} />
            {priceOpen ? <ExpandLess /> : <ExpandMore />}
          </ListItem>
        </Tooltip>
        <Collapse in={priceOpen} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
            <Link to="/priceondate">
              <Tooltip title="Price on date" placement="right">
                <ListItem
                  button
                  id="price-on-date-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <LocalOfferIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.priceOnDate")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/selectproduct">
              <Tooltip title="Assign Price to Zone/Cluster" placement="right">
                <ListItem
                  button
                  id="assign-price-zone-cluster-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <ShoppingCartIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.assignProductToClusterZone")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/sellcancel/fixedprice">
              <Tooltip
                title="Sell/ Cancel Product at Fixed Price"
                placement="right"
              >
                <ListItem
                  button
                  id="sell-cancel-fixed-price-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.sellCancelProductFixedPrice")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/product/pricechange/canceleffective">
              <Tooltip title="Cancel Effective Price Change" placement="right">
                <ListItem
                  button
                  id="cancel-effective-price-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.cancelEffectivePriceChange")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/product/pricechange/cancelnoteffective">
              <Tooltip
                title="Cancel Not Effective Price Change"
                placement="right"
              >
                <ListItem
                  button
                  id="cancel-not-effective-price-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.cancelNotEffectivePriceChange")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
          </List>
        </Collapse>

        {/* Promotions */}
        <Tooltip title="Promotions Menu" placement="right">
          <ListItem
            button
            id="promotion-dropdown"
            onClick={handleClickPromotion}
          >
            <ListItemIcon>
              <ViewListRoundedIcon />
            </ListItemIcon>
            <ListItemText primary={t("welcome.promotions")} />
            {promotionOpen ? <ExpandLess /> : <ExpandMore />}
          </ListItem>
        </Tooltip>
        <Collapse in={promotionOpen} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
            <Link to="/applypromotion/zone">
              <Tooltip title="Apply Promotion in Zone Level" placement="right">
                <ListItem
                  button
                  id="apply-promotion-zone-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <ShoppingCartIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.applyPromotionInZoneLevel")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/applypromotion/cluster">
              <Tooltip
                title="Apply Promotion in Cluster Level"
                placement="right"
              >
                <ListItem
                  button
                  id="apply-promotion-cluster-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <ShoppingCartIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.applyPromotionInClusterLevel")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/queryondaterange">
              <Tooltip title="Query on Date Range" placement="right">
                <ListItem
                  button
                  id="query-on-date-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventNoteIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.queryPromotionsOnDateRange")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/withdraw/zonepromotion">
              <Tooltip
                title="Withdraw Percentage Promotion Zone"
                placement="right"
              >
                <ListItem
                  button
                  id="withdraw-zone-promotion-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.withdrawPercentagePromotionZone")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
            <Link to="/withdraw/clusterpromotion">
              <Tooltip
                title="Withdraw Percentage Promotion Cluster"
                placement="right"
              >
                <ListItem
                  button
                  id="withdraw-cluster-promotion-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.withdrawPercentagePromotionCluster")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/cancel/promotion">
              <Tooltip title="Cancel Percentage Promotion" placement="right">
                <ListItem
                  button
                  id="cancel-promotion-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <EventBusyIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.cancelPromotionPercentage")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
          </List>
        </Collapse>

        {/* Location */}
        <Tooltip title="Location Menu" placement="right">
          <ListItem button id="location-dropdown" onClick={handleClickLocation}>
            <ListItemIcon>
              <ViewListRoundedIcon />
            </ListItemIcon>
            <ListItemText primary={t("welcome.location")} />
            {locationOpen ? <ExpandLess /> : <ExpandMore />}
          </ListItem>
        </Tooltip>
        <Collapse in={locationOpen} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
            <Link to="/zone">
              <Tooltip title="Create a Zone" placement="right">
                <ListItem
                  button
                  id="create-zone-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <PublicIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.createZone")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
            <Link to="/cluster">
              <Tooltip title="Create a Cluster" placement="right">
                <ListItem
                  button
                  id="create-cluster-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <LocationCityIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.createCluster")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
            <Link to="/store">
              <Tooltip title="Create a Store" placement="right">
                <ListItem
                  button
                  id="create-store-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <StoreIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.createStore")}
                  />
                </ListItem>
              </Tooltip>
            </Link>

            <Link to="/view/zones">
              <Tooltip title="View Zones" placement="right">
                <ListItem button id="view-zones-btn" className={classes.nested}>
                  <ListItemIcon>
                    <FormatListBulletedIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.viewZones")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
            <Link to="/view/clusters">
              <Tooltip title="View Clusters" placement="right">
                <ListItem
                  button
                  id="view-clusters-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <FormatListBulletedIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.viewClusters")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
          </List>
        </Collapse>

        {/* Quantity */}
        <Tooltip title="Quantity Menu" placement="right">
          <ListItem button id="qty-dropdown" onClick={handleClickQty}>
            <ListItemIcon>
              <ViewListRoundedIcon />
            </ListItemIcon>
            <ListItemText primary={t("welcome.quantity")} />
            {qtyOpen ? <ExpandLess /> : <ExpandMore />}
          </ListItem>
        </Tooltip>
        <Collapse in={qtyOpen} timeout="auto" unmountOnExit>
          <List component="div" disablePadding>
            <Link to="/updateqty/zone">
              <Tooltip title="Update the price of a Product" placement="right">
                <ListItem
                  button
                  id="add-qty-zone-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <AddShoppingCartIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.increaseQuantityAtZone")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
            <Link to="/updateqty/cluster">
              <Tooltip title="Update the price of a Product" placement="right">
                <ListItem
                  button
                  id="add-qty-cluster-btn"
                  className={classes.nested}
                >
                  <ListItemIcon>
                    <AddShoppingCartIcon />
                  </ListItemIcon>
                  <ListItemText
                    className="list-item-text"
                    primary={t("welcome.increaseQuantityAtCluster")}
                  />
                </ListItem>
              </Tooltip>
            </Link>
          </List>
        </Collapse>
      </List>
    </div>
  )

  const Vendordrawer = (
    <div>
      <div className={classes.toolbar} />
      <List>
        <Link to="/vendor/addproduct">
          <Tooltip title="Add a Product" placement="right">
            <ListItem button id="add-product-btn">
              <ListItemIcon>
                <AddShoppingCartIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("vendorWelcome.addProduct")}
              />
            </ListItem>
          </Tooltip>
        </Link>
        <Link to="/vendor/updateprice">
          <Tooltip title="Update the price of a Product" placement="right">
            <ListItem buttonid="update-price-product-btn">
              <ListItemIcon>
                <AddShoppingCartIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("vendorWelcome.updatePriceOfProduct")}
              />
            </ListItem>
          </Tooltip>
        </Link>
        <Link to="/viewproducts">
          <Tooltip title="View Products" placement="right">
            <ListItem buttonid="view-products-btn">
              <ListItemIcon>
                <AddShoppingCartIcon />
              </ListItemIcon>
              <ListItemText
                className="list-item-text"
                primary={t("vendorWelcome.viewProducts")}
              />
            </ListItem>
          </Tooltip>
        </Link>
      </List>
    </div>
  )

  return (
    <div className={classes.root}>
      <Router>
        <CssBaseline />
        <AppBar
          position="fixed"
          className={clsx(classes.appBar, {
            [classes.appBarShift]: open,
          })}
          style={{
            width: "100%",
            zIndex: 1400,
          }}
        >
          <Toolbar>
            <StyledTab
              label={t("header.home")}
              component={Link}
              id="app-header"
              to="/"
            />
            {sessionStorage.getItem("token") ? (
              <>
                <IconButton
                  color="inherit"
                  aria-label="open drawer"
                  onClick={handleDrawerToggle}
                  edge="start"
                  className={clsx(classes.menuButton, {
                    [classes.hide]: open,
                  })}
                >
                  <MenuIcon />
                </IconButton>
                <div className="right-nav-btn">
                  <ReactFlagsSelect
                    countries={["US", "FR", "DE"]}
                    customLabels={{
                      US: " ",
                      FR: " ",
                      DE: " ",
                    }}
                    className="right-nav-btn"
                    placeholder="Select Language"
                    defaultCountry={sessionStorage.getItem("countryCode")}
                    onSelect={(countryCode) => {
                      i18n.changeLanguage(countryCode)
                      sessionStorage.setItem("countryCode", countryCode)
                      if (countryCode === "FR" || countryCode === "DE") {
                        sessionStorage.setItem("currency", "EUR")
                      } else {
                        sessionStorage.setItem("currency", "USD")
                      }
                    }}
                  />
                  &emsp;
                  <StyledTab
                    id="admin-login-name"
                    label={`${t("welcomeMain")} ${loggedInUser.userName}`}
                  />
                  <Link to="/" id="logout-btn">
                    <StyledTab
                      label={t("header.logOut")}
                      onClick={() => {
                        sessionStorage.removeItem("token")
                        sessionStorage.removeItem("userType")
                        // sessionStorage.removeItem()
                        props.logout()
                      }}
                    />
                  </Link>
                </div>
              </>
            ) : (
              <div className="right-nav-btn">
                {loggedInUser.token === "" && (
                  <>
                    <Link className="button-link" to="/vendor">
                      <StyledTab
                        color="default"
                        className="{classes.link}"
                        id="reg-vendor"
                        label={t("loginAsVendor")}
                      />
                    </Link>
                    <Link className="button-link" to="/admin/login">
                      <StyledTab
                        color="default"
                        className="{classes.link}"
                        id="admin-login"
                        label={t("loginAsAdmin")}
                      />
                    </Link>
                  </>
                )}
                <ReactFlagsSelect
                  countries={["US", "FR", "DE"]}
                  customLabels={{
                    US: " ",
                    FR: " ",
                    DE: " ",
                  }}
                  id="flag-select"
                  placeholder="Select Language"
                  defaultCountry={sessionStorage.getItem("countryCode")}
                  onSelect={(countryCode) => {
                    i18n.changeLanguage(countryCode, () => {
                      sessionStorage.setItem("countryCode", countryCode)
                      if (countryCode === "FR" || countryCode === "DE") {
                        sessionStorage.setItem("currency", "EUR")
                      } else {
                        sessionStorage.setItem("currency", "USD")
                      }
                    })
                  }}
                />
              </div>
            )}
          </Toolbar>
        </AppBar>
        {loggedInUser.token !== "" && (
          <nav className={classes.drawer} aria-label="mailbox folders">
            {/* The implementation can be swapped with js to avoid SEO duplication of links. */}
            <Hidden smUp implementation="css">
              <Drawer
                container={container}
                variant="temporary"
                anchor={theme.direction === "rtl" ? "right" : "left"}
                open={mobileOpen}
                onClose={handleDrawerToggle}
                classes={{
                  paper: classes.drawerPaper,
                }}
                ModalProps={{
                  keepMounted: true, // Better open performance on mobile.
                }}
              >
                {loggedInUser.userType === "vendor"
                  ? Vendordrawer
                  : Retailerdrawer}
              </Drawer>
            </Hidden>
            <Hidden xsDown implementation="css">
              <Drawer
                classes={{
                  paper: classes.drawerPaper,
                }}
                variant="permanent"
                open
              >
                {loggedInUser.userType === "vendor"
                  ? Vendordrawer
                  : Retailerdrawer}
              </Drawer>
            </Hidden>
          </nav>
        )}
        <Switch>
          <Route exact path="/" component={Login} />
          <Route exact path="/group" component={AddGroup} />
          <Route exact path="/dashboard" component={Dashboard} />
          <Route
            exact
            path={["/products/store", "/addproducts"]}
            component={ProductRouter}
          />
          <Route
            exact
            path={["/approvepromotion", "/approvepromotionpage"]}
            component={ApprovePromotionsRouter}
          />
          <Route exact path="/zone" component={ZoneForm} />
          <Route exact path="/cluster" component={ClusterForm} />
          <Route exact path="/store" component={StoreForm} />
          <Route exact path="/view/zones" component={ViewZones} />
          <Route exact path="/view/clusters" component={ViewClusters} />
          <Route
            exact
            path="/product/pricechange/cancelnoteffective"
            component={CancelNotEffectivePriceChange}
          />
          <Route
            exact
            path="/product/pricechange/canceleffective"
            component={CancelEffectivePriceChange}
          />
          <Route
            exact
            path={["/sellcancel/fixedprice", "/sellcancel/fixedprice/product"]}
            component={SellCancelProductFixedPriceRouter}
          />

          <Route
            exact
            path={["/cancel/promotion", "/cancel/productdetails"]}
            component={CancelPromotionRouter}
          />
          <Route
            exact
            path={["/withdraw/zonepromotion", "/withdraw/zoneproduct"]}
            component={WithdrawPromotionZoneRouter}
          />
          <Route
            exact
            path={["/withdraw/clusterpromotion", "/withdraw/clusterproduct"]}
            component={WithdrawPromotionClusterRouter}
          />
          <Route
            exact
            path={[
              "/selectproduct",
              "/assigntocluster",
              "/assigntozone",
              "/view/assigned/zones",
              "/view/assigned/clusters",
              "/editprice",
            ]}
            component={ZoneClusterRouter}
          />
          <Route
            exact
            path={["/queryondaterange", "/showproducts"]}
            component={QueryOnDateRouter}
          />
          {/* <Route
            exact
            path={["/selectproductname", "/addpromotion"]}
            component={PromotionRouter}
          /> */}
          <Route
            exact
            path={[
              "/applypromotion/zone",
              "/definepromotion/zone",
              "/view/promotions/zone",
            ]}
            component={ZonePromotionRouter}
          />
          <Route
            exact
            path={[
              "/applypromotion/cluster",
              "/definepromotion/cluster",
              "/view/promotions/cluster",
            ]}
            component={ClusterPromotionRouter}
          />
          <Route exact path="/priceondate" component={PriceOnDate} />
          <Route
            exact
            path="/assignpricetoproduct"
            component={AssignPriceToProduct}
          />

          <Route exact path="/vendor" component={VendorLogin} />
          <Route exact path="/vendor/reg" component={Registration} />
          <Route exact path="/vendor/addproduct" component={AddProduct} />
          <Route exact path="/vendor/updateprice" component={SelectProduct} />
          <Route exact path="/viewproducts" component={ViewProducts} />
          <Route
            exact
            path={["/updateqty/zone", "/updateqty/zoneform"]}
            component={IncreaseZoneQtyRouter}
          />
          <Route
            exact
            path={["/updateqty/cluster", "/updateqty/clusterform"]}
            component={IncreaseClusterQtyRouter}
          />
          <Route exact path="/vendor/editproduct" component={EditItemPrice} />
          <Route exact path="/admin" component={CreateAdmin} />
          <Route exact path="/admin/login" component={AdminLogin} />
        </Switch>
      </Router>
    </div>
  )
}
FullNavbar.propTypes = {
  logout: PropTypes.func.isRequired,
  container: PropTypes.shape.isRequired,
  loggedInUser: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  loggedInUser: store.RetailerReducer.loggedInUser,
  loginStatus: store.RetailerReducer.loginStatus,
})
const actionsAsProps = {
  logout,
}
export default connect(stateAsProps, actionsAsProps)(FullNavbar)
