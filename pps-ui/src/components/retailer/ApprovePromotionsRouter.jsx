import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import ApprovePromotionsForm from "./ApprovePromotionsForm"
import ApprovePromotions from "./ApprovePromotions"

const ApprovePromotionsRouter = () => {
  return (
    <Router>
      <Redirect to="/approvepromotion" />
      <Switch>
        <PrivateRoute
          exact
          path="/approvepromotion"
          component={ApprovePromotionsForm}
        />
        <PrivateRoute
          exact
          path="/approvepromotionpage"
          component={ApprovePromotions}
        />
      </Switch>
    </Router>
  )
}

export default ApprovePromotionsRouter
