import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import CancelProductPromotion from "./CancelProductPromotion"
import CancelPromotionForm from "./CancelPromotionForm"

const CancelPromotionRouter = () => {
  return (
    <Router>
      <Redirect to="/cancel/promotion" />
      <Switch>
        <PrivateRoute
          exact
          path="/cancel/promotion"
          component={CancelPromotionForm}
        />
        <PrivateRoute
          exact
          path="/cancel/productdetails"
          component={CancelProductPromotion}
        />
      </Switch>
    </Router>
  )
}

export default CancelPromotionRouter
