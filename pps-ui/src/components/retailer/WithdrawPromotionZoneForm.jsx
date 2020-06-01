import {
  InputLabel,
  TextField,
  Typography,
  Button,
  FormControl,
  Select,
} from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import React, { Component } from "react"
import { connect } from "react-redux"
import Autocomplete from "@material-ui/lab/Autocomplete"
import PropTypes from "prop-types"
import {
  getProductList,
  saveProductValue,
  saveZoneValue,
  getProductDetails,
  getZonesForProduct,
} from "../../redux/actions/RetailerActions"
import {
  withdrawPromotionZoneConst,
  selectZone,
  selectProduct,
} from "../utils/constants"

class WithdrawPromotionZoneForm extends Component {
  constructor(props) {
    super(props)
    this.state = {
      zone: "",
      productName: "",
    }
    this.handleChangeZone = this.handleChangeZone.bind(this)
    this.handleChangeProduct = this.handleChangeProduct.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { getProductList: getProductListAlt } = this.props
    getProductListAlt()
  }

  handleChangeProduct = (e, value) => {
    const {
      getZonesForProduct: getZonesForProductAlt,
      saveProductValue: saveProductValueAlt,
      getProductDetails: getProductDetailsAlt,
    } = this.props
    const productName = value
    this.setState({ productName })
    saveProductValueAlt(productName)
    getZonesForProductAlt(productName)
    getProductDetailsAlt(value)
  }

  handleChangeZone(e) {
    const { value } = e.target
    this.setState({ zone: value })
    const { saveZoneValue: saveZoneValueAlt } = this.props
    saveZoneValueAlt(value)
  }

  handleSubmit() {
    const { history } = this.props
    history.push("/withdraw/zoneproduct")
  }

  render() {
    const { zone, productName } = this.state
    const { products, productZoneList } = this.props
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3 className="center-h3">Requirements</h3>
              {zone === "" && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectZone}
                  </Typography>
                </div>
              )}
              {zone !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectZone}
                  </Typography>
                </div>
              )}
              {productName === "" && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectProduct}
                  </Typography>
                </div>
              )}
              {productName !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectProduct}
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
                  {withdrawPromotionZoneConst}
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
              <FormControl margin="normal" variant="outlined" fullWidth>
                <InputLabel htmlFor="outlined-age-native-simple">
                  Zone
                </InputLabel>
                <Select
                  fullWidth
                  native
                  value={zone}
                  onChange={this.handleChangeZone}
                  label="Zone"
                  inputProps={{
                    name: "zone",
                    id: "zone",
                  }}
                >
                  <option aria-label="None" value="" />
                  {productZoneList.map((zoneValue) => {
                    return <option value={zoneValue}>{zoneValue}</option>
                  })}
                </Select>
              </FormControl>
              {productName !== "" && zone !== "" && (
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

WithdrawPromotionZoneForm.propTypes = {
  products: PropTypes.arrayOf.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  saveProductValue: PropTypes.func.isRequired,
  getProductList: PropTypes.func.isRequired,
  saveZoneValue: PropTypes.func.isRequired,
  productZoneList: PropTypes.arrayOf.isRequired,
  getZonesForProduct: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  productZoneList: store.RetailerReducer.productZoneList,
  products: store.RetailerReducer.productList,
})
const actionAsProps = {
  getProductList,
  saveProductValue,
  getProductDetails,
  saveZoneValue,
  getZonesForProduct,
}
export default connect(stateAsProps, actionAsProps)(WithdrawPromotionZoneForm)
