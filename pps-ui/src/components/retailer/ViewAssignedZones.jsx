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
import { viewAssignedZones } from "../utils/constants"
import Message from "../utils/Message"
import convertCurrency from "../utils/ConvertCurrency"

class ViewAssignedZones extends Component {
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
    return (
      <div className="box-container">
        {resetStatusCodeAlt()}
        <div className="joint-form-large">
          <ProductDetails />
          <div className="product-form-body-padding">
            <Typography className="card-header" variant="h4">
              Assign to Zone
            </Typography>
            <TableContainer component={Paper}>
              <Table aria-label="a dense table">
                <TableHead>
                  <TableRow>
                    {viewAssignedZones.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <tbody>
                  {productDetails.assignProduct.map((zone) => {
                    return (
                      <TableRow>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {zone.zoneName}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {zone.quantityAssigned}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {zone.profitPercentage}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {sessionStorage.getItem("currency") === "USD"
                              ? `$ ${zone.price}`
                              : convertCurrency(
                                  "USD",
                                  sessionStorage.getItem("currency"),
                                  zone.price
                                )}
                          </Typography>
                        </TableCell>
                      </TableRow>
                    )
                  })}
                </tbody>
              </Table>
            </TableContainer>
          </div>
        </div>
        <Message />
      </div>
    )
  }
}

ViewAssignedZones.propTypes = {
  resetStatusCode: PropTypes.func.isRequired,
  productDetails: PropTypes.shape.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  productName: PropTypes.string.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
})
const actionAsProps = {
  getProductDetails,
  resetStatusCode,
}
export default connect(stateAsProps, actionAsProps)(ViewAssignedZones)
