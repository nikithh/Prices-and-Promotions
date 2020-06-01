import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import SellCancelProductFixedPrice from "./SellCancelProductFixedPrice"
import SellCancelProductFixedPriceForm from "./SellCancelProductFixedPriceForm"

const SellCancelProductFixedPriceRouter = () => {
  return (
    <Router>
      <Redirect to="/sellcancel/fixedprice" />
      <Switch>
        <PrivateRoute
          exact
          path="/sellcancel/fixedprice"
          component={SellCancelProductFixedPriceForm}
        />
        <PrivateRoute
          exact
          path="/sellcancel/fixedprice/product"
          component={SellCancelProductFixedPrice}
        />
      </Switch>
    </Router>
  )
}

export default SellCancelProductFixedPriceRouter
