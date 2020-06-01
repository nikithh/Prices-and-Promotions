import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import WithdrawPromotionClusterForm from "./WithdrawPromotionClusterForm"
import WithdrawClusterPromotion from "./WithdrawClusterPromotion"

const WithdrawPromotionClusterRouter = () => {
  return (
    <Router>
      <Redirect to="/withdraw/clusterpromotion" />
      <Switch>
        <PrivateRoute
          exact
          path="/withdraw/clusterpromotion"
          component={WithdrawPromotionClusterForm}
        />
        <PrivateRoute
          exact
          path="/withdraw/clusterproduct"
          component={WithdrawClusterPromotion}
        />
      </Switch>
    </Router>
  )
}

export default WithdrawPromotionClusterRouter
