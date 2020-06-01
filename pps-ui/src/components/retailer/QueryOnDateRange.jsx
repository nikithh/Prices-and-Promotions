import {
  Typography,
  Button,
  TextField,
  Radio,
  RadioGroup,
  FormControlLabel,
  FormControl,
} from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getPromotionsInRange,
  saveLevelValue,
  saveStartDate,
  saveEndDate,
} from "../../redux/actions/RetailerActions"
import {
  startdate,
  enddate,
  datecheck,
  selectLevel,
  queryPromotionsForProducts,
} from "../utils/constants"

class QueryOnDateRange extends Component {
  constructor(props) {
    super(props)
    this.state = {
      startDate: "",
      endDate: "",
      levelOption: "",
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleSubmit = () => {
    const {
      saveLevelValue: saveLevelValueAlt,
      saveStartDate: saveStartDateAlt,
      saveEndDate: saveEndDateAlt,
      getPromotionsInRange: getPromotionsInRangeAlt,
      history,
    } = this.props
    const { startDate, endDate, levelOption } = this.state
    saveLevelValueAlt(levelOption)
    saveStartDateAlt(startDate)
    saveEndDateAlt(endDate)
    getPromotionsInRangeAlt(startDate, endDate, levelOption)
    history.push("/view/promotions")
  }

  handleChange(e) {
    const { name, value } = e.target
    this.setState({ [name]: value })
  }

  render() {
    const { startDate, endDate, levelOption } = this.state
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3>Requirements</h3>
              {startDate.length === 0 && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {startdate}
                  </Typography>
                </div>
              )}
              {startDate.length !== 0 && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {startdate}
                  </Typography>
                </div>
              )}
              {endDate.length === 0 && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {enddate}
                  </Typography>
                </div>
              )}
              {endDate.length !== 0 && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {enddate}
                  </Typography>
                </div>
              )}
              {endDate <= startDate && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {datecheck}
                  </Typography>
                </div>
              )}
              {endDate > startDate && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {datecheck}
                  </Typography>
                </div>
              )}
              {levelOption === "" && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectLevel}
                  </Typography>
                </div>
              )}
              {levelOption !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {selectLevel}
                  </Typography>
                </div>
              )}
            </div>
          </div>
          <div className="form-half">
            <form className="{classes.form}" noValidate>
              <div className="help-block">
                <Typography color="primary" component="h1" variant="h4">
                  {queryPromotionsForProducts}
                </Typography>
              </div>
              <TextField
                id="startDate-query"
                label="Start Date"
                name="startDate"
                fullWidth
                value={startDate}
                type="date"
                variant="outlined"
                margin="normal"
                autoComplete="startDate"
                required
                onChange={this.handleChange}
                autoFocus
                InputLabelProps={{ shrink: true, required: true }}
              />
              <TextField
                id="endDate-query"
                name="endDate"
                label="End Date"
                fullWidth
                value={endDate}
                type="date"
                variant="outlined"
                margin="normal"
                autoComplete="endDate"
                required
                onChange={this.handleChange}
                InputLabelProps={{ shrink: true, required: true }}
              />

              <FormControl>
                <Typography variant="h6">Level Option</Typography>
                <RadioGroup
                  row
                  aria-label="condition"
                  name="levelOption"
                  value={levelOption}
                  onChange={this.handleChange}
                >
                  <FormControlLabel
                    value="zone"
                    control={<Radio />}
                    label="Zone"
                  />
                  <FormControlLabel
                    value="cluster"
                    control={<Radio />}
                    label="Cluster"
                  />
                </RadioGroup>
              </FormControl>

              {endDate > startDate && levelOption !== "" && (
                <Button
                  type="button"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className="{classes.submit}"
                  style={{
                    marginTop: "30px",
                    marginBottom: "30px",
                  }}
                  id="query-submit"
                  onClick={this.handleSubmit}
                >
                  Show Promotions
                </Button>
              )}
            </form>
          </div>
        </div>
      </div>
    )
  }
}

QueryOnDateRange.propTypes = {
  getPromotionsInRange: PropTypes.func.isRequired,
  saveLevelValue: PropTypes.func.isRequired,
  saveStartDate: PropTypes.func.isRequired,
  saveEndDate: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}

const actionAsProps = {
  getPromotionsInRange,
  saveLevelValue,
  saveStartDate,
  saveEndDate,
}

export default connect(null, actionAsProps)(QueryOnDateRange)
