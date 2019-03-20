import React from 'react'
import HighchartsReact from 'highcharts-react-official'
import Highcharts from 'highcharts/highstock'

require('highcharts/indicators/indicators')(Highcharts)
require('highcharts/indicators/pivot-points')(Highcharts)
require('highcharts/indicators/ema')(Highcharts)
require('highcharts/indicators/macd')(Highcharts)
require('highcharts/modules/exporting')(Highcharts)

const stockOptions = {
  yAxis: [{
    height: '75%',
    labels: {
      align: 'right',
      x: -3
    },
    title: {
      text: 'Price'
    }
  }, {
    top: '75%',
    height: '25%',
    labels: {
      align: 'right',
      x: -3
    },
    offset: 0,
    title: {
      text: 'MACD'
    }
  }],
  series: [ ]
}

class Chart extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
    var stock = this.props.stock;
    var data = stock.historicalPrices.map(function(price) {
        return  [Date.parse(price.date),
                   price.closing,
                   price.opening,
                   price.daysHigh,
                   price.daysLow]});

//TODO: improve this
    stockOptions.series = [];
    stockOptions.series.push(
    {
        data: data,
        type: 'ohlc',
        name: stock.companyName + 'Stock Price',
        id: stock.symbol
    });

    stockOptions.series.push(
    {
        type: 'pivotpoints',
        linkedTo: stock.symbol,
        zIndex: 0,
        lineWidth: 1,
        dataLabels: {
          overflow: 'none',
          crop: false,
          y: 4,
          style: {
            fontSize: 9
          }
        }
     });

    stockOptions.series.push(
    {
        type: 'macd',
        yAxis: 1,
        linkedTo: stock.symbol
    });

    const shouldDisplayPrices = stock.historicalPrices.length>0;
    if (shouldDisplayPrices) {
        return (
            <div>
              <HighchartsReact
                highcharts={Highcharts}
                constructorType={'stockChart'}
                options={stockOptions}
              />
            </div>
        )
    } else {
        return (<div className="noHistoricalPrices"/>)
        }
    }
}

export default Chart;