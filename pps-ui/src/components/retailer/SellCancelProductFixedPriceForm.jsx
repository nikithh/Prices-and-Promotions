import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import Autocomplete from "@material-ui/lab/Autocomplete"
import { TextField, Typography, Button, FormControl } from "@material-ui/core"
import {
  getProductList,
  saveProductValue,
  getProductDetails,
} from "../../redux/actions/RetailerActions"

class SellCancelProductFixedPriceForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      productName: "",
    }
    this.handleChangeProduct = this.handleChangeProduct.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { getProductList: getProductListAlt } = this.props
    getProductListAlt()
  }

  handleChangeProduct = (e, value) => {
    const productName = value
    const {
      saveProductValue: saveProductValueAlt,
      getProductDetails: getProductDetailsAlt,
    } = this.props
    this.setState({ productName })
    saveProductValueAlt(productName)
    getProductDetailsAlt(value)
  }

  handleSubmit() {
    const { history } = this.props
    history.push("/sellcancel/fixedprice/product")
  }

  render() {
    const { productName } = this.state
    const { products } = this.props
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3 className="center-h3">Requirements</h3>
              {productName === "" && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please select a product name
                  </Typography>
                </div>
              )}
              {productName !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please select a product name
                  </Typography>
                </div>
              )}
            </div>
          </div>
          <div className="form-half">
            <form className="{classes.form}" noValidate>
              <div className="help-block">
                <Typography
                  color="primary"
                  component="h1"
                  variant="h4"
                  className="help-block-h4"
                >
                  Select a Product
                </Typography>
              </div>
              <FormControl variant="outlined" fullWidth>
                <Autocomplete
                  id="product-list"
                  fullWidth
                  options={products}
                  getOptionLabel={(option) => option}
                  renderInput={(params) => (
                    <TextField
                      // eslint-disable-next-line react/jsx-props-no-spreading
                      {...params}
                      label="Product Name"
                      variant="outlined"
                      autoFocus
                    />
                  )}
                  onChange={this.handleChangeProduct}
                  name="productName"
                />
              </FormControl>
              {productName !== "" && (
                <Button
                  type="button"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className="{classes.submit} submit-pad"
                  onClick={this.handleSubmit}
                  id="cluster-form-submit"
                >
                  Go
                </Button>
              )}
            </form>
          </div>
        </div>
      </div>
    )
  }
}

SellCancelProductFixedPriceForm.propTypes = {
  products: PropTypes.arrayOf.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  getProductList: PropTypes.func.isRequired,
  saveProductValue: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  products: store.RetailerReducer.productList,
})
const actionAsProps = {
  getProductList,
  saveProductValue,
  getProductDetails,
}
export default connect(
  stateAsProps,
  actionAsProps
)(SellCancelProductFixedPriceForm)
