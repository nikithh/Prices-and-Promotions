import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import WithdrawPromotionZoneForm from "./WithdrawPromotionZoneForm"
import WithdrawZonePromotion from "./WithdrawZonePromotion"

const WithdrawPromotionZoneRouter = () => {
  return (
    <Router>
      <Redirect to="/withdraw/zonepromotion" />
      <Switch>
        <PrivateRoute
          exact
          path="/withdraw/zonepromotion"
          component={WithdrawPromotionZoneForm}
        />
        <PrivateRoute
          exact
          path="/withdraw/zoneproduct"
          component={WithdrawZonePromotion}
        />
      </Switch>
    </Router>
  )
}

export default WithdrawPromotionZoneRouter
