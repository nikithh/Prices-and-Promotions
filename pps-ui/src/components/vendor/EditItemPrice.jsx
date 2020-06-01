import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  Typography,
  Paper,
  Table,
  TextField,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  IconButton,
} from "@material-ui/core"
import Alert from "@material-ui/lab/Alert"
import CloseIcon from "@material-ui/icons/Close"
import {
  getProductDetails,
  updateProduct,
} from "../../redux/actions/VendorActions"
import Message from "../utils/Message"

class EditItemPrice extends Component {
  constructor(props) {
    super(props)
    const { productDetails } = this.props
    this.state = {
      updatedProduct: {
        productName: productDetails.productName,
        newQuantity: 0,
        newBasePrice: productDetails.productBasePrice,
      },
    }
    this.handleQuantityChange = this.handleQuantityChange.bind(this)
    this.handlePriceChange = this.handlePriceChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    const { productName, getProductDetails: getProductDetailsAlt } = this.props
    getProductDetailsAlt(productName)
  }

  handleQuantityChange(e) {
    const { value } = e.target
    const { updatedProduct } = this.state
    updatedProduct.newQuantity = value
    this.setState({
      updatedProduct: {
        ...updatedProduct,
        newQuantity: value,
      },
    })
  }

  handlePriceChange(e) {
    const { value } = e.target
    const { updatedProduct } = this.state
    updatedProduct.newBasePrice = value
    this.setState({
      updatedProduct: {
        ...updatedProduct,
        newBasePrice: value,
      },
    })
  }

  handleSubmit(e) {
    e.preventDefault()
    const { updatedProduct } = this.state
    const { updateProduct: updateProductAlt } = this.props
    const { productDetails } = this.props
    if (updatedProduct.newBasePrice > productDetails.minimumBasePrice) {
      updateProductAlt(updatedProduct, updatedProduct.productName)
    }
  }

  render() {
    const { productDetails } = this.props
    const { updatedProduct } = this.state
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <div className="help-block">
                <Typography
                  color="primary"
                  component="h1"
                  variant="h4"
                  className="help-block-h4"
                >
                  Update Product Price and Quantity
                </Typography>
              </div>

              <div className="product-name">
                <Message />
                <Typography className="card-header" variant="h5">
                  Product Name : {productDetails.productName}
                </Typography>
              </div>
              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      <TableCell>Product Image</TableCell>
                      <TableCell>Product Description</TableCell>
                    </TableRow>
                  </TableHead>
                  <tbody>
                    <TableRow>
                      <TableCell>
                        <div className="imagePreview" id="imagePreview">
                          {productDetails.productImage.map((image) => (
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
                      </TableCell>
                      <TableCell>{productDetails.productDescription}</TableCell>
                    </TableRow>
                  </tbody>
                </Table>
              </TableContainer>
              <Typography className="card-header" variant="h6">
                Product Category: {productDetails.productCategory}
              </Typography>
              <Typography className="card-header" variant="h6">
                productBasePrice: {productDetails.productBasePrice}
                <Typography className="card" id="span-warning">
                  product base price should be greater than{" "}
                  {productDetails.minimumBasePrice.toFixed(2)}
                </Typography>
              </Typography>
              {productDetails.isPromotionRunning && (
                <Alert
                  severity="info"
                  action={
                    <IconButton aria-label="close" color="inherit" size="small">
                      <CloseIcon fontSize="inherit" />
                    </IconButton>
                  }
                >
                  Sorry you cannot change the price
                </Alert>
              )}
              {!productDetails.isPromotionRunning && (
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  type="number"
                  id="BasePrice"
                  label="BasePrice"
                  InputLabelProps={{ shrink: true, required: true }}
                  name="newBasePrice"
                  autoComplete="newBasePrice"
                  autoFocus
                  // value={
                  //   sessionStorage.getItem("currency") === "USD"
                  //     ? `$ ${updatedProduct.newBasePrice}`
                  //     : convertCurrency(
                  //         "USD",
                  //         sessionStorage.getItem("currency"),
                  //         updatedProduct.newBasePrice
                  //       )
                  // }
                  value={updateProduct.newBasePrice}
                  onChange={this.handlePriceChange}
                />
              )}
              <Typography className="card-header" variant="h6">
                Units of Measurement: {productDetails.productUOM}
              </Typography>
              <Typography className="card-header" variant="h6">
                Quantity: {productDetails.initialQuantity}
              </Typography>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                type="number"
                id="newQuantity"
                label="New Quantity"
                InputLabelProps={{ shrink: true, required: true }}
                name="newQuantity"
                autoComplete="newQuantity"
                value={updatedProduct.newQuantity}
                onChange={this.handleQuantityChange}
              />
              <Button
                halfWidth
                id="update-btn"
                type="button"
                variant="contained"
                color="primary"
                className="form-button {classes.submit}"
                onClick={this.handleSubmit}
                style={{ marginTop: "10px" }}
              >
                Update
              </Button>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

EditItemPrice.propTypes = {
  getProductDetails: PropTypes.func.isRequired,
  updateProduct: PropTypes.func.isRequired,
  productName: PropTypes.string.isRequired,
  productDetails: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.VendorReducer.productDetails,
  productName: store.VendorReducer.productName,
  isPromotion: store.VendorReducer.isPromotion,
  minimumsellingprice: store.VendorReducer.minimumsellingprice,
})
const actionsAsProps = {
  getProductDetails,
  updateProduct,
}
export default connect(stateAsProps, actionsAsProps)(EditItemPrice)
