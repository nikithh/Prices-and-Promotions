import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import ApplyPromotionInCluster from "./ApplyPromotionInCluster"
import DefinePromotionInCluster from "./DefinePromotionInCluster"
import ViewClusterPromotions from "./ViewClusterPromotions"

const ClusterPromotionRouter = () => {
  return (
    <Router>
      <Redirect to="/applypromotion/cluster" />
      <Switch>
        <PrivateRoute
          exact
          path="/applypromotion/cluster"
          component={ApplyPromotionInCluster}
        />
        <PrivateRoute
          exact
          path="/definepromotion/cluster"
          component={DefinePromotionInCluster}
        />
        <PrivateRoute
          exact
          path="/view/promotions/cluster"
          component={ViewClusterPromotions}
        />
      </Switch>
    </Router>
  )
}
export default ClusterPromotionRouter
