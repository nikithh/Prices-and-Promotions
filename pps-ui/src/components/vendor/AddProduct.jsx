import {
  InputLabel,
  TextField,
  Typography,
  Button,
  FormControl,
  InputAdornment,
  MenuItem,
} from "@material-ui/core"
import CloudUploadIcon from "@material-ui/icons/CloudUpload"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import PropTypes from "prop-types"
import React, { Component } from "react"
import { connect } from "react-redux"
import Select from "@material-ui/core/Select"
import Alert from "@material-ui/lab/Alert"
import Message from "./Message"
import currencyConvert from "../utils/CurrencyConvert"

import { postProduct } from "../../redux/actions/VendorActions"
// import convertCurrency from "../utils/ConvertCurrency"

class AddProduct extends Component {
  constructor(props) {
    super(props)
    this.state = {
      product: {
        productName: "",
        companyName: "",
        productCategory: "",
        productBasePrice: "",
        initialQuantity: "",
        uom: "",
        productDescription: "",
        productGroup: "",
        abv: "",
        volume: "",
      },
      selectedImages: [],
      errorImages: [],
      currency: "",
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.onFileChange = this.onFileChange.bind(this)
  }

  onFileChange = (event) => {
    const { files } = event.target
    const errorFiles = []
    const perfectFiles = []
    Array.from(files).forEach((file) => {
      if (file.size / 1024 / 1024 > 1) {
        errorFiles.push(file.name)
      } else {
        perfectFiles.push(file)
      }
    })
    this.readURL(perfectFiles)
    this.setState({ selectedImages: perfectFiles, errorImages: errorFiles })
  }

  readURL = (files) => {
    document.getElementById("imagePreview").innerHTML = ""
    for (let i = 0; i < files.length; i += 1) {
      const file = files[i]
      if (file.type.match("image")) {
        const reader = new FileReader()
        reader.addEventListener("load", (event) => {
          const picFile = event.target
          let images = document.getElementById("imagePreview").innerHTML
          images += `<div><img class='thumbnail' src=${picFile.result} /><div>`
          document.getElementById("imagePreview").innerHTML = images
        })
        reader.readAsDataURL(file)
      }
    }
  }

  setCurrency = (e) => {
    this.setState({ currency: e.target.value })
  }

  handleSubmit() {
    const { loggedInUser } = this.props
    const { selectedImages } = this.state
    const { product, currency } = this.state
    product.companyName = loggedInUser.userName
    const {
      productName,
      productBasePrice,
      initialQuantity,
      productCategory,
    } = product
    const test = this.props
    product.productBasePrice = currencyConvert(currency, productBasePrice)

    if (productCategory === "ALCOHOL_PROD") {
      product.uom = "Lts"
    }

    if (productName && productBasePrice > 0 && initialQuantity > 1) {
      if (
        (productCategory === "ALCOHOL_PROD" && initialQuantity < 101) ||
        (productCategory === "BABY_PROD" && initialQuantity < 501)
      ) {
        const data = new FormData()
        data.append("product", JSON.stringify(product))
        Array.from(selectedImages).forEach((file) => {
          data.append("files", file)
        })
        test.postProduct(data)
      }
    }
  }

  handleChange(e) {
    const { name, value } = e.target
    const { product } = this.state
    product[name] = value
    this.setState({ product })
  }

  render() {
    if (sessionStorage.getItem("token") != null) {
      const { product, errorImages } = this.state
      const {
        productName,
        productBasePrice,
        initialQuantity,
        productCategory,
        productDescription,
        abv,
        volume,
        uom,
      } = product
      return (
        <div className="box-container">
          <div className="joint-form">
            <div className="validation-half">
              <div className="validations">
                <h3>Requirements</h3>
                {product.productName.length <= 0 && (
                  <div className="typo-div">
                    <ClearIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Name cannot be empty
                    </Typography>
                  </div>
                )}
                {product.productName.length > 0 && (
                  <div className="approved-text">
                    <CheckIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Name cannot be empty
                    </Typography>
                  </div>
                )}
                {product.productBasePrice < 1 && (
                  <div className="typo-div">
                    <ClearIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Base price must be greater than 0
                    </Typography>
                  </div>
                )}
                {product.productBasePrice > 0 && (
                  <div className="approved-text">
                    <CheckIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Base price must be greater than 0
                    </Typography>
                  </div>
                )}
                {product.initialQuantity < 1 && (
                  <div className="typo-div">
                    <ClearIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Minimum quantity should be 1
                    </Typography>
                  </div>
                )}
                {product.initialQuantity > 0 && (
                  <div className="approved-text">
                    <CheckIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      Minimum quantity should be 1
                    </Typography>
                  </div>
                )}
                {product.productCategory === "ALCOHOL_PROD" &&
                  product.initialQuantity < 101 && (
                    <div className="approved-text">
                      <CheckIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Maximum quantity of ALCOHOL should be 100
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "ALCOHOL_PROD" &&
                  product.initialQuantity > 100 && (
                    <div className="typo-div">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Maximum quantity of ALCOHOL should be 100
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "ALCOHOL_PROD" && product.abv > 0 && (
                  <div className="approved-text">
                    <CheckIcon className="icon-style" />
                    <Typography variant="subtitle2" gutterBottom>
                      ABV Should be greater than 0
                    </Typography>
                  </div>
                )}
                {product.productCategory === "ALCOHOL_PROD" &&
                  product.abv <= 0 && (
                    <div className="typo-div">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        ABV should be greater than 0
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "ALCOHOL_PROD" &&
                  product.volume > 0 && (
                    <div className="approved-text">
                      <CheckIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Volume should be greater than 0
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "ALCOHOL_PROD" &&
                  product.volume <= 0 && (
                    <div className="typo-div">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Volume should be greater than 0
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "BABY_PROD" &&
                  product.initialQuantity < 501 && (
                    <div className="approved-text">
                      <CheckIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Maximum quantity of BabyProducts should be 500
                      </Typography>
                    </div>
                  )}
                {product.productCategory === "BABY_PROD" &&
                  product.initialQuantity > 500 && (
                    <div className="typo-div">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Maximum quantity of BabyProducts should be 500
                      </Typography>
                    </div>
                  )}
              </div>
            </div>
            <div className="form-half" id="add-prods-form">
              <form className="{classes.form}" noValidate>
                <div className="help-block">
                  <Typography
                    color="primary"
                    component="h1"
                    variant="h4"
                    className="help-block-h4"
                  >
                    AddProduct
                  </Typography>
                </div>
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  id="productName"
                  label="ProductName"
                  name="productName"
                  autoComplete="productName"
                  onChange={this.handleChange}
                  value={productName}
                  autoFocus
                />
                <FormControl
                  variant="outlined"
                  fullWidth
                  className="space-margin-top"
                >
                  <InputLabel htmlFor="outlined-age-native-simple">
                    Product Category
                  </InputLabel>
                  <Select
                    labelId="productCategory"
                    fullWidth
                    variant="outlined"
                    margin="normal"
                    required
                    name="productCategory"
                    label="productCategory"
                    onChange={this.handleChange}
                    id="select-product"
                  >
                    <MenuItem value="ALCOHOL_PROD">ALCOHOL_PROD</MenuItem>
                    <MenuItem value="BABY_PROD">BABY_PROD</MenuItem>
                  </Select>
                </FormControl>
                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  type="number"
                  id="initialQuantity"
                  label="quantity"
                  name="initialQuantity"
                  autoComplete="initialQuantity"
                  onChange={this.handleChange}
                  value={initialQuantity}
                />
                <div className="currency-container">
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    type="number"
                    id="productBasePrice"
                    label="Product base price"
                    name="productBasePrice"
                    autoComplete="productBasePrice"
                    onChange={this.handleChange}
                    // value={
                    //   sessionStorage.getItem("currency") !== "USD"
                    //     ? convertCurrency(
                    //         "USD",
                    //         sessionStorage.getItem("currency"),
                    //         productBasePrice
                    //       )
                    //     : productBasePrice
                    // }
                    value={productBasePrice}
                    startAdornment={
                      <InputAdornment position="start">$</InputAdornment>
                    }
                  />
                  <div className="vert-space" />
                  <FormControl
                    variant="outlined"
                    fullWidth
                    id="currency-select"
                  >
                    <InputLabel htmlFor="outlined-age-native-simple">
                      Currency Unit
                    </InputLabel>
                    <Select
                      labelId="productCategory"
                      fullWidth
                      variant="outlined"
                      margin="normal"
                      required
                      name="productCategory"
                      label="Currency"
                      id="currency-unit"
                      onChange={this.setCurrency}
                    >
                      <MenuItem value="USD">$</MenuItem>
                      <MenuItem value="EUR">€</MenuItem>
                      <MenuItem value="INR">₹</MenuItem>
                    </Select>
                  </FormControl>
                </div>

                {productCategory === "ALCOHOL_PROD" && (
                  <>
                    <TextField
                      variant="outlined"
                      margin="normal"
                      required
                      fullWidth
                      type="number"
                      id="abv"
                      label="abv"
                      name="abv"
                      autoComplete="abv"
                      onChange={this.handleChange}
                      value={abv}
                    />
                    <TextField
                      variant="outlined"
                      margin="normal"
                      required
                      fullWidth
                      type="number"
                      id="volume"
                      label="volume(in litres)"
                      name="volume"
                      autoComplete="volume"
                      onChange={this.handleChange}
                      value={volume}
                      autoFocus
                    />
                  </>
                )}
                {productCategory === "BABY_PROD" && (
                  <>
                    <FormControl
                      variant="outlined"
                      fullWidth
                      className="space-margin-top"
                    >
                      <InputLabel htmlFor="outlined-age-native-simple">
                        Units Of Measurement
                      </InputLabel>

                      <Select
                        labelId="uom"
                        fullWidth
                        variant="outlined"
                        margin="normal"
                        required
                        name="uom"
                        label="Units of Measurement "
                        onChange={this.handleChange}
                        value={uom}
                        id="baby-prod"
                      >
                        <MenuItem value="KGs">KGs</MenuItem>
                        <MenuItem value="Pounds">Pounds</MenuItem>
                      </Select>
                    </FormControl>
                  </>
                )}

                <TextField
                  variant="outlined"
                  margin="normal"
                  required
                  fullWidth
                  label="Product description"
                  name="productDescription"
                  autoComplete="productDescription"
                  onChange={this.handleChange}
                  value={productDescription}
                  aria-label="minimum height"
                  rowsMin={3}
                  placeholder="product discription"
                />
                <div className="upload-btn-wrapper">
                  <Button
                    type="button"
                    className="btn"
                    fullWidth
                    variant="outlined"
                    color="primary"
                    startIcon={<CloudUploadIcon />}
                  >
                    Choose Product Images
                  </Button>
                  <input
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    type="file"
                    name="productImage"
                    id="productImage"
                    multiple
                    onChange={this.onFileChange}
                  />
                </div>
                <div className="imagePreview" id="imagePreview" />
                {errorImages.length > 0 && (
                  <div className="image-error">
                    {/* <Typography
                      id="span-warning"
                      variant="h6"
                      className="help-block-h4"
                    >
                      <div className="error-line">
                        Image size should be less than 1 MB
                      </div>
                      {errorImages.map((imageName) => (
                        <div className="error-line">{imageName}</div>
                      ))}
                    </Typography> */}
                    <Alert severity="warning">
                      <div className="error-line">
                        Maximum image upload size is 1 MB.
                      </div>
                      Images{" "}
                      {errorImages.map((imageName) => (
                        <React.Fragment className="error-line">
                          &ldquo;{imageName}&rdquo;{" "}
                        </React.Fragment>
                      ))}
                      exceed this limit.
                    </Alert>
                  </div>
                )}

                <Button
                  type="button"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className="{classes.submit}"
                  onClick={this.handleSubmit}
                  id="submit-prods"
                >
                  Save
                </Button>
              </form>
              <Message />
            </div>
          </div>
        </div>
      )
    }
    return true
  }
}

AddProduct.propTypes = {
  postProduct: PropTypes.func.isRequired,
  loggedInUser: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  loggedInUser: store.RetailerReducer.loggedInUser,
})
const actionAsProps = {
  postProduct,
}
export default connect(stateAsProps, actionAsProps)(AddProduct)
