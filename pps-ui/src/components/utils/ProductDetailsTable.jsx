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
import { productDetailsTable, product } from "./constants"
import { getProductDetails } from "../../redux/actions/RetailerActions"
import convertCurrency from "./ConvertCurrency"

class ProductDetailsTable extends Component {
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
      <div className="flex-grid">
        <div className="product-name">
          <Typography className="card-header" variant="h5">
            Product Name : {productDetails.productName}
          </Typography>
        </div>
        <TableContainer component={Paper}>
          <Table aria-label="a dense table">
            <TableHead>
              <TableRow>
                {product.map((tcell) => (
                  <TableCell>{tcell}</TableCell>
                ))}
              </TableRow>
            </TableHead>
            <tbody>
              <TableRow>
                <TableCell>
                  {/* <Carousel interval="3000" animation="fade">
                    {productDetails.productImagePath
                      .slice(0)
                      .reverse()
                      .map((image) => (
                        <a
                          target="_blank"
                          rel="noopener noreferrer"
                          href={image}
                        >
                          <img className="thumbnail" src={image} alt="none" />
                        </a>
                      ))}
                  </Carousel> */}
                  <div className="imagePreview" id="imagePreview">
                    {productDetails.productImagePath.map((image) => (
                      <a target="_blank" rel="noopener noreferrer" href={image}>
                        <img className="thumbnail" src={image} alt="none" />
                      </a>
                    ))}
                  </div>
                </TableCell>
                <TableCell>{productDetails.productDescription}</TableCell>
              </TableRow>
            </tbody>
          </Table>
        </TableContainer>
        <TableContainer component={Paper}>
          <Table aria-label="a dense table">
            <TableHead>
              <TableRow>
                {productDetailsTable.map((tcell) => (
                  <TableCell>{tcell}</TableCell>
                ))}
              </TableRow>
            </TableHead>
            <tbody>
              <TableRow>
                <TableCell>{productDetails.companyName}</TableCell>
                <TableCell>
                  {sessionStorage.getItem("currency") === "USD"
                    ? `$ ${productDetails.productBasePrice}`
                    : convertCurrency(
                        "USD",
                        sessionStorage.getItem("currency"),
                        productDetails.productBasePrice
                      )}
                </TableCell>
                <TableCell>
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
                <TableCell>{productDetails.initialQuantity}</TableCell>
                <TableCell>{productDetails.remainingQuantity}</TableCell>
                <TableCell>{productDetails.productCategory}</TableCell>
                <TableCell>{productDetails.uom}</TableCell>
              </TableRow>
            </tbody>
          </Table>
        </TableContainer>
      </div>
    )
  }
}

ProductDetailsTable.propTypes = {
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
export default connect(stateAsProps, actionsAsProps)(ProductDetailsTable)
