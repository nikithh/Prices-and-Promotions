import React from "react"
import { BrowserRouter as Router, Switch } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import SelectProduct from "./SelectProduct"
import AssignToZone from "./AssignToZone"
import AssignToCluster from "./AssignToCluster"
import ViewAssignedZones from "./ViewAssignedZones"
import ViewAssignedClusters from "./ViewAssignedClusters"
import EditItemPrice from "./EditItemPrice"

const ZoneClusterRouter = () => {
  return (
    <Router>
      <Switch>
        <PrivateRoute exact path="/selectproduct" component={SelectProduct} />
        <PrivateRoute exact path="/editprice" component={EditItemPrice} />
        <PrivateRoute
          exact
          path="/assigntocluster"
          component={AssignToCluster}
        />
        <PrivateRoute exact path="/assigntozone" component={AssignToZone} />
        <PrivateRoute
          exact
          path="/view/assigned/zones"
          component={ViewAssignedZones}
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
export default ZoneClusterRouter
