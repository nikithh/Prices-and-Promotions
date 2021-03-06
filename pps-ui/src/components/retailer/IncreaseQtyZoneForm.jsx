import { TextField, Typography, Button } from "@material-ui/core"
import React, { Component } from "react"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import { connect } from "react-redux"
import PropTypes from "prop-types"
// import { spacing } from "@material-ui/system"
import {
  getZoneQuantity,
  updateQuantity,
} from "../../redux/actions/RetailerActions"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import { zonequantity } from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"

class IncreaseQtyZoneForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      zoneQuantity: "",
      levelOption: "zone",
      increaseQtyZone: { zoneName: "", quantityAssigned: "" },
    }

    this.handleChangeQuantity = this.handleChangeQuantity.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      productName,
      zone,
      getZoneQuantity: getZoneQuantityAlt,
    } = this.props
    getZoneQuantityAlt(productName, zone)
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const {
      productName,
      zone,
      updateQuantity: updateQuantityAlt,
      history,
    } = this.props
    const { zoneQuantity, levelOption, increaseQtyZone } = this.state
    increaseQtyZone.zoneName = zone
    increaseQtyZone.quantityAssigned = zoneQuantity
    updateQuantityAlt(increaseQtyZone, productName, levelOption)
    history.push("/view/assigned/zones")
  }

  handleChangeQuantity(e) {
    this.setState({
      zoneQuantity: e.target.value,
    })
  }

  render() {
    const { zoneQuantity } = this.state
    const {
      zone,
      assignedPrice,
      quantityAssignedAtZone,
      productDetails,
    } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="store-requirement">
            <h3 className="center-h3">Requirements</h3>
            {(zoneQuantity >= productDetails.remainingQuantity ||
              zoneQuantity <= 0) && (
              <div className="approved-text">
                <ClearIcon className="icon-style" />
                <Typography variant="subtitle2" gutterBottom>
                  {zonequantity}
                </Typography>
              </div>
            )}

            {zoneQuantity > 0 &&
              zoneQuantity < productDetails.remainingQuantity && (
                <div className="unapproved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {zonequantity}
                  </Typography>
                </div>
              )}
          </div>

          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                Increase Quantity in Zone
              </Typography>
              <ProductDetailsTable />
              <Typography className="card-header" variant="h6">
                Selected Zone : {zone}
              </Typography>
              <Typography className="card-header" variant="h6">
                Actual Price :{" "}
                {sessionStorage.getItem("currency") === "USD"
                  ? `$ ${assignedPrice}`
                  : convertCurrency(
                      "USD",
                      sessionStorage.getItem("currency"),
                      assignedPrice
                    )}
              </Typography>
              <Typography className="card-header" variant="h6">
                Quantity Assigned : {quantityAssignedAtZone}
              </Typography>
              <TextField
                variant="outlined"
                margin="normal"
                defaultValue=""
                required
                fullWidth="false"
                type="number"
                step="0.01"
                id="zonequantity"
                label="Add Quantity"
                name="zonequantity"
                autoComplete="zonequantity"
                onChange={this.handleChangeQuantity}
                InputProps={{
                  inputProps: {
                    max: productDetails.remainingQuantity,
                    min: 1,
                  },
                }}
                autoFocus
              />
              {zoneQuantity < productDetails.remainingQuantity &&
                zoneQuantity > 0 && (
                  <Button
                    halfWidth
                    type="button"
                    variant="contained"
                    color="primary"
                    className="{classes.submit}"
                    onClick={this.handleSubmit}
                    style={{ marginTop: "10px" }}
                    id="add-zone-quantity"
                  >
                    Add Quantity to Zone
                  </Button>
                )}
            </div>
          </div>
        </div>
      </div>
    )
  }
}

IncreaseQtyZoneForm.propTypes = {
  productName: PropTypes.string.isRequired,
  zone: PropTypes.string.isRequired,
  history: PropTypes.shape.isRequired,
  assignedPrice: PropTypes.string.isRequired,
  getZoneQuantity: PropTypes.func.isRequired,
  quantityAssignedAtZone: PropTypes.shape.isRequired,
  productDetails: PropTypes.shape.isRequired,
  updateQuantity: PropTypes.func.isRequired,
}
const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
  assignedPrice: store.RetailerReducer.assignedPrice,
  loggedInUser: store.RetailerReducer.loggedInUser,
  promotionAlert: store.AdminReducer.promotionAlert,
  quantityAssignedAtZone: store.RetailerReducer.quantityAssignedAtZone,
})

const actionAsProps = {
  getZoneQuantity,
  updateQuantity,
}
export default connect(stateAsProps, actionAsProps)(IncreaseQtyZoneForm)
