/* eslint-disable no-nested-ternary */
import {
  Table,
  Typography,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Button,
  IconButton,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
// eslint-disable-next-line no-unused-vars
import Alert from "@material-ui/lab/Alert"
import CloseIcon from "@material-ui/icons/Close"
import PropTypes from "prop-types"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import {
  getProductDetails,
  cancelPromotion,
} from "../../redux/actions/RetailerActions"
import {
  cancelProductPromotion,
  promotions,
  cancelPromotionConst,
} from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"
import Message from "../utils/Message"

class CancelProductPromotion extends Component {
  constructor(props) {
    super(props)

    this.state = {
      date: new Date().toISOString().slice(0, 10),
      levelOption: "zone",
      cancelStatus: 1,
    }

    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { getProductDetails: getProductDetailsAlt, productName } = this.props
    getProductDetailsAlt(productName)
  }

  handleSubmit() {
    const {
      cancelPromotion: cancelPromotionAlt,
      zone,
      productName,
    } = this.props
    const { date, levelOption } = this.state
    const details = {
      zoneName: zone,
      date,
    }
    cancelPromotionAlt(details, productName, levelOption)
    this.setState({ cancelStatus: 0 })
  }

  render() {
    const { productDetails } = this.props
    const { cancelStatus } = this.state
    const zoneData = productDetails.assignProduct
    const tableRowElm = (zone) => {
      return zone.promotions.map(
        (promotion) =>
          promotion.cancelledDate === null && (
            <TableRow key={promotion.promotionId}>
              <TableCell>
                <Typography variant="subtitle1" gutterBottom>
                  {promotion.promotionPercentage}
                </Typography>
              </TableCell>
              <TableCell>
                <Typography variant="subtitle1" gutterBottom>
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
                </Typography>
              </TableCell>
              <TableCell>
                <Typography variant="subtitle1" gutterBottom>
                  {promotion.startDate.slice(0, 10)}
                </Typography>
              </TableCell>
              <TableCell>
                <Typography variant="subtitle1" gutterBottom>
                  {promotion.endDate.slice(0, 10)}
                </Typography>
              </TableCell>
            </TableRow>
          )
      )
    }

    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              {productDetails.assignProduct.length <= 0 && (
                <Alert
                  severity="info"
                  action={
                    <IconButton aria-label="close" color="inherit" size="small">
                      <CloseIcon fontSize="inherit" />
                    </IconButton>
                  }
                >
                  Sorry No Promotions are applied on this Product:{" "}
                  {productDetails.productName}
                </Alert>
              )}

              <Typography className="card-header" variant="h4">
                {cancelPromotionConst}
              </Typography>
              <ProductDetailsTable />

              {cancelStatus ? (
                <>
                  <Typography className="card-header" variant="h5">
                    {promotions}
                  </Typography>

                  <TableContainer component={Paper}>
                    <Table aria-label="a dense table">
                      <TableHead>
                        <TableRow>
                          {cancelProductPromotion.map((tcell) => (
                            <TableCell>{tcell}</TableCell>
                          ))}
                        </TableRow>
                      </TableHead>
                      <tbody>{zoneData.map((zone) => tableRowElm(zone))}</tbody>
                    </Table>
                  </TableContainer>

                  <Typography variant="subtitle1" gutterBottom>
                    <Button
                      type="button"
                      fullWidth
                      variant="contained"
                      color="primary"
                      className="form-button {classes.submit}"
                      onClick={(e) => {
                        // eslint-disable-next-line no-alert
                        if (window.confirm("Are you sure you wish to cancel?"))
                          this.handleSubmit(e)
                      }}
                    >
                      Cancel Promotion
                    </Button>
                  </Typography>
                </>
              ) : null}
            </div>
          </div>
        </div>
        <Message />
      </div>
    )
  }
}

CancelProductPromotion.propTypes = {
  zone: PropTypes.string.isRequired,
  productDetails: PropTypes.shape.isRequired,
  productName: PropTypes.string.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  cancelPromotion: PropTypes.func.isRequired,
}
const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
  products: store.RetailerReducer.products,
  zone: store.RetailerReducer.zone,
})
const actionAsProps = {
  getProductDetails,
  cancelPromotion,
}
export default connect(stateAsProps, actionAsProps)(CancelProductPromotion)
