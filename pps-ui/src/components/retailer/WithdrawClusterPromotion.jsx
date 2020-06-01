/* eslint-disable no-nested-ternary */
import {
  Typography,
  Paper,
  Button,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Table,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getPromotionsIncluster,
  withdrawPromotion,
} from "../../redux/actions/RetailerActions"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import {
  withdrawClusterPromotion,
  clusterPromotion,
  withdrawPromotionClusterConst,
} from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"
import Message from "../utils/Message"

class WithdrawClusterPromotion extends Component {
  constructor(props) {
    super(props)

    this.state = {
      date: new Date().toISOString().slice(0, 10),
      levelOption: "cluster",
    }
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      productName,
      zone,
      cluster,
      getPromotionsIncluster: getPromotionsInclusterAlt,
    } = this.props
    getPromotionsInclusterAlt(productName, zone, cluster)
  }

  handleSubmit = (e, promoId) => {
    const {
      productName,
      zone,
      cluster,
      withdrawPromotion: withdrawPromotionAlt,
      history,
    } = this.props
    const { levelOption, date } = this.state
    const details = {
      zoneName: zone,
      date,
      clusterName: cluster,
    }
    withdrawPromotionAlt(details, productName, levelOption, promoId)
    history.push("/withdraw/clusterproduct")
    document
      .getElementById("withdraw-tbody")
      .removeChild(document.getElementById(`row${promoId}`))
  }

  render() {
    const { clusterPromotions, productDetails } = this.props
    const { date } = this.state
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {withdrawPromotionClusterConst}
              </Typography>
              <ProductDetailsTable />
              <Typography className="card-header" variant="h6">
                {clusterPromotion}
              </Typography>

              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      {withdrawClusterPromotion.map((tcell) => (
                        <TableCell>{tcell}</TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <tbody id="withdraw-tbody">
                    {clusterPromotions.map(
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

WithdrawClusterPromotion.propTypes = {
  productDetails: PropTypes.shape.isRequired,
  productName: PropTypes.string.isRequired,
  zone: PropTypes.string.isRequired,
  cluster: PropTypes.string.isRequired,
  clusterPromotions: PropTypes.shape.isRequired,
  history: PropTypes.shape.isRequired,

  withdrawPromotion: PropTypes.func.isRequired,
  getPromotionsIncluster: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
  cluster: store.RetailerReducer.cluster,
  clusterPromotions: store.RetailerReducer.clusterPromotions,
})

const actionAsProps = {
  withdrawPromotion,
  getPromotionsIncluster,
}
export default connect(stateAsProps, actionAsProps)(WithdrawClusterPromotion)
