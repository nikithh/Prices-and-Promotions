import {
  Table,
  Typography,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getProductDetails,
  resetStatusCode,
} from "../../redux/actions/RetailerActions"
import ProductDetails from "../utils/ProductDetails"
import { viewAssignedClusters } from "../utils/constants"
import Message from "../utils/Message"
import convertCurrency from "../utils/ConvertCurrency"

class ViewAssignedClusters extends Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { getProductDetails: getProductDetailsAlt, productName } = this.props
    getProductDetailsAlt(productName)
  }

  render() {
    const { productDetails, resetStatusCode: resetStatusCodeAlt } = this.props
    const zoneData = productDetails.assignProduct // Swap with the actual prop while integrating: this.props.productDetails.assignProduct

    const tableRowElm = (zone) => {
      if (zone.cluster !== null && Array.isArray(zone.cluster)) {
        return zone.cluster.map((cluster) => (
          <TableRow>
            <TableCell>
              {zone.price > 0 ? (
                <Typography variant="subtitle1" gutterBottom>
                  {cluster.clusterName}

                  <Typography variant="subtitle1" gutterBottom>
                    {zone.zoneName}
                  </Typography>
                </Typography>
              ) : (
                <Typography variant="subtitle1" gutterBottom>
                  {cluster.clusterName}
                  <Typography variant="subtitle1" gutterBottom>
                    {zone.zoneName}
                  </Typography>
                </Typography>
              )}
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" gutterBottom>
                {cluster.quantityAssigned}
              </Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" gutterBottom>
                {cluster.profitPercentage}
              </Typography>
            </TableCell>
            <TableCell>
              <Typography variant="subtitle1" gutterBottom>
                {sessionStorage.getItem("currency") === "USD"
                  ? `$ ${cluster.price}`
                  : convertCurrency(
                      "USD",
                      sessionStorage.getItem("currency"),
                      cluster.price
                    )}
              </Typography>
            </TableCell>
          </TableRow>
        ))
      }
      return null
    }

    return (
      <div className="box-container">
        {resetStatusCodeAlt()}
        <div className="joint-form-large">
          <ProductDetails />
          <div className="product-form-body-padding">
            <Typography className="card-header" variant="h4">
              Assign to Cluster
            </Typography>
            <TableContainer component={Paper}>
              <Table aria-label="a dense table">
                <TableHead>
                  <TableRow>
                    {viewAssignedClusters.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <tbody>{zoneData.map((zone) => tableRowElm(zone))}</tbody>
              </Table>
            </TableContainer>
          </div>
        </div>
        <Message />
      </div>
    )
  }
}

ViewAssignedClusters.propTypes = {
  resetStatusCode: PropTypes.func.isRequired,
  productDetails: PropTypes.shape.isRequired,
  productName: PropTypes.string.isRequired,
  getProductDetails: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
})
const actionAsProps = {
  getProductDetails,
  resetStatusCode,
}

export default connect(stateAsProps, actionAsProps)(ViewAssignedClusters)
