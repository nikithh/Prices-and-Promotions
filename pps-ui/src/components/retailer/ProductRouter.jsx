import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import AddProducts from "./AddProducts"
import AddProductToStore from "./AddProductToStore"
import StoreForm from "./StoreForm"

const ProductRouter = () => {
  return (
    <Router>
      <Redirect to="/products/store" />
      <Switch>
        <PrivateRoute
          exact
          path="/products/store"
          component={AddProductToStore}
        />
        <PrivateRoute exact path="/addproducts" component={AddProducts} />
        <PrivateRoute exact path="/store" component={StoreForm} />
      </Switch>
    </Router>
  )
}

export default ProductRouter
