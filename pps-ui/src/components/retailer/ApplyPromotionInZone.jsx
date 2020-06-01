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
import Autocomplete from "@material-ui/lab/Autocomplete"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getProductList,
  saveProductValue,
  getProductDetails,
  saveZoneValue,
  checkAssignedZone,
  clearAssignedPrice,
  getZonesForProduct,
} from "../../redux/actions/RetailerActions"
import {
  selectProduct,
  selectZone,
  selectProductandZone,
} from "../utils/constants"
import Message from "../utils/Message"

class ApplyPromotionInZone extends Component {
  constructor(props) {
    super(props)

    this.state = {
      productName: "",
      zone: "",
      assignedPriceState: "",
      // status: 0
    }
    this.handleChangeProduct = this.handleChangeProduct.bind(this)
    this.handleChangeZone = this.handleChangeZone.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getProductList: getProductListAlt,
      clearAssignedPrice: clearAssignedPriceAlt,
    } = this.props
    const { assignedPriceState } = this.state
    getProductListAlt()
    clearAssignedPriceAlt(assignedPriceState)
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
    const {
      zone,
      productName,
      checkAssignedZone: checkAssignedZoneAlt,
    } = this.props
    checkAssignedZoneAlt(productName, zone)
  }

  render() {
    const { productZoneList, products, assignedPrice, history } = this.props
    const { productName, zone } = this.state
    return (
      <>
        {assignedPrice !== "" ? (
          history.push("/definepromotion/zone")
        ) : (
          <div className="box-container">
            <div className="joint-form">
              <div className="validation-half">
                <div className="validations">
                  <h3 className="center-h3">Requirements</h3>

                  {productName === "" && (
                    <div className="unapproved-text">
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
                  {zone === "" && (
                    <div className="unapproved-text">
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
                      {selectProductandZone}
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
                      id="apply-promotion-zone-submit"
                    >
                      Go
                    </Button>
                  )}
                </form>
              </div>
              <Message />
            </div>
          </div>
        )}
      </>
    )
  }
}

ApplyPromotionInZone.propTypes = {
  products: PropTypes.arrayOf.isRequired,
  zones: PropTypes.arrayOf.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  saveProductValue: PropTypes.func.isRequired,
  getProductList: PropTypes.func.isRequired,
  saveZoneValue: PropTypes.func.isRequired,
  zone: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  history: PropTypes.shape.isRequired,
  assignedPrice: PropTypes.string.isRequired,
  clearAssignedPrice: PropTypes.func.isRequired,
  checkAssignedZone: PropTypes.func.isRequired,
  productZoneList: PropTypes.arrayOf.isRequired,
  getZonesForProduct: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  products: store.RetailerReducer.productList,
  zone: store.RetailerReducer.zone,
  productName: store.RetailerReducer.productName,
  assignedPrice: store.RetailerReducer.assignedPrice,
  productZoneList: store.RetailerReducer.productZoneList,
})
const actionAsProps = {
  getProductList,
  saveProductValue,
  getProductDetails,
  saveZoneValue,
  checkAssignedZone,
  clearAssignedPrice,
  getZonesForProduct,
}
export default connect(stateAsProps, actionAsProps)(ApplyPromotionInZone)
