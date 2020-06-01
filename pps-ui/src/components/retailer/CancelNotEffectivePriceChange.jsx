import React, { Component } from "react"
import { connect } from "react-redux"
import { Typography } from "@material-ui/core"
import PropTypes from "prop-types"
import { getNotEffecticePriceChangeProducts } from "../../redux/actions/RetailerActions"
import PriceChangeProductDetailsTable from "../utils/PriceChangeProductDetailsTable"
import { cancelNotEffectivePriceChange } from "../utils/constants"

class CancelNotEffectivePriceChange extends Component {
  constructor(props) {
    super(props)

    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getNotEffecticePriceChangeProducts: getNotEffecticePriceChangeProductsAlt,
    } = this.props
    getNotEffecticePriceChangeProductsAlt()
  }

  render() {
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {cancelNotEffectivePriceChange}
              </Typography>
              <PriceChangeProductDetailsTable />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

CancelNotEffectivePriceChange.propTypes = {
  getNotEffecticePriceChangeProducts: PropTypes.func.isRequired,
}

const actionAsProps = {
  getNotEffecticePriceChangeProducts,
}

export default connect(null, actionAsProps)(CancelNotEffectivePriceChange)
