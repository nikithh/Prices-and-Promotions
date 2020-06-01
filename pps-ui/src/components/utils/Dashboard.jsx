import React, { Component } from "react"
import Chart from "react-apexcharts"
import Typography from "@material-ui/core/Typography"
import AddBoxOutlinedIcon from "@material-ui/icons/AddBoxOutlined"
import { IconButton } from "@material-ui/core"
import { Link } from "react-router-dom"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { getDashboardData } from "../../redux/actions/RetailerActions"

class Dashboard extends Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  componentDidMount() {
    const { getDashboardData: getDashboardDataAlt } = this.props
    getDashboardDataAlt()
  }

  render() {
    const { dashboardData } = this.props
    const series = [
      {
        name: "Baby Products",
        data: dashboardData.noOfBabyProductsPerZone,
      },
      {
        name: "Alcohol Products",
        data: dashboardData.noOfAlcoholProductsPerZone,
      },
      {
        name: "Active Promotions",
        data: dashboardData.totalNoOfActivePromotions,
      },
    ]

    const series1 = [
      dashboardData.totalNoOfAlcoholProducts,
      dashboardData.totalNoOfBabyProducts,
    ]

    const options = {
      chart: {
        type: "bar",
        height: 350,
      },
      plotOptions: {
        bar: {
          horizontal: false,
        },
      },
      dataLabels: {
        enabled: false,
      },
      stroke: {
        show: true,
        width: 2,
        colors: ["transparent"],
      },
      responsive: [
        {
          breakpoint: 480,
          options: {
            legend: {
              position: "bottom",
              offsetX: -20,
              offsetY: 0,
            },
          },
        },
      ],
      xaxis: {
        categories: dashboardData.ZoneNames,
        show: true,
        rotateAlways: false,
        hideOverlappingLabels: true,
        trim: true,
      },
      labels: ["Alcohol Products", "Baby Products"],
      tooltip: {
        enabled: true,
        enabledOnSeries: undefined,
        shared: true,
        followCursor: false,
        intersect: false,
        inverseOrder: false,
        custom: undefined,
        fillSeriesColor: true,
        theme: true,
        style: {
          fontSize: "12px",
          fontFamily: "Calibri",
        },
        onDatasetHover: {
          highlightDataSeries: true,
        },
        marker: {
          show: true,
        },
      },
    }

    return (
      <div className="box-container">
        <div className="joint-products-form" id="dashboard-box-container">
          <div className="dashboard-top-half">
            <div className="advanced-card-container">
              <div className="card-container">
                <Typography gutterBottom>Number of Zones</Typography>
                <Typography className="subtitle1" variant="h1">
                  {dashboardData.NoOfZones}
                </Typography>
                <div className="dashboard-button">
                  <Link to="/zone">
                    <IconButton color="primary">
                      <AddBoxOutlinedIcon />
                    </IconButton>
                  </Link>
                </div>
              </div>
              <div className="card-container">
                <Typography gutterBottom>Number of Clusters</Typography>
                <Typography className="subtitle1" variant="h1">
                  {dashboardData.NoOfClusters}
                </Typography>
                <div className="dashboard-button">
                  <Link to="/cluster">
                    <IconButton color="primary">
                      <AddBoxOutlinedIcon />
                    </IconButton>
                  </Link>
                </div>
              </div>
              <div className="card-container">
                <Typography gutterBottom>Number of Vendors</Typography>
                <Typography className="subtitle1" variant="h1">
                  {dashboardData.NoOfVendors}
                </Typography>
              </div>
              <div className="card-container">
                <Typography gutterBottom>Number of Products</Typography>
                <Typography className="subtitle1" variant="h1">
                  {dashboardData.NoOfProducts}
                </Typography>
                <div className="dashboard-button">
                  <Link to="/addproducts">
                    <IconButton color="primary">
                      <AddBoxOutlinedIcon />
                    </IconButton>
                  </Link>
                </div>
              </div>
            </div>
          </div>
          <div className="dashboard-bottom-half">
            <div className="advanced-chart-container">
              <div className="app">
                <div className="row">
                  <div className="mixed-chart">
                    <Chart
                      options={options}
                      series={series}
                      type="bar"
                      width="500"
                    />
                  </div>
                </div>
              </div>
              <div className="donut">
                <Chart
                  options={options}
                  series={series1}
                  type="donut"
                  width="400"
                />
              </div>
            </div>
          </div>
        </div>
        <div className="joint-products-form" id="iframe-container">
          <iframe
            title="Kibana Dashboard"
            src="https://compute.price-and-promotions.com:5601/goto/16eb54fa4f860d8732ed3821ff31ac62?embed=true"
            height="630"
            width="960"
          />
        </div>
      </div>
    )
  }
}
Dashboard.propTypes = {
  getDashboardData: PropTypes.func.isRequired,
  dashboardData: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  dashboardData: store.RetailerReducer.dashboardData,
})

const actionAsProps = {
  getDashboardData,
}

export default connect(stateAsProps, actionAsProps)(Dashboard)
