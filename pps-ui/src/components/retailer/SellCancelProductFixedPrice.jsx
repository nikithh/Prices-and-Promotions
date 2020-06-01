/* eslint-disable no-nested-ternary */
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  Typography,
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
} from "@material-ui/core"

import {
  getProductDetails,
  sellProductFixedPrice,
  cancelProductFixedPrice,
} from "../../redux/actions/RetailerActions"
import { sellCancelProductFixedPrice } from "../utils/constants"
import Message from "../utils/Message"
import convertCurrency from "../utils/ConvertCurrency"

class SellCancelProductFixedPrice extends Component {
  constructor(props) {
    super(props)

    this.state = {}
    //  this.productNameNotSelected = this.productNameNotSelected.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { productName, getProductDetails: getProductDetailsAlt } = this.props
    getProductDetailsAlt(productName)
  }

  handleSubmitSell = (productNameAlt) => {
    const {
      productName,
      getProductDetails: getProductDetailsAlt,
      sellProductFixedPrice: sellProductFixedPriceAlt,
    } = this.props
    sellProductFixedPriceAlt(productNameAlt)
    getProductDetailsAlt(productName)
  }

  handleSubmitCancel = (productNameAlt) => {
    const {
      productName,
      getProductDetails: getProductDetailsAlt,
      cancelProductFixedPrice: cancelProductFixedPriceAlt,
    } = this.props
    cancelProductFixedPriceAlt(productNameAlt)
    getProductDetailsAlt(productName)
  }

  render() {
    const { productDetails } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {sellCancelProductFixedPrice}
              </Typography>
              {/* <img src={productDetails.productImagePath} alt="none" /> */}
              <Typography className="card-header" variant="h5">
                Product Name: {productDetails.productName}
              </Typography>
              <div className="product-table-data">
                <TableContainer component={Paper} className="product-table">
                  <Table size="small" aria-label="a dense table">
                    <TableHead>
                      <TableRow className="product-details-row">
                        <TableCell className="table-text" colSpan={2}>
                          Product Image
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableRow>
                      <TableCell colSpan={2}>
                        <div id="outer-image">
                          <div className="imagePreview" id="imagePreview">
                            {productDetails.productImagePath.map((image) => (
                              <a
                                target="_blank"
                                rel="noopener noreferrer"
                                href={image}
                              >
                                <img
                                  className="thumbnail"
                                  src={image}
                                  alt="none"
                                />
                              </a>
                            ))}
                          </div>
                        </div>
                      </TableCell>
                    </TableRow>
                    <TableHead>
                      <TableRow className="product-details-row">
                        <TableCell className="table-text">Vendor</TableCell>
                        <TableCell className="table-text">
                          Vendor Price
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableRow>
                      <TableCell className="table-text">
                        {productDetails.companyName}
                      </TableCell>
                      <TableCell className="table-text">
                        {sessionStorage.getItem("currency") === "USD"
                          ? `$ ${productDetails.productBasePrice}`
                          : convertCurrency(
                              "USD",
                              sessionStorage.getItem("currency"),
                              productDetails.productBasePrice
                            )}
                      </TableCell>
                    </TableRow>
                    <TableHead>
                      <TableRow className="product-details-row">
                        <TableCell className="table-text">
                          Unit Of Measurement
                        </TableCell>
                        <TableCell className="table-text">Category</TableCell>
                      </TableRow>
                    </TableHead>
                    <TableRow>
                      <TableCell className="table-text">
                        {productDetails.uom}
                      </TableCell>
                      <TableCell className="table-text">
                        {productDetails.productCategory}
                      </TableCell>
                    </TableRow>
                    <TableHead>
                      <TableRow className="product-details-row">
                        <TableCell className="table-text">
                          Product Price
                        </TableCell>
                        <TableCell className="table-text">
                          Product Description
                        </TableCell>
                      </TableRow>
                    </TableHead>
                    <TableRow>
                      <TableCell className="table-text">
                        {productDetails.effectivePriceObj !== null
                          ? sessionStorage.getItem("currency") === "USD"
                            ? `$ ${productDetails.effectivePriceObj.effectivePrice}`
                            : convertCurrency(
                                "USD",
                                sessionStorage.getItem("currency"),
                                productDetails.effectivePriceObj.effectivePrice
                              )
                          : sessionStorage.getItem("currency") === "USD"
                          ? `$ ${productDetails.productBasePrice}`
                          : convertCurrency(
                              "USD",
                              sessionStorage.getItem("currency"),
                              productDetails.productBasePrice
                            )}
                      </TableCell>
                      <TableCell>
                        <div className="product-desc">
                          <Typography variant="body2">
                            {productDetails.productDescription}
                          </Typography>
                        </div>
                      </TableCell>
                    </TableRow>
                  </Table>
                </TableContainer>
              </div>
              <>
                <Button
                  fullWidth
                  type="button"
                  variant="contained"
                  color="primary"
                  className="form-button {classes.submit} submit-pad"
                  onClick={() => {
                    if (
                      // eslint-disable-next-line no-alert
                      window.confirm(
                        "Are you sure you wish to sell it at fixed price?"
                      )
                    )
                      this.handleSubmitSell(productDetails.productName)
                  }}
                  id="sell-price-change-btn"
                >
                  Sell Only At Fixed Price
                </Button>
                <Button
                  fullWidth
                  type="button"
                  variant="contained"
                  color="primary"
                  className="form-button {classes.submit} submit-pad"
                  onClick={() => {
                    if (
                      // eslint-disable-next-line no-alert
                      window.confirm(
                        "Are you sure you wish to cancel selling at fixed price?"
                      )
                    )
                      this.handleSubmitCancel(productDetails.productName)
                  }}
                  id="cancel-price-change-btn"
                >
                  Cancel Selling At Fixed Price
                </Button>
              </>
              <Message />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

SellCancelProductFixedPrice.propTypes = {
  sellProductFixedPrice: PropTypes.func.isRequired,
  cancelProductFixedPrice: PropTypes.func.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  productName: PropTypes.string.isRequired,
  productDetails: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
})
const actionAsProps = {
  getProductDetails,
  sellProductFixedPrice,
  cancelProductFixedPrice,
}
export default connect(stateAsProps, actionAsProps)(SellCancelProductFixedPrice)
