/* eslint-disable no-nested-ternary */
import React, { Component } from "react"
import connect from "react-redux/es/connect/connect"
import {
  Typography,
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
} from "@material-ui/core"
import PropTypes from "prop-types"

import { getProductDetails } from "../../redux/actions/RetailerActions"
import convertCurrency from "./ConvertCurrency"

class ProductDetails extends Component {
  constructor(props) {
    super(props)

    this.state = {}
  }

  componentDidMount() {
    const { productName, getProductDetails: getProductDetailsAlt } = this.props
    getProductDetailsAlt(productName)
  }

  render() {
    const { productDetails } = this.props
    return (
      <div className="flex-grid-padding">
        <div className="product-name">
          <Typography className="card-header" variant="h4">
            {productDetails.productName}
          </Typography>
        </div>
        <div className="product-image">
          {productDetails.productImagePath.map((image) => (
            <img src={image} alt="none" />
          ))}
        </div>
        {/* </div> */}
        <div className="product-table-data">
          <TableContainer component={Paper} className="product-table">
            <Table size="small" aria-label="a dense table">
              <TableHead>
                <TableRow className="product-details-row">
                  <TableCell className="table-text">Base</TableCell>
                  <TableCell className="table-text">Vendor</TableCell>
                </TableRow>
              </TableHead>
              <TableRow>
                <TableCell className="table-text">
                  {productDetails.productBasePrice !== null
                    ? sessionStorage.getItem("currency") === "USD"
                      ? `$ ${productDetails.productBasePrice}`
                      : convertCurrency(
                          "USD",
                          sessionStorage.getItem("currency"),
                          productDetails.productBasePrice
                        )
                    : "NO EFFECTIVE PRICE"}
                </TableCell>
                <TableCell className="table-text">
                  {productDetails.companyName}
                </TableCell>
              </TableRow>
              <TableHead>
                <TableRow className="product-details-row">
                  <TableCell className="table-text">Quantity</TableCell>
                  <TableCell className="table-text">Category</TableCell>
                </TableRow>
              </TableHead>
              <TableRow>
                <TableCell className="table-text">
                  {productDetails.remainingQuantity}
                </TableCell>
                <TableCell className="table-text">
                  {productDetails.productCategory}
                </TableCell>
              </TableRow>
              <TableHead>
                <TableRow className="product-details-row">
                  <TableCell className="table-text">Effective Price</TableCell>
                  <TableCell className="table-text">
                    {productDetails.effectivePriceObj !== null
                      ? sessionStorage.getItem("currency") === "USD"
                        ? `$ ${productDetails.effectivePriceObj.effectivePrice}`
                        : convertCurrency(
                            "USD",
                            sessionStorage.getItem("currency"),
                            productDetails.effectivePriceObj.effectivePrice
                          )
                      : "NO EFFECTIVE PRICE"}
                  </TableCell>
                </TableRow>
              </TableHead>
            </Table>
          </TableContainer>
        </div>
        <div className="product-desc">
          <Typography variant="body2">
            {productDetails.productDescription}
          </Typography>
        </div>
      </div>
    )
  }
}

ProductDetails.propTypes = {
  getProductDetails: PropTypes.func.isRequired,

  productName: PropTypes.string.isRequired,
  productDetails: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
})
const actionsAsProps = {
  getProductDetails,
}
export default connect(stateAsProps, actionsAsProps)(ProductDetails)
