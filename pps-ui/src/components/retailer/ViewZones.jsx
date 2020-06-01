import {
  Table,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  TableBody,
  Typography,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { getZoneList } from "../../redux/actions/RetailerActions"
import { viewZones, viewZonesConst } from "../utils/constants"

class ViewZones extends Component {
  constructor(props) {
    super(props)
    const { getZoneList: getZoneListAlt } = this.props
    getZoneListAlt()

    this.state = {
      page: 0,
      rowsPerPage: 10,
    }
  }

  handleChangePage = (event, newPage) => {
    this.setState({ page: +newPage })
  }

  handleChangeRowsPerPage = (event) => {
    this.setState({ rowsPerPage: +event.target.value })
  }

  render() {
    const { zoneList } = this.props
    const { page, rowsPerPage } = this.state
    const classes = this.props
    const rows = zoneList
    return (
      <div className="box-container-table data-tables">
        <div className="flex-grid">
          <Typography className="card-header" variant="h4">
            {viewZonesConst}
          </Typography>
          <Paper className={classes.root}>
            <TableContainer className={classes.container}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    {/* <TableCell>Zone Name</TableCell>
                  <TableCell>Number of Zones</TableCell> */}
                    {viewZones.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {Object.keys(rows)
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      return (
                        <TableRow
                          hover
                          role="checkbox"
                          tabIndex={-1}
                          key={row.code}
                        >
                          <TableCell>{row}</TableCell>
                          <TableCell>{rows[row]}</TableCell>
                        </TableRow>
                      )
                    })}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={[10, 25, 100]}
              component="div"
              count={Object.keys(rows).length}
              rowsPerPage={rowsPerPage}
              page={page}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.handleChangeRowsPerPage}
            />
          </Paper>
        </div>
      </div>
    )
  }
}
ViewZones.propTypes = {
  getZoneList: PropTypes.func.isRequired,
  zoneList: PropTypes.shape.isRequired,
  root: PropTypes.shape.isRequired,
  container: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  zoneList: store.RetailerReducer.zoneList,
})
const actionAsProps = {
  getZoneList,
}
export default connect(stateAsProps, actionAsProps)(ViewZones)
