import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { Typography } from "@material-ui/core"
import { getEffecticePriceChangeProducts } from "../../redux/actions/RetailerActions"
import PriceChangeProductDetailsTable from "../utils/PriceChangeProductDetailsTable"
import { cancelEffectivePriceChange } from "../utils/constants"

class CancelEffectivePriceChange extends Component {
  constructor(props) {
    super(props)

    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getEffecticePriceChangeProducts: getEffecticePriceChangeProductsAlt,
    } = this.props
    getEffecticePriceChangeProductsAlt()
  }

  render() {
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {cancelEffectivePriceChange}
              </Typography>
              <PriceChangeProductDetailsTable />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

CancelEffectivePriceChange.propTypes = {
  getEffecticePriceChangeProducts: PropTypes.func.isRequired,
}

const actionAsProps = {
  getEffecticePriceChangeProducts,
}

export default connect(null, actionAsProps)(CancelEffectivePriceChange)
