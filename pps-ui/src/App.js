import React from "react"
import { Provider } from "react-redux"
import { BrowserRouter as Router, Route, Switch } from "react-router-dom"
import FullNavbar from "./components/organisms/FullNavbar"
// eslint-disable-next-line import/no-named-as-default
import PrivateRoute from "./components/utils/privateRoute"
import store from "./redux/store"

const App = () => {
  const retailerRoutes = [
    "/updateqty/zone",
    "/updateqty/zoneform",
    "/group",
    "/products/store",
    "/zone",
    "/cluster",
    "/store",
    "/view/zones",
    "/view/clusters",
    "/products/assign",
    "/view/products/daterange",
    "/selectproductname",
    "/addproducts",
    "/selectproduct",
    "/assigntocluster",
    "/assigntozone",
    "/view/assigned/zones",
    "/view/assigned/clusters",
    "/view/products/daterange",
    "/view/effectiveprices",
    "/queryondaterange",
    "/showproducts",
    "/selectproductname",
    "/addpromotion",
    "/cancel/promotion",
    "/cancel/productdetails",
    "/withdraw/zonepromotion",
    "/withdraw/zoneproduct",
    "/withdraw/clusterpromotion",
    "/withdraw/clusterproduct",
    "/applypromotion/zone",
    "/definepromotion/zone",
    "/view/promotions/zone",
    "/applypromotion/cluster",
    "/definepromotion/cluster",
    "/view/promotions/cluster",
    "/priceondate",
    "/assignpricetoproduct",
    "/editprice",
    "/product/pricechange/canceleffective",
    "/product/pricechange/cancelnoteffective",
    "/vendor/addproduct",
    "/vendor/updateprice",
    "/vendor/editproduct",
    "/admin/login",
    "/sellcancel/fixedprice/product",
    "/sellcancel/fixedprice",
    "/cancel/productdetails",
    "/withdraw/zoneproduct",
    "/cancel/promotion",
    "/withdraw/zonepromotion",
    "/withdraw/zoneproduct",
    "/withdraw/clusterpromotion",
    "/withdraw/clusterproduct",
    "/applypromotion/zone",
    "/definepromotion/zone",
    "/view/promotions/zone",
    "/applypromotion/cluster",
    "/definepromotion/cluster",
    "/view/promotions/cluster",
    "/admin",
    "/dashboard",
    "/approvepromotion",
    "/approvepromotionpage",
    "/vendor/increaseqtyzone",
    "/vendor/increaseqtycluster",
    "/viewproducts",
    "/updateqty/clusterform",
  ]
  return (
    <Provider store={store}>
      <div>
        <Router>
          <Switch>
            <Route exact path="/" component={FullNavbar} />
            {retailerRoutes.map((route) => {
              return <PrivateRoute component={FullNavbar} path={route} />
            })}
            <Route exact path="/vendor" component={FullNavbar} />
            <Route exact path="/vendor/reg" component={FullNavbar} />
            <Route exact path="/admin/login" component={FullNavbar} />
          </Switch>
        </Router>
      </div>
    </Provider>
  )
}
export default App
