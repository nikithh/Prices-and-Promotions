import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import IncreaseQtyClusterForm from "./IncreaseQtyClusterForm"
import IncreaseQtyCluster from "./IncreaseQtyCluster"
import ViewAssignedClusters from "./ViewAssignedClusters"

const IncreaseClusterQtyRouter = () => {
  return (
    <Router>
      <Redirect to="/updateqty/cluster" />
      <Switch>
        <PrivateRoute
          exact
          path="/updateqty/cluster"
          component={IncreaseQtyCluster}
        />
        <PrivateRoute
          exact
          path="/updateqty/clusterform"
          component={IncreaseQtyClusterForm}
        />
        <PrivateRoute
          exact
          path="/view/assigned/clusters"
          component={ViewAssignedClusters}
        />
      </Switch>
    </Router>
  )
}
export default IncreaseClusterQtyRouter
