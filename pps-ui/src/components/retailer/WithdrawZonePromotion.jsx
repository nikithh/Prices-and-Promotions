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
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import {
  getPromotionsInzone,
  withdrawPromotion,
} from "../../redux/actions/RetailerActions"
import {
  withdrawZonePromotion,
  zoneLevelPromotions,
  withdrawPromotionZoneConst,
} from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"
import Message from "../utils/Message"

class WithdrawZonePromotion extends Component {
  constructor(props) {
    super(props)

    this.state = {
      date: new Date().toISOString().slice(0, 10),
      levelOption: "zone",
    }
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      productName,
      zone,
      getPromotionsInzone: getPromotionsInzoneAlt,
    } = this.props
    getPromotionsInzoneAlt(productName, zone)
  }

  handleSubmit = (e, promoId) => {
    const {
      zone,
      productName,
      withdrawPromotion: withdrawPromotionAlt,
    } = this.props
    const { date, levelOption } = this.state
    const details = {
      zoneName: zone,
      date,
    }
    withdrawPromotionAlt(details, productName, levelOption, promoId)
    document
      .getElementById("withdraw-tbody")
      .removeChild(document.getElementById(`row${promoId}`))
  }

  render() {
    const { zonePromotions, productDetails } = this.props
    const { date } = this.state
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {withdrawPromotionZoneConst}
              </Typography>
              <ProductDetailsTable />
              <Typography className="card-header" variant="h5">
                {zoneLevelPromotions}
              </Typography>
              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      {withdrawZonePromotion.map((tcell) => (
                        <TableCell>{tcell}</TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <tbody id="withdraw-tbody">
                    {zonePromotions.map(
                      (promotion) =>
                        promotion.withDrawnDate === null && (
                          <TableRow id={`row${promotion.promotionId}`}>
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
                                        productDetails.effectivePriceObj
                                          .effectivePrice
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
                            <TableCell>
                              <Typography variant="subtitle1" gutterBottom>
                                {promotion.startDate > date ? (
                                  <Button
                                    type="button"
                                    halfWidth
                                    variant="contained"
                                    color="primary"
                                    className="form-button {classes.submit}"
                                    onClick={(e) => {
                                      if (
                                        // eslint-disable-next-line no-alert
                                        window.confirm(
                                          "Are you sure you wish to withdraw the promotion?"
                                        )
                                      )
                                        this.handleSubmit(
                                          e,
                                          promotion.promotionId
                                        )
                                    }}
                                  >
                                    Withdraw
                                  </Button>
                                ) : (
                                  <Typography variant="subtitle1" gutterBottom>
                                    Active Promotion
                                  </Typography>
                                )}
                              </Typography>
                            </TableCell>
                          </TableRow>
                        )
                    )}
                  </tbody>
                </Table>
              </TableContainer>
            </div>
          </div>
        </div>
        <Message />
      </div>
    )
  }
}

WithdrawZonePromotion.propTypes = {
  productDetails: PropTypes.shape.isRequired,
  zonePromotions: PropTypes.shape.isRequired,
  productName: PropTypes.string.isRequired,
  zone: PropTypes.string.isRequired,
  getPromotionsInzone: PropTypes.shape.isRequired,
  withdrawPromotion: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  zonePromotions: store.RetailerReducer.zonePromotions,
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
})
const actionAsProps = {
  withdrawPromotion,
  getPromotionsInzone,
}
export default connect(stateAsProps, actionAsProps)(WithdrawZonePromotion)
