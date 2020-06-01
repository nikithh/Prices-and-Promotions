import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import QueryOnDateRange from "./QueryOnDateRange"
import ViewPromotions from "./ViewPromotions"

const QueryOnDateRouter = () => {
  return (
    <Router>
      <Redirect to="/queryondaterange" />
      <Switch>
        <PrivateRoute
          exact
          path="/queryondaterange"
          component={QueryOnDateRange}
        />
        <PrivateRoute
          exact
          path="/view/promotions"
          component={ViewPromotions}
        />
      </Switch>
    </Router>
  )
}
export default QueryOnDateRouter
