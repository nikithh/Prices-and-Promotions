import { Typography, Button, Snackbar, TextField } from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import MuiAlert from "@material-ui/lab/Alert"
import PropTypes from "prop-types"
import ProductDetails from "../utils/ProductDetails"
import {
  saveFromDate,
  saveToDate,
  saveEffectivePercentage,
  getEffectivePrice,
  getProductDetails,
} from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"

class AssignPriceToProduct extends Component {
  constructor(props) {
    super(props)
    const newDate = new Date()
    this.state = {
      startDate: "",
      endDate: "",
      effectivePercentage: "",
      parameter: {},
      day: newDate.getDate(),
      saveStatus: 1,
    }
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {}

  handleChange1 = (e) => {
    this.setState({ startDate: e.target.value })
  }

  handleChange2 = (e) => {
    this.setState({ endDate: e.target.value })
  }

  handleChange3 = (e) => {
    this.setState({ effectivePercentage: e.target.value })
  }

  handleSubmit = () => {
    const {
      saveFromDate: saveFromDateAlt,
      saveToDate: saveToDateAlt,
      saveEffectivePercentage: saveEffectivePercentageAlt,
      getEffectivePrice: getEffectivePriceAlt,
      productDetails,
      getProductDetails: getProductDetailsAlt,
    } = this.props
    const { startDate, endDate, effectivePercentage, parameter } = this.state
    saveEffectivePercentageAlt(effectivePercentage)
    saveFromDateAlt(startDate)
    saveToDateAlt(endDate)
    parameter.startDate = startDate
    parameter.endDate = endDate
    parameter.effectivePercentage = effectivePercentage
    getEffectivePriceAlt(parameter, productDetails.productName)
    getProductDetailsAlt(productDetails.productName)
  }

  render() {
    const {
      startDate,
      endDate,
      effectivePercentage,
      day,
      saveStatus,
    } = this.state
    const dayval = startDate.slice(8, endDate.length)
    return (
      <div className="box-container">
        <div className="joint-form-large">
          {saveStatus ? (
            <>
              <ProductDetails />
              <div className="product-form-body-padding">
                <Typography className="card-header" variant="h4">
                  Assign Price To Product
                </Typography>
                <form className="{classes.form}" noValidate>
                  <TextField
                    id="startDate-query"
                    label="From Date"
                    name="fromDate"
                    fullWidth
                    type="date"
                    variant="outlined"
                    margin="normal"
                    autoComplete="fromDate"
                    required
                    onChange={this.handleChange1}
                    autoFocus
                    InputLabelProps={{ shrink: true, required: true }}
                  />
                  <TextField
                    id="endDate-query"
                    label="To Date"
                    name="toDate"
                    fullWidth
                    type="date"
                    variant="outlined"
                    margin="normal"
                    autoComplete="toDate"
                    required
                    onChange={this.handleChange2}
                    InputLabelProps={{ shrink: true, required: true }}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="ProfitPercentage"
                    label="Percentage"
                    name="ProfitPercentage"
                    type="number"
                    onChange={this.handleChange3}
                  />
                  {startDate.length > 0 &&
                    endDate.length > 0 &&
                    effectivePercentage !== "" &&
                    startDate < endDate && (
                      // dayval >= day &&
                      <Button
                        fullWidth
                        type="button"
                        variant="contained"
                        color="primary"
                        className="form-button {classes.submit} submit-pad"
                        onClick={this.handleSubmit}
                        id="assign-cluster-submit"
                      >
                        Save
                      </Button>
                    )}
                </form>
              </div>
            </>
          ) : (
            <>
              <ProductDetails />
              <div className="product-form-body">
                <Typography className="card-header" variant="h4">
                  Assign Price To Product
                </Typography>
                <form className="{classes.form}" noValidate>
                  <TextField
                    id="startDate-query"
                    label="From Date"
                    name="fromDate"
                    fullWidth
                    type="date"
                    variant="outlined"
                    margin="normal"
                    autoComplete="fromDate"
                    required
                    onChange={this.handleChange1}
                    autoFocus
                    InputLabelProps={{ shrink: true, required: true }}
                  />
                  <TextField
                    id="startDate-query"
                    label="To Date"
                    name="toDate"
                    fullWidth
                    type="date"
                    variant="outlined"
                    margin="normal"
                    autoComplete="toDate"
                    required
                    onChange={this.handleChange2}
                    autoFocus
                    InputLabelProps={{ shrink: true, required: true }}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="ProfitPercentage"
                    label="ProfitPercentage"
                    name="ProfitPercentage"
                    type="number"
                    onChange={this.handleChange3}
                    autoFocus
                  />
                  <null>
                    {dayval < day && (
                      <div>
                        <Snackbar open="true" autoHideDuration={2000}>
                          <MuiAlert
                            severity="error"
                            elevation={6}
                            variant="filled"
                          >
                            Enter Valid Start Date
                          </MuiAlert>
                        </Snackbar>
                      </div>
                    )}
                  </null>
                  {startDate.length > 0 &&
                    endDate.length > 0 &&
                    effectivePercentage !== "" &&
                    startDate < endDate && (
                      // dayval >= day &&
                      <Button
                        fullWidth
                        type="button"
                        variant="contained"
                        color="primary"
                        className="form-button {classes.submit} submit-pad"
                        onClick={this.handleSubmit}
                        id="assign-cluster-submit"
                      >
                        Save
                      </Button>
                    )}
                </form>
              </div>
            </>
          )}
        </div>
        <Message />
      </div>
    )
  }
}

AssignPriceToProduct.propTypes = {
  getEffectivePrice: PropTypes.func.isRequired,
  saveEffectivePercentage: PropTypes.func.isRequired,
  saveFromDate: PropTypes.func.isRequired,
  saveToDate: PropTypes.func.isRequired,
  productDetails: PropTypes.shape.isRequired,
  getProductDetails: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
})

const actionAsProps = {
  saveFromDate,
  saveToDate,
  saveEffectivePercentage,
  getEffectivePrice,
  getProductDetails,
}

export default connect(stateAsProps, actionAsProps)(AssignPriceToProduct)
